package org.firstinspires.ftc.teamcode.Test_Files;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "Turret Test")
public class TurretTest extends OpMode {
    private Turret turret;

    private Follower follower;
    private Pose startPose = new Pose(71.97724039829302,71.70412517780939, Math.toRadians(90));
    @Override
    public void init() {
        turret = new Turret(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.update();
    }

    @Override
    public void loop() {
        turret.set_using_LL(24);
        if (gamepad1.x) {
            turret.turret_update(24, follower.getPose());
        } else {
            turret.set_power(0);
        }
        turret.turret_telemetry();
        telemetry.update();
        follower.update();
    }
}
