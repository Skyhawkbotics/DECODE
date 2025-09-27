package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Collections;

@TeleOp(name = "ShooterTest")
public class shooterMain extends OpMode {
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

        if (gamepad1.aWasPressed()) {
            // move the servo
            // shooter.positionOne();
            shooter.startMotor();
        }

        if (gamepad1.bWasPressed()) {
            // move the servo
            // shooter.positionZero();
            shooter.idle();


        }

        if (gamepad1.yWasPressed()) {
            // Increase the position
            shooter.increasePosition();
        }

        if (g1.x && !previousg1.x) {
            // Decrease the position
            shooter.decreasePosition();
        }

        telemetry.addData("Servo Position:", shooter.readServo());
        telemetry.addData("Motor Position:", shooter.readMotor());
        telemetry.addData("Gamepad Pressed: ", gamepad1.a);
        telemetry.addData("G1 Pressed:", g1.a);
        telemetry.addData("PreviousG1 Pressed:", previousg1.a);
        telemetry.update();

    }
}
