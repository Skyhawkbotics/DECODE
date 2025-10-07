package org.firstinspires.ftc.teamcode.inspo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Abe Test Code")
public class abeExClass extends OpMode {

    private DcMotorEx motor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("Motor Current Power", motor.getPower());
        telemetry.addData("Motor Current Position", motor.getCurrentPosition());
        telemetry.update();

        if(gamepad1.left_stick_y > 0.1){
            motor.setPower(gamepad1.left_stick_y);
        }

        if(gamepad1.left_stick_y < -0.1){
            motor.setPower(gamepad1.left_stick_y);
        }

    }
}
