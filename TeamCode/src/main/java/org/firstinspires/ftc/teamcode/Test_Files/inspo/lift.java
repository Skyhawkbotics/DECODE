package org.firstinspires.ftc.teamcode.Test_Files.inspo;



import static org.firstinspires.ftc.teamcode.Test_Files.inspo.Exapmle_Robot_Constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class lift {


    private Telemetry telemetry;

    public DcMotor up1;
    public DcMotor up2;


    public int pos;
    public PIDController pid;
    public static int target;


    public lift(HardwareMap hardwareMap, Telemetry telemetry) { // init method.
        this.telemetry = telemetry;

        up1 = hardwareMap.get(DcMotor.class, up1motorname);
        up1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        up1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        up1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        up2 = hardwareMap.get(DcMotor.class, up2motorname);
        up2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        up2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        up2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        up2.setDirection(DcMotorSimple.Direction.REVERSE);

        pid = new PIDController(ep, ei, ed);
    }
    public void update() { // actual method
            /* PID has many methods
            SetPID sets the Coefficients
            pid.calculate ( pv, sp) , sets the target to sp, and then calculates the pid output required for it to work based on the current position pv
             */

        pid.setPID(ep,ei,ed);

        up1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // Sets it so it floats, no active resistance, active power for pid?
        up2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        double pid_output1 = pid.calculate(up1.getCurrentPosition(), target); // pid usage
        double power1 = pid_output1 + f; // adding f from the pid
        // setting tolerances
        up1.setPower(power1);
        double pid_output2 = pid.calculate(up2.getCurrentPosition(), target); // pid usage
        double power2 = pid_output2 + f; // adding f from the pid
        up2.setPower(power2);

    }
    public void setTarget(int b) {
        target = b;
    }

    public void init() {
        pid.setPID(ep,ei,ed);
    }
    public void to_zero() {
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

        return Math.abs(((up1.getCurrentPosition())) - target) < 170;

    }
    public void telemetry() {
        telemetry.addData("up1 position", up1.getCurrentPosition());
        telemetry.addData("is finished", is_finished());

    }
    public void periodic() {
        update();
        telemetry();
        is_finished();
    }


}
