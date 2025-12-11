package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "Red Drive", group = "Opmode")

public class red_drive extends OpMode {
    private Robot robot;
    int ID = 24;
    private Pose startPoseRed = new Pose(71, 24,0);


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, startPoseRed, true);

    }

    @Override
    public void loop() {
        robot.solo_drive();
        robot.robot_update();
        robot.robot_telemetry();
    }
}