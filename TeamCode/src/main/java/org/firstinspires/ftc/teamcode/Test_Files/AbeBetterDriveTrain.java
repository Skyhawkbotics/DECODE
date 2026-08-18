package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "BetterDriveTrain", group = "abe")
@Configurable
public class AbeBetterDriveTrain extends OpMode {

    private AbeBetterDriveTrainSubsystem drive;

    @Override
    public void init() {

        drive = new AbeBetterDriveTrainSubsystem(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        drive.testDriveControls (gamepad1);
        drive.driveTelemetry();

    }
}
