package org.firstinspires.ftc.teamcode.test_files;


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
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class LL {
    double TAG_HEIGHT = 19;
    private Limelight3A limelight;

    public PIDController pid;

    public static int target;

    public boolean usingLL_correction;

    public LL(HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);

        limelight.start();

        pid = new PIDController(ep, ei, ed);

        telemetry.addData(">", "Robot Ready. Press Play.");
        telemetry.update();
    }

    public void RunLimelight(Telemetry telemetry) {

        LLStatus status = limelight.getStatus();

        telemetry.setMsTransmissionInterval(20);

        telemetry.addData("Name", "%s", status.getName());

        telemetry.addData("LL", "Temp: %.1fc, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());

        telemetry.addData("Pipeline", "Index: %d, Type, %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = limelight.getLatestResult();

        if (result.isValid()) {

            // this pose 3d thing gets the position of the robot
            Pose3D botpose = result.getBotpose();


            // latencies record how long it took limelight to process parts of the frame
            // capture - camera exposure & data capture time
            // target - time spent processing and detecting targets
            // parse - time to format and send data
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();

            telemetry.addData("Capture Latency",
                    captureLatency + targetingLatency);
            // telemetry.addData("Parse Latency", parseLatency);
            // telemetry.addData("PythonOutput",
            java.util.Arrays.toString(result.getPythonOutput());

            // offsets, result.getTxNc() gives us depression angle for calculations
            telemetry.addData("txnc", result.getTxNC());
            telemetry.addData("tync", result.getTyNC());

            // corrected values
            telemetry.addData("tx", result.getTx());
            telemetry.addData("ty", result.getTy());

            // Access apriltag stuff
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f",
                        fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(),
                        fr.getTargetYDegrees());

                // pattern based on ID detected pls work pls pls pls pls ps
                if (fr.getFiducialId() == 21) {
                    telemetry.addData("Pattern", "Green Purple Purple");
                    TAG_HEIGHT = 3;
                } else if (fr.getFiducialId() == 22) {
                    telemetry.addData("Pattern", "Purple Green Purple");
                    TAG_HEIGHT = 3;
                } else if (fr.getFiducialId() == 23) {
                    telemetry.addData("Pattern", "Purple Purple Green");
                    TAG_HEIGHT = 3;
                } else if (fr.getFiducialId() == 24) {
                    telemetry.addData("Pattern", "DEPOT DETECTED");
                    TAG_HEIGHT = 15;
                }

                double y_degrees = fr.getTargetYDegrees(); // vertical offset from crosshair
                // ultimately the problem with this calculation is that the
                // y degrees are skewed somehow.
                // fr.getTargetYDegrees? ok so lowkey theyre the same thing but
                // fr.getTargetYDegrees is newer
                double y_rads = Math.toRadians(y_degrees);
                double horizontalDistance = TAG_HEIGHT / Math.tan(y_rads);
                ///  telemetry.addData("Tag Height", TAG_HEIGHT);
                ///                 telemetry.addData("Y Offset (Radians)", y_rads);
                ///                 telemetry.addData("Tangent of Angle", Math.tan(y_rads));
                ///                 telemetry.addData("Horizontal Distance", horizontalDistance);

            }
        }

        ///  Testing Case: 10/6/25 7:49 PM
        ///  All the results worked, but the values only show up when the april tag is detected.
        ///  However, the apriltag is hard to detect. Maybe adjust how fast the loop goes??
        ///  I'm going to try this statement to slow the telemetry down:
        ///  telemetry.setMsTransmissionInterval(11);

        /// Testing Case: 10/9/25 7:25 PM
        /// The horizontal distance formula seems to based on the angle of elevation,
        ///  not angle of depression with respect to the limelight camera
        ///  Therefore, depot height / tangent of offset ty should be used to calculate
        ///  the horizontal distance. Additionally, the depot height should be actual height
        ///  subtracted by the height of the camera off the ground. Otherwise, it should
        ///  be on the ground so that it works properly.

        ///  TLDR: Set the camera on the ground when calculating. / a constant place
        else {
            telemetry.addData("Limelight", "No Data Available");
        }
        telemetry.update();

    }

    public double getTx(Telemetry telemetry) {
        LLStatus status = limelight.getStatus();
        LLResult result = limelight.getLatestResult();

        double DISTANCE_FROM_MIDDLE = 5;

        if (result.isValid()) {
            telemetry.addData("X Degrees", result.getTx());
            telemetry.addData("Corrected X Degrees", result.getTx() + DISTANCE_FROM_MIDDLE);
            return result.getTx() + DISTANCE_FROM_MIDDLE;
        }
        return 100;
    }

    public void turret_pid_update(Telemetry telemetry, DcMotorEx turret, int ID) {
        if (usingLL_correction) {

            LLStatus status = limelight.getStatus();
            LLResult result = limelight.getLatestResult();

            if (result.isValid()) {

                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    if (fr.getFiducialId() == ID) {
                        pid.setPID(ep, ei, ed);
                        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                        double pid_output = pid.calculate(result.getTx(), 0);
                        telemetry.addData("PID SPEED", pid_output);
                        telemetry.update();
                        turret.setPower(pid_output);

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
        }
    }
    public void LL_correction_ON() {
        usingLL_correction = true;
    }
    public void LL_correction_OFF() {
        usingLL_correction = false;
    }

}
