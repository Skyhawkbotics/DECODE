package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.core.constants.intake_speed;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private DcMotorEx intake_motor;

    private Servo servo_1, servo_2;

    private double POSITION_IN = 1;
    private double POSITION_OUT = 0;

    public Intake (HardwareMap hardwareMap, Telemetry telemetry){
        intake_motor = hardwareMap.get(DcMotorEx.class, "intake");
        intake_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        servo_1 = hardwareMap.get(Servo.class, "intake servo 1");
        servo_2 = hardwareMap.get(Servo.class, "intake servo 2");
        servo_2.setDirection(Servo.Direction.REVERSE);
    }

    public void on(){
        intake_motor.setPower(intake_speed);
    }
    public void on_little(){
        intake_motor.setPower(0.7);
    }

    public void off(){
        intake_motor.setPower(0);
    }
    public void reverse() {
        intake_motor.setPower(-1);
            }

    public void servosIn() {
        servo_1.setPosition(POSITION_IN);
        servo_2.setPosition(POSITION_IN);
    }

    public void servosOut() {
        servo_1.setPosition(POSITION_OUT);
        servo_2.setPosition(POSITION_OUT);
    }
}

