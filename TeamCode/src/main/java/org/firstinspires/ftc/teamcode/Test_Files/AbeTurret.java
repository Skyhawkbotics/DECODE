package org.firstinspires.ftc.teamcode.Test_Files;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Configurable
@TeleOp(name = "abeturret", group = "abe")
public class AbeTurret extends OpMode {
    private AbeTurretSubsystem turret;

    @Override
    public void init() {
        turret = new AbeTurretSubsystem(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

        turret.AngleServoupdate();

        turret.PIDcalc();

        turret.shootertelemetry();

        if (gamepad1.dpad_left) {
            turret.fourTarget();
        }

        if (gamepad1.dpad_up) {
            turret.threeTarget();
        }

        if (gamepad1.dpad_right) {
            turret.twoTarget();
        }

        if (gamepad1.dpad_down) {
            turret.oneTarget();
        }


//        turret.settingTarget();


//figure out a way to both increase servo position as velocity increases
//turn this into subsystem, max 50 lines
//find a way to filer the sensor/enconder input (low pass filter, kalman filter)

    }
}
