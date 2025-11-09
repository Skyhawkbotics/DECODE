package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.ericauto.score_command_close;
import org.firstinspires.ftc.teamcode.unused.whole_robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Close Auto", group = "Test")
public class closeauto extends OpModeCommand {
    whole_robot bot;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    public static final Pose startPose = new Pose(122.5, 124.4, Math.toRadians(36)); // Start Pose of our robot.

    public final Pose shootPos = new Pose(94,95, Math.toRadians(46)); // shooting position

    public final Pose parkPos = new Pose(38.214,14.7, Math.toRadians(90)); // park position

    private Path gotoShooterSpot;
    private Path gotoPark;

    public void buildPaths(){
        gotoShooterSpot = new Path(new BezierLine(startPose, shootPos));
        gotoShooterSpot.setLinearHeadingInterpolation(startPose.getHeading(), shootPos.getHeading());

        gotoPark = new Path(new BezierLine(shootPos, parkPos));
        gotoPark.setLinearHeadingInterpolation(shootPos.getHeading(), parkPos.getHeading());
    }

    public void initialize() {
        buildPaths();
        bot = new whole_robot(hardwareMap, telemetry); // startpose
        bot.getFollower().setStartingPose(startPose);

        schedule(
                new RunCommand(bot::robot_update), // This is a method reference ::
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), gotoShooterSpot)
                                .alongWith(
                                        new score_command_close(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gotoPark)
                                )

                )
        );

    }
}
