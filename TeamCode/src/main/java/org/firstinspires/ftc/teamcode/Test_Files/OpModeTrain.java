package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "opmode")
public class OpModeTrain extends OpMode {

    public DcMotorEx motor;
    private Boolean motorpower = true;


    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "OpModeTrain");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.triangleWasPressed()) {
            motor.setPower(1);
        } else if (gamepad1.circleWasPressed()) {
            motor.setPower(-1);
        } else {
            motor.setPower(0);
        }
    }
}
