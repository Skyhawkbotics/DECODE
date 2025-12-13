package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;

import com.pedropathing.paths.Path;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Andy far side red")


public class Andyfarsideblue extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(54.5, 8.7, (Math.toRadians(90)));

    public static final Pose shootpos = new Pose(54.5, 15.6, (Math.toRadians(112)));

    public static final Pose grab1 = new Pose(48, 35, (Math.toRadians(180)));

    public static final Pose grab2 = new Pose(24, 35, (Math.toRadians(180)));

    public static final Pose grab3 = new Pose(48, 59.5, (Math.toRadians(180)));

    public static final Pose grab4 = new Pose(24, 59.5, (Math.toRadians(180)));

    public static final Pose grab5 = new Pose(48, 83.6, (Math.toRadians(180)));

    public static final Pose grab6 = new Pose(24, 83.6, (Math.toRadians(180)));

    public static final Pose end = new Pose(54.5, 35, (Math.toRadians(90)));

    private Path line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12;

    public void buildPaths() {
        line1 = new Path (new BezierLine(startpos, shootpos));
        line1.setLinearHeadingInterpolation(startpos.getHeading(), shootpos.getHeading());

        line2 = new Path (new BezierLine(shootpos, grab1));
        line2.setLinearHeadingInterpolation(shootpos.getHeading(), grab1.getHeading());

        line3 = new Path (new BezierLine(grab1, grab2));
        line3.setConstantHeadingInterpolation(Math.toRadians(180));

        line4 = new Path (new BezierLine(grab2, shootpos));
        line4.setLinearHeadingInterpolation(grab2.getHeading(), shootpos.getHeading());

        line5 = new Path (new BezierLine(shootpos, grab3));
        line5.setLinearHeadingInterpolation(shootpos.getHeading(), grab3.getHeading());

        line6 = new Path (new BezierLine(grab3, grab4));
        line6.setConstantHeadingInterpolation(Math.toRadians(180));

        line7 = new Path (new BezierLine(grab4, shootpos));
        line7.setLinearHeadingInterpolation(grab4.getHeading(), shootpos.getHeading());

        line8 = new Path (new BezierLine(shootpos, grab5));
        line8.setLinearHeadingInterpolation(shootpos.getHeading(), grab5.getHeading());

        line9 = new Path (new BezierLine(grab5, grab6));
        line9.setConstantHeadingInterpolation(Math.toRadians(180));

        line10 = new Path (new BezierLine(grab6, shootpos));
        line10.setLinearHeadingInterpolation(grab6.getHeading(), shootpos.getHeading());

        line11 = new Path(new BezierLine(shootpos, startpos));
        line11.setLinearHeadingInterpolation(shootpos.getHeading(), startpos.getHeading());

        line12 = new Path(new BezierLine(shootpos, end));
        line12.setLinearHeadingInterpolation(shootpos.getHeading(), end.getHeading());
    }

    public void  initialize() {
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, startpos);

        schedule(
                new RunCommand(bot:: robot_update),
                new SequentialCommandGroup(


                        new FollowPath(bot.getFollower(), line1, 1)
                                .andThen(
                                        new WaitCommand(4000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line2, 1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line3, 0.7)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line4, 1)
                                )
                                .andThen(
                                        new WaitCommand(4000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line5, 1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line6, 0.7)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line7, 1)
                                )
                                .andThen(
                                        new WaitCommand(4000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line7, 1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line8,1 )
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line9, 0.7)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line10, 1)
                                )
                                .andThen(
                                        new WaitCommand(4000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), line12, 1)
                                )

                )
        );
    }

}
