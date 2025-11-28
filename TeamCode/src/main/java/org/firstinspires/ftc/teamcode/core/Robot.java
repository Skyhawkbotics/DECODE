package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.core.subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Robot {

    private Shooter shooter;

    private Elevator elevator;

    private Turret turret;

    private boolean SLOW_MODE = false;

    private boolean manual_turret = false;

    private Intake intake;
    private int ID;

    private Boolean isparking = false;
    private Boolean autofiring = false;

    private Boolean useShooter = false;

    private int autoFireState;

    int intake_state = 0;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public static final Pose startPose = new Pose(56   ,8, Math.toRadians(90));

    public Timer autoFireTimer;

    private double pid_output = 0;


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;


    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2, int ID, Pose startPose) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        this.dummyg1 = game1;
        this.dummyg2 = game2;
        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry, ID); // THIS INDICATES BLUE DEPOT
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();
        follower.setStartingPose(startPose);
        follower.startTeleopDrive();
        follower.update();
        autoFireTimer = new Timer();
        this.ID = ID;

    }
    public Robot(HardwareMap hardwareMap, Telemetry telemetry,int  ID, Pose startPose) {/// FOR AUTO
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry, 20);



    }

    public void setAutoFireState(int state) {
        autoFireState = state;
        autoFireTimer.resetTimer();
    }
    public void solo_drive() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);

        follower.setTeleOpDrive(0.8 * -g1.left_stick_y, 0.4 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );

        if(g1.right_trigger > 0) {
            intake.intake();
        } else if (g1.left_trigger > 0){
            intake.fire();
        } else {
            intake.servosOut();
        }

        if(g1.aWasPressed()) {
            useShooter = !useShooter;
        }
        if(useShooter) {
            shooter.shooterON();
        } else {
            shooter.turnOFF();
        }


        if(g2.dpadUpWasPressed()) {
            shooter.perf_velocity();
        }
        if(g2.dpadLeftWasPressed()) {
            shooter.closest();
        }
        if(g2.dpadRightWasPressed()) {
            shooter.back_perf();
        }

    }
    public void robot_telemetry() {
        telemetry.addData("Turret Position", turret.getPosition());
        telemetry.addData("RobotPose", follower.getPose());
        telemetry.addData("Manual Move", manual_turret);
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
        turret.updateTurret(telemetry, red_ID);
    }
    public Follower getFollower() {
        return follower;
    }
    public Shooter getShooter() {
        return shooter;
    }
    public Turret getTurret() {
        return turret;
    }
    public Elevator getElevator() {
        return elevator;
    }
    public Telemetry getTelemetry() {
        return telemetry;
    }
    public Intake getIntake() {
        return intake;
    }


}
