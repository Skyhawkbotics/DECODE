package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Turret {

    private Limelight3A ll;
    private PIDFController ll_pid, t_pid;

    public double ll_pid_output;

    private DcMotorEx turret;

    private Pose redPose = new Pose(134.5, 135);
    private Pose bluePose = new Pose(9.5, 135);

    private Telemetry telemetry;

    private double LL_buffer = 500;

    private Boolean using_LL = false, manual_turret = false, using_turret = true;

    private Timer timer = new Timer();

    private double turret_offset, correct_turret_offset_position, raw_turret_offset_position;

    private Pose correctPose;
    private double odometry_turret_error, LL_turret_error;

    private double turretPose = 395;
    private double turret_close_pos_BLUE = -369;

    private double turretP, turretD, turretI;


    public Turret(HardwareMap hardwareMap, Telemetry telemetry, int ID) {
        turretP = turret_p;
        turretI = turret_i;
        turretD = turret_d;


        ///  INITIALIZING LIMELIGHT
        ll = hardwareMap.get(Limelight3A.class, "limelight");
        init_pipeline(ID);
        ll.start();

        ///  INITIALIZING PID
        ll_pid = new PIDFController(lp, li, ld, lf);
        ll_pid.setTolerance(LLTolerance);
        t_pid = new PIDController(turret_p, turret_i, turret_d);

        ///  INITIALIZING TURRET MOTOR
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.telemetry = telemetry;
        if (ID == 24) {
            correctPose = redPose;
        } else {
            correctPose = bluePose;
        }
    }
    public void init_pipeline(int ID) {
        if (ID == 20) {
            ll.pipelineSwitch(0); // filter to 20 blue only
        } else if(ID == 24) {
            ll.pipelineSwitch(1); // filter to 24 red only
        }
    }

    ///  24 is red, 20 is blue

    public void turret_update(int ID, Pose followerPose, float manual_input, double LL_target) { ///  if iszero is 1, it will stay normal, if it is 0, it will point toward 0 no matter what

        double ll_pid_output;
        double turretOffset = 1.909449; // 3 inches behind center of robot
        double turretX = followerPose.getX() - turretOffset * Math.cos(followerPose.getHeading());
        double turretY = followerPose.getY() - turretOffset * Math.sin(followerPose.getHeading());

        double dx = correctPose.getX() - turretX;
        double dy = correctPose.getY() - turretY;

// Angle from robot position to goal (field reference)
        double fieldTargetAngle = Math.toDegrees(Math.atan2(dy, dx));

// Robot heading in degrees
        double robotHeading = Math.toDegrees(followerPose.getHeading());

// Turret must compensate for robot heading
        double turretTargetAngle = fieldTargetAngle - robotHeading;

// Normalize to -180 to 180
        turretTargetAngle = normalizeAngle(turretTargetAngle);

        raw_turret_offset_position = degrees_to_position(turretTargetAngle);

        if(!manual_turret) {
                if (using_LL) {
                    LLResult result = ll.getLatestResult();

                    if (result.isValid()) {
                        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                        for (LLResultTypes.FiducialResult fr : fiducialResults) {
                            if (fr.getFiducialId() == ID) {
                                LLTarget = LL_target;
                                timer.resetTimer();
                                ll_pid.setPIDF(lp, li, ld, lf);
                                ll_pid_output = ll_pid.calculate(result.getTx() / 100, LLTarget); // + sign * turret_f;

                                if ((turret.getCurrentPosition() <= left_limit) &&
                                        (Math.signum(ll_pid_output) == -1)) {
                                    turret.setPower(0);
                                } else if ((turret.getCurrentPosition() >= right_limit) &&
                                        (Math.signum(ll_pid_output) == 1)) {
                                    turret.setPower(0);
                                } else {
                                    turret.setPower(ll_pid_output);
                                }
                                LL_turret_error = result.getTx() - LLTarget;
                                telemetry.addData("LL PID OUTPUT", ll_pid_output);
                            }
                        }
                    }
                } else {
                    ///  heading update
                    turretP = turret_p;
                    turretI = turret_i;
                    turretD = turret_d;
                    if (raw_turret_offset_position > right_limit) { // from the back the right of the robot should be negative no?
                        correct_turret_offset_position = right_limit;
                    } else if (raw_turret_offset_position < left_limit) {
                        correct_turret_offset_position = left_limit;
                    } else {
                        correct_turret_offset_position = raw_turret_offset_position;
                    }

                    t_pid.setPIDF(turretP, turretI, turretD, feedforward);
                    turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    double pid_output = t_pid.calculate(turret.getCurrentPosition(), correct_turret_offset_position);
                    turret.setPower(pid_output);
                    odometry_turret_error = correct_turret_offset_position - turret.getCurrentPosition();


                }

        } else {

            manual_input *= -0.5;

            if ((turret.getCurrentPosition() <= left_limit) &&
                    (Math.signum(manual_input) == -1)) {
                turret.setPower(0);
            } else if ((turret.getCurrentPosition() >= right_limit) &&
                    (Math.signum(manual_input) == 1)) {
                turret.setPower(0);
            } else {
                turret.setPower(manual_input);
            }
        }

    }
    public void increaseTurretP() {
        turret_p = turret_p + 0.0005;
    }
    public void decreaseTurretP() {
        turret_p = turret_p - 0.0005;
    }
    private double normalizeAngle(double angle) {
        double normalizedAngle;
        if(angle > 180) {
            normalizedAngle = angle - 360;
        } else if(angle < -180) {
            normalizedAngle = angle + 360;
        } else {
            normalizedAngle = angle;
        }
        return normalizedAngle;
    }
    public void turretcloseZero() {
        turretPose = 0;
    }

    public void turretcloseRED() {
        turretPose = 395;
    }

    ///  Takes the offset in degrees and turn it into a motor encoder reading
    public double degrees_to_position(double degrees) {
        return degrees / 360 * 2246;

    }

    public double position_to_degrees(double position) {
        return (position * 360) /  2246;
    }

    public void toZero() {
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turret.setTargetPosition(0);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
   public void ll_pause() {
        using_LL = false;
   }
   public void ll_start() {
        using_LL = true;
   }

    public void set_using_LL(int ID, boolean gamepadinput) {
        if(gamepadinput) {
            LLResult result = ll.getLatestResult();

            if (result.isValid()) {
                telemetry.addData("LIMELIGHT LOOP WORKING", "");
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    if (fr.getFiducialId() == ID) {
                        timer.resetTimer();
                    }
                }
            }

            if (timer.getElapsedTime() > LL_buffer) {
                using_LL = false;
            } else {
                using_LL = true;
            }
        } else {
            using_LL = false;
        }


    }
    public void set_using_LL(int ID) {
        LLResult result = ll.getLatestResult();

        if (result.isValid()) {
            telemetry.addData("LIMELIGHT LOOP WORKING", "");
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                if (fr.getFiducialId() == ID) {
                    timer.resetTimer();
                }
            }
        }

        if(timer.getElapsedTime() > LL_buffer) {
            using_LL = false;
        } else {
            using_LL = false;
        }


    }

    public void turret_telemetry() {

        telemetry.addData("Using LL", using_LL);
        telemetry.addData("Turret Position", turret.getCurrentPosition());
        telemetry.addData("turret position in degrees", position_to_degrees(turret.getCurrentPosition()));
        telemetry.addData("Filtered turret Offset(position", correct_turret_offset_position);
        telemetry.addData("manual turret", manual_turret);
        telemetry.addData("using turret", using_turret);
        telemetry.addData("Ododmetry turret PID error", odometry_turret_error);
        telemetry.addData("turret power",turret.getPower());
        telemetry.addData("LL Turret PID Erorr", LL_turret_error);

    }

    public void set_power(double p) {
        turret.setPower(p);
    }
    public void reset_turret() {
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void toggle_manual_turret() {
        manual_turret = !manual_turret;
    }
    public boolean IsTurretDone_BLUE() {
        return turret_close_pos_BLUE - turret.getCurrentPosition() < 20;
    }
}
