package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretNEW {
    public DcMotorEx turret;
    private PIDController heading_turret_pid;

    private Pose redPose = new Pose(135, 141, 0);
    private Pose bluePose = new Pose(40, 4,0);


    public TurretNEW(HardwareMap hardwareMap, Telemetry telemetry) {
        turret = hardwareMap.get(DcMotorEx.class, turret_name);

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);

        heading_turret_pid = new PIDController(turret_p, turret_i, turret_d);
    }
    public double calculate_X_distance(Pose followerPose, boolean isRED) {
        Pose correctPose;
        if(isRED) {
            correctPose = redPose;
        } else {
            correctPose = bluePose;
        }
        return Math.abs(correctPose.getX() - followerPose.getX());

    }
    public double calculate_Y_distance(Pose followerPose, boolean isRED) {
        Pose correctPose;
        if(isRED) {
            correctPose = redPose;
        } else {
            correctPose = bluePose;
        }
        return correctPose.getY() - followerPose.getY();
    }

    public double calculate_distance(Pose followerPose, boolean isRED) { /// angle is in degrees
        double x_distance = calculate_Y_distance(followerPose, isRED);
        double y_distance = calculate_X_distance(followerPose, isRED);


        double distance = Math.sqrt((x_distance * x_distance) + (y_distance * y_distance));

        return distance;
    }
    public double calculate_angle_offset(Pose followerPose,boolean isRED, double angle ) {

        double correct_angle_offset;
        double angle_offset = Math.toDegrees(followerPose.getHeading()) - 90;

        if(angle_offset >= 180) {
            correct_angle_offset = angle_offset - 360;
        } else {
            correct_angle_offset = angle_offset;
        }
        return correct_angle_offset;
    }
    public double calculate_angle(Pose followerPose,boolean isRED) {
        double x_distance = calculate_Y_distance(followerPose, isRED);
        double y_distance = calculate_X_distance(followerPose, isRED);

        double angle = Math.atan(y_distance/x_distance);
        if(isRED) {
            angle = -angle;
        }

        return 90 + angle;
    }

}
