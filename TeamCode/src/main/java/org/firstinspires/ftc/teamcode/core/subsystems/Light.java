package org.firstinspires.ftc.teamcode.core.subsystems;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Light {
    private Servo light;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;


    public Light(HardwareMap hardwareMap, PanelsTelemetry telemetry) {
        light = hardwareMap.get(Servo.class, "light");
        this.panelsTelemetry = telemetry;
    }

    public void increase() {
        light.setPosition(light.getPosition() + 0.025);
    }

    public void decrease() {
        light.setPosition(light.getPosition() - 0.025);
    }

    public void red() {
        light.setPosition(0.135);
    }

    public void green() {
        light.setPosition(0.5);
    }


    public void light_telemetry() {
        panelsTelemetry.getTelemetry().addData("Light Value", light.getPosition());
    }
}
