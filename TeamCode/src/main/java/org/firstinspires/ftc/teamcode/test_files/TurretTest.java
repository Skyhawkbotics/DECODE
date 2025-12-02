package org.firstinspires.ftc.teamcode.test_files;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.TurretNEW;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp (name = "turret test", group = "tests")
public class TurretTest extends OpMode {
    private TurretNEW turret;
    private Follower follower;
    private Pose startPoseRed = new Pose(118, 130,0);

    private double offset;


    @Override
    public void init() {
        turret = new TurretNEW(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPoseRed);
        follower.update();
    }

    @Override
    public void loop() {
        offset = turret.calculate_angle_offset(follower.getPose().getHeading(), 0);
        turret.turret_update(offset);

        if(gamepad1.aWasPressed()) {
            turret.reset_turret();
        }
        if(gamepad1.xWasPressed()) {
            turret.turret_update_toggle();
        }



        telemetry();
        telemetry.update();
        follower.update();
    }

    public void telemetry() { ///  TELEMETRY
        turret.turret_telemetry();
        telemetry.addData("offset", offset);
        //telemetry.addData("Follower Pose", follower.getPose());

    }
}
