package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Abe Test Code", group = "Tests")
public class Motor_test extends OpMode {

    private DcMotorEx motor1;
    private DcMotorEx motor2;


    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "s top");
        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motor2 = hardwareMap.get(DcMotorEx.class, "s bot");
        motor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        telemetry.addData("Motor Current Power", motor1.getPower());
        telemetry.addData("Motor Current Position", motor1.getCurrentPosition());
        telemetry.addData("Motor 2 Current Power", motor2.getPower());
        telemetry.addData("Motor 2 Current Position", motor2.getCurrentPosition());
        telemetry.update();

        if (gamepad1.left_stick_y > 0.1) {
            motor1.setPower(gamepad1.left_stick_y);
        }

        if (gamepad1.left_stick_y < -0.1) {
            motor1.setPower(gamepad1.left_stick_y);
        }

        if (gamepad1.right_stick_y > 0.1) {
            motor2.setPower(gamepad1.right_stick_y);
        }

        if (gamepad1.right_stick_y < -0.1) {
            motor2.setPower(gamepad1.right_stick_y);
        }

        if (gamepad1.b) {
            motor1.setPower(0.5);
            motor2.setPower(0.5);
        }
        if (gamepad1.a) {
            motor1.setPower(0);
            motor2.setPower(0);
        }
    }
}
