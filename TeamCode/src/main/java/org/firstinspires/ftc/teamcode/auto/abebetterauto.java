package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.unused.whole_robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "blue close side o", group = "gf")
@Disabled
public class abebetterauto extends OpModeCommand {
    whole_robot bot;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    public static final Pose pose1  = new Pose(21.083, 123.514);
    public static final Pose pose2  = new Pose(46.343, 98.055);
    public static final Pose pose3  = new Pose(33.613, 83.735);
    public static final Pose pose4  = new Pose(16.707, 83.934);
    public static final Pose pose5  = new Pose(46.541, 97.657);
    public static final Pose pose6  = new Pose(38.983, 60.066);
    public static final Pose pose7  = new Pose(15.116, 59.867);
    public static final Pose pose8  = new Pose(46.144, 97.260);
    public static final Pose pose9  = new Pose(39.580, 35.602);
    public static final Pose pose10 = new Pose(15.116, 35.602);
    public static final Pose pose11 = new Pose(45.547, 123.912);
    private Path path1, path2, path3, path4,  path5, path6, path7, path8, path9, path10;
    public void buildPaths(){
        path1 = new Path(new BezierLine(pose1, pose2));
        path1.setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading());

        path2 = new Path(new BezierCurve(
                pose2,
                new Pose (51.3149, 83.5359116),
                pose3));
        path2.setLinearHeadingInterpolation(pose2.getHeading(), pose3.getHeading());

        path3 = new Path(new BezierLine(pose3, pose4));
        path3.setLinearHeadingInterpolation(pose3.getHeading(), pose4.getHeading());

        path4 = new Path(new BezierCurve(
                pose4,
                new Pose(37, 87),
                pose5));
        path4.setLinearHeadingInterpolation(pose4.getHeading(), pose5.getHeading());

        path5 = new Path(new BezierCurve(
                pose4,
                new Pose(54.298342, 60.4640883),
                pose5));
        path5.setLinearHeadingInterpolation(pose4.getHeading(), pose5.getHeading());

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
        bot.getFollower().setStartingPose(pose1);

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
