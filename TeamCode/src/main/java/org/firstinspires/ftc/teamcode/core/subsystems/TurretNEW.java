package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretNEW {
    public DcMotorEx turret;

    private LL LL;
    private PIDController heading_turret_pid;

        private Pose redPose = new Pose(137.20508166969148, 140.8638838475499, 0);
    private Pose bluePose = new Pose(7.056261343012704, 140.8638838475499,0);


    private Boolean isUpdating = true;

    private Telemetry telemetry;



///  Init
    public TurretNEW(HardwareMap hardwareMap, Telemetry telemetry) {
        turret = hardwareMap.get(DcMotorEx.class, turret_name);


        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        heading_turret_pid = new PIDController(turret_p, turret_i, turret_d);

        this.telemetry = telemetry;


       // LL = new LL(hardwareMap, telemetry);
    }



/// PID correction loop, toggle for manual
  public void turret_update(Pose followerPose, boolean isRED, double manual_power) {
        if(isUpdating) {
            heading_turret_pid.setPID(turret_p, turret_i, turret_d);
            turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            double angle = calculate_angle(followerPose, isRED);

            double complmentary_angle = calculate_turret_angle_complementary(angle, isRED);

            double offset = calculate_angle_offset(followerPose.getHeading(), complmentary_angle);

            double pid_output = heading_turret_pid.calculate(turret.getCurrentPosition(), offset);


            turret.setPower(pid_output + turret_f);
        } else {
            turret.setPower(manual_power); // add a multiplier if its too slow
        }

    }


    public void turret_update(double offset, double manual_power) {
        if(isUpdating) {
             heading_turret_pid.setPID(turret_p, turret_i, turret_d);
            turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            double pid_output = heading_turret_pid.calculate(turret.getCurrentPosition(), offset);


            turret.setPower(pid_output + turret_f);
        } else {
            turret.setPower(manual_power); // add a multiplier if its too slow
        }

    }

   // public void LL_update(int ID) {
  //      turret.setPower(LL.turret_pid_update(telemetry, turret, ID));
 //   }



    ///  manual controls
    public void turret_update_toggle() {
        isUpdating = !isUpdating;
    }
    public void reset_turret() {
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    ///  calculate
    ///  turret offset depending on robot change in heading



    public double calculate_X_distance(Pose followerPose, boolean isRED) {
        Pose correctPose;
        if(isRED) {
            correctPose = redPose;
        } else {
            correctPose = bluePose;
        }
        return Math.abs(correctPose.getX() - followerPose.getX());



    }
    public double calculate_Y_distance(Pose followerPose, boolean isRED) {
        Pose correctPose;
        if(isRED) {
            correctPose = redPose;
        } else {
            correctPose = bluePose;
        }
        return correctPose.getY() - followerPose.getY();
    }


    public double calculate_angle(Pose followerPose,boolean isRED) { ///  calculates best angle for turret
        double x_distance = calculate_X_distance(followerPose, isRED);
        double y_distance = calculate_Y_distance(followerPose, isRED);

        double angle = Math.toDegrees(Math.atan2(y_distance, x_distance));



        return angle;
    }

    public double calculate_turret_angle_complementary(double angle, boolean isRED) {
        double real_angle;
        double correct_distance_angle;
        if(isRED) {
             real_angle = 90 - angle - 4;
        } else {
            real_angle = 90 - angle -4;
        }
        if(angle > 50) {
            correct_distance_angle = real_angle - 5;
        } else {
            correct_distance_angle = real_angle;
        }
        double real_angle_position = correct_distance_angle; // takes the complementary angle
        if(isRED) {
            return real_angle_position;
        } else {
            return -real_angle_position;
        }
    }


    public double calculate_angle_offset(double robotHeading, double angle ) {

        double raw_angle_offset;
        double robot_heading = Math.toDegrees(robotHeading);
        double converted_robot_heading = robot_heading - 90;
        double correct_angle_offset;


        raw_angle_offset =  offset_to_position(converted_robot_heading +angle);
        if(raw_angle_offset <= left_limit) { // if it's bigger than left limit
            correct_angle_offset= left_limit;
        } else if(raw_angle_offset >= right_limit) {// if it's smaller than right limit
            correct_angle_offset = right_limit;
        } else {
            correct_angle_offset = raw_angle_offset;
        }

        return correct_angle_offset;
    }

    ///  Takes the offset in degrees and turn it into a motor encoder reading
    public double offset_to_position(double offset) {
        return offset / 360 * 3100;

    }
///  angle + 90 made it face the opposite direction aka our  human player xzone??
/// angle - 90 as a fix,
    public void turret_telemetry() {
        telemetry.addData("turret position", turret.getCurrentPosition());
    }


}
