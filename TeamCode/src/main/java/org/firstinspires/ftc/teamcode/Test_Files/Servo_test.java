package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Test", group = "Tests")
public class Servo_test extends OpMode {
    private Servo servo;
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo");
    }
    public void loop() {
        if (gamepad1.aWasPressed()) {
            servo.setPosition(0);
        }

        if (gamepad1.bWasPressed()) {
            servo.setPosition(1);
        }

        if (gamepad1.xWasPressed()) {
            servo.setPosition(servo.getPosition() + 0.05);
        }

        if (gamepad1.yWasPressed()) {
            servo.setPosition(servo.getPosition() - 0.05);
        }

        telemetry.addData("Servo Position", servo.getPosition());
        telemetry.update();
    }
}
