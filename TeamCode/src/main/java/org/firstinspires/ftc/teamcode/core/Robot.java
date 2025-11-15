package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
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
    public void eric_halle_controls() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        if(g1.right_bumper) {
            isparking = !isparking;
        }
        if(isparking) {
            follower.setTeleOpDrive(0.3 * -g1.left_stick_y, 0.3 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );

        } else if(g1.right_trigger > 0) {
            follower.setTeleOpDrive(-1, 0.3 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );
        } else if (g1.right_trigger == 0){
            follower.setTeleOpDrive(0.8 * -g1.left_stick_y, 0.4 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );
        }



        if (g1.left_trigger > 0 || g2.left_trigger > 0) {
            intake.on();
        } else if(g1.left_bumper) {
            intake.reverse();
        } else if(!autofiring) {
            intake.off();
        }

        if (g2.left_stick_x != 0) {
            turret.manual_move(g2);
        } else {
            turret.setTurretPower(0);
        }
        if (g2.right_stick_x !=0) {
            turret.updateTurret(telemetry, ID);
        }




        if(g2.crossWasPressed()) {
          //  setAutoFireState(1);
        }
        if(g2.circle && !autofiring) {
            elevator.on();
        } else if (!autofiring) {
            elevator.off();
        }

        if(g2.right_trigger > 0 && !(prevg2.right_trigger > 0)) {
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


        switch (autoFireState) {
            case -1:
                autofiring = false;
                break;
            case 1:
                autofiring = true;
                intake.off();
                elevator.on();
                setAutoFireState(2);
                break;
            case 2:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.off();
                    setAutoFireState(3);
                }
                break;
            case 3:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    intake.on();
                    setAutoFireState(4);
                }
                break;
            case 4:
                if (autoFireTimer.getElapsedTimeSeconds() > 1.5) {
                    intake.off();
                    setAutoFireState(5);
                }
                break;
            case 5:
                if (autoFireTimer.getElapsedTimeSeconds() > 0.5) {
                    elevator.on();
                    setAutoFireState(6);
                }
                break;
            case 6:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.off();
                    setAutoFireState(7);
                }
                break;
            case 7:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    intake.on();
                    setAutoFireState(8);
                }
                break;
            case 8:
                if (autoFireTimer.getElapsedTimeSeconds() > 1.5) {
                    intake.off();
                    setAutoFireState(9);
                }
                break;
            case 9:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.on();
                    setAutoFireState(10);
                }
                break;
            case 10:
                if(autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.off();
                    setAutoFireState(-1);
                }
                break;
        }



        }
    public void kristine_abraham_controls() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        follower.setTeleOpDrive(-g1.left_stick_y, -g1.left_stick_x, -g1.right_stick_x * 0.5, true);
        follower.update();
        if (g1.right_trigger > 0) {
            intake.on();
        } else if(g1.right_bumper) {
            intake.reverse();
        } else if(!autofiring) {
            intake.off();
        }
        if (g1.leftBumperWasPressed()) SLOW_MODE = !SLOW_MODE;
        double speed = SLOW_MODE ? 0.5 : 1.0;
        follower.setTeleOpDrive(
                -g1.left_stick_y * 0.5,
                -g1.left_stick_x * 0.5,
                -g1.right_stick_x * 0.5,
                true
        );

        if (g2.right_bumper) {
            turret.limelightON();
        }


        if(g2.crossWasPressed()) {
            setAutoFireState(1);
        }
        if(g2.circle && !autofiring) {
            elevator.on();
        } else if (!autofiring) {
            elevator.off();
        }
        if(g2.right_trigger > 0 && !(prevg2.right_trigger > 0)) {
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




        switch (autoFireState) {
            case -1:
                autofiring = false;
                break;
            case 1:
                autofiring = true;
                intake.off();
                elevator.on();
                setAutoFireState(2);
                break;
            case 2:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.off();
                    setAutoFireState(3);
                }
                break;
            case 3:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    intake.on();
                    setAutoFireState(4);
                }
                break;
            case 4:
                if (autoFireTimer.getElapsedTimeSeconds() > 0.5) {
                    intake.off();
                    elevator.on();
                    setAutoFireState(5);
                }
                break;
            case 5:
                if (autoFireTimer.getElapsedTimeSeconds() > 0.5) {
                    intake.off();
                    elevator.on();
                    setAutoFireState(6);
                }
                break;
            case 6:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.off();
                    setAutoFireState(7);
                }
                break;
            case 7:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    intake.on();
                    setAutoFireState(8);
                }
                break;
            case 8:
                if (autoFireTimer.getElapsedTimeSeconds() > 0.5) {
                    intake.off();
                    elevator.on();
                    setAutoFireState(9);
                }
                break;
            case 9:
                if (autoFireTimer.getElapsedTimeSeconds() > 1) {
                    elevator.off();
                    setAutoFireState(-1);
                }
                break;
        }



    }
    public void robot_telemetry() {
        telemetry.addData("Turret Position", turret.getPosition());
        telemetry.addData("RobotPose", follower.getPose());
    }

    public void robot_update () {

        shooter.shooter_telemetry();
        shooter.update();
        robot_telemetry();
        follower.update();
        telemetry.update();
        turret.updateTurret(telemetry, ID);

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
