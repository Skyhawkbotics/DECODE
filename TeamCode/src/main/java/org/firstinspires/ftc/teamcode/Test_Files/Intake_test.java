package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Intake;

@TeleOp(name = "Intake_test", group = "Z")
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

            if (gamepad1.dpad_down) {
                button_pressed = !button_pressed;
                /// !button_pressed means the opposite of button_pressed
            }
            if(button_pressed) {
                intake.on();
            } else {
                intake.off();

            }
        }

    }

