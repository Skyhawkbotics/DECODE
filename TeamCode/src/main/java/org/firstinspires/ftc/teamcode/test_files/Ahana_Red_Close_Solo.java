package org.firstinspires.ftc.teamcode.test_files;


import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.unused.whole_robot;
import com.pedropathing.paths.Path;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "BLUE close side auto", group = "gf")

public class Ahana_Red_Close_Solo extends OpModeCommand {
    Robot bot;
    public static final Pose startPose = new Pose (27.540983606557376    ,125.50819672131146, Math.toRadians(90));
    public static final Pose first3pts = new Pose(22.81967213114754    ,100.72131147540983, Math.toRadians(270));
    public static final Pose preloads = new Pose(34.62295081967213    ,113.11475409836066, Math.toRadians(270));
    public static final Pose chase1 = new Pose(21.049180327868854    ,83.21311475409834, Math.toRadians(180));
    public static final Pose score1n2 = new Pose(43.868852459016395    ,104.65573770491802, Math.toRadians(140));
    public static final Pose chase2 = new Pose(19.475409836065577    ,59.60655737704917, Math.toRadians(180));
    public static final Pose chase3 = new Pose(20.065573770491802    ,35.8032786885246, Math.toRadians(180));
    public static final Pose score3 = new Pose(64.91803278688525    ,11.409836065573767, Math.toRadians(115));

    private Path leave, scorepres, gather1, score1, gather2, score2, gather3, finalscore;

    public void buildPaths (){
        leave = new Path (new BezierLine(startPose, first3pts));
        leave.setLinearHeadingInterpolation(startPose.getHeading(), first3pts.getHeading());

        scorepres =  new Path (new BezierLine(first3pts, preloads));
        scorepres.setConstantHeadingInterpolation(Math.toRadians(270));

        gather1 = new Path (new BezierCurve
                (preloads,
                        new Pose(73.377, 79.279),
                        chase1));
        gather1.setLinearHeadingInterpolation(preloads.getHeading(), chase1.getHeading());

        score1 = new Path (new BezierLine(chase1, score1n2));
        score1.setLinearHeadingInterpolation(chase1.getHeading(),score1n2.getHeading());

        gather2 = new Path (new BezierCurve(
                score1n2,
                new Pose (52.721, 59.213),
                chase2));
        gather2.setConstantHeadingInterpolation(Math.toRadians(180));

        score2 = new Path (new BezierLine(chase2, score1n2));
        score2.setLinearHeadingInterpolation(chase2.getHeading(),score1n2.getHeading());

        gather3 = new Path (new BezierCurve(
                score1n2,
                new Pose (65.115, 24.590),
                chase3));
        gather2.setConstantHeadingInterpolation(Math.toRadians(180));

        finalscore = new Path (new BezierLine(chase3,score3));
        finalscore.setLinearHeadingInterpolation(chase3.getHeading(),score3.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, red_ID, startPose);

        schedule(
                new RunCommand(bot::robot_update), // This is a method reference ::
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), leave)
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorepres)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gather1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gather2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gather3)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), finalscore)
                                )
                )
        );
    }


}
