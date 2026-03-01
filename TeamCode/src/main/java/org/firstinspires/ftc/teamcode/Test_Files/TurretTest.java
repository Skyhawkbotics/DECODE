package org.firstinspires.ftc.teamcode.Test_Files;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "Turret Test", group = "Z")
public class TurretTest extends OpMode {
    private Turret turret;

    private Follower follower;

    private int ID = 24;
    private Pose startPose = new Pose(72.13067150635209,79.44827586206897, Math.toRadians(90));
    @Override
    public void init() {
        turret = new Turret(hardwareMap, telemetry, ID);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.update();
    }

    @Override
    public void loop() {
        turret.set_using_LL(ID);
        if (gamepad1.x) {
            turret.turret_update(ID, follower.getPose(), 0);
        } else {
            turret.set_power(0);
        }

        if (gamepad1.yWasPressed()) {
            if (ID == 24) {
                ID = 20;
            } else if(ID == 20) {
                ID = 24;
            }
        }
        telemetry.addData("ID", ID);
        turret.turret_telemetry();
        telemetry.update();
        follower.update();
    }
}
