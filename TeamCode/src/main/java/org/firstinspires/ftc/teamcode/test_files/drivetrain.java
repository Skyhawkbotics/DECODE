package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "drive train test !!! d–(^ ᗜ ^ )z")

public class drivetrain extends OpMode {

    private DcMotorEx left_front, left_back, right_front, right_back;

    @Override
    public void init() {


         left_front = hardwareMap.get(DcMotorEx.class, "L front");
         left_front.setDirection(DcMotorSimple.Direction.REVERSE);
         left_back = hardwareMap.get(DcMotorEx.class, "L rear");
         left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back = hardwareMap.get(DcMotorEx.class, "R front");
        right_back = hardwareMap.get(DcMotorEx.class, "R rear");


    }

    @Override
    public void loop() {

        telemetry.addData("back left direction", left_back.getDirection());
        telemetry.addData("back right direction", right_back.getDirection());
        telemetry.addData("front left direction", left_front.getDirection());
        telemetry.addData("front right direction", right_front.getDirection());
        telemetry.addData("left stick x", gamepad1.left_stick_x);
        telemetry.addData("left stick y", gamepad1.left_stick_y);
        telemetry.update();
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        left_front.setPower(frontLeftPower);
        left_back.setPower(backLeftPower);
        right_front.setPower(frontRightPower);
        right_back.setPower(backRightPower);
    }


}
