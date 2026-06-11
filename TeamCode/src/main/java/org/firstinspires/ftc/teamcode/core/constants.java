package org.firstinspires.ftc.teamcode.core;


import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

/// This class stores all values such as positions for bucket, transfer, and claws.
/// allowing the dashboard to config it allows us to modify it
/// All these variables need to be public and static
///
///
@Configurable
public class constants {
    /// Hardware names
    public static String turret_name = "turret";
    public static double left_limit = -500;
    public static double right_limit = 900;

    public static int blue_ID = 20;
    public static int red_ID = 24;


    ///  Shooter PID constants

    public static double ep = 0.003, ei = 0, ed = 0.0000, f = 0.00038; ///these are good as of 10/11

    public static int max = 2000;


    ///
    public static int auto_speed2 = 1060;

    public static int auto_speed3 = 1150;
    public static int auto_speed4 = 1260;
    public static int auto_speed_blue = 1140;
    public static int perf_value = 1450; ///vertex of largest triangle

    public static int far_side_shoot = 1740; ///vertex of smallest triangle
    public static double far_side_angle = 0.6;


    public static int closest = 1150; ///position closest on the line

    public static int middle_of_field = 1200; ///position from middle of the largest triangle

    public static double positionMin = 0.05;
    public static double positionMax = 0.95;



    ///  Turret  + Turret PID controller
    public static double turret_p = 0.004, turret_i = 0.07, turret_d = 0.00008, turret_f = 0;

    public static double LLTarget = 0;



    ///  Limelight PID Controller

//    public static double lp = 0.015;
//    public static double li = 0;
//    public static double ld = 0.00013;
    public static double lf = 0.0;

    public static double lp = 0.007;
    public static double li = 0.05;
    public static double ld = 0.00000;
    public static double LLTolerance = 0.3;
    /// /// Intake speed constants

    public static double intake_speed = 1;
    public static double intake_slow = 0.8;

    public static double right_servos_in_pos = 0.7;
    public static double left_servos_in_pos = 0;
    public static double right_servos_out_pos = 0.5;
    public static double left_servos_out_pos = 0.3;



    public static double recoil_constant = 0.005;

    public static double feedforward = 0;


    /**
     * Wiring Diagram
     * Limelight called "limelight"
     * Control hub
     * Motors
     * 0 right back
     * 1 right front
     * 2 left front
     * 3 left back
     *
     * Servos
     * 5 tilt2
     *
     * I2C
     * 0 pin
     * 1 distance
     *
     * Expansion hub
     * 0 left shoot
     * 1 right shoot
     * 2 intake
     * 3 turret
     *
     * Servos
     * 0 right stop
     * 1 left stop
     * 2 shoot angle
     * 3 tilt1
     * 5 light
     */
    public static double myp = 0.006, myi = 0, myd = 0, myf = 0.05;

    public static double  shooter_constant = 1, dampening_constant = 0.95, sharpening_constant = 1.13;
    public static double turret_constant = 0.1;


    ///  drivetrain constants
    public  static double rate = 0.9;
    public static double drivetrain_p = 0.001, drivetrain_i = 0.00, drivetrain_d = 0, drivetrain_f = 0;
    public static double gate_heading_red = 30, gateheading_blue = 145;
    public static double HEADING_KP = 0.9;
    public static double HEADING_KD = 0.13;

    public static double projectile_speed = 200;

}
