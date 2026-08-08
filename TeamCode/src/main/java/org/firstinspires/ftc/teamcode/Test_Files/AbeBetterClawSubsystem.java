package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
public class AbeBetterClawSubsystem {

    private Servo servoRight, servoLeft, servoClaw;

    private ColorSensor color;

    private Telemetry telemetry;

    private Boolean world;

    private int clawstate = 0, bluestate = 0;

    public void setBlueState(int pState) {
        bluestate = pState;
    }

    public void setClawState (int pState) {
        clawstate = pState;
    }

    public AbeBetterClawSubsystem (HardwareMap hardwareMap, Telemetry telemetry) {
        servoRight = hardwareMap.get(Servo.class, "servoRight");

        servoLeft = hardwareMap.get(Servo.class, "servoLeft");

        servoClaw = hardwareMap.get(Servo.class, "servoClaw");

        color = hardwareMap.get(ColorSensor.class, "color");

        servoLeft.setDirection(Servo.Direction.REVERSE);

        servoRight.setPosition(0);

        servoLeft.setPosition(0);

        this.telemetry = telemetry;

    }

    // hw: automate evrything so once claw is closed then flip over immeditely and 180
    //then press button to open claw, when it does that, automatically flip back to zero

    public void thesuitcase (Gamepad g1) {
        switch (bluestate) {
            case 0:
                switch(clawstate) {
                    case 0:
                        if(color.red() > 1200 && color.green() < 1000) {
                            setClawState(1);
                        } else {
                            servoClaw.setPosition(0.6);
                        }
                        break;
                    case 1:
                        servoClaw.setPosition(0.3);
                        if (g1.squareWasPressed()) {
                            setClawState(2);
                        }
                        break;
                    case 2:
                        servoClaw.setPosition(0.6);
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
                            servoClaw.setPosition(0.6);
                        }
                        break;
                    case 1:
                        servoClaw.setPosition(0.3);
                        if (g1.squareWasPressed()) {
                            setClawState(2);
                        }
                        break;
                    case 2:
                        servoClaw.setPosition(0.6);
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

        if (g1.crossWasPressed()) {
            servoRight.setPosition(0.27);
            servoLeft.setPosition(0.27+0.65);

        }

        if (g1.dpadUpWasPressed()) {
            servoRight.setPosition(servoRight.getPosition() + 0.2);
            servoLeft.setPosition(servoLeft.getPosition() + 0.2);
//            servoLeft.setPosition(-1 * servoRight.getPosition());
//            servoLeft.setPosition(servoLeft.getPosition() + 0.03);
        }

        if (g1.dpadRightWasPressed()) {
            servoRight.setPosition(servoRight.getPosition() - 0.2);
            servoLeft.setPosition(servoLeft.getPosition() - 0.2);
//            servoRight.setPosition(servoRight.getPosition() - 0.03);
//            servoLeft.setPosition(-1 * servoRight.getPosition());
//            servoLeft.setPosition(servoLeft.getPosition() - 0.03);
        }

        if (g1.dpadDownWasPressed()) {
            servoLeft.setPosition(servoLeft.getPosition() + 0.03);
//            servoRight.setPosition(-1*servoLeft.getPosition());
        }

        if (g1.dpadLeftWasPressed()) {
            servoLeft.setPosition(servoLeft.getPosition() - 0.03);
//            servoRight.setPosition(-1*servoLeft.getPosition());
        }
    }

    public void clawtelemetry() {
        telemetry.addData("Servo left Position", servoLeft.getPosition());
        telemetry.addData("Servo right Position", servoRight.getPosition());
        telemetry.addData("Servo claw Position", servoClaw.getPosition());

        telemetry.addData("red", color.red());

        telemetry.addData("green", color.green());

        telemetry.addData("blue", color.blue());
        telemetry.update();
    }

}
