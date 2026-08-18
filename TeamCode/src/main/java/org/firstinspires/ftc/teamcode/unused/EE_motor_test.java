package org.firstinspires.ftc.teamcode.unused;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Single Motor Test", group = "z")

public class EE_motor_test extends OpMode {
    private DcMotorEx motor1;


    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motor1.setPower(1);
    }
    public void loop() {
        telemetry.addData("Motor Current Power", motor1.getPower());
        telemetry.addData("Motor Current Position", motor1.getCurrentPosition());
        telemetry.update();

        //if (gamepad1.aWasPressed()) {
        //    motor1.setPower(0);
        //}

        //if (gamepad1.bWasPressed()) {
            //motor1.setPower(1);
        //}

    }
}


