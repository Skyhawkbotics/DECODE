package org.firstinspires.ftc.teamcode.Test_Files;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "BetterClawTest", group = "abe")
@Configurable
public class AbeBetterClaw extends OpMode {
    // into the deep, specimens, hanging on the bar, picking up the specimens on the human zone wall
    // same orientation, flips specimen

    //three servo

    //one servo for claw
    //opposite diretions will rotate
    //same diretion for

    //color sensor

    //angular constraint
    //servo only 0-1, no constant rotations
    //wiring

    // starting on one side
    // opening claw if color is detected
    // use color sensor to close on right color
    // right servo the rest of the half way
    // right and left servo move same direction half way
    //
    //

    private AbeBetterClawSubsystem claw;


    @Override
    public void init() {
        claw = new AbeBetterClawSubsystem(hardwareMap, telemetry);

    }

    @Override
    public void loop() {

        claw.clawtelemetry();
        claw.thesuitcase(gamepad1);
        claw.controls(gamepad1);




//        if (gamepad1.circleWasPressed()) {
//            servoClaw.setPosition(open);
//        }
//        if (gamepad1.crossWasPressed()) {
//            servoRight.setPosition(halfway);
//            /// flips servo and moves arm halfway
//            servoRight.setPosition(fullway - halfway);
//            servoLeft.setPosition(halfway);
//            /// moves together all the way to the other side
//        }


    }
}
