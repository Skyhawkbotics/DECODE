package org.firstinspires.ftc.teamcode.core;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class whole_robot {
    private Shooter shooter;

    private Elevator elevator;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower drivetrain;
    public static Pose startPose = new Pose(0,0, Math.toRadians(90));


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;
    public whole_robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        drivetrain = Constants.createFollower(hardwareMap);
        this.dummyg1 = game1;
        this.dummyg2 = game2;
        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();
        drivetrain.setStartingPose(startPose);

    }
    public void solo_controls() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        drivetrain.setTeleOpDrive(0.7* g1.left_stick_y, 0.7 * g1.left_stick_x, 0.4 * g1.right_stick_x, false);

        ///  SHOOTER CODE - GAMEPAD 2
        if (g2.aWasPressed()) {
            // motor to max target
            shooter.perf_velocity();
        }
        if (g2.leftBumperWasPressed()) {
            shooter.increaseVelocity();
        }

        if (g2.rightBumperWasPressed()) {
            shooter.decreaseVelocity();
        }

        if (g2.bWasPressed()) {
            shooter.to_zero();
        }
        if (g2.yWasPressed()) {
            ///Increase the position 0.1
            shooter.increasePosition();
        }
        if (g2.xWasPressed()) {
            // Decrease the position 0.1
            shooter.decreasePosition();
        }
        ///  to the max angles
        if(g2.dpadLeftWasPressed()) {
            shooter.positionMin();
        }
        if(g2.dpadRightWasPressed()) {
            shooter.positionMax();
        }
        if(g2.left_stick_y > 0) {
            elevator.top();
        }
        if(g2.left_stick_y < 0) {
            elevator.bottom();
        }
        if(g2.right_stick_y < 0  ) {
            elevator.positionZero();
        }0
        if(g2.right_stick_y > 0) {
            elevator.positionOne();
        }
    }
    public void robot_update() {
        shooter.shooter_telemetry();
        shooter.update();
        drivetrain.update();
    }
}
