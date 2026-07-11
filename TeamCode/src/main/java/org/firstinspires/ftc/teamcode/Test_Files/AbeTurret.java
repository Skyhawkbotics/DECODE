package org.firstinspires.ftc.teamcode.Test_Files;

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

    private Servo stopperServo;

    private Servo angleServo;

    private DcMotorEx shooterMotor;

    private PIDController pid;

    private static double p = 0.065, i = 0, d = 0;

    private double power;

    private int target;

    private int servoAngle = 0;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

//    public AbeTurret (PanelsTelemetry panelsTelemety) {
//
//    }
    public void setAngleServoState (int pState) {
        servoAngle = pState;
    }

    @Override
    public void init() {
//        stopperServo = hardwareMap.get(Servo.class, "stopperServo");
//        angleServo = hardwareMap.get(Servo.class, "angleServo");
//        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
//        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        pid = new PIDController (p, i, d);
//        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        turret = new AbeTurretSubsystem(hardwareMap);
    }

    @Override
    public void loop() {

        telemetry.addData("stopperServo position", stopperServo.getPosition());
        telemetry.addData("angleServo position", angleServo.getPosition());
        telemetry.addData("motor power", shooterMotor.getPower());
        telemetry.addData("motor position", shooterMotor.getCurrentPosition());
        telemetry.update();

        panelsTelemetry.getTelemetry().addData("target velocity", target);
        panelsTelemetry.getTelemetry().addData("current velocity", shooterMotor.getVelocity());
        panelsTelemetry.getTelemetry().update();

        turret.AngleServoupdate();
//
//        pid.setPID(p, i, d);
//
//        power = pid.calculate(shooterMotor.getVelocity(), target);
//        shooterMotor.setPower(power);

//        switch (servoAngle) {
//            case 0:
//                if (target >= 1450) {
//                    angleServo.setPosition(0.8);
//                }
//
//        }
//        if (target >= 1500) {
//            angleServo.setPosition(0.8);
//        } else if (target >= 1100){
//            angleServo.setPosition(0.6);
//        } else if (target >= 700) {
//            angleServo.setPosition(0.4);
//        } else {
//            angleServo.setPosition(0.2);
//        }

        // angleServo.setPosition(target * 0.00044);


        //stopperServo open is 0.51
        //stopperServo close is 0.67

        //inequalities allow to run loops multiple times

//        if (gamepad1.right_trigger > 0) {
//            shooterMotor.setPower(1.0);
//        } else {
//            shooterMotor.setPower(0.0);
//        }


        turret.PIDcalc();


        if (gamepad1.dpad_left) {
            target = 1800;
        }

        if (gamepad1.dpad_up) {
            target = 1400;
        }

        if (gamepad1.dpad_right) {
            target = 1000;
        }

        if (gamepad1.dpad_down) {
            target = 600;
        }



//figure out a way to both increase servo position as velocity increases
//turn this into subsystem, max 50 lines
//find a way to filer the sensor/enconder input (low pass filter, kalman filter)



        if (gamepad1.triangleWasPressed() && angleServo.getPosition() <= 0.7) {
            angleServo.setPosition(angleServo.getPosition() + 0.03);
        }
        if (gamepad1.crossWasPressed() && angleServo.getPosition() >= 0.03) {
            angleServo.setPosition(angleServo.getPosition() - 0.03);
        }
        if (gamepad1.circleWasPressed()) {
            stopperServo.setPosition(0.67);
        }
        if (gamepad1.squareWasPressed()) {
            stopperServo.setPosition(0.51);
        }
    }
}
