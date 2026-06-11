package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "Clawman")
@Disabled
public class Clawman extends OpMode {

    public Servo swervo;

    @Override
    public void init() {
        swervo = hardwareMap.get(Servo.class, "swervo");
    }

    @Override
    public void loop() {

        if (gamepad1.dpadRightWasPressed()) {
            swervo.setPosition(swervo.getPosition() + 0.1);
        } else if (gamepad1.dpadLeftWasPressed()) {
            swervo.setPosition(swervo.getPosition() - 0.1);
        }

        if (gamepad1.crossWasPressed()) {
            swervoClose();
        }

        if (gamepad1.circleWasPressed()) {
            swervoOpen();
        }

        telemetry.addData("swervo power", swervo.getPosition());
        telemetry.update();
    }

    public void swervoClose() {
        swervo.setPosition(0.95);
    }
    public void swervoOpen() {
        swervo.setPosition(0);
    }
}