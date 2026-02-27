package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Tina's Train", group = "anything")
public class tinastrain extends OpMode {
    DcMotor leftFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightFrontMotor;
    DcMotor rightBackMotor;

    DcMotor intake;

    @Override
    public void init() {
//         leftFrontMotor = hardwareMap.dcMotor.get("left_front");
         leftBackMotor = hardwareMap.dcMotor.get("left_back");
//         rightFrontMotor = hardwareMap.dcMotor.get("right_front");
         rightBackMotor = hardwareMap.dcMotor.get("right_back");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
//        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
//        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
//        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
//        double rx = gamepad1.right_stick_x * 0.8;
//
//        // Denominator is the largest motor power (absolute value) or 1
//        // This ensures all the powers maintain the same ratio,
//        // but only if at least one is out of the range [-1, 1]
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;
//
//        leftFrontMotor.setPower(frontLeftPower);
//        leftBackMotor.setPower(backLeftPower);
//        rightFrontMotor.setPower(frontRightPower);
//       // rightBackMotor.setPower(backRightPower);


        if(gamepad1.left_stick_y != 0) {
            leftBackMotor.setPower(gamepad1.left_stick_y);
        }

        if(gamepad1.right_stick_y != 0 ) {
            rightBackMotor.setPower(gamepad1.right_stick_y );
        }


    }
}