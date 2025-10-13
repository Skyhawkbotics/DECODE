package org.firstinspires.ftc.teamcode.unused;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorSpinSubsystem {
    private DcMotorEx motor;

    private Telemetry telemetry;

    public MotorSpinSubsystem (HardwareMap h, Telemetry t){
        motor = h.get(DcMotorEx.class, "spin");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void turnon(){
        motor.setPower(1);
    }
    public void turnoff(){
        motor.setPower(0);
    }
    public void turnback(){
        motor.setPower(-1);
    }

}
