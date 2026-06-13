package org.firstinspires.ftc.teamcode.auto.canada.red;


import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.canada.commands.Fire_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_Timed_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_on_automatic;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Prep_Fire_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.prep_Shooter_preload;
import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "RED CLOSE 21", group = "Solo", preselectTeleOp = "21 CLOSE Red Drive")
@Configurable
public class Red_21 extends OpModeCommand {


    Robot bot;
    private Pose redCloseStartPose = new Pose(113, 135, Math.toRadians(270));
    private Pose preloadScorePose = new Pose(89, 87, Math.toRadians(310));
    private Pose intakeSpike2Pose = new Pose(129, 60, Math.toRadians(0));
    private Pose shootPose = new Pose(88, 80);
    private static Pose beforeGateIntakePose = new Pose(113, 62);
    private static Pose gateIntakePose = new Pose(132.0,65, Math.toRadians(30));

    private Pose intakeSpike1Pose = new Pose(120, 84, Math.toRadians(0));
    private Pose shootPose2 = new Pose(93, 84);
    private Pose beforeSpike3Pose = new Pose(98, 61);
    private Pose intakeSpike3Pose = new Pose(124  , 36, Math.toRadians(0));
    private Pose park = new Pose(92, 113, Math.toRadians(293));





    private Pose controlPoint1Pose = new Pose(124, 119);

    private Pose controlPoint2Pose = new Pose(88, 63);

    private Pose controlPoint3Pose = new Pose(102, 65);
    private static Pose controlPoint4Pose =  new Pose(104, 60);
    private Pose controlPoint5Pose =  new Pose(97,84);
    private Pose controlPoint6Pose =  new Pose(97, 33);











    private PathChain preload_spike2, gateIntake1, return1, spike1, spike3;


    public void buildPaths() {
        preload_spike2 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                redCloseStartPose, controlPoint1Pose, preloadScorePose
                        )
                )

                .setConstantHeadingInterpolation(preloadScorePose.getHeading())
                .addPath(
                        new BezierCurve(
                                preloadScorePose,
                                controlPoint2Pose,
                                intakeSpike2Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierCurve(
                                intakeSpike2Pose,
                                controlPoint3Pose,
                                shootPose

                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        gateIntake1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                shootPose,
                                controlPoint4Pose,
                                beforeGateIntakePose

                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                        beforeGateIntakePose,
                        gateIntakePose
                        )
                )
                .setConstantHeadingInterpolation(gateIntakePose.getHeading())

                .build();
        return1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                gateIntakePose,
                                shootPose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        spike1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                shootPose,
                                controlPoint5Pose,
                                intakeSpike1Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                        intakeSpike1Pose,
                        shootPose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        spike3 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                shootPose,
                                beforeSpike3Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierCurve(
                                beforeSpike3Pose,
                                controlPoint6Pose,
                                intakeSpike3Pose


                        )
                )
                .setConstantHeadingInterpolation(intakeSpike3Pose.getHeading())
                .addPath(
                        new BezierLine(
                                intakeSpike3Pose,
                                park
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();


    }

    @Override
    public void init_loop() {
        bot.getTurret().set_using_LL(24, false); /// NO limelight?
        bot.getTurret().turret_update(24, bot.getFollower().getPose(), 0, 0);
    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redCloseStartPose, 24);
        buildPaths();

        schedule(
                new RunCommand(bot::close_any_autonomous_robot_update),

                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload_spike2, true)
                                .alongWith(
                                        new prep_Shooter_preload(bot, 1380, 0.3, 1180, 0.05)
                                ),
                        new WaitCommand(100),
                        new Fire_Command(bot),



                        new ParallelRaceGroup(
                        new FollowPath(bot.getFollower(), gateIntake1, true)
                                .alongWith(
                                        new Intake_Command(bot)
                                ),

                                new WaitCommand(2000)

                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(1000)
                        ),
                        new FollowPath(bot.getFollower(), return1)
                                .alongWith(
                        new Prep_Fire_Command(bot)
                                ),

                        new WaitCommand(50),
                        new Fire_Command(bot),



                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), gateIntake1, true)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),

                                new WaitCommand(2000)

                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(1500)
                        ),
                        new FollowPath(bot.getFollower(), return1)
                                .alongWith(
                                        new Prep_Fire_Command(bot)
                                ),

                        new WaitCommand(50),
                        new Fire_Command(bot),




                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), gateIntake1, true)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),

                                new WaitCommand(2000)

                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(1500)
                        ),
                        new FollowPath(bot.getFollower(), return1)
                                .alongWith(
                                        new Prep_Fire_Command(bot)
                                ),

                        new WaitCommand(50),
                        new Fire_Command(bot),





                        new FollowPath(bot.getFollower(), spike1)
                                .alongWith(
                                        new Intake_Command(bot)
                                ),

                        new Fire_Command(bot),



                       new FollowPath(bot.getFollower(), spike3)
                               .alongWith(
                                        new Intake_Timed_Command(bot, 0.1, 3)
                               ),
                       new Fire_Command(bot)
                                )

        );





///  auto cant run at 12.6 volts?? turret takes about 2 volts, shooters takes about like 2 volts

    }
}
