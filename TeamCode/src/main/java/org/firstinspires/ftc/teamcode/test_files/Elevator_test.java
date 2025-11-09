package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;

@TeleOp (name = "Elevator_test", group = "Tests")
public class Elevator_test extends OpMode {
    private Elevator elevator;

    @Override
    public void init() {
        elevator = new Elevator(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            elevator.on();
        } else if (gamepad1.b) {
            elevator.on_reverse();
        } else {
            elevator.off();
        }
    }
}
