package org.firstinspires.ftc.teamcode.Test_Files;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name = "opmode")
@Disabled
public class SlideTest extends OpMode {

    public DcMotorEx motor;

    public PIDFController pid;

    public static int target;

    private Telemetry auggimetry;

    private double power;

    private boolean is_updating;

    private double slide_position;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;


    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "slidetest");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        telemetry.addData("Motor current power", motor.getPower());

        telemetry.addData("Motor Current position", motor.getCurrentPosition());

        telemetry.update();

        int max = Math.abs(2800);

        int min = Math.abs(400);

        int current = Math.abs(motor.getCurrentPosition());

/// if going up &&&& not above te up limit = salllow to go up
        if (gamepad1.triangle && current < max) {
            motor.setPower(0.25);
            motor.setTargetPosition(2000);
        } else if (gamepad1.circle && current > min) {
            motor.setPower(-0.25);
            motor.setTargetPosition(350);
        } else {
            motor.setPower(0);
        }
    }
}
