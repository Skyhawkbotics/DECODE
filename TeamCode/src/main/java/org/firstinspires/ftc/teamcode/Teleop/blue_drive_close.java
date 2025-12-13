package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "CLOSE Blue Drive", group = "Opmode")

public class blue_drive_close extends OpMode {
    private Robot robot;
    int ID = 20;
    public Pose pres  = new Pose(45.21234119782214, 108.19600725952813, Math.toRadians(143));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, pres, false);

    }

    @Override
    public void loop() {
        robot.robot_update();
        robot.solo_drive();
        robot.robot_telemetry();
    }
}
