package org.firstinspires.ftc.teamcode.Test_Files;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Tilt;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp (name = "Tilt", group = "A")
public class tiltDRIVE extends OpMode {
    // private Pose startPose = new Pose(3, 3, Math.toRadians(90));
    private Follower follower;
    private Tilt tilt;
    boolean parking = true;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.update();

        tilt = new Tilt(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

        follower.update();
        if(gamepad1.leftBumperWasPressed()) {
            parking =!parking;
        }
        if (parking) {
            double speedMultiplier = 0.3;
            follower.setTeleOpDrive(
                    speedMultiplier * -gamepad1.left_stick_y, speedMultiplier * -gamepad1.left_stick_x, speedMultiplier * -gamepad1.right_stick_x, true);
        } else {
            follower.setTeleOpDrive(0.8 * -gamepad1.left_stick_y, 0.4 * -gamepad1.left_stick_x, 0.5 * -gamepad1.right_stick_x, true );
        }
        if(gamepad1.triangle) {
            tilt.tiltREAL();
        } else {
            tilt.tiltZERO();
        }

    }
}
