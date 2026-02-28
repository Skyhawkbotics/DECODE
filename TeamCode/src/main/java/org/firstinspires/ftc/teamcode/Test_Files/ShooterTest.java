package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;

@TeleOp(name = "ShooterTest", group = "Z")

public class ShooterTest extends OpMode {

    private Shooter shooter;
    private Intake intake;

    private Gamepad g1 = new Gamepad();
    private Gamepad previousg1 = new Gamepad();
    private PanelsTelemetry telemetry1 = PanelsTelemetry.INSTANCE;

    public void init() {
        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);

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

        if(gamepad1.right_trigger > 0) {
            intake.intake();
        } else if(gamepad1.left_trigger >0) {
            intake.fire();
        } else {
            intake.off();
        }


        ///  to the max angles
        if(g1.dpadLeftWasPressed()) {
            shooter.positionMin();
        }
        if(g1.dpadUpWasPressed()) {
            shooter.increasePositionBare();
        }


        if(g1.dpadRightWasPressed()) {
            shooter.positionMax();
        }
        if(g1.dpadDownWasPressed()) {
            shooter.decreasePositionBare();
        }


        shooter.shooter_telemetry();
        shooter.panelstelemetry();

        telemetry1.getTelemetry().update();


    }
}
