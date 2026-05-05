package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "TAO Solo Red Drive", group = "Red Opmode")

public class solo_drive extends OpMode {
    private Robot robot;
    int ID = 24;


    public final Pose gather2 = new Pose (125, 58, Math.toRadians(0));

    public Pose resetPose = new Pose(14.89655172413793, 15.157894736842088, Math.toRadians(90));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, gather2, true, resetPose);

    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.solo_drive();
        robot.robot_telemetry();
    }
}