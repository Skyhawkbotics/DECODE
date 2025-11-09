package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;

@TeleOp(name = "Intake_test", group = "Tests")
public class Intake_test extends OpMode {

    private Intake intake;


    @Override
    public void init() {
         intake = new Intake(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_down) {
            intake.on();
        }
            else if (gamepad1.dpad_up) {
                intake.off();

            }
        }

    }

