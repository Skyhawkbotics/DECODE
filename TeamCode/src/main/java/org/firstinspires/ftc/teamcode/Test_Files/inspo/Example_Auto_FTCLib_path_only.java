package org.firstinspires.ftc.teamcode.Test_Files.inspo;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "red far 6", group = "red", preselectTeleOp = "redCloseDrive")
/// make sure to signify it is auto, and you can additionally preselect a teleOp that will appear after the auto ends
public class Example_Auto_FTCLib_path_only extends OpModeCommand { ///  fit lib opMode structure

    Robot bot;
    public static final Pose startpos = new Pose(56, 8, Math.toRadians(90));
    public final Pose shootme = new Pose(55, 14.5, Math.toRadians(114));
    public final Pose humanintake = new Pose(10.5, 8.2, Math.toRadians(180));
    public final Pose shoottres = new Pose(50, 14.5, Math.toRadians(114));
    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));
    public final Pose curvy = new Pose(40, 11);

    /// ^^ declare paths here. Usually, include a heading and use Math.toRadians as it takes it as a radian
    /// if it's a control point usually you don't need to include a heading
    /// by including a heading on a point you can set the path to stay / move towards the heading of the pose
    ///
    ///


    private Path scorepre1, intakeuno, dametime, end; /// declare your paths
    private PathChain pathchain1; /// declaring a pathChain

    public void buildPaths () {
        /// Two types of paths - Lines or Curves
        scorepre1 = new Path (new BezierLine(startpos,shootme)); ///lines
        scorepre1.setLinearHeadingInterpolation(startpos.getHeading(), shootme.getHeading());

        intakeuno = new Path (new BezierCurve(shootme, curvy, humanintake));///curve
        intakeuno.setLinearHeadingInterpolation(shootme.getHeading(), humanintake.getHeading());

        dametime = new Path (new BezierLine(humanintake, shoottres));
        dametime.setConstantHeadingInterpolation(Math.toRadians(57));

        end = new Path (new BezierLine(shoottres, gametime));
        end.setLinearHeadingInterpolation(shoottres.getHeading(), gametime.getHeading());
        pathchain1 = bot.getFollower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                gametime,
                                humanintake, /// Control Point of the curve
                                startpos
                        )
                )
                .setLinearHeadingInterpolation(gametime.getHeading(), shootme.getHeading())
                .setBrakingStrength(0.8) ///  you can edit braking strength here
                .build();
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startpos);
        buildPaths(); // put this after robot constructor the initialization of the FOLLOWER is used for the paths
        schedule (
                new RunCommand(bot :: drivetrain_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepre1,0.85) /// changing the max power of this path
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorepre1),
                                        new FollowPath(bot.getFollower(), intakeuno),
                                        new FollowPath(bot.getFollower(), dametime),
                                        new WaitCommand(3000), ///  waits for a certain amount of time
                                        new FollowPath(bot.getFollower(), end),
                                        new FollowPath(bot.getFollower(), pathchain1)
                                )

                )
        );
    }
}
