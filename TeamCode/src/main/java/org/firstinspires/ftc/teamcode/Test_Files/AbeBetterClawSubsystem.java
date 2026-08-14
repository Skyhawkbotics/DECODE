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

    private double servoPos;

    private double servoRotation;

    private static double degree_to_servo_pos_shaft = 0.1, degree_to_servo_pos_rotation = 0.1;

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

    public void clawArmAutomation (int shaftPosDegrees, int rotationDegree) {
            servoPos = shaftPosDegrees * degree_to_servo_pos_shaft;
            servoRotation = rotationDegree * degree_to_servo_pos_rotation;
            servoRight.setPosition(servoPos);
            servoLeft.setPosition(servoPos + servoRotation);
    }

    public void armZero () {
        servoRight.setPosition(0);
        servoLeft.setPosition(0);
    }

    public void servoOpen () {
        servoClaw.setPosition(0.6);
    }

    public void servoClose () {
        servoClaw.setPosition(0.3);
    }

    public void degreeMethod () {

    }

    public void thesuitcase (Gamepad g1) {
        switch (bluestate) {
            case 0:
                switch(clawstate) {
                    case 0:
                        if(color.red() > 1200 && color.green() < 1000) {
                            setClawState(1);

                        } else {
                            servoOpen();

                        }
                        break;
                    case 1:
                        servoClose();

                        clawArmAutomation(90,180);

                        if (g1.squareWasPressed()) {
                            setClawState(2);

                        }
                        break;
                    case 2:
                        servoOpen();
                        armZero();

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
                            servoOpen();

                        }
                        break;
                    case 1:
                        servoClose();

                        clawArmAutomation(90,180);

                        if (g1.squareWasPressed()) {
                            setClawState(2);

                        }
                        break;
                    case 2:
                        servoOpen();

                        armZero();

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

    public void clawtelemetry() {
        telemetry.addData("Servo left Position", servoLeft.getPosition());
        telemetry.addData("Servo right Position", servoRight.getPosition());
        telemetry.addData("Servo claw Position", servoClaw.getPosition());

        telemetry.addData("claw state", bluestate);

        telemetry.addData("red", color.red());

        telemetry.addData("green", color.green());

        telemetry.addData("blue", color.blue());
        telemetry.update();
    }

}
