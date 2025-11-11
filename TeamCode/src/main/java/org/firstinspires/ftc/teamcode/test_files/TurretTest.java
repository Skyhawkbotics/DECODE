package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Turret;
@TeleOp (name = "turret test", group = "tests")
public class TurretTest extends OpMode {
    private Turret turret;

    @Override
    public void init() {
        turret = new Turret(hardwareMap, telemetry, 21);
    }

    @Override
    public void loop() {
        if(gamepad1.left_stick_y != 0) {
            turret.manual_move(gamepad1);
        }

    }
}
