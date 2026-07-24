package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
@TeleOp(name = "Claw", group = "abe")
public class AbeClaw extends OpMode {

    private AbeClawSubsystem claw;


    @Override
    public void init() {
        claw = new AbeClawSubsystem(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        claw.clawTelemetry();

        //open 0.6

        //close 0.27

        claw.thesuitcase(gamepad1);

        claw.controls(gamepad1);
    }
}


// add PIDs for slides, set positions (fourths), so we don't have to manually go to a certain position
// whenever magnet sensor is turned on, that enconder position is then zero




