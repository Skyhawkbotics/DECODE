package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.core.constants.angle;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.Light;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.core.subsystems.Tilt;
import org.firstinspires.ftc.teamcode.core.subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Robot {

    /// SUBSYSTEMS
    private Shooter shooter;
    private Intake intake;
    private Turret turret;
    private Light light;
    private Tilt tilt;
    ///  ROBOT STUFF
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public Pose startPose;
    private Pose resetPose;
    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;
    private boolean updating_shooter = false, isFull = false, using_turret = false, isred = true, parking = false, isTilting = false;
    private boolean shooter_telemetry, light_telemetry, turret_telemetry, robot_telemetry = true;
    private double depot_distance;
    private int ID;



    // Tele-OP Constructor
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2, int ID, Pose startPose, Boolean red, Pose resetPose) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        follower = Constants.createFollower(hardwareMap);

        this.dummyg1 = game1;
        this.dummyg2 = game2;
        this.isred = red;
        this.resetPose = resetPose;



        ///  CONSTRUCTORS
        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry);
        light = new Light(hardwareMap, telemetry);
        tilt = new Tilt(hardwareMap, telemetry);
        tilt.tiltZERO();


        ///  GAMEPADS
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();


        follower.setStartingPose(startPose);
        follower.startTeleopDrive();

        this.ID = ID;


        ///  angle initialization :3
        shooter.positionMax();

    }
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Pose startPose, int ID) {/// FOR AUTO
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry);
        tilt = new Tilt(hardwareMap, telemetry);
        follower.update();
        intake.servosIn();
        this.ID = ID;
        tilt.tiltZERO();

    }

// Autonomous Initialization
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Pose startPose) {/// FOR AUTO
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry);
        light = new Light(hardwareMap, telemetry);
        tilt = new Tilt(hardwareMap, telemetry);
        tilt.tiltZERO();

        follower.update();
        intake.servosIn();

    }
// Tele - Op Update
    public void robot_update() {
        shooter.update();
        robot_telemetry();
        follower.update();
        telemetry.update();


        isFull = light.isFull();

        if (isFull) {
            light.green();
        } else {
            light.red();
        }

    }
