package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Claw", group = "abe")
public class AbeClaw extends OpMode {

    private Servo servo;
    private ColorSensor color;

    private Boolean world;

    private int clawstate = 0;
    public void setClawState (int pState) {
        clawstate = pState;
    }

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo");
        color = hardwareMap.get(ColorSensor.class, "color");
    }

    @Override
    public void loop() {

        world = false;
        telemetry.addData("Servo Position", servo.getPosition());
        telemetry.update();
        telemetry.addData("red", color.red());
        telemetry.update();
        telemetry.addData("green", color.green());
        telemetry.update();
        telemetry.addData("blue", color.blue());
        telemetry.update();

        //open 0.6
        //close 0.27

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
               if (gamepad1.squareWasPressed()) {
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
        }


//        if (gamepad1.triangleWasPressed()) {
//            servo.setPosition(0.6);
//        }
//        if (gamepad1.squareWasPressed()) {
//            servo.setPosition(0.27);
//        }
//        if (gamepad1.crossWasPressed()) {
//            servo.setPosition(servo.getPosition() + 0.03);
//        }
//        if (gamepad1.circleWasPressed()) {
//            servo.setPosition(servo.getPosition() - 0.03);
//        }

}
