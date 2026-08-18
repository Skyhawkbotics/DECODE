package org.firstinspires.ftc.teamcode.Test_Files;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp (name = "slideTest", group = "abe")
public class AbeSlideTest extends OpMode {
    private AbeSlideSubsystem slide;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;


    @Override
    public void init() {
        slide = new AbeSlideSubsystem(hardwareMap, telemetry, panelsTelemetry);
    }

    @Override
    public void loop() {
        slide.slideGamepad(gamepad1);
        slide.zeroSensor();
        slide.suitcase(gamepad1);
        slide.slideTelemetry();
        slide.RunToZeroSystem();
    }
}

//subsystem
//sensor reset position 0
