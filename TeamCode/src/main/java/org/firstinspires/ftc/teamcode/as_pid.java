package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.inspo.Exapmle_Robot_Constants.ed;
import static org.firstinspires.ftc.teamcode.inspo.Exapmle_Robot_Constants.ei;
import static org.firstinspires.ftc.teamcode.inspo.Exapmle_Robot_Constants.ep;
import static org.firstinspires.ftc.teamcode.inspo.Exapmle_Robot_Constants.f;
import static org.firstinspires.ftc.teamcode.inspo.Exapmle_Robot_Constants.max;
import static org.firstinspires.ftc.teamcode.inspo.Exapmle_Robot_Constants.zero;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class as_pid {
    private Telemetry telemetry;
    public DcMotorEx baby;
    public PIDController pid;
    public static int target;

    public as_pid(HardwareMap hardwaremap, Telemetry telemetry){
        this.telemetry = telemetry;

        baby = hardwaremap.get(DcMotorEx.class,"motor");
        baby.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        baby.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);


        pid = new PIDController(ep, ei, ed);
    }

    public void update (){
        pid.setPID(ep, ei, ed);
        baby.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        double pid_output = pid.calculate(baby.getCurrentPosition(), target);
        double power = pid_output + f;
        baby.setPower(power);
    }

    public void setTarget(int b){
        target = b;
    }

    public void init(){
        pid.setPID(ep, ei, ed);
    }

    public void set_zero(){
        setTarget(zero);
    }

    public void to_third() {
        setTarget(max/3);
    }
    public void to_half() {
        setTarget(max/2);
    }
    public void to_two_third() {
        setTarget(2*max/3);
    }
    public void to_full() {
        setTarget(max);
    }

    public Boolean is_finished() {
        return Math.abs(((baby.getCurrentPosition())) - target) < 170;
    }

    public void telemetry(){
        telemetry.addData("s.motor position", baby.getCurrentPosition());
        telemetry.addData("is finished", is_finished());
    }

    public void periodic() {
        update();
        telemetry();
        is_finished();
    }
}
