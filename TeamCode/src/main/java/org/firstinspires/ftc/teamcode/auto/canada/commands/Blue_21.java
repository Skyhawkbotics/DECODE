package org.firstinspires.ftc.teamcode.auto.canada.commands;


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

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "BLUE CLOSE 21", group = "Solo", preselectTeleOp = "18 CLOSE Blue Drive")
@Configurable
public class Blue_21 extends OpModeCommand {


    Robot bot;
    private Pose redCloseStartPose = new Pose(113, 135, Math.toRadians(270));
    private Pose preloadScorePose = new Pose(89, 87, Math.toRadians(310));
    private Pose intakeSpike2Pose = new Pose(129, 62, Math.toRadians(0));
    private Pose shootPose = new Pose(87, 77);
    private static Pose beforeGateIntakePose = new Pose(120, 60);
    private static Pose gateIntakePose = new Pose(133,65, Math.toRadians(25));

    private Pose intakeSpike1Pose = new Pose(127, 84, Math.toRadians(0));
    private Pose shootPose2 = new Pose(93, 84);
    private Pose intakeSpike3Pose = new Pose(129, 34);
    private Pose park = new Pose(91, 16);


    private Pose blueredCloseStartPose = redCloseStartPose.mirror();
    private Pose bluepreloadScorePose = preloadScorePose.mirror();
    private Pose blueintakeSpike2Pose = intakeSpike2Pose.mirror();
    private Pose blueshootPose = shootPose.mirror();
    private static Pose bluebeforeGateIntakePose = beforeGateIntakePose.mirror();
    private static Pose bluegateIntakePose = gateIntakePose.mirror();

    private Pose blueintakeSpike1Pose = intakeSpike1Pose.mirror();
    private Pose blueshootPose2 = shootPose2.mirror();
    private Pose blueintakeSpike3Pose = intakeSpike3Pose.mirror();
    private Pose bluepark = park.mirror();




    private Pose controlPoint1Pose = new Pose(124, 119);

    private Pose controlPoint2Pose = new Pose(88, 63);

    private Pose controlPoint3Pose = new Pose(102, 65);
    private Pose controlPoint4Pose =  new Pose(99, 58);
    private Pose controlPoint5Pose =  new Pose(97,84);
    private Pose controlPoint6Pose =  new Pose(93.33030852994557, 29.818511796733212);


    private Pose bluecontrolPoint1Pose = controlPoint1Pose.mirror();

    private Pose bluecontrolPoint2Pose = controlPoint2Pose.mirror();

    private Pose bluecontrolPoint3Pose = controlPoint3Pose.mirror();
    private Pose bluecontrolPoint4Pose =  controlPoint4Pose.mirror();
    private Pose bluecontrolPoint5Pose =  new Pose(97,84);
    private Pose bluecontrolPoint6Pose =  new Pose(93.33030852994557, 29.818511796733212);











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
                                controlPoint6Pose,
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
        bot.getTurret().set_using_LL(24, false); /// NO limelight?
        bot.getTurret().turret_update(24, bot.getFollower().getPose(), 0, 0);
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
                                new Intake_Timed_Command(bot, 0, 1.5)
                        ),
                        new Fire_Command(bot),



                        new FollowPath(bot.getFollower(), spike3)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1300, 0.3),
                                        new Intake_Timed_Command(bot, 0, 3)
                                ),
                        new Fire_Command(bot)
                                )

        );





///  auto cant run at 12.6 volts?? turret takes about 2 volts, shooters takes about like 2 volts

    }
}
