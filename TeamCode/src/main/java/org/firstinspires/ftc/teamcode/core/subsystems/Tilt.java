package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
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
        tilt2 = hardwareMap.get(Servo.class, "tilt2");
        tilt2.setDirection(Servo.Direction.FORWARD);

    }
    public void tilt() {
        tilt1.setPosition(0);
       tilt2.setPosition(0);

    }
    public void stop_tilt() {
       tilt1.setPosition(1);
        tilt2.setPosition(1);
    }
    public void reverse_tilt() {
      // tilt1.setPosition(-1);
        tilt2.setPosition(0);
    }
}
