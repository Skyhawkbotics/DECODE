package org.firstinspires.ftc.teamcode.test_files;

import android.graphics.Point;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.FuturePose;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.whole_robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "red far side o", group = "gf")
public class ahana_redFS_autotest extends OpModeCommand {
    whole_robot bot;
    public static final Pose startPose = new Pose (58.21    ,9.05, Math.toRadians(90));
    public static final Pose first3pts = new Pose(57.6    ,27.56, Math.toRadians(90));
    public static final Pose preloads = new Pose(65.42    ,11.52, Math.toRadians(64));
    public static final Pose chase1 = new Pose(20.16    ,35.38, Math.toRadians(180));
    public static final Pose score1 = new Pose(62.74    ,11.314, Math.toRadians(180));
    public static final Pose chase2 = new Pose(17.6    ,60, Math.toRadians(180));
    public static final Pose score2 = new Pose(61.1    ,84.14, Math.toRadians(180));
    public static final Pose chase3 = new Pose(18.51    ,83.52, Math.toRadians(180));
    public static final Pose score3 = new Pose(61.2    ,84.34, Math.toRadians(35));

    private Path leave, scorepres, gather1, scoreyi,  gather2, scoreer, gather3, scoresan;
    public void buildPaths(){
        leave = new Path(new BezierLine(startPose, first3pts));
        leave.setLinearHeadingInterpolation(startPose.getHeading(), first3pts.getHeading());

        scorepres = new Path(new BezierLine(first3pts,preloads));
        scorepres.setLinearHeadingInterpolation(first3pts.getHeading(), preloads.getHeading());

        gather1 = new Path(new BezierCurve(
                preloads,
                new Pose(57.6, 37.85),
                chase1));
        gather1.setLinearHeadingInterpolation(preloads.getHeading(), chase1.getHeading());

        scoreyi = new Path(new BezierLine(chase1, score1));
        scoreyi.setLinearHeadingInterpolation(chase1.getHeading(), score1.getHeading());

        gather2 = new Path(new BezierCurve(
                score1,
                new Pose (61.1, 84.14),
                chase2)
        );
        gather2.setLinearHeadingInterpolation(score1.getHeading(),chase2.getHeading());

        scoreer = new Path(new BezierLine(chase2, score2));
        scoreer.setLinearHeadingInterpolation(chase2.getHeading(), score2.getHeading());

        gather3 = new Path(new BezierLine(score2, chase3));
        gather3.setLinearHeadingInterpolation(score2.getHeading(), chase3.getHeading());

        scoresan = new Path(new BezierLine(chase3, score3));
        scoresan.setLinearHeadingInterpolation(chase3.getHeading(), score3.getHeading());


    }
    public void initialize(){
        buildPaths();
        bot = new whole_robot (hardwareMap, telemetry);
        bot.getFollower().setStartingPose(startPose);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), leave)
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorepres)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gather1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),scoreyi)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),gather2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), scoreer)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gather3)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), scoresan)
                                )
                )
        );
    }

}
