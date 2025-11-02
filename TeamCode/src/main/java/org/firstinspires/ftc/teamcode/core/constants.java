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



    ///  Shooter PID constants

    public static double ep = 0.005, ei = 0, ed = 0.000001, f = 0; ///these are good as of 10/11

    public static int max = 2000;
    public static int three_fourths = 750;
    public static int half_speed = 500;


    /// constants for halle
    ///
    public static int perf_value = 1250; ///vertex of largest triangle

    public static int far_side_shoot = 1500; ///vertex of smallest triangle

    public static int closest = 1000; ///position closest on the line

    public static int middle_of_field = 1100; ///position from middle of the largest triangle

    public static double positionMin = 0.0;
    public static double positionMax = 0.4;
    public static double bottom = 1;

    public static double top = 0.5;

}
