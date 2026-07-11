package org.firstinspires.ftc.teamcode.Test_Files;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AbeTurretSubsystem {
    private Servo stopperServo;
    private Servo angleServo;
    private DcMotorEx shooterMotor;
    private PIDController pid;
    private static double p = 0.065, i = 0, d = 0;
    private double power;
    private int target;
    private int servoAngle = 0;
    public AbeTurretSubsystem (HardwareMap hardwareMap) {
        stopperServo = hardwareMap.get(Servo.class, "stopperServo");
        angleServo = hardwareMap.get(Servo.class, "angleServo");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        pid = new PIDController (p, i, d);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void PIDcalc() {
        pid.setPID(p, i, d);

        power = pid.calculate(shooterMotor.getVelocity(), target);
        shooterMotor.setPower(power);
    }
    public void AngleServoupdate() {
        if (target >= 1500) {
            angleServo.setPosition(0.8);
        } else if (target >= 1100){
            angleServo.setPosition(0.6);
        } else if (target >= 700) {
            angleServo.setPosition(0.4);
        } else {
            angleServo.setPosition(0.2);
        }
    }
}
