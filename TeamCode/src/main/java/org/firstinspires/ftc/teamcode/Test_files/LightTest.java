package org.firstinspires.ftc.teamcode.Test_files;

import com.bylazar.lights.RGBIndicator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Light;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@TeleOp(name = "Light Test")
public class LightTest extends OpMode {
    private Servo light;
    public void init() {
        light = hardwareMap.get(Servo.class, "light");
    }
    public void loop() {
        double power = gamepad1.left_stick_y * 0.00025;
        light.setPosition(light.getPosition() + power);
        telemetry.addData("Position", light.getPosition());
        telemetry.update();
    }

}
