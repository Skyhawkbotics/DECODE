package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "FAR Red Drive", group = "Opmode")

public class red_drive_far extends OpMode {
    private Robot robot;

    private Telemetry t;
    int ID = 24;
    private Pose startPoseRed = new Pose(106.03, 102.10,Math.toRadians(45));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, t, gamepad1, gamepad2, ID, startPoseRed, true, startPoseRed);

    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.dual_drive();
        robot.robot_telemetry();
    }
}