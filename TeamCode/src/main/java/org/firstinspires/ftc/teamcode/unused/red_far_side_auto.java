package org.firstinspires.ftc.teamcode.unused;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import ftclib.OpModeCommand;

@Autonomous(name = "Red side far auto", group = "gf")
public class red_far_side_auto extends OpModeCommand {
    @Override
    public void initialize() {

    }
    /*
    Robot bot;

    public static final Pose startPose = new Pose(89,7.2, Math.toRadians(-90));

    public  final Pose hpZonePickup = new Pose(124.25,7.5, Math.toRadians(0));

    public final Pose shoot1 = new Pose(93,9.6, Math.toRadians(-110));

    public final Pose ballPickup1 = new Pose(125.5, 36, Math.toRadians(0));

    public final Pose shoot2 = new Pose(80.5,15.8, Math.toRadians(-120));

    public  final Pose parkPose = new Pose(98,15.8,Math.toRadians(0));
    private Path pickuphpballs, scorehpballs,intakerow1, scorerow1balls, park;

    public void buildPaths() {
        pickuphpballs = new Path(new BezierCurve(startPose, new Pose(110.8,15) , hpZonePickup));
        pickuphpballs.setLinearHeadingInterpolation(startPose.getHeading(), hpZonePickup.getHeading());

        scorehpballs = new Path (new BezierCurve(hpZonePickup, new Pose(110,16), shoot1));
        scorehpballs.setLinearHeadingInterpolation(hpZonePickup.getHeading(), shoot1.getHeading());

        intakerow1 = new Path (new BezierCurve(shoot1, new Pose(67,50.7), ballPickup1));
        intakerow1.setLinearHeadingInterpolation(shoot1.getHeading(), ballPickup1.getHeading());

        scorerow1balls = new Path (new BezierLine(ballPickup1, shoot2));
        scorerow1balls.setLinearHeadingInterpolation(ballPickup1.getHeading(), shoot2.getHeading());

        park = new Path(new BezierLine(shoot2, parkPose));
        park.setLinearHeadingInterpolation(shoot2.getHeading(), parkPose.getHeading());
    }

    public void initialize() {
        buildPaths();
        bot=new Robot(hardwareMap,telemetry,startPose);

        schedule(
                new RunCommand(bot::autonomous_robot_update), // This is a method reference ::
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

     */
}
