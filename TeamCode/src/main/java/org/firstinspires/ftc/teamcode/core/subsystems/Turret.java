package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

///  Idea of turret: Two modes of correction
/// One using Limelight
/// One using the Heading of the robot vs the average heading of the depot
/// We can use the april tags to detect the depot then align using PID
/// We can use heading of the robot to then point to the depot knowing the depots direction
/// Example we want the turret to point forward no matter what direction we in
/// therefore the turret ideal pose is 0 degrees
/// However as the robot's heading changes we calculate the difference in headings
/// Example 50 degrees off (50)
/// The difference in headings is used to then calculate number of ticks it changed by
/// example 50 degrees is 148 ticks
/// then calculate the position it is now 0+148 = 148 ticks is the position
/// Move the turret to the position using a PID
/// pid.calculate(position, current position)
///
/// Then we add a way to use limelight as secondary correction?
/// maybe after a range like when the PID is outputting little to no correction
/// then we turn off the heading turret PID and then use the limelight PID
public class Turret {
    private LL limelight;
    public DcMotorEx turret;
    public Boolean usingHeadingCorrection;
    private PIDController heading_turret_pid;

    public Turret(HardwareMap hardwareMap, Telemetry telemetry, int ID) {
        limelight = new LL(hardwareMap, telemetry);
        turret = hardwareMap.get(DcMotorEx.class, turret_name);

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);

        heading_turret_pid = new PIDController(turret_p, turret_i, turret_d);
        ID = ID;
    }

    public double calculate_difference(Pose followerPose) {
        double current_heading = Math.toDegrees(followerPose.getHeading());
        double difference = current_heading - turret_align_heading;
        return 0 + (difference * degrees_to_ticks);
    }

    public double targetBlue() {
        return 20;
    }

    public double targetRed() {
        return 24;
    }
    public void manual_move(Gamepad gamepad) {
        limelight.LL_correction_OFF();
        usingHeadingCorrection = false;
        turret.setPower(gamepad.left_stick_y);
    }

    public void updateTurret(Telemetry telemetry, Pose followerPose, int ID) {

        telemetry.addData("Current ID", ID);

        if (usingHeadingCorrection) {
            limelight.LL_correction_OFF();
            heading_turret_pid.setPID(turret_p, turret_i, turret_d);
            turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            double pid_input = calculate_difference(followerPose);
            double output = heading_turret_pid.calculate(pid_input, turret.getCurrentPosition());
            turret.setPower(output);

        } else {
            limelight.LL_correction_ON();
            limelight.turret_pid_update(telemetry, turret, ID);
        }
    }
}
