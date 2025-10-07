package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "limelight")
public class LLtest extends OpMode {

    private Limelight3A limelight;
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);

        limelight.start();

        telemetry.addData(">", "Robot Ready. Press Play.");
        telemetry.update();
    }

    public void loop() {
        LLStatus status = limelight.getStatus();

        telemetry.addData("Name", "%s", status.getName());

        telemetry.addData("LL", "Temp: %.1fc, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int)status.getFps());

        telemetry.addData("Pipeline", "Index: %d, Type, %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = limelight.getLatestResult();

        if (result.isValid)
            telemetry.addData("txnc", result.getTxNC());
            telemetry.addData("tynx", result.getTyNC());

    }



}
