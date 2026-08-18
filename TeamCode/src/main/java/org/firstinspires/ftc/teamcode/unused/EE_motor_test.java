package org.firstinspires.ftc.teamcode.unused;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Single Motor Test", group = "z")

public class EE_motor_test {
    private DcMotorEx motor1;

    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void loop() {
        telemetry.addData("Motor Current Power", motor1.getPower());
        telemetry.addData("Motor Current Position", motor1.getCurrentPosition());
        telemetry.update();

        if (gamepad1.left_stick_y > 0.1) {
            motor1.setPower(gamepad1.left_stick_y);
        }

        if (gamepad1.left_stick_y < -0.1) {
            motor1.setPower(gamepad1.left_stick_y);
        }

        if (gamepad1.x) {
            motor1.setPower(1);
        }
        if (gamepad1.triangle) {
            motor1.setPower(0);
        }
    }
}


