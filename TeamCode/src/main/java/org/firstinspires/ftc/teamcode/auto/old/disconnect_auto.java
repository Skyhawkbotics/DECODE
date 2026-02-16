package org.firstinspires.ftc.teamcode.auto.old;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Preload_score_red;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "Disconnect Auto", group = "Red")
@Disabled
public class disconnect_auto extends OpModeCommand {
    Robot bot;

    public static final Pose startPose = new Pose(88, 7, Math.toRadians(0));

    public static final Pose preloadPose = new Pose(88, 13, Math.toRadians(0));
    // start pose is the tip of the triangle / the inner line of the next tile
    public final Pose pickup1 = new Pose(125, 7, Math.toRadians(0));
    public final Pose pickup2 = new Pose(135, 7, Math.toRadians(-5));
    private PathChain preload, pickuphp1, reversefromHP;
    public void buildPaths(){
        preload = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(startPose, preloadPose)
                )
                .setConstantHeadingInterpolation(0)
                .build();

        pickuphp1 = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(preloadPose, pickup1)
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(pickup1, pickup2)
                )
                .setLinearHeadingInterpolation(preloadPose.getHeading(),pickup2.getHeading())
                .setGlobalDeceleration()
                .build();
        reversefromHP = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(pickup2, preloadPose)
                )
                .setLinearHeadingInterpolation(pickup2.getHeading(),preloadPose.getHeading())
                .build();

    }

    public void initialize(){
        bot = new Robot(hardwareMap, telemetry,startPose, 24);
        buildPaths();

        schedule (
                new RunCommand(bot :: far_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload, true)
                                .alongWith(
                                        new Preload_score_red(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), pickuphp1, true)
                                )
                                .andThen(
                                        new WaitCommand(400)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), reversefromHP, true)
                                )
                )

        );
    }

}

