package org.firstinspires.ftc.teamcode.test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;

@TeleOp (name = "Elevator_test", group = "Tests")
public class Elevator_test extends OpMode {
    private Elevator elevator;
    private Intake intake;

    private Shooter shooter;

    @Override
    public void init() {
        elevator = new Elevator(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        shooter = new Shooter(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            elevator.on();
            if (gamepad1.b) {
                elevator.off();
            }
        }  else {
        elevator.off();
        }
        elevator.elevator_telemetry();
        telemetry.update();
        if(gamepad1.dpad_down) {
            intake.on();
        } else if (gamepad1.dpad_left) {
            intake.reverse();
        } else {
            intake.off();
        }
        if(gamepad1.left_bumper) {
            shooter.perf_velocity();
        } else {
            shooter.shooterON();
        }

    }
}
