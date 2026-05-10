package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "CLOSE Red Drive", group = "Red Opmode")

public class red_drive_close extends OpMode {
    private Robot robot;

    private ElapsedTime e;
    int ID = 24;


    public final Pose gather2 = new Pose (125, 58, Math.toRadians(0));

    public Pose resetPose = new Pose(14.89655172413793, 15.157894736842088, Math.toRadians(90));


    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, gather2, true, resetPose);
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