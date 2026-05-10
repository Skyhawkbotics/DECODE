package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.core.constants.angle;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;
import org.firstinspires.ftc.teamcode.core.subsystems.LL;
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
    private LL ll;

    ///  ROBOT STUFF
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public Pose startPose;
    private Pose resetPose;
    private double turretPose;
    private Gamepad g1, g2, prevg1, prevg2;
    private boolean updating_shooter = false, isFull = false, using_turret = false, isred = true, parking = false, isTilting = false, isValid = false, LLDistance = false;
    private boolean shooter_telemetry, light_telemetry, turret_telemetry, robot_telemetry = true;
    private double depot_distance;
    private int ID;



    // Tele-OP Constructor
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2, int ID, Pose startPose, Boolean red, Pose resetPose) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        telemetry.setMsTransmissionInterval(100);

        follower = Constants.createFollower(hardwareMap);

        this.g1 = game1;
        this.g2 = game2;
        this.isred = red;
        this.resetPose = resetPose;



        ///  CONSTRUCTORS
        shooter = new Shooter(hardwareMap, telemetry);
        ll = new LL(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry, ID);
        light = new Light(hardwareMap, telemetry);
        tilt = new Tilt(hardwareMap, telemetry);
        tilt.tiltZERO();

///  :)
        ///  GAMEPADS
        // hi hi hi hi hi
        //hi h ih i hi hi hi h i
        // hihihihihihihihix
//        this.g1 = new Gamepad();
//        this.g2 = new Gamepad();



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
        turret = new Turret(hardwareMap, telemetry, ID);
        tilt = new Tilt(hardwareMap, telemetry);
        follower.update();
        intake.servosIn();
        this.ID = ID;
        tilt.tiltZERO();
        turret.set_using_LL(ID);
        turret.turret_update(ID, follower.getPose(), 0);

    }
    ///  this is a init just for a follower drivetrain nothing else
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Pose startPose) {/// FOR AUTO, no subsystems
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

    }


