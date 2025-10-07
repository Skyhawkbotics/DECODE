package org.firstinspires.ftc.teamcode.inspo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@TeleOp(name = "Servo Test Code")

public class abeservo extends OpMode{
    private Servo servo;
    private Telemetry telemetry;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo");
        servo.setPosition(Servo.MAX_POSITION);
        servo.setPosition(Servo.MIN_POSITION);
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


