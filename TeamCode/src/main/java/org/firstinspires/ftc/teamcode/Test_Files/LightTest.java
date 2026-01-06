package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.subsystems.Light;

@TeleOp(name = "Light Test")
public class LightTest extends OpMode {
    private boolean isFull = false;
    private Light light;
    public void init() {
        light = new Light(hardwareMap, telemetry);
    }

    public void loop() {

        isFull = light.isFull();

        if (isFull) {
            light.green();
        } else {
            light.red();
        }

        light.light_telemetry();
        telemetry.update();
    }

}
