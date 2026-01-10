package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Tilt {
    private CRServo tilt;

    private HardwareMap hardwareMap;

    private Telemetry telemetry;

    public Tilt(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        tilt = hardwareMap.get(CRServo.class, "tilt");

    }
    public void tilt() {
        tilt.setPower(0.8);
    }
    public void stop_tilt() {
        tilt.setPower(0);
    }
    public void reverse_tilt() {
        tilt.setPower(-0.8);
    }
}
