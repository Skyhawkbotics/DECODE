package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;

@TeleOp(name = "ShooterTest", group = "Tests")
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

        shooter.update();

        // we want to find a target position for servo that corresponds to angle
        // can put motor into rad/s. maybe some equations to find how it corresponds to velocity?
        // increment code
        // runs every 0.001 ms or wtv

        ///  PID
        if (gamepad1.aWasPressed()) {
            // motor to max target
            shooter.perf_velocity();
        }
        if (gamepad1.left_trigger > 0.4) {
            shooter.to_fivehund();
        }

        if (gamepad1.right_trigger > 0.4) {
            shooter.stress();
        }

        if (gamepad1.bWasPressed()) {
            shooter.to_zero();
        }

        ///  incrementally change the motor speed
        /*
        if(gamepad1.dpadUpWasPressed()) {
            shooter.increaseVelocity();
        }
        if(gamepad1.dpadDownWasPressed()) {
            shooter.decreaseVelocity();
        }

         */






        ///  change angle incrementally

        if (gamepad1.yWasPressed()) {
            ///Increase the position 0.1
            shooter.increasePosition();
        }
        if (g1.xWasPressed()) {
            // Decrease the position 0.1
            shooter.decreasePosition();
        }
        if (gamepad1.leftBumperWasPressed()){
            shooter.increasePositionBare();
        }
        if (gamepad1.rightBumperWasPressed()){
            shooter.decreasePositionBare();
        }


        ///  to the max angles
        if(g1.dpadLeftWasPressed()) {
            shooter.positionMin();
        }
        if(g1.dpadRightWasPressed()) {
            shooter.positionMax();
        }



        shooter.shooter_telemetry();
        telemetry.update();

    }
}
