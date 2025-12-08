package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.core.constants.intake_speed;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private DcMotorEx intake_motor;

    private Servo right_servo, left_servo;

    private double POSITION_IN = 1;
    private double POSITION_OUT = 0;

    public Intake (HardwareMap hardwareMap, Telemetry telemetry){
        intake_motor = hardwareMap.get(DcMotorEx.class, "intake");
        intake_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        right_servo = hardwareMap.get(Servo.class, "right stop");
        left_servo = hardwareMap.get(Servo.class, "left stop");
    }

    public void on(){
        intake_motor.setPower(intake_speed);
    }
    public void off(){
        intake_motor.setPower(0);
    }
    public void reverse() {
        intake_motor.setPower(-1);
    }
    public void servosIn() {
        right_servo.setPosition(0);
        left_servo.setPosition(1);
    }
    public void servosOut() {
        right_servo.setPosition(1);
        left_servo.setPosition(0);
    }
    ///  since the servos and the intake are in this subsystem, we can code the logic needed switch between firing and intaking
    public void intake() {
        servosIn();
        on();
    }
    public void fire() {
        servosOut();
        on();
    }
}

