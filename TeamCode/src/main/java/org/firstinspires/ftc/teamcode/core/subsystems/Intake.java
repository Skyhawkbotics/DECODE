package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.intake_speed;

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
        telemetry.addData("Motor Current Power", intake_motor.getPower());
    }

    public void on(){
        intake_motor.setPower(intake_speed);
    }
    public void off(){
        intake_motor.setPower(0);
    }
}
