package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Shooter {
    private DcMotorEx top_motor, bot_motor;

    public PIDController pid;

    public static int target;

    private Servo servo;

    private double max_angle_serv_pos = 0;

    private double min_angle_serv_pos = 0;

    private Telemetry telemetry; //hi sam



    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        // hi
        top_motor = hardwareMap.get(DcMotorEx.class, "top");
        top_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        top_motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        bot_motor = hardwareMap.get(DcMotorEx.class, "bottom");
        bot_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bot_motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        bot_motor.setDirection(DcMotorSimple.Direction.REVERSE);


        pid = new PIDController(ep, ei, ed);

        servo = hardwareMap.get(Servo.class, "shoot");
        this.telemetry = telemetry;
    }

    public void update() { /// PID Update loop
        pid.setPID(ep, ei, ed);
        top_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        double pid_output = pid.calculate(top_motor.getVelocity(), target);
        double power = pid_output;
        top_motor.setPower(power);
        bot_motor.setPower(power);
    }

    public void setTarget(int b) {
        ///  sets PID target
        target = b;
    }

    public void setTarget(int t, boolean usingRPM) {
        // way to set target where target can be in RPM, just set usingRPM to true
        if (!usingRPM) {
            target = t;
        } else {
            target = getTPS(t);
        }


    }

    ///  Methods for quick PID target values
    public void perf_velocity() {
        setTarget(perf_value);
    }
    public void to_fivehund() {
        setTarget(half_speed);
    }
    public void to_three_fourths() {
        setTarget(three_fourths);
    }
    public void to_zero() {
        setTarget(0);
    }
    public void stress() {
        setTarget(max);
    }



    ///  Servo
    public void positionMax() {
        servo.setPosition(positionMax);
        // Change this number to be the max servo position
    }

    public void positionMin() {
        servo.setPosition(positionMin);
        // change this number to be the minimum servo position
    }

    public void increasePositionBare() {
        // increments servo by hundredths
        servo.setPosition(servo.getPosition() + 0.01);
    }

    public void increasePosition() {
        servo.setPosition(servo.getPosition() + 0.1);
    }

    public void decreasePositionBare() {
        // increments servo
        servo.setPosition(servo.getPosition() - 0.01);
    }

    public void decreasePosition() {
        servo.setPosition(servo.getPosition() - 0.1);
    }



    ///  old motor
    public void increaseVelocity() {
        // increments motor speed by 200 ticks
        setTarget(target + 50);
    }
    public void decreaseVelocity() {
        // increments motor speed by 200 ticks
        setTarget(target - 50);

    }




    public double conversion_RPM(double velocity) {
        ///  This function converts TPS to RPM
        int ticks_per_rev = 28;
        int seconds_per_min = 60;
        return (velocity / ticks_per_rev) * seconds_per_min;
    }

    public int getTPS(double RPM) {
        /// This function converts RPM to TPS
        int TPR = 28;
        return (int) (RPM * TPR) / 60; /// casts it so it only does integers
    }

    public void shooter_telemetry() {
        telemetry.addData("motor speed in ticks", top_motor.getVelocity());
        telemetry.addData("motor position", top_motor.getCurrentPosition());
        telemetry.addData("motor rpm", conversion_RPM(top_motor.getVelocity()));
        telemetry.addData("servo position", servo.getPosition());
        telemetry.addData("current target", target);
        // telemetry.addData("Servo position", servo.getPosition());
    }
}

