package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Claw", group = "abe")
public class AbeClaw extends OpMode {

    private Servo servo;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo");
    }

    @Override
    public void loop() {

        telemetry.addData("Servo Position", servo.getPosition());
        telemetry.update();

        if (gamepad1.aWasPressed()) {
            servo.setPosition(1);
        }
        if (gamepad1.bWasPressed()) {
            servo.setPosition(0);
        }
        if (gamepad1.squareWasPressed()) {
            servo.setPosition(servo.getPosition() + 0.01);
        }
        if (gamepad1.circleWasPressed()) {
            servo.setPosition(servo.getPosition() - 0.01);
        }

    }
}
