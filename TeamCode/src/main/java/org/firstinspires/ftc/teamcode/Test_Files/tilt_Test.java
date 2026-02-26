package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.subsystems.Tilt;

@TeleOp (name = "tilt test", group = "Z")
public class tilt_Test extends OpMode {
    private Tilt tilt;
    @Override
    public void init() {
        tilt = new Tilt(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        if(gamepad1.dpadUpWasPressed()) {
            tilt.tiltZERO();
        }
        if(gamepad1.dpadDownWasPressed()) {
            tilt.tiltREAL();
        }
        if(gamepad1.triangleWasPressed()) {
            tilt.tilt1FULL();
        }
        if(gamepad1.crossWasPressed()) {
            tilt.tilt1Zero();
        }
        if(gamepad1.squareWasPressed()) {
            tilt.tilt2FULL();
        }
        if(gamepad1.circleWasPressed()) {
            tilt.tilt2Zero();
        }
        tilt.tilt_telemetry();
        telemetry.update();
    }
}
