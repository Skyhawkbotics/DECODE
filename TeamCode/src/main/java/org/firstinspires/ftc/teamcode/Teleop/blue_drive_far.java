package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "FAR Blue Drive", group = "Opmode")

public class blue_drive_far extends OpMode {
    private Robot robot;
    int ID = 20;
    private Pose startPoseRed = new Pose(21, 24,Math.toRadians(90));

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;


    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, panelsTelemetry, gamepad1, gamepad2, ID, startPoseRed, false, startPoseRed);

    }

    @Override
    public void loop() {
        robot.robot_update();
        robot.solo_drive();
        robot.robot_telemetry();
    }
}
