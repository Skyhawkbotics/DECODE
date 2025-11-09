package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.bottom;
import static org.firstinspires.ftc.teamcode.core.constants.top;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {


    private Telemetry telemetry;
    private CRServo servo_1, servo_2;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        servo_1 = hardwareMap.get(CRServo.class, "servo_1");
        servo_2 = hardwareMap.get(CRServo.class, "servo_2");
        servo_2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void on() {
        servo_1.setPower(1);
        servo_2.setPower(1);
    }
    public void on_reverse() {
        servo_1.setPower(-1);
        servo_2.setPower(-1);
    }

    public void off() {
        servo_1.setPower(0);
        servo_2.setPower(0);
    }

}
