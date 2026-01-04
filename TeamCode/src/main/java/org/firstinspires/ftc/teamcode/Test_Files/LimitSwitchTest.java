package org.firstinspires.ftc.teamcode.Test_Files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "Limit Switch Test")
public class LimitSwitchTest extends OpMode {
    private DigitalChannel digIn;

    public void init() {
        digIn = hardwareMap.get(DigitalChannel.class, "digIn");
        telemetry.addData("Initialization successful. Press Start.", "");
    }

    public void loop() {
        telemetry.addData("Is Pressed", digIn.getState());
        telemetry.update();
    }
}
