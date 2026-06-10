package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.Robot;


@TeleOp (name = "18 CLOSE Blue Drive", group = "Blue Opmode 15")

public class blue_drive_close_18 extends OpMode {
    private Robot robot;

    int ID = 20;



    public Pose resetPose = new Pose(16, 78, Math.toRadians(180));

    ///  NEED TO EDIT STARTING POSE FOR DISTANCE FORMULA TO WORK
    private final Pose parkPose = new Pose(16, 78, Math.toRadians(180));


    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID, parkPose, false, resetPose);

    }

    @Override
    public void loop() {
        robot.robot_update();

        robot.dual_drive();
        robot.robot_telemetry();
    }
}
