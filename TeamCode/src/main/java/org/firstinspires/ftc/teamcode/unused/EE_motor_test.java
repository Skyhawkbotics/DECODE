package org.firstinspires.ftc.teamcode.unused;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "KRISTINE EE Motor Test", group = "EE")

public class EE_motor_test extends OpMode {
    private DcMotorEx motor1;

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
    @Override
    public void loop() {
        telemetry.addData("Motor Power Level (Fraction)", motor1.getPower());
        telemetry.addData("Motor Current Position (Ticks) ", motor1.getCurrentPosition());
        telemetry.addData(" Motor Current (AMPS) ", motor1.getCurrent(CurrentUnit.AMPS)); // Eric
        telemetry.addData("Motor Velocity (Ticks) ", motor1.getVelocity());
        telemetry.addData("Motor Current Alert", motor1.getCurrentAlert(CurrentUnit.AMPS));
        telemetry.update();

        if (gamepad1.aWasPressed()) {
            motor1.setPower(0);
        }

        if (gamepad1.bWasPressed()) {
            motor1.setPower(1);
        }

    }
}


