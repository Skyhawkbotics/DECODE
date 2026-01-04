package org.firstinspires.ftc.teamcode.core.subsystems;


import static org.firstinspires.ftc.teamcode.core.constants.ed;
import static org.firstinspires.ftc.teamcode.core.constants.ei;
import static org.firstinspires.ftc.teamcode.core.constants.ep;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.Arrays;
import java.util.List;

public class LL {
    double TAG_HEIGHT = 19;
    private Limelight3A limelight;

    public PIDController pid;

    public static int target;

    private double lp = 0.02;

    private double li = 0;

    private double ld = 0.00007;

    private Telemetry telemetry;

    public LL(HardwareMap hardwareMap, Telemetry t) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);

        limelight.start();

        pid = new PIDController(lp, li, ld);

        this.telemetry = t;

        telemetry.addData(">", "Robot Ready. Press Play.");
        telemetry.update();
    }

    public double turret_pid_update(Telemetry telemetry, DcMotorEx turret, int ID) {
        double pid_output = 0;
            LLStatus status = limelight.getStatus();
            LLResult result = limelight.getLatestResult();
            if (result.isValid()) {

                telemetry.addData("LIMELIGHT LOOP WORKING", "");
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    if (fr.getFiducialId() == ID) {
                        pid.setPID(lp, lp, ld);
                        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                        pid_output = pid.calculate(result.getTx(), 0);
                        telemetry.addData("PID SPEED", pid_output);
                        telemetry.update();


                        telemetry.addData("X Degrees", result.getTx());

                        if (result.getTx() < -3) {
                            telemetry.addData("TX Offset", "Negative");
                        } else if (result.getTx() > 3) {
                            telemetry.addData("Tx Offset", "Positive");
                        } else {
                            telemetry.addData("Tx Offset", "Aligned");
                        }
                    }
                }
            }
            telemetry.update();
        return pid_output;
    }
}
