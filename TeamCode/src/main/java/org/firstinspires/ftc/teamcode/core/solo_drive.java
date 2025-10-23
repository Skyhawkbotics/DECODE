package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (name = "Solo Drive", group = "Opmode")

public class solo_drive extends OpMode {
    private whole_robot clanker;
    @Override
    public void init() {
        clanker = new whole_robot(hardwareMap, telemetry, gamepad1, gamepad2);

    }

    @Override
    public void loop() {
        clanker.solo_controls();
        clanker.robot_update();

    }
}
