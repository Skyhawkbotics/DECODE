package org.firstinspires.ftc.teamcode.core;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.core.subsystems.TurretNEW;
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

    public static final Pose resetPose = new Pose(72   ,80, Math.toRadians(90));



    private double pid_output = 0;


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;


    private double offset;
    private double angle = 0;
    private boolean isred = true;

    private Boolean using_turret = true;

    private double PID_output;

    private double x, y, depot_distance;
    private double v, s;


    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2, int ID, Pose startPose, Boolean red) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        follower = Constants.createFollower(hardwareMap);

        this.dummyg1 = game1;
        this.dummyg2 = game2;
        this.isred = red;

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turretNEW = new TurretNEW(hardwareMap, telemetry);

        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();


        follower.setStartingPose(startPose);
        follower.startTeleopDrive();

        this.ID = ID;

        shooter.positionMax();

    }
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Pose startPose) {/// FOR AUTO
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turretNEW = new TurretNEW(hardwareMap, telemetry);
        follower.update();
        intake.servosIn();
        turretNEW.reset_turret();

    }

    public void autonomous_robot_update () {
        shooter.shooter_telemetry();
        shooter.update();
        robot_telemetry();
        follower.update();
        telemetry.update();

        turretNEW.turret_update(0,  0);

    }

    public void solo_drive() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        if (g1.right_bumper) {
            double speedMultiplier = 0.3;
            follower.setTeleOpDrive(
                    speedMultiplier * -g1.left_stick_y, speedMultiplier * -g1.left_stick_x, speedMultiplier * -g1.right_stick_x, true);
        } else {
            follower.setTeleOpDrive(0.8 * -g1.left_stick_y, 0.4 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );
        }
        // follower.setTeleOpDrive(0.8 * -g1.left_stick_y, 0.4 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );

        if (ID == 24) {
            x = turretNEW.calculate_X_distance(follower.getPose(), true);
            y = turretNEW.calculate_Y_distance(follower.getPose(), true);
        } else if (ID == 20) {
            x = turretNEW.calculate_X_distance(follower.getPose(), false);
            y = turretNEW.calculate_Y_distance(follower.getPose(), false);
        }
        depot_distance = Math.sqrt(x * x + y * y) * 2.54 / 100;



       // if (g1.right_bumper) {
          //  speedMultiplier = 0.3;
          //  follower.setTeleOpDrive(speedMultiplier * -g1.left_stick_y, speedMultiplier * -g1.left_stick_x, speedMultiplier * -g1.right_stick_x, true);
      //  } else {
        //    follower.setTeleOpDrive(0.8 * -g1.left_stick_y, 0.4 * -g1.left_stick_x, 0.5 * -g1.right_stick_x, true );

      //  }


        if (g1.left_trigger > 0) {
            intake.intake();
        }
        else if (g1.right_trigger > 0) {
            intake.reverse();
        }
        else {
            intake.off();
        }

        if(g1.rightBumperWasPressed()) {
            follower.setPose(resetPose);
        }


        if (g2.dpadUpWasPressed()) {
            v = shooter.setShooterSpeed(depot_distance - 0.15);
            s = shooter.getShooterAngle(depot_distance - 0.15);
        }
        if (g2.dpadDownWasPressed()) {
            if (depot_distance > 2.50) {
                shooter.positionMax();
            } else if (depot_distance < 2.50) {
                shooter.positionMin();
            }
        }

        if (g2.dpadLeftWasPressed()) {
            shooter.turnOFF();
        }

        if (g2.right_trigger > 0) {
            intake.fire();
        }


        /* if (g2.dpadUpWasPressed()) {
            shooter.perf_velocity();
        } else if (g2.dpadDownWasPressed()) {
            shooter.turnOFF();
        } */


        if(g2.rightBumperWasPressed()) {///  RESET
            turretNEW.reset_turret();
        }

        if(g2.bWasPressed()) {///  toggle the PID updating
           turretNEW.turret_update_toggle();
        }


        if(g2.y) {
            PID_output = offset;
        } else {
            PID_output = 0;
        }


        turretNEW.turret_update(PID_output, g2.left_stick_x * 0.2);

        offset = turretNEW.calculate_angle_offset(follower.getPose().getHeading(), angle);

        angle = turretNEW.calculate_angle(follower.getPose(), isred);
    }

    public void robot_telemetry() {
        //turretNEW.turret_telemetry();
        telemetry.addData("offset", offset);
        telemetry.addData("angle", angle);
        telemetry.addData("Follower Pose", follower.getPose());
        telemetry.addData("Distance from Depot", depot_distance);

        telemetry.addData("CALCULATED ANGLE", s);
        telemetry.addData("CALCULATED VELOCITY", v);

    }
    public void robot_update () {
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
