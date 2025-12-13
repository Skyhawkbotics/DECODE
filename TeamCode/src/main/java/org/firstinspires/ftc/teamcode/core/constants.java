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
    public static double left_limit = -750;
    public static double right_limit = 750;

    public static int blue_ID = 20;
    public static int red_ID = 24;


    ///  Shooter PID constants

    public static double ep = 0.006, ei = 0, ed = 0.00000, f = 0.5; ///these are good as of 10/11

    public static int max = 2000;


    ///
    public static int auto_speed = 1200;
    public static int perf_value = 1450; ///vertex of largest triangle

    public static int far_side_shoot = 1560; ///vertex of smallest triangle

    public static int closest = 1150; ///position closest on the line

    public static int middle_of_field = 1200; ///position from middle of the largest triangle

    public static double positionMin = 0.05;
    public static double positionMax = 0.80;
    public static double bottom = 1;

    public static double top = 0.5;
    public static double angle = 90;




    ///  Turret  + Turret PID controller
    public static double turret_p = 0.005, turret_i = 0, turret_d = 0, turret_f = 0.01;
    public static double turret_align_heading = 0;
    public static double degrees_to_ticks;


    /// Intake speed constants

    public static double intake_speed = 1;



}
