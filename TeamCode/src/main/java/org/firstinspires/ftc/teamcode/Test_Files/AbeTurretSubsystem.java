package org.firstinspires.ftc.teamcode.Test_Files;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
public class AbeTurretSubsystem {
    private Servo stopperServo;
    private Servo angleServo;
    private DcMotorEx shooterMotor;
    private PIDController pid;
    private static double p = 0, i = 0, d = 0;
    private double power;
    private int target;


    private DcMotorEx otherShooterMotor;

    private Telemetry telemetry;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;
    public AbeTurretSubsystem (HardwareMap hardwareMap, Telemetry tel) {
        stopperServo = hardwareMap.get(Servo.class, "stopperServo");

        angleServo = hardwareMap.get(Servo.class, "angleServo");

        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        otherShooterMotor = hardwareMap.get(DcMotorEx.class, "otherShooterMotor");
        otherShooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        otherShooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        pid = new PIDController (p, i, d);

        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        otherShooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.telemetry = tel;
    }


    public void PIDcalc() {
        pid.setPID(p, i, d);

        power = pid.calculate(shooterMotor.getVelocity(), target);

        otherShooterMotor.setPower(power);
        shooterMotor.setPower(power);
    }
    public void AngleServoupdate() {
        if (target >= 1500) {
            angleServo.setPosition(0.8);
        } else if (target >= 1100){
            angleServo.setPosition(0.6);
        } else if (target >= 700) {
            angleServo.setPosition(0.4);
        } else {
            angleServo.setPosition(0.2);
        }
    }

    public void twoTarget() {
        target = 1000;
    }
    public void threeTarget() {
        target = 1400;
    }
    public void fourTarget() {
        target = 1800;
    }
    public void oneTarget() {
        target = 600;
    }

    public void shootertelemetry() {
        telemetry.addData("stopperServo position", stopperServo.getPosition());
        telemetry.addData("angleServo position", angleServo.getPosition());
        telemetry.addData("motor power", shooterMotor.getPower());
        telemetry.addData("motor position", shooterMotor.getCurrentPosition());
        telemetry.update();

        panelsTelemetry.getTelemetry().addData("target velocity", target);
        panelsTelemetry.getTelemetry().addData("current velocity", shooterMotor.getVelocity());
        panelsTelemetry.getTelemetry().update();
    }
}
