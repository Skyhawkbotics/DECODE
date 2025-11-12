package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.elevator_retract;
import static org.firstinspires.ftc.teamcode.core.constants.elevator_shoot;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {


    private Telemetry telemetry;
    private CRServo servo_1;
    private Servo flip;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        servo_1 = hardwareMap.get(CRServo.class, "auto");
        servo_1.setDirection(DcMotorSimple.Direction.REVERSE);
        flip = hardwareMap.get(Servo.class, "flip");
        this.telemetry = telemetry;
    }
    public void on() {
        servo_1.setPower(1);
    }
    public void secondary() {
        flip.setPosition(elevator_shoot);
    }

    public void off() {
        servo_1.setPower(0);
        flip.setPosition(elevator_retract);
    }
    public void elevator_telemetry() {
        telemetry.addData("flip position", flip.getPosition());

    }


}
