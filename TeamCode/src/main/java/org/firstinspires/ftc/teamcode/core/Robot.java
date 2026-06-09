package org.firstinspires.ftc.teamcode.core;

import static androidx.core.math.MathUtils.clamp;
import static com.arcrobotics.ftclib.purepursuit.PurePursuitUtil.angleWrap;
import static org.firstinspires.ftc.teamcode.core.constants.HEADING_KD;
import static org.firstinspires.ftc.teamcode.core.constants.HEADING_KP;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_d;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_f;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_i;
import static org.firstinspires.ftc.teamcode.core.constants.drivetrain_p;
import static org.firstinspires.ftc.teamcode.core.constants.gate_heading_red;
import static org.firstinspires.ftc.teamcode.core.constants.gateheading_blue;
import static org.firstinspires.ftc.teamcode.core.constants.rate;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.subsystems.Drivetrain;
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
    private Drivetrain drivetrain;

    ///  ROBOT STUFF
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public Pose startPose;
    private Pose resetPose;
    private double turretPose;
    private Gamepad g1, g2, prevg1, prevg2;
    private boolean updating_shooter = false, isFull = false, using_turret = false, isred = true, parking = false, isTilting = false, isValid = false, LLDistance = false, isIntaking = false;
    private boolean shooter_telemetry, light_telemetry, turret_telemetry, robot_telemetry = true;
    private double depot_distance;
    private int ID;
    private double currentForward = 0;
    private double currentStrafe = 0;
    private double currentTurn = 0;
    private PIDFController drivetrainPID;
    private Boolean useHeadingLock = false;
    private double gate_heading;

    private static final double FAR_HEADING_DEG = 120 ;
    private static final double CLOSE_HEADING_DEG = 135;


    private static final double MAX_TURN = 1;
    private static final double DEADZONE_RAD = Math.toRadians(0.25);
    private static final double MIN_DT = 0.01;
    private final ElapsedTime timer = new ElapsedTime();
    private double lastError = 0.0;
    private boolean robotcentric = true;
    private int strafe_toggle = 1;
    private boolean automatic_intake =  false;



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
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        tilt.tiltZERO();
        drivetrainPID = new PIDFController(drivetrain_p, drivetrain_i, drivetrain_d, drivetrain_f);

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
        light = new Light(hardwareMap, telemetry);
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
        telemetry.addData("current t", follower.getCurrentTValue());
        telemetry.addData("follower busy", follower.isBusy());
        light.light_telemetry();
        turret.turret_telemetry();
        shooter.shooter_telemetry();
        turret.set_using_LL(ID);
        turret.turret_update(ID, follower.getPose(), 0);
        shooter.update();
        follower.update();
        telemetry.update();
        tilt.tiltZERO();
//        isFull = light.isFull();

//        if (isFull) {
//            if (!light.lightIsGreen()) {
//                light.green();
//            }
//        } else {
//            if (!light.lightIsRed()) {
//                light.red();
//            }
//        }
    }
    private double slew(double target, double current, double rate) {
        double delta = target - current;

        if (delta > rate) {
            delta = rate;
        }
        else if (delta < -rate) {
            delta = -rate;
        }

        return current + delta;
    }

    public void dual_drive() {
        if(isred) {
            gate_heading = gate_heading_red;
        } else {
            gate_heading = gateheading_blue;
        }


        /// KRISTEN'S CONTROLS

        double targetDeg = gate_heading;

        double targetRad = Math.toRadians(targetDeg);
        double error = angleWrap(targetRad - follower.getHeading());

        double dt = Math.max(timer.seconds(), MIN_DT);
        timer.reset();

        currentForward = slew(-g1.left_stick_y, currentForward, rate);
        currentStrafe = slew(g1.left_stick_x, currentStrafe, rate);
        if(useHeadingLock) {
            currentTurn = drivetrainPID.calculate(Math.toDegrees(follower.getHeading()), gate_heading);
        } else {
            currentTurn = slew(g1.right_stick_x, currentTurn, 1);
        }

        if (useHeadingLock) {
            if (Math.abs(error) < DEADZONE_RAD) {
                currentTurn = 0.0;
            } else {
                double derivative = (error - lastError) / dt;
                currentTurn = -(HEADING_KP * error + HEADING_KD * derivative);
            }
            lastError = error;
            currentTurn = clamp(currentTurn, -MAX_TURN, MAX_TURN);
        } else {
            currentTurn = slew(g1.right_stick_x, currentTurn, 1);
            lastError = error;
        }
//        if(g1.leftBumperWasPressed()) {
//            parking =!parking;
//        }
        if (parking) {
            double speedMultiplier = 0.3;
            follower.setTeleOpDrive(
                    speedMultiplier * currentForward, speedMultiplier * -currentStrafe, speedMultiplier * -currentTurn, true);
        } else {

            follower.setTeleOpDrive( currentForward, strafe_toggle * -currentStrafe, 0.8* -currentTurn, true);
        }
//        if(g1.squareWasPressed()) {
//            if(strafe_toggle == 1) {
//                strafe_toggle = -1;
//            } else {
//                strafe_toggle = 1;
//
//            }
//        }
//        drivetrain.robotCentricPowers(-g1.left_stick_y, g1.left_stick_x, g1.right_stick_x);

        if (g1.left_trigger > 0) {
            intake.intake();
        } else if(g1.left_bumper) {
            intake.intake();
            useHeadingLock = true;
            automatic_intake = true;
        } else if (g1.right_trigger > 0) {
            intake.fire();
        }
        else {
            intake.off();
            useHeadingLock = false;

        }


        if(automatic_intake) {
            if(!light.isFullCertain()) {
                intake.intake();
            } else {
                intake.off();
                automatic_intake = false;
            }
        }
//        if(g1.square) {
//            useHeadingLock = true;
//
//        } else {
//            useHeadingLock = false;
//        }





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
        tilt.tilt_telemetry();

        if (robot_telemetry) {
            telemetry.addData("Follower Pose", follower.getPose());
            telemetry.addData("Distance from Depot", depot_distance);
            telemetry.addData("Robot Speed", follower.getVelocity().getMagnitude());
        }
        if (turret_telemetry) {
            turret.turret_telemetry();
            telemetry.addData("Using Turret", using_turret);
        }
        if (light_telemetry) {
            light.light_telemetry();
            light.isFullCertain();
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
    public Intake getIntaking() {
        return intake;
    }
    public Turret getTurret() {
        return turret;
    }
    public Light getLight() {
        return light;
    }
    public boolean isIsred() {
        return isred;
    }


}
