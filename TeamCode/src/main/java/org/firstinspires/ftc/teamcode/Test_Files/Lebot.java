package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "LeBot")
public class Lebot extends OpMode {

    public DcMotorEx motor_A, motor_B;
    public Servo servo;
    public boolean off = false;

    public void init() {

        motor_A = hardwareMap.get(DcMotorEx.class, "motor A");
        motor_B = hardwareMap.get(DcMotorEx.class, "motor B");
        motor_A.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_B.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servo = hardwareMap.get(Servo.class, "servo A");


    }

    @Override
    public void loop() {
        //object_name.method_name(parameter_1, param _2);
        // if (gamepad1.right_trigger > 0) {
        //            servo.setPosition(gamepad1.right_trigger);
        //        } else if (gamepad1.left_trigger > 0) {
        //            servo.setDirection(Servo.Direction.REVERSE);
        //            servo.setPosition(gamepad1.left_trigger);
        //        } else {
        //            servo.setPosition(0);
        //        }

        if (gamepad1.dpadUpWasPressed()) {
            servo.setPosition(servo.getPosition() + 0.1);
        } else if (gamepad1.dpadDownWasPressed()) {
            servo.setPosition(servo.getPosition() - 0.1);
        }

        motor_A.setPower(servo.getPosition());
        motor_B.setPower(-servo.getPosition());


        telemetry.addData("left stick y", gamepad1.left_stick_y);
        telemetry.addData("motor A power", motor_A.getPower());
        telemetry.addData("motor B power", motor_B.getPower());
        telemetry.addData("servo power", servo.getPosition());
        telemetry.update();


    }
}
