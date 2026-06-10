package org.firstinspires.ftc.teamcode.auto.canada;


import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "aaron", group = "Solo", preselectTeleOp = "18 CLOSE Red Drive")
@Configurable
public class Red_21 extends OpModeCommand {


    Robot bot;
    private Pose redCloseStartPose = new Pose(113, 135, Math.toRadians(270));
    private Pose preloadScorePose = new Pose(89, 87, Math.toRadians(310));
    private Pose intakeSpike2Pose = new Pose(129, 62, Math.toRadians(0));
    private Pose shootPose = new Pose(87, 77);
    private static Pose beforeGateIntakePose = new Pose(120, 63);
    private static Pose gateIntakePose = new Pose(133,65, Math.toRadians(25));

    private Pose intakeSpike1Pose = new Pose(127, 84, Math.toRadians(0));
    private Pose shootPose2 = new Pose(93, 84);
    private Pose intakeSpike3Pose = new Pose(129, 34);
    private Pose park = new Pose(91, 16);






    private PathChain preload_spike2, gateIntake1, return1, spike1, spike3;


    public void buildPaths() {
        preload_spike2 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                redCloseStartPose, new Pose(124, 119), preloadScorePose
                        )
                )

                .setConstantHeadingInterpolation(preloadScorePose.getHeading())
                .addPath(
                        new BezierCurve(
                                preloadScorePose,
                                new Pose(88, 63),
                                intakeSpike2Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierCurve(
                                intakeSpike2Pose,
                                new Pose(102, 65),
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
                                new Pose(99, 61),
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
                                new Pose(97,84),
                                intakeSpike1Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                        intakeSpike1Pose,
                        shootPose2
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        spike3 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                shootPose2,
                                new Pose(103, 34),
                                intakeSpike3Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                intakeSpike3Pose,
                                shootPose


                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();


    }

    @Override
    public void init_loop() {
        bot.getTurret().set_using_LL(24);
        bot.getTurret().turret_update(24, bot.getFollower().getPose(), 0, bot.getFollower());
    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redCloseStartPose, 24);
        buildPaths();

        schedule(
                new RunCommand(bot::any_autonomous_robot_update),

                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload_spike2, true)
                                .alongWith(
                                        new prep_Shooter_preload(bot, 1380, 0.3, 1300, 0.3)
                                ),
                        new Fire_Command(bot),
/// 6 inside already
                        new ParallelRaceGroup(
                        new FollowPath(bot.getFollower(), gateIntake1)
                                .alongWith(
                                        new Intake_Command(bot)
                                ),

                                new WaitCommand(2000)

                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(1500)
                        ),
                        new FollowPath(bot.getFollower(), return1),
                        new WaitCommand(400),
                        new Fire_Command(bot),

///  9 inside

/// 12 inside
                        new ParallelRaceGroup(
                                new WaitCommand(2000),
                                new FollowPath(bot.getFollower(), gateIntake1)
                                .alongWith(
                                        new Intake_Command(bot)
                                )

                                ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(1500)
                        ),
                        new FollowPath(bot.getFollower(), return1),
                        new WaitCommand(400),
                        new Fire_Command(bot),

                                new ParallelRaceGroup(
                                        new WaitCommand(2000),

                                        new FollowPath(bot.getFollower(), gateIntake1)
                                .alongWith(
                                        new Intake_Command(bot)
                                )
                                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(1500)
                        ),
                        new FollowPath(bot.getFollower(), return1),
                        new WaitCommand(400),
                        new Fire_Command(bot),

/// 15 inside
                        new FollowPath(bot.getFollower(), spike1)
                        .alongWith(
                                new Prep_Shooter_any(bot, 1160, 0),
                                new Intake_Timed_Command(bot, 0.2, 1)
                        ),
                        new Fire_Command(bot),



                        new FollowPath(bot.getFollower(), spike3)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1300, 0.3),
                                        new Intake_Timed_Command(bot, 0.2, 2)
                                ),
                        new Fire_Command(bot)
                                )

        );





///  auto cant run at 12.6 volts?? turret takes about 2 volts, shooters takes about like 2 volts

    }
}
