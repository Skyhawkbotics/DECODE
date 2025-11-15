package org.firstinspires.ftc.teamcode.core.ericauto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import ftclib.FollowPath;
import ftclib.OpModeCommand;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

@Autonomous(name = "Red side far auto", group = "gf")
public class red_far_side_auto extends OpModeCommand {
    Robot bot;

    public static final Pose startPose = new Pose(89,7.2, Math.toRadians(-90));

    public  final Pose hpZonePickup = new Pose(124.25,7.5, Math.toRadians(0));

    public final Pose shoot1 = new Pose(93,9.6, Math.toRadians(-110));

    public final Pose ballPickup1 = new Pose(125.5, 36, Math.toRadians(0));

    public final Pose shoot2 = new Pose(80.5,15.8, Math.toRadians(-120));

    public  final Pose parkPose = new Pose(98,15.8,Math.toRadians(0));
    private Path pickuphpballs, scorehpballs,intakerow1, scorerow1balls, park;

    public void buildPaths() {
        pickuphpballs = new Path(new BezierLine(startPose, hpZonePickup));
        pickuphpballs.setLinearHeadingInterpolation(startPose.getHeading(), hpZonePickup.getHeading());

        scorehpballs = new Path (new BezierLine(hpZonePickup, shoot1));
        scorehpballs.setLinearHeadingInterpolation(hpZonePickup.getHeading(), shoot1.getHeading());

        intakerow1 = new Path (new BezierLine(shoot1, ballPickup1));
        intakerow1.setLinearHeadingInterpolation(shoot1.getHeading(), ballPickup1.getHeading());

        scorerow1balls = new Path (new BezierLine(ballPickup1, shoot2));
        scorerow1balls.setLinearHeadingInterpolation(ballPickup1.getHeading(), shoot2.getHeading());

        park = new Path(new BezierLine(shoot2, parkPose));
        park.setLinearHeadingInterpolation(shoot2.getHeading(), parkPose.getHeading());
    }

    public void initialize() {
        buildPaths();
        bot=new Robot(hardwareMap,telemetry,red_ID,startPose);

        schedule(
                new RunCommand(bot::auto_robot_update), // This is a method reference ::
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), pickuphpballs)
                                .andThen(
                                        new FollowPath(bot.getFollower(),scorehpballs)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakerow1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorerow1balls)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), park)
                                )

                )
        );

    }
}
