package org.firstinspires.ftc.teamcode.core;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Light;
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

    private Light light;

    public static final Pose startPose = new Pose(56   ,8, Math.toRadians(90));

    public static final Pose resetPose = new Pose(72   ,80, Math.toRadians(90));



    private double pid_output = 0;


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;


    private double offset;
    private double angle = 0;

    private double turret_angle = 0;
    private boolean isred = true;

    private boolean using_turret = true;

    private boolean updating_shooter = false;

    private double PID_output;

    private double x, y, depot_distance;
    private double v, s;

    private Pose resetPose1;


    private Boolean parking = false;
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2, int ID, Pose startPose, Boolean red, Pose resetPose) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        follower = Constants.createFollower(hardwareMap);

        this.dummyg1 = game1;
        this.dummyg2 = game2;
        this.isred = red;

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turretNEW = new TurretNEW(hardwareMap, telemetry);
        light = new Light(hardwareMap, telemetry);

        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();


        follower.setStartingPose(startPose);
        follower.startTeleopDrive();

        this.ID = ID;
        resetPose1 = resetPose;


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
        if(g1.leftBumperWasPressed()) {
            parking =!parking;
        }
        if (parking) {
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

        if (updating_shooter == true) {
            shooter.setShooterSpeed(depot_distance);
            shooter.setShooterAngle(depot_distance);
        } else if (updating_shooter == false) {
            shooter.turnOFF();
        }


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
            follower.setPose(resetPose1);
        }




        if (g2.dpadUpWasPressed()) {
            updating_shooter = true;
        }
        if (g2.dpadLeftWasPressed()) {
            updating_shooter = false;
        }


        if (g2.right_trigger > 0) {
            intake.fire();
        }

        if(g2.rightBumperWasPressed()) {///  RESET
            turretNEW.reset_turret();
        }

        if(g2.bWasPressed()) {///  toggle the PID updating
           turretNEW.turret_update_toggle();
        }

        if (g1.dpadLeftWasPressed()) {
            light.increase();
        }
        if (g1.dpadRightWasPressed()) {
            light.decrease();
        }

        if(shooter.getVelocity() >= shooter.getTarget() - 75) {
            light.green();
        } else {
            light.red();








        }

        if(g2.y) {
            PID_output = offset;
        } else {
            PID_output = 0;
        }


        turretNEW.turret_update(PID_output, g2.left_stick_x * 0.2);

        offset = turretNEW.calculate_angle_offset(follower.getPose().getHeading(), turret_angle);

        angle = turretNEW.calculate_angle(follower.getPose(), isred);

        turret_angle = turretNEW.calculate_turret_angle_complementary(angle, isred);


    }

    public void robot_telemetry() {
        //turretNEW.turret_telemetry();
        telemetry.addData("offset", offset);
        telemetry.addData("angle", angle);
        telemetry.addData("complementary angle for turret", turret_angle);
        telemetry.addData("Follower Pose", follower.getPose());
        telemetry.addData("Distance from Depot", depot_distance);
        turretNEW.turret_telemetry();
        light.light_telemetry();
        telemetry.addData("CALCULATED ANGLE", s);
        telemetry.addData("CALCULATED VELOCITY", v);

    }
    public void robot_update () {
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
