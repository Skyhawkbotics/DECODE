package org.firstinspires.ftc.teamcode.core.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private DcMotorEx intake_motor;

    public Intake (HardwareMap hardwareMap, Telemetry telemetry){
        intake_motor = hardwareMap.get(DcMotorEx.class, "intake");
        intake_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void on(){
        intake_motor.setPower(0.6);
    }
    public void off(){
        intake_motor.setPower(0);
    }
}
