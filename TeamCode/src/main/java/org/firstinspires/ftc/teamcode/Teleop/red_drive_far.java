package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "Red Drive Close", group = "Opmode")

public class red_drive_far extends OpMode {
    private Robot robot;
    int ID = 24;
    private Pose startPoseRed = new Pose(106.03, 102.10,Math.toRadians(45));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, startPoseRed, true);

    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.solo_drive();
        robot.robot_telemetry();
    }
}