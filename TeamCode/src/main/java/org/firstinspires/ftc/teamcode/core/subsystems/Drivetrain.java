package org.firstinspires.ftc.teamcode.core.subsystems;


import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_d;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_f;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_i;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_p;
import static org.firstinspires.ftc.teamcode.core.constants.rate;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


public class Drivetrain {
    private Follower follower;

    private DcMotorEx  rightFront, rightBack, leftBack, leftFront;

//            .rightFrontMotorName("right_front")
//            .rightRearMotorName("right_back")
//            .leftRearMotorName("left_back")
//            .leftFrontMotorName("left_front")
    public Telemetry telemetry;
    private double currentForward = 0;
    private double currentStrafe = 0;
    private double currentTurn = 0;

    public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry ) {
        rightFront = hardwareMap.get(DcMotorEx.class, "right_front");
        rightBack = hardwareMap.get(DcMotorEx.class, "right_back");
        leftFront = hardwareMap.get(DcMotorEx.class, "left_front");
        leftBack = hardwareMap.get(DcMotorEx.class, "left_back");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.telemetry = telemetry;

    }
    public void robotCentricPowers(double forward, double x, double rx) {
        // Taking in all the powers, I first run them through a slew rate limiter with a tuned "rate" value
        // this limits the change of powers, slowing acceleration in order to reduce tipping
        currentForward = slew(forward, currentForward, rate);
        currentStrafe = slew(x, currentStrafe, rate);
        currentTurn = slew(rx, currentTurn, 0.08);

        //  drive code
        double denominator = Math.max(Math.abs(forward) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (currentForward + currentStrafe + currentTurn) / denominator;
        double backLeftPower = (currentForward - currentStrafe + currentTurn) / denominator;
        double frontRightPower = (currentForward - currentStrafe - currentTurn) / denominator;
        double backRightPower = (currentForward +  currentStrafe - currentTurn) / denominator;
        setPowers(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }
    private double slew(double target, double current, double rate) {
        double delta = target - current;

        if (delta > rate) {
            delta = rate;
        }
        else if (delta < -rate) {
            delta = -rate;
        }

        return current + delta;
    }
    public void setPowers(double leftFrontPower, double leftBackPower, double rightFrontPower, double rightBackPower){
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }

}
