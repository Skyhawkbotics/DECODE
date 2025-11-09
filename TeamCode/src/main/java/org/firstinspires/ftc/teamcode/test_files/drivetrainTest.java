package org.firstinspires.ftc.teamcode.test_files;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Drivetrain;

@TeleOp (name = "Drivetrain Test", group = "Tests")
public class drivetrainTest extends OpMode {
    private Pose startPose = new Pose(3, 3, Math.toRadians(90));
    private Drivetrain follower;
    @Override
    public void init() {
        follower = new Drivetrain(hardwareMap, telemetry, startPose);

    }

    @Override
    public void loop() {
        follower.ericMove(gamepad1);
        if(gamepad1.a) {
            follower.DRS();
        } else {
            follower.startDriving();
        }
        follower.update_follower();
        telemetry.update();

    }
}
