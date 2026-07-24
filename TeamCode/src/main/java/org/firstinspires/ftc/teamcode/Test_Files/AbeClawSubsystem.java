package org.firstinspires.ftc.teamcode.Test_Files;


import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
public class AbeClawSubsystem {
    private Servo servo;
    private ColorSensor color;
    private Telemetry telemetry;

    private Boolean world;

    private int clawstate = 0, bluestate = 0;

    private PIDController pid;
    private static double p = 0, i = 0, d = 0;

    public int ClawState() {
        return clawstate;
    }

    public int Bluestate() {
        return bluestate;
    }

    public void setBlueState(int pState) {
        bluestate = pState;
    }

    public void setClawState (int pState) {
        clawstate = pState;
    }
    public void closeclaw() {
        servo.setPosition(0.3);
    }

    public void thesuitcase (Gamepad g1) {
        switch (bluestate) {
            case 0:
                switch(clawstate) {
                    case 0:
                        if(color.red() > 1200 && color.green() < 1000) {
                            setClawState(1);
                        } else {
                            servo.setPosition(0.6);
                        }
                        break;
                    case 1:
                        servo.setPosition(0.3);
                        if (g1.squareWasPressed()) {
                            setClawState(2);
                        }
                        break;
                    case 2:
                        servo.setPosition(0.6);
                        if(color.red() < 100) {
                            setClawState(0);
                        }
                        break;
                }
            case 1:
                switch(clawstate) {
                    case 0:
                        if(color.blue() > 1200 && color.red() < 1000) {
                            setClawState(1);
                        } else {
                            servo.setPosition(0.6);
                        }
                        break;
                    case 1:
                        servo.setPosition(0.3);
                        if (g1.squareWasPressed()) {
                            setClawState(2);
                        }
                        break;
                    case 2:
                        servo.setPosition(0.6);
                        if(color.blue() < 100) {
                            setClawState(0);
                        }
                        break;
                }
        }
    }

    public void controls (Gamepad g1) {
        if(g1.touchpadWasPressed()) {
            if(bluestate == 1) {
                setBlueState(0);
            } else if(bluestate == 0) {
                setBlueState(1);
            }
        }
    }

    public AbeClawSubsystem (HardwareMap hardwareMap, Telemetry telemetry) {
        servo = hardwareMap.get(Servo.class, "servo");
        color = hardwareMap.get(ColorSensor.class, "color");

        this.telemetry = telemetry;

        pid = new PIDController (p, i, d);

    }

    public void PIDcalc() {
        pid.setPID(p, i, d);

    }

    public void casezero () {
        if(color.red() > 1200 && color.green() < 1000) {
            setClawState(1);
        } else {
            servo.setPosition(0.6);
        }
    }

    public void casetwo () {
        servo.setPosition(0.6);
        if(color.red() < 100) {
            setClawState(0);
        }
    }

    public void secondcasezero() {
        if(color.blue() > 1200 && color.red() < 1000) {
            setClawState(1);
        } else {
            servo.setPosition(0.6);
        }
    }

    public void secondcasetwo() {
        servo.setPosition(0.6);
        if(color.blue() < 100) {
            setClawState(0);
        }
    }
    public void clawTelemetry () {
        world = false;
        telemetry.addData("Servo Position", servo.getPosition());
        telemetry.update();
        telemetry.addData("red", color.red());
        telemetry.update();
        telemetry.addData("green", color.green());
        telemetry.update();
        telemetry.addData("blue", color.blue());
        telemetry.update();
    }
}
