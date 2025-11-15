package org.firstinspires.ftc.teamcode.core.ericauto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.unused.whole_robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Red side far auto", group = "gf")
@Disabled
public class red_far_side_auto extends OpModeCommand {
    whole_robot bot;

    public static final Pose startPose = new Pose(84    ,8, Math.toRadians(90));

    public  final Pose shootPose = new Pose(80,20, Math.toRadians(70));

    public  final Pose parkPose = new Pose(84,40,Math.toRadians(90));
    private Path score1, park;

    public void buildPaths() {
        score1 = new Path(new BezierLine(startPose, shootPose));
        score1.setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading());

        park = new Path(new BezierLine(shootPose, parkPose));
        park.setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading());
    }

    public void initialize() {
        buildPaths();
        bot = new whole_robot(hardwareMap, telemetry); // startpose
        bot.getFollower().setStartingPose(startPose);

        schedule(
                new RunCommand(bot::robot_update), // This is a method reference ::
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), score1)
                                .alongWith(
                                        new score_command(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), park)
                                )

                )
        );

    }
}
