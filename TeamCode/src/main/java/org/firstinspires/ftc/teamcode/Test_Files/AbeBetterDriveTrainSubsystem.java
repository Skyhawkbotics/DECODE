package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AbeBetterDriveTrainSubsystem {

    private Servo twoWheelServo;

    private DcMotorEx driveMotor;

    private Telemetry telemetry;

    public AbeBetterDriveTrainSubsystem (HardwareMap hardwareMap, Telemetry telemetry) {
        twoWheelServo = hardwareMap.get(Servo.class, "second Wheel Servo");

        driveMotor = hardwareMap.get(DcMotorEx.class, "drive motor");
        driveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        twoWheelServo.setPosition(0.4783);

        this.telemetry = telemetry;
    }

    public void testDriveControls (Gamepad g1) {

        if (g1.triangleWasPressed()) {
            driveMotor.setPower(0.3);
        }

        if (g1.dpadUpWasPressed()) {
            twoWheelServo.setPosition(0.4783);
        }// 0.4783 not touchiing ground

        // 0.6383 locked


        if (g1.dpadDownWasPressed()) {
            twoWheelServo.setPosition(0.64);
        }
    }

    public void driveTelemetry () {
        telemetry.addData ("second wheel servo", twoWheelServo.getPosition());

        telemetry.addData ("drive motor", driveMotor.getPower());

        telemetry.update();

    }

}
