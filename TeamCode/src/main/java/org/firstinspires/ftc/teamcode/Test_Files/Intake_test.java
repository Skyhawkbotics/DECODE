package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Intake;

@TeleOp(name = "Intake_test", group = "Z")
@Disabled
public class Intake_test extends OpMode {

    private Intake intake;

    boolean button_pressed;
    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;



    @Override
    public void init() {
         intake = new Intake(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

           if(gamepad1.cross) {
               intake.intake();
           } else if(gamepad1.triangle) {
               intake.fire();
           } else {
               intake.off();
           }
        }

    }

