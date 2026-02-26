package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "FAR Red Drive", group = "Red Opmode")

public class red_drive_far extends OpMode {
    private Robot robot;

    private Telemetry t;
    int ID = 24;
    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));
    public Pose resetPose = new Pose(14.89655172413793, 15.157894736842088, Math.toRadians(90));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, t, gamepad1, gamepad2, ID, gametime, true, resetPose);

    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.dual_drive();
        robot.robot_telemetry();
    }
}