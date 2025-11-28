package org.firstinspires.ftc.teamcode.test_files;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Turret;
import org.firstinspires.ftc.teamcode.core.subsystems.TurretNEW;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp (name = "turret test", group = "tests")
public class TurretTest extends OpMode {
    private TurretNEW turret;
    private Follower follower;
    private Pose startPoseRed = new Pose(118, 130,Math.toDegrees(38));


    @Override
    public void init() {
        turret = new TurretNEW(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPoseRed);
        follower.update();
    }

    @Override
    public void loop() {
        follower.setTeleOpDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true);
        follower.startTeleopDrive();
        follower.update();
        double distance = turret.calculate_distance(follower.getPose(), true);
        double angle = turret.calculate_angle(follower.getPose(), true);
        telemetry.addData("Follower Pose", follower.getPose());
        telemetry.addData("Distance", distance);
        telemetry.update();

    }
}
