package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Tilt {
    private CRServo tilt1, tilt2;

    private HardwareMap hardwareMap;

    private Telemetry telemetry;

    public Tilt(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        tilt1 = hardwareMap.get(CRServo.class, "tilt1");
        tilt2 = hardwareMap.get(CRServo.class, "tilt2");

    }
    public void tilt() {
        tilt1.setPower(1);
        tilt2.setPower(1);
    }
    public void stop_tilt() {
        tilt2.setPower(0);
        tilt1.setPower(0);
    }
    public void reverse_tilt() {
        tilt1.setPower(-0.8);
        tilt2.setPower(-0.8);
    }
}
