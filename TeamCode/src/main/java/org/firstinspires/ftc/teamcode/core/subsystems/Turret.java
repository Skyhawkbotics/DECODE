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

    private Pose redPose = new Pose(133, 135, 0);
    private Pose bluePose = new Pose(11, 135,0);

    private Telemetry telemetry;

    private Boolean using_LL = false;

    private Timer timer = new Timer();

    private double turret_offset;

    private double raw_turret_offset_position;
    private double correct_turret_offset_position;

    private double angle;

    private double correct_angle;

    private double robot_heading;
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

    public void turret_update(int ID, Pose followerPose) {

        double ll_pid_output;
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
            Pose correctPose;
            if(ID == 24) {
                correctPose = redPose;
            } else {
                correctPose = bluePose;
            }
            double x_distance = Math.abs(correctPose.getX() - followerPose.getX());
            double y_distance = Math.abs(correctPose.getY() - followerPose.getY());
            angle = 90 - Math.toDegrees(Math.atan2(y_distance, x_distance));
            if(ID == 24) {
                correct_angle = angle;
            } else {
                correct_angle = -angle;
            }

            robot_heading= Math.toDegrees(followerPose.getHeading());


            turret_offset = robot_heading; // is it + or - 90? goal is to try to get this to point towards the 0 of the field
            raw_turret_offset_position = degrees_to_position(turret_offset);


            if(raw_turret_offset_position > 800) {
                correct_turret_offset_position = 800;
            } else if(raw_turret_offset_position < -743) {
                correct_turret_offset_position = -743;
            } else {
                correct_turret_offset_position = raw_turret_offset_position;
            }


            t_pid.setPID(turret_p, turret_i, turret_d);
            turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            double pid_output = t_pid.calculate(turret.getCurrentPosition(), correct_turret_offset_position);
            turret.setPower(pid_output);


        }
    }


    public double calculate_turret_angle_complementary(double angle, boolean isRED) {
        double real_angle;
        double correct_distance_angle;
        if(isRED) {
            real_angle = 90 - angle - 4;
        } else {
            real_angle = 90 - angle -4;
        }
        if(angle > 50) {
            correct_distance_angle = real_angle - 5;
        } else {
            correct_distance_angle = real_angle;
        }
        double real_angle_position = correct_distance_angle; // takes the complementary angle
        if(isRED) {
            return real_angle_position;
        } else {
            return -real_angle_position;
        }
    }




    ///  Takes the offset in degrees and turn it into a motor encoder reading
    public double degrees_to_position(double offset) {
        return offset / 360 * 3100;

    }
    public double position_to_degrees(double position) {
        return (position * 360) / 3100;
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

        if(timer.getElapsedTime() > 100) {
            using_LL = false;
        } else {
            using_LL = true;
        }
    }

    public void turret_telemetry() {
        telemetry.addData("Using LL", using_LL);
        telemetry.addData("Turret Position", turret.getCurrentPosition());
        telemetry.addData("turret position in degrees", position_to_degrees(turret.getCurrentPosition()));
        telemetry.addData("turret offset", turret_offset);
        telemetry.addData("turret offset (position)", raw_turret_offset_position);
        telemetry.addData("Filtered turret Offset(position", correct_turret_offset_position);
        telemetry.addData("robot heading", robot_heading);
        telemetry.addData("angle", angle);
    }

    public void set_power(double p) {
        turret.setPower(p);
    }
    public void manual_update(double input) {

    }
    /*
    if (detect limelight based off a timer) {
     limelight correction
    } else {
     heading correction
    }
     */


}
