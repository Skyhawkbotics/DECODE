package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Tilt {
    private Servo tilt1, tilt2;

    private HardwareMap hardwareMap;

    private Telemetry telemetry;

    public Tilt(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        tilt1 = hardwareMap.get(Servo.class, "tilt1");
        tilt1.setDirection(Servo.Direction.REVERSE);
        tilt2 = hardwareMap.get(Servo.class, "tilt2");
        tilt2.setDirection(Servo.Direction.FORWARD);

    }
    public void tiltREAL() {
        tilt1.setPosition(0.8);
       tilt2.setPosition(0.8);

    }
    public void tilt1Zero() {
        tilt1.setPosition(1);
    }
    public void tilt2Zero() {
        tilt2.setPosition(1);
    }
    public void tiltZERO() {
       tilt1.setPosition(1);
        tilt2.setPosition(1);
    }
    public void tilt1FULL() {
        tilt1.setPosition(0.5);
    }
    public void tilt2FULL() {
        tilt2.setPosition(0.5);
    }
    public void tilt_telemetry() {
        telemetry.addData("tilt 1", tilt1.getPosition());
        telemetry.addData("tilt 2", tilt2.getPosition());
        telemetry.addData("tilt 1 mode", tilt1.getController());
        telemetry.addData("tilt 2 mode", tilt2.getController());


    }
}
