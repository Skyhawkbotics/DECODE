package org.firstinspires.ftc.teamcode.core.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Drivetrain {
    public Follower follower;
    public Telemetry telemetry;
    public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry, Pose startPose) {
        follower = Constants.createFollower(hardwareMap);
        this.telemetry = telemetry;
        follower.setStartingPose(startPose);

    }
    public void ericMove(Gamepad gamepad) {
        follower.setTeleOpDrive(0.8 * -gamepad.left_stick_y, 0.8 * -gamepad.left_stick_x, 0.5 * -gamepad.left_stick_x, false );
    }
    public void park_controls(Gamepad gamepad) {
        follower.setTeleOpDrive(0.3 * -gamepad.left_stick_y, 0.3 * -gamepad.left_stick_x, 0.5 * -gamepad.left_stick_x, false );
    }
    public void startDriving() {
        follower.startTeleopDrive();
    }
    public void DRS(Gamepad gamepad) {
        follower.setTeleOpDrive(1, 0.3 * -gamepad.left_stick_x, 0.5 * -gamepad.left_stick_x, true );

    }
    public void update_follower() {
        follower.update();
    }
    public void follower_telemetry() {
        telemetry.addData("robot pose ", follower.getPose());
    }


}
