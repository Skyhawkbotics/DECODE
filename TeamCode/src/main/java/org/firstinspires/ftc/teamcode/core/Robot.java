package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.core.subsystems.TurretNEW;
import org.firstinspires.ftc.teamcode.unused.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Robot {

    private Shooter shooter;
    private Intake intake;
    private TurretNEW turretNEW;
    private int ID;


    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public static final Pose startPose = new Pose(56   ,8, Math.toRadians(90));


    private double pid_output = 0;


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;


    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2, int ID, Pose startPose) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        follower = Constants.createFollower(hardwareMap);

        this.dummyg1 = game1;
        this.dummyg2 = game2;

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turretNEW = new TurretNEW(hardwareMap, telemetry);


        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();


        follower.setStartingPose(startPose);
        follower.startTeleopDrive();
        follower.update();

        this.ID = ID;

    }
    public Robot(HardwareMap hardwareMap, Telemetry telemetry,int  ID, Pose startPose) {/// FOR AUTO
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);



    }

    public void solo_drive() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);

        follower.setTeleOpDrive(0.8 * -g1.left_stick_y, 0.4 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true);

        if (g1.right_trigger > 0) {
            intake.servosOut();
        } else if (g1.rightBumperWasPressed()) {
            intake.servosIn();
        }
        if (g1.left_trigger > 0) {
            intake.on();
        }
        if (g1.left_bumper) {
            intake.off();
        }
        if (g1.dpadUpWasPressed()) {
            shooter.setShooterSpeed(2.04);
        }
        if (g1.dpadLeftWasPressed()) {
            shooter.setShooterSpeed(3.11);
        }
        if (g1.dpadDownWasPressed()) {
            shooter.turnOFF();
        }


        if (g2.dpadUpWasPressed()) {
            shooter.perf_velocity();
        } else if (g2.dpadDownWasPressed()) {
            shooter.turnOFF();
        }

    }

    public void robot_telemetry() {

    }
    public void robot_update () {

        shooter.shooter_telemetry();
        shooter.update();
        robot_telemetry();
        follower.update();
        telemetry.update();


    }
    public void auto_robot_update() {
        shooter.shooter_telemetry();
        shooter.update();
        robot_telemetry();
        follower.update();
        telemetry.update();
    }
    public Follower getFollower() {
        return follower;
    }
    public Shooter getShooter() {
        return shooter;
    }
    public TurretNEW getTurretNEW() {
        return turretNEW;
    }
    public Telemetry getTelemetry() {
        return telemetry;
    }
    public Intake getIntake() {
        return intake;
    }


}
