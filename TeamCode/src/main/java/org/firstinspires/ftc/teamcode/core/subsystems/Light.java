package org.firstinspires.ftc.teamcode.core.subsystems;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Light {
    private Servo light;

    private Telemetry telemetry;

    private DistanceSensor distance_sensor;

    private Timer timer;

    private double ballBuffer = 1.5;

    public Light(HardwareMap hardwareMap, Telemetry telemetry) {
        light = hardwareMap.get(Servo.class, "light");
        distance_sensor = hardwareMap.get(DistanceSensor.class, "distance");
        this.telemetry = telemetry;
        timer = new Timer();
    }

    public boolean isFull() {
        if (distance_sensor.getDistance(DistanceUnit.INCH) < 3) {
            return true;
        } else {
            return false;
        }
    }


     public boolean isFullCertain() { // if the ball is in the intake (distance less than x) for more than x seconds, it returns true
            if(isFull() & timer.getElapsedTimeSeconds() > 1) {
                // if the distance is less than 3 and the time is over 1.5
                // return as true
                return true;
            } else {
                // this will  happen if it is not full or if distance timer is not greater than 1.5
                timer.resetTimer();
                return false;
            }
        }

     public void resetTimer() {
        timer.resetTimer();
     }

     public double getTime() {
        return timer.getElapsedTimeSeconds();
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
        telemetry.addData("Timer", timer.getElapsedTimeSeconds());
    }
}
