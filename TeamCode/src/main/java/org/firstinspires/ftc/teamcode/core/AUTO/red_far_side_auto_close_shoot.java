package org.firstinspires.ftc.teamcode.core.AUTO;


import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.close_score_command;
import org.firstinspires.ftc.teamcode.core.commands.intake_swallow;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Red side far auto close shoot", group = "gf")
public class red_far_side_auto_close_shoot extends OpModeCommand {
    Robot bot;

    public static final Pose startPose = new Pose(89,7.2, Math.toRadians(-90));

    public  final Pose hpZonePickup = new Pose(124.25,7.5, Math.toRadians(0));

    public final Pose shoot1 = new Pose(84.25,83.3, Math.toRadians(-130));

    public final Pose ballPickup1 = new Pose(130, 32, Math.toRadians(0));

    public  final Pose parkPose = new Pose(98,15.8,Math.toRadians(0));
    private Path pickuphpballs, scorehpballs,intakerow1, scorerow1balls, park;

    public void buildPaths() {
        pickuphpballs = new Path(new BezierCurve(startPose, new Pose(110.8,15) , hpZonePickup));
        pickuphpballs.setLinearHeadingInterpolation(startPose.getHeading(), hpZonePickup.getHeading());

        scorehpballs = new Path (new BezierCurve(hpZonePickup, new Pose(110,16), shoot1));
        scorehpballs.setLinearHeadingInterpolation(hpZonePickup.getHeading(), shoot1.getHeading());

        intakerow1 = new Path (new BezierCurve(shoot1, new Pose(67,58), ballPickup1));
        intakerow1.setLinearHeadingInterpolation(shoot1.getHeading(), ballPickup1.getHeading());

        scorerow1balls = new Path (new BezierCurve(ballPickup1,new Pose(89,55) ,shoot1));
        scorerow1balls.setLinearHeadingInterpolation(ballPickup1.getHeading(), shoot1.getHeading());

        park = new Path(new BezierLine(shoot1, parkPose));
        park.setLinearHeadingInterpolation(shoot1.getHeading(), parkPose.getHeading());
    }

    public void initialize() {
        buildPaths();
        bot=new Robot(hardwareMap,telemetry,red_ID,startPose);

        schedule(
                new RunCommand(bot::auto_robot_update), // This is a method reference ::
                new SequentialCommandGroup(
                        new Prep_Shooter(bot)
                                .andThen(
                                        new close_score_command(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), pickuphpballs)
                                                .alongWith(
                                                        new intake_swallow(bot)
                                                )
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),scorehpballs)
                                                .alongWith(
                                                        new intake_swallow(bot)
                                                )
                                )
                                .andThen(
                                        new close_score_command(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakerow1)
                                                .alongWith(
                                                        new intake_swallow(bot)
                                                )
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorerow1balls)
                                )
                                .andThen(
                                        new close_score_command(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), park)
                                )

                )
        );

    }
}
