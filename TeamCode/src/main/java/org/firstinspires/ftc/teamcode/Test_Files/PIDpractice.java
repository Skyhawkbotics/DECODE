package org.firstinspires.ftc.teamcode.Test_Files;

import static org.firstinspires.ftc.teamcode.core.constants.myd;
import static org.firstinspires.ftc.teamcode.core.constants.myf;
import static org.firstinspires.ftc.teamcode.core.constants.myi;
import static org.firstinspires.ftc.teamcode.core.constants.myp;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.bylazar.telemetry.PanelsTelemetry;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDpractice {

    public PIDFController pid;

    public static int target;

    private Telemetry auggimetry;

    private DcMotorEx motor;

    private double power;

    private boolean is_updating;

    private double slide_position;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public PIDpractice (HardwareMap cat, Telemetry auggie) {

        motor = cat.get(DcMotorEx.class, "slide up");
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior (DcMotorEx.ZeroPowerBehavior.FLOAT);

        pid = new PIDFController(myp, myi, myd, myf);
        this.auggimetry = auggie;

    }
    public void update() {

        pid.setPIDF(myp,myi,myd,myf);

        double pid_output = pid.calculate(motor.getCurrentPosition(), 100);

        power = pid_output;

        motor.setPower(power);


    }

}
