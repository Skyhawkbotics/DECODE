package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Robot {

    private Shooter shooter;

    private Elevator elevator;

    private Intake intake;

    boolean button_pressed;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public static final Pose startPose = new Pose(56   ,8, Math.toRadians(90));


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        this.dummyg1 = game1;
        this.dummyg2 = game2;
        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();
        follower.setStartingPose(startPose);
        follower.startTeleopDrive();
        follower.update();


    }
    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);



    }


    public void solo_controls() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        follower.setTeleOpDrive(-g1.left_stick_y, -g1.left_stick_x, -g1.right_stick_x * 0.4, true);
        follower.update();

        if (g1.a) {
            elevator.on();
        } else if (g1.b) {
            elevator.secondary();
        } else {
            elevator.off();
        }
        elevator.elevator_telemetry();
        telemetry.update();
        if(g1.dpad_down) {
            intake.on();
        } else if (g1.dpad_left) {
            intake.reverse();
        }
        if(g1.left_bumper) {
            shooter.perf_velocity();
        } else {
            shooter.to_zero();
        }

        if (g1.dpadDownWasPressed()) {
            button_pressed = !button_pressed;
            /// !button_pressed means the opposite of button_pressed
        }
        if(button_pressed) {
            intake.on();
        } else if (g1.dpad_left) {
            intake.reverse();
        }
            else {
            intake.off();

        }

    }

    public void robot_update () {
        //shooter.shooter_telemetry();
        shooter.update();
        telemetry.update();
        follower.update();


    }
    public Follower getFollower() {
        return follower;
    }
    public Shooter getShooter() {
        return shooter;
    }
    public Elevator getElevator() {
        return elevator;
    }
    public Telemetry getTelemetry() {
        return telemetry;
    }


}