// Autonomous Robot Update Loop
    public void autonomous_robot_update () {
        telemetry.addData("Position", follower.getPose());
        telemetry.addData("Current Path", follower.getCurrentPath());
        turret.turret_telemetry();
        shooter.shooter_telemetry();
       //turret.set_using_LL(ID);
        turret.turret_update_close(ID, follower.getPose());


        shooter.update();
        follower.update();
        telemetry.update();
        shooter.recoil();
        tilt.tiltZERO();

    }
    public void blue_autonomous_robot_update () {
        telemetry.addData("Position", follower.getPose());
        telemetry.addData("Current Path", follower.getCurrentPath());
        turret.turret_telemetry();
        shooter.shooter_telemetry();
        //turret.set_using_LL(ID);
        turret.turret_update_close_blue(ID, follower.getPose());


        shooter.update();
        follower.update();
        telemetry.update();
        shooter.recoil();
        tilt.tiltZERO();


    }
    public void far_autonomous_robot_update () {
        telemetry.addData("Position", follower.getPose());
        telemetry.addData("Current Path", follower.getCurrentPath());
        turret.turret_telemetry();
        shooter.shooter_telemetry();
        //turret.set_using_LL(ID);
            turret.turret_update_far_blue(ID, follower.getPose());

        shooter.recoil();
        shooter.update();
        shooter.panelstelemetry();
        follower.update();
        telemetry.update();

        tilt.tiltZERO();

    }
    public void far_autonomous_robot_update_blue () {
        telemetry.addData("Position", follower.getPose());
        telemetry.addData("Current Path", follower.getCurrentPath());
        turret.turret_telemetry();
        shooter.shooter_telemetry();
        //turret.set_using_LL(ID);
        turret.turret_update_far_blue(ID, follower.getPose());


        shooter.recoil();
        shooter.update();
        shooter.panelstelemetry();
        follower.update();
        telemetry.update();

        tilt.tiltZERO();

    }

    public void dual_drive() {
        prevg1.copy(g1);
        prevg2.copy(g2);
        g1.copy(dummyg1);
        g2.copy(dummyg2);


        /// KRISTEN'S CONTROLS

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

        if (g1.left_trigger > 0) {
            intake.intake();
        }
        else if (g1.right_trigger > 0) {
            intake.reverse();
        }
        else {
            intake.off();
        }

        if(g1.triangleWasPressed()) {
            isTilting = !isTilting;
        }
        if(isTilting) {
            tilt.tiltREAL();
        } else {
            tilt.tiltZERO();
        }

        if(g1.rightBumperWasPressed()) {
            follower.setPose(resetPose);
        }

        ///  TELEMETRY TOGGLES
        if (g1.dpadLeftWasPressed()) {
            turret_telemetry = !turret_telemetry;
        }
        if (g1.dpadUpWasPressed()) {
            light_telemetry = !light_telemetry;
        }
        if (g1.dpadRightWasPressed()) {
            shooter_telemetry = !shooter_telemetry;
        }
        if (g1.dpadDownWasPressed()) {
            robot_telemetry = !robot_telemetry;
        }


        ///  ABRAHAM'S CONTROLS


        if (g2.right_trigger > 0) {
            intake.fire();
        }

//  Turret
        if(g2.rightBumperWasPressed()) {
            turret.reset_turret();
        }

        if(g2.squareWasPressed()) {
            turret.toggle_manual_turret();

        }
        turret.set_using_LL(ID);
        turret.turret_update(ID, follower.getPose(), g2.left_stick_x);
        if (g2.triangleWasPressed()) {
            turret.toZero();
        }



// Shooter
        depot_distance = shooter.getDistance(follower.getPose(), isred);

        if (g2.dpadLeftWasPressed()) {
            shooter.turnOFF();
        }
        if(g2.dpadUpWasPressed()) {
            updating_shooter = !updating_shooter;
        }
        if (updating_shooter) {
            shooter.setShooterSpeed(depot_distance);
            shooter.setShooterAngle(depot_distance);
        } else {
            shooter.turnOFF();
        }



        if (shooter.velocityReached()) {
            g2.rumble (1000);
        }
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

        depot_distance = shooter.getDistance(follower.getPose(), isred);



        /// KRISTEN'S CONTROLS
        if (g1.left_trigger > 0) {
            intake.intake();
        }
        else if (g1.right_trigger > 0) {
            intake.reverse();
        } else if(g1.square) {
            intake.fire();
        }
        else {
            intake.off();
        }

        if(g1.rightBumperWasPressed()) {
            follower.setPose(resetPose);
            turret.reset_turret();

        }

        ///  TELEMETRY TOGGLES
        if (g1.dpadLeftWasPressed()) {
            turret_telemetry = !turret_telemetry;
        }
        if (g1.dpadUpWasPressed()) {
            light_telemetry = !light_telemetry;
        }
        if (g1.dpadRightWasPressed()) {
            shooter_telemetry = !shooter_telemetry;
        }
        if (g1.dpadDownWasPressed()) {
            robot_telemetry = !robot_telemetry;
        }


        if (g1.triangleWasPressed()) {
            if (depot_distance > 2.50) {
                shooter.positionMax();
            } else if (depot_distance < 2.50) {
                shooter.positionMin();
            }
        }

        if (g1.circleWasPressed()) {
            shooter.turnOFF();
        }


        if(g2.cross) { //
            turret.set_using_turret_true();
        } else {
            turret.set_using_turret_false();
        }
        if(g2.optionsWasPressed()) {
            turret.toggle_manual_turret();
        }


        turret.set_using_LL(ID);
        turret.turret_update(ID, follower.getPose(), g2.left_stick_x);




        if(g2.dpadUpWasPressed()) {
            updating_shooter = !updating_shooter;
        }
        if (updating_shooter) {
            shooter.setShooterSpeed(depot_distance);
            shooter.setShooterAngle(depot_distance);
        } else {
            shooter.turnOFF();
        }
        if (shooter.velocityReached()) {
            g2.rumble (1000);
        }


    }

    public void robot_telemetry() {

        if (robot_telemetry) {
            telemetry.addData("Follower Pose", follower.getPose());
            telemetry.addData("Distance from Depot", depot_distance);
        }
        if (turret_telemetry) {
            turret.turret_telemetry();
            telemetry.addData("Using Turret", using_turret);
        }
        if (light_telemetry) {
            light.light_telemetry();
        }
        if (shooter_telemetry) {
            shooter.shooter_telemetry();
        }

    }


    public Follower getFollower() {
        return follower;
    }
    public Shooter getShooter() {
        return shooter;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }
    public Intake getIntake() {
        return intake;
    }
    public Turret getTurret() {
        return turret;
    }
    public boolean isIsred() {
        return isred;
    }


}
