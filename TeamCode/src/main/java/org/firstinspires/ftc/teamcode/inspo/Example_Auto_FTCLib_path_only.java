package org.firstinspires.ftc.teamcode.inspo;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;


import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Example Autonomous", group = "Example", preselectTeleOp = "Example TeleOp ") /// @Autonomous to show it's an auto, Name of OpMode on driver hub
/// You can additionally preselect a teleOp that will appear after the auto ends
@Disabled
public class Example_Auto_FTCLib_path_only extends OpModeCommand { ///  FTC lib opMode structure

/// Declare the "Robot" class here. This is our compiled code of all the robot's subsystems, esp. the drivetrain
    Robot bot;

    /// Declare your poses here
    public final Pose startpos = new Pose(56, 8, Math.toRadians(90));
    /// A pose is usually final (Not to be edited during it's runtime)
    /// The object type is "Pose" , and create a new Pose object.
    /// The Pose object needs a X and Y value, and you can also add a heading (Radian)
    /// by adding a heading, you can set the path to travel with / end the Pose's heading
    public final Pose shootme = new Pose(55, 14.5, Math.toRadians(114));
    /// DECLARE YOUR POSES/PATHS IN A LOGICAL MANNER. NO ONE BUT YOU KNOWS WHAT "shootme" Path DOES
    /// This is abraham's old auto

    public final Pose humanintake = new Pose(10.5, 8.2, Math.toRadians(180));
    public final Pose shoottres = new Pose(50, 14.5, Math.toRadians(114));
    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));
    public final Pose curvy = new Pose(40, 11);

    private Path scorepre1, intakeuno, dametime, end; /// declare your paths
    private PathChain pathchain1; /// declaring a pathChain
    /// Two types of Paths
    /// Path - a single line or curve
    /// PathChain - multiple lines or curves connected without stops between (faster)
    /// You can have a PathChain with only one path, so ngl I make all my paths pathChains usually since you get more features



    public void buildPaths () { /// Make the buildPaths() method which will be called in the initialization to make the paths
        /// Two types of paths - Lines or Curves
        /// Heading:
        /// You can set the heading to be Constant with setConstantHeadingInterpolation
        /// You can set the heading to be Linear, which means that it will go from 1 heading to the other specified by parameters
        ///  You can set the heading to be tangential, which means it will be tangent to the path
        /// You can also set the heading to be tangential, then make it reversed (I think this is only for pathChain) for it to travel backwards tangentially
        /// here, in PathChain's pathBuilder, you can edit more path parameters to make it perhaps smoother such as editing braking strength + start
        ///  I would suggest retuning if your paths are consistently overshooting / too slow and only editing a specific path parameter if its just applies to that path
        // https://pedropathing.com/docs/pathing/reference/path-builder

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
                                curvy, /// Control Point of the curve
                                startpos
                        )
                )
                .setTangentHeadingInterpolation()/// tangent
                .setReversed() /// reversed it
                .setBrakingStrength(0.8) ///  you can edit braking strength here
                .build();
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startpos); /// robot init.
        buildPaths(); // put this after robot constructor the initialization of the FOLLOWER is used for the paths
        schedule (
                new RunCommand(bot :: drivetrain_update), // this allows the follower to update throughout the scheduler
                new SequentialCommandGroup(///  makes the
                        new FollowPath(bot.getFollower(),scorepre1,0.85), /// changing the max power of this path
                                        new FollowPath(bot.getFollower(), scorepre1),
                                        new FollowPath(bot.getFollower(), intakeuno),
                                        new FollowPath(bot.getFollower(), dametime),
                                        new WaitCommand(3000), ///  waits for a certain amount of time
                                        new FollowPath(bot.getFollower(), end),
                                        /// additionally, you can use the following commands for more consistency / fail safes
                        /*.raceWith(
                                uncomment these and Ctrl Click / hover them to learn more
                        )
                        .alongWith(

                        )
                        .andThen(

                        )

                         */
                                        new FollowPath(bot.getFollower(), pathchain1)


                )
        );
    }
}
