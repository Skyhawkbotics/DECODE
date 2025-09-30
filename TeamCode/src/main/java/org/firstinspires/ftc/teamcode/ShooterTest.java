package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "ShooterTest")
public class ShooterTest extends OpMode {
    private Shooter shooter;

    private Gamepad g1 = new Gamepad();
    private Gamepad previousg1 = new Gamepad();

    public void init() {
        shooter = new Shooter(hardwareMap, telemetry);
        g1 = new Gamepad();
        previousg1 = new Gamepad();
    }

    @Override
    public void loop() {
        previousg1.copy(g1);
        g1.copy(gamepad1);

        // we want to find a target position for servo that corresponds to angle
        // can put motor into rad/s. maybe some equations to find how it corresponds to velocity?
        // increment code
        // runs every 0.001 ms or wtv

        ///  Motor controls
        ///  might have to reverse motor direction
        if (gamepad1.aWasPressed()) {
            // motor to max speed
            shooter.motor_speed_max();
        }
        ///  incrementally change the motor speed
        if(gamepad1.dpadUpWasPressed()) {
            shooter.increaseVelocity();
        }
        if(gamepad1.dpadDownWasPressed()) {
            shooter.decreaseVelocity();
        }

        ///  stop motor
        if (gamepad1.bWasPressed()) {
            // move the servo
            // shooter.positionZero();
            shooter.idle();
        }



        ///  change angle incrementally

        if (gamepad1.yWasPressed()) {
            // Increase the position
            shooter.increasePosition();
        }

        if (g1.xWasPressed()) {
            // Decrease the position
            shooter.decreasePosition();
        }
        ///  to the max angles
        if(g1.dpadLeftWasPressed()) {
            shooter.positionZero();
        }
        if(g1.dpadRightWasPressed()) {
            shooter.positionMax();
        }



        shooter.shooter_telemetry();
        telemetry.update();

    }
}
