package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.gamepad.GamepadManager;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.bylazar.gamepad.PanelsGamepad;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;

@TeleOp(name = "ShooterTest", group = "Z")

public class ShooterTest extends OpMode {

    private Shooter shooter;
    private Intake intake;
    private PanelsTelemetry telemetry1 = PanelsTelemetry.INSTANCE;

    GamepadManager panelsg1 = PanelsGamepad.INSTANCE.getFirstManager();

    public void init() {
        shooter = new Shooter(hardwareMap, telemetry, telemetry1);
        intake = new Intake(hardwareMap, telemetry);


    }



    @Override
    public void loop() {
        panelsg1.asCombinedFTCGamepad(gamepad1);

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
        if(gamepad1.dpadLeftWasPressed()) {
            shooter.positionMin();
        }
        if(gamepad1.dpadUpWasPressed()) {
            shooter.increasePositionBare();
        }

        shooter.shooter_telemetry();
        shooter.panelstelemetry();

        telemetry1.getTelemetry().update();


    }
}
