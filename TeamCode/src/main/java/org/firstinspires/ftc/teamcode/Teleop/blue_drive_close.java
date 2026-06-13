package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "21 CLOSE Blue Drive", group = "Opmode")

public class blue_drive_close extends OpMode {
    private Robot robot;

    int ID = 20;



    private Pose resetPose = new Pose(128, 78, Math.toRadians(0));

    private Pose blueresetPose = resetPose.mirror();

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK
    public final Pose end = new Pose(92, 113, Math.toRadians(293));
    public final Pose blueend = end.mirror();

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, blueend, false, blueresetPose);

    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.dual_drive();
        robot.robot_telemetry();
    }
}
