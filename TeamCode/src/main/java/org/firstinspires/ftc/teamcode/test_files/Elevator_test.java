package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;

@TeleOp (name = "Elevator_test", group = "Tests")
public class Elevator_test extends OpMode {
    private Elevator eric;

    @Override
    public void init() {
        eric = new Elevator(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            eric.on();
        } else if (gamepad1.b) {
            eric.on_reverse();
        } else {
            eric.off();
        }
    }
}
