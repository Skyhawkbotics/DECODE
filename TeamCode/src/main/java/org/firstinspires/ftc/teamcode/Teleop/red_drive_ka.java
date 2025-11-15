package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;

@TeleOp(name = "Red Solo Drive Kristine Abraham", group = "Opmode")

public class red_drive_ka extends OpMode {
    private Robot robot;

    int ID = 24;
    public Pose startPose = new Pose(56   ,8, Math.toRadians(90));


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, startPose);
    }

    @Override
    public void loop() {
        robot.kristine_abraham_controls();
        robot.robot_update();
        robot.robot_telemetry();

    }
}
