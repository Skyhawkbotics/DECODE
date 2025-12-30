package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;

@TeleOp(name = "ShooterTest", group = "Tests")

public class ShooterTest extends OpMode {

    private Shooter shooter;

    private Gamepad g1 = new Gamepad();
    private Gamepad previousg1 = new Gamepad();
    private PanelsTelemetry telemetry1 = PanelsTelemetry.INSTANCE;

    public void init() {
        shooter = new Shooter(hardwareMap, telemetry1);
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



        ///  to the max angles
        if(g1.dpadLeftWasPressed()) {
            shooter.positionMin();
        }
        if(g1.dpadRightWasPressed()) {
            shooter.positionMax();
        }


        shooter.shooter_telemetry();
        telemetry1.getTelemetry().update();



    }
}
