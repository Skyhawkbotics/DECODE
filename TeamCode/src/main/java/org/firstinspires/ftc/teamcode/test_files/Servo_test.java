package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@TeleOp(name = "Servo Test Code", group = "Tests")

public class Servo_test extends OpMode{
    private Servo servo;
    private Telemetry telemetry;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "angle");
    }

    @Override
    public void loop() {
        telemetry.addData("Servo Current Position", servo.getPosition());
        telemetry.update();

        if(gamepad1.left_stick_y > 0.1){
            servo.setPosition(gamepad1.left_stick_y);
        }

        if(gamepad1.left_stick_y < -0.1){
            servo.setPosition(gamepad1.left_stick_y);
        }
    }
}


