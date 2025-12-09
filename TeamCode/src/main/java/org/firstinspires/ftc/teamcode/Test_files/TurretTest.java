package org.firstinspires.ftc.teamcode.Test_files;

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
    private Pose startPoseRed = new Pose(71, 24,0);

    private double offset;
    private double angle = 0;
    private boolean isred = true;

    @Override
    public void init() {
        turret = new TurretNEW(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPoseRed);
        follower.update();
    }

    @Override
    public void loop() {
        offset = turret.calculate_angle_offset(follower.getPose().getHeading(), angle);


        angle = turret.calculate_angle(follower.getPose(), isred);

        if(gamepad1.bWasPressed()) { // toggle alliance side
            isred = !isred;
        }

        if(gamepad1.aWasPressed()) {///  RESET
            turret.reset_turret();
        }
        if(gamepad1.xWasPressed()) {///  toggle the PID updating
            turret.turret_update_toggle();
        }

        turret.turret_update(offset, gamepad1.left_stick_x * 0.2);


        telemetry();
        telemetry.update();
        follower.update();
    }

    public void telemetry() { ///  TELEMETRY
        turret.turret_telemetry();
        telemetry.addData("offset", offset);
        telemetry.addData("angle", angle);
        telemetry.addData("Follower Pose", follower.getPose());
        telemetry.addData(" x = toggle update", 0);
        telemetry.addData(" a = reset turret", 0);

    }
}
