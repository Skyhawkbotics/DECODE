package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "drive train test !!! d–(^ ᗜ ^ )z")

public class drivetrain extends OpMode {

    private DcMotorEx motor1;
    /// ts the shorter belt motor
    private DcMotorEx motor2;
    /// ts the longer belt motor

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "one");
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        /// config on port 2 (not required ig)
        motor2 = hardwareMap.get(DcMotorEx.class, "two");
        /// config on port 2 (not required ig)
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    @Override
    public void loop() {
        telemetry.addData("Motor 1 Current Power", motor1.getPower());
        telemetry.addData("Motor 2 Current Power", motor2.getPower());
        telemetry.addData("Motor 1 Current Position", motor1.getCurrentPosition());
        telemetry.addData("Motor 2 Current Position", motor2.getCurrentPosition());

        if (gamepad1.x) {
            motor1.setPower(-1);
            /// setting m1=-1 and m2=0 causes them to rotate the same direction
        }
        if (gamepad1.b) {
            motor1.setPower(1);
        }
        if (gamepad1.right_bumper){
            motor1.setPower(0);
            ///  stops motor 1
        }
        if (gamepad1.y) {
            motor1.setVelocity(motor1.getVelocity() + 1);
        }
        if (gamepad1.a) {
            motor1.setVelocity(motor1.getVelocity() - 1);
            /// increases/decreases velocities of motor 2
        }



        if (gamepad1.dpad_left) {
            motor2.setPower(1);
        }
        if (gamepad1.dpad_right) {
            motor2.setPower(-1);
        }
        if (gamepad1.left_bumper){
            motor2.setPower(0);
            /// stops motor 2
        }
        if (gamepad1.dpad_up) {
            motor2.setVelocity(motor1.getVelocity() + 1);
        }
        if (gamepad1.dpad_down) {
            motor2.setVelocity(motor1.getVelocity() - 1);
            /// increases/decreases velocities of motor 2
        }
    }
}
