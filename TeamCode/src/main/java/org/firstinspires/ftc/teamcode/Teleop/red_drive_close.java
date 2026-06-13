package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "21 CLOSE Red Drive", group = "Red Opmode")

public class red_drive_close extends OpMode {
    private Robot robot;

    private ElapsedTime e;
    int ID = 24;


    public final Pose startPose = new Pose (92, 113, Math.toRadians(293));

    private Pose resetPose = new Pose(128, 78, Math.toRadians(0));


    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, startPose, true, resetPose);
        e = new ElapsedTime();
        e.reset();
    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.dual_drive();
        robot.robot_telemetry();
        panelsTelemetry.getTelemetry().addData("loop time", e.milliseconds());
        panelsTelemetry.getTelemetry().update();
        telemetry.addData("loop time", e.milliseconds());
        e.reset();
    }
}