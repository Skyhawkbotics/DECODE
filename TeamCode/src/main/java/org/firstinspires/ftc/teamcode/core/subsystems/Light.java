package org.firstinspires.ftc.teamcode.core.subsystems;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Light {
    private Servo light;

    private Telemetry telemetry;

    private DistanceSensor distance_sensor;

    public Light(HardwareMap hardwareMap, Telemetry telemetry) {
        light = hardwareMap.get(Servo.class, "light");
        distance_sensor = hardwareMap.get(DistanceSensor.class, "distance");
        this.telemetry = telemetry;
    }

    public boolean isFull() {
        if (distance_sensor.getDistance(DistanceUnit.INCH) < 3) {
            return true;
        } else {
            return false;
        }
    }

    public void red() {
        light.setPosition(0.135);
    }

    public void green() {
        light.setPosition(0.5);
    }

    public boolean lightIsRed() {
        if (light.getPosition() == 0.135) {
            return true;
        } else {
            return false;
        }
    }

    public boolean lightIsGreen() {
        if (light.getPosition() == 0.5) {
            return true;
        } else {
            return false;
        }
    }

    public double getDistance() {
        return distance_sensor.getDistance(DistanceUnit.INCH);
    }


    public void light_telemetry() {
        telemetry.addData("Light Value", light.getPosition());
        telemetry.addData("Distance Value", getDistance());
    }
}
