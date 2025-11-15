package org.firstinspires.ftc.teamcode.core.ericauto;


import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.unused.whole_robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "blue side far auto", group = "gf")
@Disabled
public class blue_far_side_auto extends OpModeCommand {
    Robot bot;

    public static final Pose startPose = new Pose(56   ,8, Math.toRadians(90));

    public  final Pose shootPose = new Pose(60,20, Math.toRadians(105));

    public  final Pose parkPose = new Pose(60,30,Math.toRadians(0));
    private Path score1, park, choc;

    public void buildPaths() {
        score1 = new Path(new BezierLine(startPose, shootPose));
        score1.setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading());

        park = new Path(new BezierLine(shootPose, parkPose));
        park.setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading());

        choc = new Path(new BezierCurve(
                startPose,
                new Pose(28, 49),
                new Pose(32, 34)

        ));

    }

    public void initialize() {
        buildPaths();
        bot=new Robot(hardwareMap,telemetry,red_ID,startPose); // startpose
        bot.getFollower().setStartingPose(startPose);


        schedule(
                new RunCommand(bot::robot_update), // This is a method reference ::
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), score1)

                                                .alongWith(
                                                        new Prep_Shooter(bot)
                                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), park)
                                )

                )
        );

    }
}
