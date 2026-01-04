package org.firstinspires.ftc.teamcode.unused.inspo;


/// This class stores all values such as positions for bucket, transfer, and claws.
/// allowing the dashboard to config it allows us to modify it
/// All these variables need to be public and static
public class Exapmle_Robot_Constants {

    public static String outmotorName = "out";

    /// lift Constants
    public static String up1motorname = "VLL";
    public static String up2motorname = "VLR";
    public static double ep = 0.005, ei = 0, ed = 0.00000000000005, f = 0.25; // Extend pid constants

    public static int zero = -10;

    public static int max = 800;

    /// intake
    public static double intake_speed = -0.75;
    public static double barf_speed = 1;


    /// horixontal slides

    public static double extendfulllength = 0.8;

    public static double retractfulllength = 0.14;

    /// intake wrist


    public static double down_pos = 0.95;

    public static double up_pos = 0.37;



    /// pivot deposit wrist


    public static double movegrabtransfer = 0.7;

    public static double movegrabdeposit = 1;

    // claw

    public static double clawgrab = 0.1;

    public static double clawrelease = 1;

    // pivot

    public static double idle_pos = 0.45;

    public static double mtranfer = 0.56;

    public static double deposit = 0.1;



}
