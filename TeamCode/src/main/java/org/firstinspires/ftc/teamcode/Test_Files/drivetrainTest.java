package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Drivetrain;

@TeleOp (name = "drivetrain test")
public class drivetrainTest extends OpMode {
    private Drivetrain dt;
    @Override
    public void init() {
        dt = new Drivetrain(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        dt.robotCentricPowers(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

    }
}
