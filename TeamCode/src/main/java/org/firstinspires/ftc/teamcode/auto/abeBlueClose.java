package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.unused.whole_robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "blue close side o", group = "gf")
@Disabled
public class abeBlueClose extends OpModeCommand {
    Robot bot;
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
                pose5,
                new Pose(54.298342, 60.4640883),
                pose6));
        path5.setLinearHeadingInterpolation(pose4.getHeading(), pose5.getHeading());

        path6 = new Path(new BezierLine(pose6, pose7));
        path6.setLinearHeadingInterpolation(pose6.getHeading(), pose7.getHeading());
        path7 = new Path(new BezierCurve(
                pose7,
                new Pose (50.12154696, 65.8342541),
                pose8)
        );
        path7.setLinearHeadingInterpolation(pose7.getHeading(),pose8.getHeading());
        path8 = new Path(new BezierCurve(
                pose8,
                new Pose (56.0883977, 35.80110497),
                pose9)
        );
        path8.setLinearHeadingInterpolation(pose8.getHeading(),pose9.getHeading());

        path9 = new Path(new BezierLine(pose9, pose10));
        path9.setLinearHeadingInterpolation(pose9.getHeading(), pose10.getHeading());

        path10 = new Path(new BezierCurve(
                pose10,
                new Pose (54.0994475, 79.55801104),
                pose11)
        );
        path10.setLinearHeadingInterpolation(pose10.getHeading(),pose11.getHeading());


    }
    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, red_ID, pose1);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), path1)
                                .andThen(
                                        new FollowPath(bot.getFollower(), path2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), path3)//intake pls
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),path4) //shooter
                                )
                                .andThen (
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),path5)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), path6) //intake
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), path7)
                                )
                                .andThen (
                                        new WaitCommand(3000) //shoot
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), path8)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), path9) //intake
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), path10)
                                ).andThen (
                                        new WaitCommand(3000) //shoot
                                )
                )
        );
    }

}
