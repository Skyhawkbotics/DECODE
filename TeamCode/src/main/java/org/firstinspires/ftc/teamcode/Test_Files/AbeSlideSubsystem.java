package org.firstinspires.ftc.teamcode.Test_Files;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
public class AbeSlideSubsystem {

    private DcMotorEx motor;

    private PIDFController pidf;

    private int minpos = 0, lowpos = 800, highpos = 1600, maxpos = 2200;

    private static double p = 0.0019, i = 0.034, d = 0, f = 0;

    private TouchSensor magnet;

    private Telemetry telemetry;

    private double power;

    private int target;

    private boolean magnetReset = false;

    private static double slopevalue = 0.008;

    private static double bvalue = -0.18;

    private boolean runToZero = false;

    private boolean powerWorlds;

    private int recoverWorld = 1, pidWorld = 0;

    public void setRecoverWorld(int pState) {
        recoverWorld = pState;
    }

    public void setPIDWorld(int pState) {
        pidWorld = pState;
    }

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public AbeSlideSubsystem(HardwareMap hardwareMap, Telemetry telemetry, PanelsTelemetry panelsTelemetry) {

        motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        magnet = hardwareMap.get(TouchSensor.class, "magnet");

        pidf = new PIDController(p, i, d);

        this.telemetry = telemetry;
        this.panelsTelemetry = panelsTelemetry;
    }

//    public void PIDcalc() {
//        pidf.setPIDF(p, i, d, f);
//
//        power = pidf.calculate(motor.getCurrentPosition(), target);
//
//        motor.setPower(power);
//    }

    public void slideTelemetry() {
        telemetry.addData("motor position", motor.getCurrentPosition());
        telemetry.addData("magnet sensor", magnet.isPressed());
        telemetry.addData("motor power", motor.getPower());
        panelsTelemetry.getTelemetry().addData("target position", target);
        panelsTelemetry.getTelemetry().addData("current position", motor.getCurrentPosition());
        panelsTelemetry.getTelemetry().update();
        telemetry.update();
    }



    //y = mx + b
    public void slideGamepad(Gamepad g1) {
//        if (g1.dpadUpWasPressed() && !magnet.isPressed()) {
//            switch (recoverWorld) {
//                case 0:
//                    if (runToZero) {
//                        double recover = -(motor.getCurrentPosition() * bvalue) + slopevalue;
//                        motor.setPower(recover);
//                    } else if (magnet.isPressed()) {
//                        runToZero = false;
//                    }
//                    break;
//                case 1:
//
//
//            }
//            pidf.setPIDF(0, 0, 0, 0);
//            runToZero = true;
//        }

        if (g1.dpadRightWasPressed()) {
            target = lowpos;
        }
        if (g1.dpadDownWasPressed()) {
            target = highpos;
        }
        if (g1.dpadLeftWasPressed()) {
            target = maxpos;
        }
    }

    public void suitcase(Gamepad g1) {

        if (g1.dpadUpWasPressed()) {
            target = minpos;
            setRecoverWorld(0);
        }

        switch (recoverWorld) {
            case 0:
               // motor.setPower(power);
                double recover = -(motor.getCurrentPosition() * slopevalue) + bvalue;
                motor.setPower(recover);

                if (magnet.isPressed()) {
                    setRecoverWorld(1);
                    motor.setPower(0);
                } else if (g1.dpadRightWasPressed()) {
                    target = lowpos;
                    motor.setPower(0);
                    setRecoverWorld(1);
                } else if (g1.dpadDownWasPressed()) {
                    target = highpos;
                    motor.setPower(0);
                    setRecoverWorld(1);
                } else if (g1.dpadLeftWasPressed()) {
                    target = maxpos;
                    motor.setPower(0);
                    setRecoverWorld(1);
                }
                break;
            case 1:
                pidf.setPIDF(p, i, d, f);

                power = pidf.calculate(motor.getCurrentPosition(), target);

                motor.setPower(power);

                break;
        }
    }
        public void RunToZeroSystem () {
            if (runToZero) {
                double recover = -(motor.getCurrentPosition() * bvalue) + slopevalue;
                motor.setPower(recover);
            } else if (magnet.isPressed()) {
                runToZero = false;
            }
        }
        public void zeroSensor () {
            if (magnet.isPressed() && !magnetReset) {
                motor.setPower(0);
                motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                magnetReset = true;
            } else if (!magnet.isPressed()) {
                magnetReset = false;
            }
        }
    }

//run through scenarios
// toggle on and offs
// everything in cases? THE SUITCASE
//
