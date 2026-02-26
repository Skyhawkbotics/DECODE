package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp (name = "Single Motor Test", group = "Z")
public class Single_motor_test extends OpMode {
    private DcMotorEx motor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            motor.setPower(1.0);
        }
        if (gamepad1.b) {
            motor.setPower(0.0);
        }
        if (gamepad1.x) {
            motor.setPower(-1);
        }
    }
}
