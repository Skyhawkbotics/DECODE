package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {

    double bottom = 0.65;

    double top = 0;
    private Telemetry telemetry;
    private Servo servo;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        servo = hardwareMap.get(Servo.class, "Elevator Servo");
        this.telemetry = telemetry;
    }
    public void bottom() {
        servo.setPosition(bottom);
    }

    public void top() {
        servo.setPosition(top);
    }

    public void increasePosition() {
        servo.setPosition(servo.getPosition() + 0.05);
    }

    public void decreasePosition() {
        servo.setPosition(servo.getPosition() - 0.05);
    }

    public void positionZero() {
        servo.setPosition(0);
    }

    public void positionOne() {
        servo.setPosition(1);
    }
}
