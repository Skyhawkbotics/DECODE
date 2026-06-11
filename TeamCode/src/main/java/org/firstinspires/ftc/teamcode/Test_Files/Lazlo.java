package org.firstinspires.ftc.teamcode.Test_Files;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Lazlo") // @TeleOp (name = "desired_name")
@Disabled
public class Lazlo extends OpMode { // extends OpMode

    public DcMotorEx motor;
    public Servo servo;
    public boolean lazlo = false;
    public boolean sammy = false;

    public double PI = 3.14159265;
    public int not_pi = 1;

    public double CIRCUMFRENCE = 2*PI*not_pi;

    public String words = "hello world";




    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servo = hardwareMap.get(Servo.class, "servo");

    }

    // public/private void init() {
    // motor initialization
    // servo initialization
    // subsystem initilization
    // }

    ///  public/private void loop() {
    /// motor do x
    /// motor do y
    /// servo do y
    /// read xyz sensor
    /// blah blah blah
    /// }

    public void loop() {
        // } else if (gamepad1.square) {
        //     servo.setPosition(1);
        // } else if (gamepad1.cross) {
        //    servo.setPower(-0.5);
        // }
        if (gamepad1.left_trigger == 0) {
            servo.setPosition(0);
        } else {
            servo.setPosition(1);
        }

        if (gamepad1.triangleWasPressed()) {
            lazlo = !lazlo;
            // if true before, it becomes false
            // if false before, it comes true
        }

        if (gamepad1.circleWasPressed()) {
            sammy = !sammy;
        }
        if (lazlo) {
            motor.setPower(1);
        } else if(sammy) {
            motor.setPower(-1);
        }
        else {
            motor.setPower(0);
        }

        telemetry.addData("Motor Power", motor.getPower());
        telemetry.addData("Lazlo", lazlo);
        telemetry.addData("Servo Position", servo.getPosition());
        telemetry.addData("Sammy", sammy);
        telemetry.addData("Half Power", get_half_power());
        telemetry.addData("Bad Lazlo", bad_lazlo());
        telemetry.addData("Half Servo", get_half_servo(servo.getPosition()));

    }

    // classes, objects, methods, parameters, and return statements
    // subsystems and n-based architecture
    public double get_half_power() {
        double value = motor.getPower() / 2;
        return value;
    }

    public double get_half_servo(double z) {
        double value = z/2;
        return value;
    }

    public boolean bad_lazlo() {
        boolean lebron = !lazlo;
        return lebron;
    }

}
