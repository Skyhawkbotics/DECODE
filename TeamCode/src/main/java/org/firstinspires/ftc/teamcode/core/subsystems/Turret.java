package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.test_files.LL;

public class Turret {
    private LL limelight;
    public DcMotorEx turret;
    public Turret(HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = new LL(hardwareMap, telemetry);
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void LL_Align(Telemetry telemetry) {
        limelight.turret_pid_update(telemetry, turret);
    }

    public void N_Align(Telemetry telemetry) {

    }
}
