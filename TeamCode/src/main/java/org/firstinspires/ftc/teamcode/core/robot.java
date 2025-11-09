package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class robot {

    private Shooter shooter;

    private Elevator elevator;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public static final Pose startPose = new Pose(56   ,8, Math.toRadians(90));


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;

    public robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        this.dummyg1 = game1;
        this.dummyg2 = game2;
        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();
        follower.setStartingPose(startPose);
        follower.startTeleopDrive();
        follower.update();


    }
    public robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        //shooter = new Shooter(hardwareMap, telemetry);
      //  elevator = new Elevator(hardwareMap, telemetry);


    }


    public void solo_controls() {
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        follower.setTeleOpDrive(-dummyg1.left_stick_y, -dummyg1.left_stick_x, -dummyg1.right_stick_x * 0.4, false);
        follower.update();

    }

    public void robot_update () {
        //shooter.shooter_telemetry();
        //shooter.update();
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
