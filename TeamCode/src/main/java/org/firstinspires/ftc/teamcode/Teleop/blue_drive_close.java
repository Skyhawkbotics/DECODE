package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "CLOSE Blue Drive", group = "Opmode")

public class blue_drive_close extends OpMode {
    private Robot robot;
    int ID = 20;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public Pose pres  = new Pose(45.21234119782214, 108.19600725952813, Math.toRadians(143));

    public Pose resetPose = new Pose(128.58076225045372, 15.680580762250456, Math.toRadians(90));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, panelsTelemetry, gamepad1, gamepad2, ID, pres, false, resetPose);

    }

    @Override
    public void loop() {
        robot.robot_update();
        robot.solo_drive();
        robot.robot_telemetry();
    }
}
