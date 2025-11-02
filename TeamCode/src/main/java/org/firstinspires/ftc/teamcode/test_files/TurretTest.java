package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Turret Testing")
public class TurretTest extends OpMode {
    private LL limelight;
    public void init() {
        limelight = new LL(hardwareMap, telemetry);
    }

    public void loop() {
        limelight.turret_pid_update(telemetry);
    }
}
