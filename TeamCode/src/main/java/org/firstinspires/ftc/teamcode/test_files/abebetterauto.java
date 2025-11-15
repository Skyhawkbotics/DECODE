package org.firstinspires.ftc.teamcode.test_files;


import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import ftclib.OpModeCommand;

@Autonomous (name = "assist red close", group = "gf")
public class abebetterauto extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose(117.348, 129.680, Math.toRadians(216));

    public final Pose presets = new Pose (95, 94.5, Math.toRadians(180));

    public final Pose path2 = new Pose (101.038674, 83.93370165, Math.toRadians(180));

    public final Pose path3 = new Pose (127.492, 83.536, Math.toRadians(180));

    public final Pose path4 = new Pose(127.890, 73.790, Math.toRadians(180));

    public final Pose path5 = new Pose(95.000, 94.500, Math.toRadians(180));

    public final Pose path6 = new Pose (95.470, 60.663, Math.toRadians(180));

    public final Pose path7 = new Pose(128.884, 58.873, Math.toRadians(180));

    public final Pose path8 = new Pose (95, 94.5, Math.toRadians(180));

    public final Pose path9 = new Pose (99.448, 35.204, Math.toRadians(180));

    public final Pose path10 = new Pose (128.486, 35.204, Math.toRadians(180));

    public final Pose path11 = new Pose (95, 94.5, Math.toRadians(180));

    public final Pose path12 = new Pose (120, 94.5, Math.toRadians(180));


    private Path startpath, secondpath, thirdpath, fourthpath, fifthpath, sixthpath, seventhpath, eighthpath, ninthpath, tenthpath, eleventhpath, twelvepath;
    public void buildPaths () {
        startpath = new Path (new BezierLine(startpose, presets));
        startpath.setLinearHeadingInterpolation(startpose.getHeading(), presets.getHeading());

        secondpath = new Path (new BezierCurve(
                presets,
                new Pose (92.0883977, 84.9281767),
                path2));
        secondpath.setConstantHeadingInterpolation(180);

        thirdpath = new Path (new BezierLine(path2, path3));
        thirdpath.setConstantHeadingInterpolation(180);

        fourthpath = new Path (new BezierCurve(
                path3,
                new Pose (74.1878453, 78.7624309),
                path4));
        fourthpath.setConstantHeadingInterpolation(180);

        fifthpath = new Path (new BezierCurve(
                path4,
                new Pose (87.9116022, 74.785303),
                path5));
        fifthpath.setConstantHeadingInterpolation(180);

        sixthpath = new Path (new BezierCurve(
                path5,
                new Pose (86.91712707, 70.8066295),
                path6));
        sixthpath.setConstantHeadingInterpolation(180);

        seventhpath = new Path (new BezierLine(path6, path7));
        seventhpath.setConstantHeadingInterpolation(180);

        eighthpath = new Path (new BezierCurve(
                path7,
                new Pose (84.9281767, 65.8342541),
                path8));
        eighthpath.setConstantHeadingInterpolation(180);

        ninthpath = new Path (new BezierCurve(
                path8,
                new Pose (82.9392265, 35.2044198),
                path9));
        ninthpath.setConstantHeadingInterpolation(180);

        tenthpath = new Path (new BezierLine(path9, path10));
        tenthpath.setConstantHeadingInterpolation(180);

        eleventhpath = new Path (new BezierCurve(
                path10,
                new Pose (88.3093922, 75.3812154),
                path11));
        eleventhpath.setConstantHeadingInterpolation(180);

        twelvepath = new Path (new BezierLine(path11, path12));
        twelvepath.setConstantHeadingInterpolation(180);
    }
    public void initialize () {
        buildPaths();
        bot = new Robot (hardwareMap, telemetry, 24);
        bot.getFollower().setStartingPose(startpose);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath (bot.getFollower())
                )
        )


    }
}
