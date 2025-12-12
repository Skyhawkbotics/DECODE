package org.firstinspires.ftc.teamcode.Teleop.oldTeleops;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "Blue Drive Old", group = "Opmode")

public class blue_drive_close extends OpMode {
    private Robot robot;
    int ID = 20;
    private Pose startPoseRed = new Pose(71, 24,Math.toRadians(90));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, startPoseRed, false);

    }

    @Override
    public void loop() {
        robot.robot_update();
        robot.solo_drive();
        robot.robot_telemetry();
    }
}
