package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "New Shooter")
public class NewShooter extends OpMode {

    private DcMotorEx motor1, motor2;
    private Servo servo;

    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "motor 1");
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setDirection(DcMotorSimple.Direction.REVERSE); //bottom motor

        motor2 = hardwareMap.get(DcMotorEx.class, "motor 2");
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        servo = hardwareMap.get(Servo.class, "servo");

    }

    public void loop() {
        if(gamepad1.aWasPressed()) {
            motor1.setVelocity(motor1.getVelocity() + 300);
        } //increase velocity

        if(gamepad1.bWasPressed()) {
            motor1.setVelocity(0);
        }

        if(gamepad1.xWasPressed()) {
            motor2.setVelocity(motor2.getVelocity() + 300);
        }

        if(gamepad1.yWasPressed()) {
            motor2.setVelocity(0);
        }

        if(gamepad1.right_bumper) {
            motor1.setVelocity(0);
        }

        telemetry.addData("Motor 1 Speed", motor1.getVelocity());
        telemetry.addData("Motor 2 Speed", motor2.getVelocity());
        telemetry.addData("Motor 1 Position", motor1.getCurrentPosition());
        telemetry.addData("Motor 2 Position", motor2.getCurrentPosition());
        telemetry.addData("Servo Position", servo.getPosition());
        telemetry.update();

    }
}
