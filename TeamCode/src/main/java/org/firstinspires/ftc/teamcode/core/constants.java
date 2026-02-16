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
    public static double right_limit = 667;

    public static int blue_ID = 20;
    public static int red_ID = 24;


    ///  Shooter PID constants

    public static double ep = 0.0045, ei = 0, ed = 0.0000, f = 0.45; ///these are good as of 10/11

    public static int max = 2000;

    public static Pose redPose = new Pose(144, 144);
    public static Pose bluePose = new Pose(0, 144);
    ///
    public static double tof_constant = 0.065;
    public static double tof_coefficient = 0.20;
    public static int auto_speed = 1170;
    public static int auto_speed2 = 1060;

    public static int auto_speed3 = 1150;
    public static int auto_speed4 = 1260;
    public static int auto_speed_blue = 1140;
    public static int perf_value = 1450; ///vertex of largest triangle

    public static int far_side_shoot = 1740; ///vertex of smallest triangle
    public static double far_side_angle = 0.6;

    public static int far_side_turret_offset = 521, far_side_turret_offset_blue = -400;

    public static double far_zone_tx = -2;

    public static double turret_close_pos = 420;


    public static int closest = 1150; ///position closest on the line

    public static int middle_of_field = 1200; ///position from middle of the largest triangle

    public static double positionMin = 0.05;
    public static double positionMax = 0.95;
    public static double bottom = 1;

    public static double top = 0.5;
    public static double angle = 90;




    ///  Turret  + Turret PID controller
    public static double turret_p = 0.006, turret_i = 0, turret_d = 0, turret_f = 0.05;

    public static double lf = 0.04;
    public static double turret_align_heading = 0;
    public static double degrees_to_ticks;


    ///  Limelight PID Controller

    public static double lp = 0.011;
    public static double li = 0;
    public static double ld = 0.00002;


    /// Intake speed constants

    public static double intake_speed = 1;
    public static double intake_slow = 0.8;

    public static double right_servos_in_pos = 0.5;
    public static double left_servos_in_pos = 0.8;
    public static double right_servos_out_pos = 0;
    public static double left_servos_out_pos = 1;



    public static double recoil_constant = 0.002;



}
