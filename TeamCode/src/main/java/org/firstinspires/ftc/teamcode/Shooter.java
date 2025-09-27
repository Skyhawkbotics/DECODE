package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    private DcMotorEx motor;
    private Servo servo;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        // hi
        motor = hardwareMap.get(DcMotorEx.class, "please work");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        servo = hardwareMap.get(Servo.class, "shooterServo");
    }

    public void idle() {
        motor.setPower(0);
    }

    public void startMotor() {
        motor.setPower(1);
    }

    public void positionZero() {
        servo.setPosition(0);
    }

    public void increasePower() {
        motor.setPower(motor.getPower() + 200);
    }

    public void decreasePower() {
        motor.setPower(motor.getPower() - 200);
    }

    public void increasePosition() {
        servo.setPosition(servo.getPosition() + 0.1);
    }

    public void decreasePosition() {
        servo.setPosition(servo.getPosition() - 0.1);
    }

    public void positionOne() {
        servo.setPosition(1);
    }

    public double readServo() {
        return servo.getPosition();
    }

    public double readMotor() {
        return motor.getCurrentPosition();
    }
}