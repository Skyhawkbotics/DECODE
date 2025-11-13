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
        follower.setTeleOpDrive(0.3 * -gamepad.left_stick_y,-gamepad.left_stick_x, -gamepad.left_stick_x, false );
    }
    public void startDriving() {
        follower.startTeleopDrive();
    }
    public void DRS() {

    }
    public void update_follower() {
        follower.update();
    }
    public void follower_telemetry() {
        telemetry.addData("robot pose ", follower.getPose());
    }


}
