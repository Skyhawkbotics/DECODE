package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
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
    private PIDController ll_pid, t_pid;

    public double ll_pid_output;

    private DcMotorEx turret;

    private Pose redPose = new Pose(132.47821849779123, 138.31011723787793, 0);
    private Pose bluePose = new Pose(11, 135,0);

    private Telemetry telemetry;

    private double LL_buffer = 250;

    private Boolean using_LL = true, manual_turret = false, using_turret = true;

    private Timer timer = new Timer();

    private double turret_offset;

    private double raw_turret_offset_position;
    private double correct_turret_offset_position;

    private double angle;

    private double correct_angle;

    private double robot_offset_to_zero;

    private Pose correctPose;

    public Turret(HardwareMap hardwareMap, Telemetry telemetry) {

        ///  INITIALIZING LIMELIGHT
        ll = hardwareMap.get(Limelight3A.class, "limelight");
        ll.pipelineSwitch(0);
        ll.start();

        ///  INITIALIZING PID
        ll_pid = new PIDController(lp, li, ld);
        ll_pid.setTolerance(0.5);
        t_pid = new PIDController(turret_p, turret_i, turret_d);

        ///  INITIALIZING TURRET MOTOR
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.telemetry = telemetry;
    }

    ///  24 is red, 20 is blue

    public void turret_update(int ID, Pose followerPose, float manual_input) { ///  if iszero is 1, it will stay normal, if it is 0, it will point toward 0 no matter what

        double ll_pid_output;

        if(!manual_turret) {
                if (using_LL) {
                    LLResult result = ll.getLatestResult();

                    if (result.isValid()) {
                        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                        for (LLResultTypes.FiducialResult fr : fiducialResults) {
                            if (fr.getFiducialId() == ID) {
                                timer.resetTimer();
                                ll_pid.setPID(lp, li, ld);
                                double sign = Math.signum(-result.getTx());
                                ll_pid_output = ll_pid.calculate(result.getTx(), 0); // + sign * turret_f;

                                if ((turret.getCurrentPosition() <= left_limit) &&
                                        (Math.signum(ll_pid_output) == -1)) {
                                    turret.setPower(0);
                                } else if ((turret.getCurrentPosition() >= right_limit) &&
                                        (Math.signum(ll_pid_output) == 1)) {
                                    turret.setPower(0);
                                } else {
                                    turret.setPower(ll_pid_output);
                                }

                                telemetry.addData("LL PID OUTPUT", ll_pid_output);
                            }
                        }
                    }
                } else {
                    ///  heading update
                    if (ID == 24) {
                        correctPose = redPose;
                    } else {
                        correctPose = bluePose;
                    }
                    double x_distance = correctPose.getX() - followerPose.getX();
                    double y_distance = correctPose.getY() - followerPose.getY();
                    angle = Math.toDegrees(Math.atan(y_distance / x_distance));
                    if (ID == 24) {
                        correct_angle = angle;
                    } else {
                        correct_angle = 180 - (angle * -1); // makes angle positive for blue depot
                    }

                    robot_offset_to_zero = -Math.toDegrees(followerPose.getHeading());


                    turret_offset = robot_offset_to_zero + correct_angle; // is it + or - 90? goal is to try to get this to point towards the 0 of the field
                    raw_turret_offset_position = degrees_to_position(turret_offset);


                    if (raw_turret_offset_position > 800) {
                        correct_turret_offset_position = 800;
                    } else if (raw_turret_offset_position < -743) {
                        correct_turret_offset_position = -743;
                    } else {
                        correct_turret_offset_position = raw_turret_offset_position;
                    }

                    t_pid.setPID(turret_p, turret_i, turret_d);
                    turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    double pid_output = t_pid.calculate(turret.getCurrentPosition(), correct_turret_offset_position);
                    turret.setPower(pid_output +turret_f);


                }

        } else if(manual_turret) {

            manual_input *= 0.5;

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
    ///  Takes the offset in degrees and turn it into a motor encoder reading
    public double degrees_to_position(double offset) {
        return offset / 360 * 3100;

    }

    public double position_to_degrees(double position) {
        return (position * 360) / 3100;
    }

    public void toZero() {
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turret.setTargetPosition(0);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
            using_LL = true;
        }
    }

    public void turret_telemetry() {
        telemetry.addData("Using LL", using_LL);
        telemetry.addData("Turret Position", turret.getCurrentPosition());
        telemetry.addData("turret position in degrees", position_to_degrees(turret.getCurrentPosition()));
        telemetry.addData("Filtered turret Offset(position", correct_turret_offset_position);
        telemetry.addData("correct angle", correct_angle);
        telemetry.addData("manual turret", manual_turret);
        telemetry.addData("using turret", using_turret);
        // telemetry.addData("correct pose", correctPose);
        // telemetry.addData("robot heading", robot_offset_to_zero);
        // telemetry.addData("angle", angle);
        //  telemetry.addData("turret offset", turret_offset);
        //  telemetry.addData("turret offset (position)", raw_turret_offset_position);
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
    public void toggle_using_turret() {
        using_turret = !using_turret;
    }
    public void set_manual_false() {
        manual_turret= false;
    }
    public void set_manual_true() {
        manual_turret = true;
    }
    public void set_using_turret_true() {
        using_turret = true;
    }
    public void set_using_turret_false() {
        using_turret = false;
    }
}
