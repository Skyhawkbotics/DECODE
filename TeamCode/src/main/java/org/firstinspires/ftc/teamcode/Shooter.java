package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter { // hi kristine
    //its is 7:31 in the morning
    // this is eric code

    private DcMotorEx motor; ///hello
    private Servo servo;

    private double max_angle_serv_pos = 0;

    private double min_angle_serv_pos = 0;

    private Telemetry telemetry; //hi sam

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) { // asldkfjsaldjkf
        // hi
        motor = hardwareMap.get(DcMotorEx.class, "please work");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        servo = hardwareMap.get(Servo.class, "shooterServo");
        this.telemetry = telemetry;
    }

    public void idle() {
        motor.setPower(0);
    }

    public void motor_speed_max() {
        motor.setPower(1);
    }


    public void increaseVelocity() {
        // increments motor speed by 200 ticks
        motor.setVelocity(motor.getVelocity() + 200);
    }

    public void decreaseVelocity() {
        // increments motor speed by 200 ticks
        motor.setVelocity(motor.getVelocity() - 200);
    }

    public void increasePosition() {
        // increments servo
        servo.setPosition(servo.getPosition() + 0.1);
    }

    public void decreasePosition() {
        // increments servo
        servo.setPosition(servo.getPosition() - 0.1);
    }

    public void positionMax() {
        servo.setPosition(max_angle_serv_pos);
        // Change this number to be the max servo position
    }

    public void positionZero() {
        servo.setPosition(min_angle_serv_pos);
        // change this number to be the minimum servo position
    }



    public void shooter_telemetry() {
        telemetry.addData("motor speed in ticks", motor.getVelocity());
        telemetry.addData("motor position", motor.getCurrentPosition());
        telemetry.addData("Servo position", servo.getPosition());

    }
}