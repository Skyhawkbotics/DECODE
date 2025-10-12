package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.constants.ed;
import static org.firstinspires.ftc.teamcode.constants.ei;
import static org.firstinspires.ftc.teamcode.constants.ep;
import static org.firstinspires.ftc.teamcode.constants.f;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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

        shooter.update();

        // we want to find a target position for servo that corresponds to angle
        // can put motor into rad/s. maybe some equations to find how it corresponds to velocity?
        // increment code
        // runs every 0.001 ms or wtv

        ///  Motor controls
        ///  might have to reverse motor direction
        if (gamepad1.aWasPressed()) {
            // motor to max target
            shooter.to_thousand();
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
            shooter.to_zero();
        }

        if (gamepad1.left_trigger > 0.4) {
            shooter.to_fivehund();
        }

        if (gamepad1.right_trigger > 0.4) {
            shooter.stress();
        }





        ///  change angle incrementally

        if (gamepad1.yWasPressed()) {
            ///Increase the position 0.1
            shooter.increasePosition();
        }
        if (gamepad1.leftBumperWasPressed()){
            shooter.increasePositionBare();
        }
        if (gamepad1.rightBumperWasPressed()){
            shooter.decreasePositionBare();
        }

        if (g1.xWasPressed()) {
            // Decrease the position 0.1
            shooter.decreasePosition();
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
