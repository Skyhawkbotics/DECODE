package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ejml.equation.Operation;
import org.firstinspires.ftc.teamcode.core.Robot;

@TeleOp (name = "Blue Solo Drive Kristine Abraham", group = "Opmode")

public class blue_drive_ka extends OpMode {
    private Robot robot;

    int ID = 20;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, ID);

    }

    @Override
    public void loop() {
        robot.kristine_abraham_controls();
        robot.robot_update();
        robot.robot_telemetry();

    }
}
