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

    public static double ep = 0.021, ei = 0, ed = 0.00000, f = 0; ///these are good as of 10/11

    public static int max = 2000;
    public static int three_fourths = 750;
    public static int half_speed = 500;
    public static int perf_value = 1250;

    public static int far_side_shoot = 1500;

    public static double positionMin = 0.0;
    public static double positionMax = 0.4;
    public static double bottom = 1;

    public static double top = 0.5;

}
