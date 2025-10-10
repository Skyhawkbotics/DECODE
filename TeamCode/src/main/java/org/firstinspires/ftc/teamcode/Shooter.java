package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter { // hi kristine
    //its is 7:31 in the morning
    // this is eric code

    private DcMotorEx motor;


    private Servo servo;

    private double max_angle_serv_pos = 0;

    private double min_angle_serv_pos = 0;

    private Telemetry telemetry; //hi sam

    public Shooter(@NonNull HardwareMap hardwareMap, Telemetry telemetry) { // asldkfjsaldjkf
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

    public void positionZero() {
        servo.setPosition(0);

    }

        public void increaseVelocity () {
            // increments motor speed by 200 ticks
            motor.setVelocity(motor.getVelocity() + 25);
        }

        public void decreaseVelocity () {
            // increments motor speed by 200 ticks
            motor.setVelocity(motor.getVelocity() - 25);
        }
/// servo incrementations
        public void increasePositionBare () {
            // increments servo by hundredths
            servo.setPosition(servo.getPosition() + 0.01);
        }
        public void increasePosition (){
            servo.setPosition(servo.getPosition()+0.1);
        }
        public void decreasePositionBare () {
            // increments servo
            servo.setPosition(servo.getPosition() - 0.01);
        }

        public void decreasePosition(){
            servo.setPosition(servo.getPosition() - 0.1);
        }

    public void positionMax() {
        servo.setPosition(0.35);
        // Change this number to be the max servo position
    }

    public void positionMin() {
        servo.setPosition(0.2);
        // change this number to be the minimum servo position
    }



        public void perf () {
            motor.setVelocity(1040);
        }

        ///  This function converts TPS to RPM
        public double conversion_RPM (double velocity){
         int ticks_per_rev = 28;
         int seconds_per_min = 60;
            return (velocity/ticks_per_rev)*seconds_per_min;
        }

        /// This function converts RPM to TPS

        public double getTPS(double RPM) {
            int TPR = 28;
            return (RPM * TPR) / 60;
        }


        public void shooter_telemetry () {
            telemetry.addData("motor speed in ticks", motor.getVelocity());
            telemetry.addData("motor position", motor.getCurrentPosition());
            telemetry.addData("motor rpm", conversion_RPM(motor.getVelocity()));
            telemetry.addData("servo position", servo.getPosition());
            // telemetry.addData("Servo position", servo.getPosition());

        }
    }

