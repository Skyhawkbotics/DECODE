package org.firstinspires.ftc.teamcode.Test_files;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Quadrupe Motor Test", group = "Tests")
public class myquads extends OpMode {
    private DcMotorEx left_front;
    private DcMotorEx left_back;
    private DcMotorEx right_front;
    private DcMotorEx right_back;

    public void init(){
        left_front = hardwareMap.get(DcMotorEx.class, "left_front");
        left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        left_back = hardwareMap.get(DcMotorEx.class, "left_back");
        left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        right_front = hardwareMap.get(DcMotorEx.class, "right_front");
        right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        right_back = hardwareMap.get(DcMotorEx.class, "right_back");
        right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void loop(){
        if (gamepad1.a){
            left_front.setPower(1);
        } else {
            left_front.setPower(0);
        }


        if (gamepad1.b){
            left_back.setPower(1);
        } else {
            left_back.setPower(0);//wrong
        }


        if (gamepad1.y){
            right_front.setPower(1);
        } else {
            right_front.setPower(0);
        }


        if (gamepad1.x){
            right_back.setPower(1);
        } else {
            right_back.setPower(0);//wrong
        }

    }
}
