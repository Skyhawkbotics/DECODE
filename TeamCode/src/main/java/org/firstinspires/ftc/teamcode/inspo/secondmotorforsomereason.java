package org.firstinspires.ftc.teamcode.inspo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Another test code")
public class secondmotorforsomereason extends OpMode {
    private DcMotorEx ericsbutt;

    @Override
    public void init() {
        ericsbutt = hardwareMap.get(DcMotorEx.class, "ericsbutt");
        ericsbutt.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        ericsbutt.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("Motor Current Power", ericsbutt.getPower());
        telemetry.addData("Motor Current Position", ericsbutt.getCurrentPosition());
        telemetry.update();

        if (gamepad1.left_stick_y > 0.5) {
            ericsbutt.setPower(0.6);
        }
        if (gamepad1.left_stick_y < 0.5) {
            ericsbutt.setPower(-0.6);
        }
        if (gamepad1.left_stick_y == 0) {
            ericsbutt.setPower(0);
        }
    }
}