// Tele - Op Update
    public void robot_update() {
        shooter.update();
        robot_telemetry();
        follower.update();

        if(LLDistance) {
            isValid = ll.isValid();
        } else {
            isValid = false;
        }

        isFull = light.isFull();

        if (isFull) {
            if (!light.lightIsGreen()) {
                light.green();
            }
        } else {
            if (!light.lightIsRed()) {
                light.red();
            }
        }
        telemetry.update();

    }

    public void drivetrain_update() { // autonomous update, no subsystems
        telemetry.addData("Position", follower.getPose());
        telemetry.addData("Current Path", follower.getCurrentPath());
        telemetry.addData("Is the follower Busy?", follower.isBusy());
        telemetry.addData("is robot stuck", follower.isRobotStuck());
        telemetry.addData("Follower Max Power", follower.getMaxPowerScaling());
        telemetry.update();
        follower.update();
    }

    public void any_autonomous_robot_update () {
        telemetry.addData("Position", follower.getPose());
        telemetry.addData("Current chain index", follower.getChainIndex());
        telemetry.addData("heading error", follower.getHeadingError());
        telemetry.addData("current t", follower.getCurrentTValue());
        telemetry.addData("follower busy", follower.isBusy());
        telemetry.addData("is robot stuck", follower.isRobotStuck());
        turret.turret_telemetry();
        shooter.shooter_telemetry();
        turret.set_using_LL(ID);
        turret.turret_update(ID, follower.getPose(), 0);
        shooter.update();
        follower.update();
        telemetry.update();
        tilt.tiltZERO();
    }


    public void dual_drive() {

        /// KRISTEN'S CONTROLS

        if(g1.leftBumperWasPressed()) {
            parking =!parking;
        }
        if (parking) {
            double speedMultiplier = 0.3;
            follower.setTeleOpDrive(
                    speedMultiplier * -g1.left_stick_y, speedMultiplier * -g1.left_stick_x, speedMultiplier * -g1.right_stick_x, true);
        } else {
            follower.setTeleOpDrive( -g1.left_stick_y,  -g1.left_stick_x, 0.8 * -g1.right_stick_x, true );
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

        if (g2.touchpad) {
            LLDistance = !LLDistance;
        }

        if (isValid) {
        } else {
            depot_distance = shooter.getDistance(follower.getPose(), isred);
        }
        ///  can average w/ ll..??????????????

        if (g2.dpadLeftWasPressed()) {
            shooter.turnOFF();
        }
        if(g2.dpadUpWasPressed()) {
            updating_shooter = !updating_shooter;
        }
        if (updating_shooter) {
            if ((144 - follower.getPose().getY()) > 105) {
                shooter.toggle_update_true();
                shooter.setAngle(0.6);
                shooter.setShooterSpeed(depot_distance, true);

            } else {
                shooter.setShooterAngle(depot_distance);
                shooter.setShooterSpeed(depot_distance, false);
            }
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
        // Controls
        // Joystick = driving
        // left trigger = intake
        // right trigger = reverse
        // right bumper = fire
        // left bumper = toggle slow mode
        // d pad up on / off shooter
        // d pud left = off shooter
        // triangle = turret = zero
        // square = toggle tracking


        /// KRISTEN'S CONTROLS

        if(g1.leftBumperWasPressed()) {
            parking =!parking;
        }
        if (parking) {
            double speedMultiplier = 0.3;
            follower.setTeleOpDrive(
                    speedMultiplier * -g1.left_stick_y, speedMultiplier * -g1.left_stick_x, speedMultiplier * -g1.right_stick_x, true);
        } else {
            follower.setTeleOpDrive( -g1.left_stick_y,  -g1.left_stick_x, 0.8 * -g1.right_stick_x, true );
        }


        if (g1.left_trigger > 0) {
            intake.intake();
        }
        else if (g1.right_trigger > 0) {
            intake.reverse();
        } else if (g2.right_bumper) {
            intake.fire();
        } else {
            intake.off();
        }


        if (g1.dpadLeftWasPressed()) {
            shooter.turnOFF();
        }
        if(g1.dpadUpWasPressed()) {
            updating_shooter = !updating_shooter;
        }
        if (updating_shooter) {
            if ((144 - follower.getPose().getY()) > 105) {
                shooter.toggle_update_true();
                shooter.setAngle(0.6);
                shooter.setShooterSpeed(depot_distance, true);

            } else {
                shooter.setShooterAngle(depot_distance);
                shooter.setShooterSpeed(depot_distance, false);
            }
        } else {

            shooter.turnOFF();
        }

        if (shooter.velocityReached()) {
            g1.rumble (1000);
        }



///  turret
        if (g1.triangleWasPressed()) {
            turret.toZero();
        }
        if(g1.squareWasPressed()) {
            turret.toggle_manual_turret();

        }

        ///  Secondary'S CONTROLS, diagnostic controls

        if(g2.rightBumperWasPressed()) {
            follower.setPose(resetPose);
        }

        ///  TELEMETRY TOGGLES
        if (g2.dpadLeftWasPressed()) {
            turret_telemetry = !turret_telemetry;
        }
        if (g2.dpadUpWasPressed()) {
            light_telemetry = !light_telemetry;
        }
        if (g2.dpadRightWasPressed()) {
            shooter_telemetry = !shooter_telemetry;
        }
        if (g2.dpadDownWasPressed()) {
            robot_telemetry = !robot_telemetry;
        }


//  Turret
        if(g2.rightBumperWasPressed()) {
            turret.reset_turret();
        }

// Shooter

        if (g2.touchpad) {
            LLDistance = !LLDistance;
        }

        if (isValid){// limelight is working/
            // depot_distance = (shooter.getDistance(follower.getPose(),isred) +
            //        ll.calc_Distance()) / 2;
        } else {
            depot_distance = shooter.getDistance(follower.getPose(), isred);
        }
        ///  can average w/ ll..??????????????
        turret.turret_update(ID, follower.getPose(), g2.left_stick_x);

        turret.set_using_LL(ID);



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
