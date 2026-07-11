package org.firstinspires.ftc.teamcode.auto.canada.blue;


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
import org.firstinspires.ftc.teamcode.auto.canada.commands.prep_Shooter_velocity_only;
import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "BLUE CLOSE 21", group = "Solo", preselectTeleOp = "21 CLOSE Blue Drive")
@Configurable
public class Blue_21 extends OpModeCommand {
    Robot bot;
    private Pose redCloseStartPose = new Pose(113, 135, Math.toRadians(270));
    private Pose preloadScorePose = new Pose(89, 87, Math.toRadians(340));
    private Pose intakeSpike2Pose = new Pose(129, 60, Math.toRadians(0));
    private Pose shootPose = new Pose(88, 80);
    private static Pose beforeGateIntakePose = new Pose(113, 62);
    private static Pose gateIntakePose = new Pose(133,66, Math.toRadians(25));

    private Pose intakeSpike1Pose = new Pose(127, 86, Math.toRadians(0));
    private Pose shootPose2 = new Pose(93, 84);
    private Pose beforeSpike3Pose = new Pose(88, 36);
    private Pose intakeSpike3Pose = new Pose(130  , 36, Math.toRadians(0));
    private Pose park = new Pose(92, 113, Math.toRadians(293));



    private Pose blueredCloseStartPose = redCloseStartPose.mirror();
    private Pose bluepreloadScorePose = preloadScorePose.mirror();
    private Pose blueintakeSpike2Pose = intakeSpike2Pose.mirror();
    private Pose blueshootPose = shootPose.mirror();
    private static Pose bluebeforeGateIntakePose = beforeGateIntakePose.mirror();
    private static Pose bluegateIntakePose = gateIntakePose.mirror();

    private Pose blueintakeSpike1Pose = intakeSpike1Pose.mirror();
    private Pose blueshootPose2 = shootPose2.mirror();
    private Pose bluebeforeSpike3Pose = beforeSpike3Pose.mirror();
    private Pose blueintakeSpike3Pose = intakeSpike3Pose.mirror();
    private Pose bluepark = park.mirror();





    private Pose controlPoint1Pose = new Pose(120, 121);

    private Pose controlPoint2Pose = new Pose(88, 63);

    private Pose controlPoint3Pose = new Pose(102, 65);
    private static Pose controlPoint4Pose =  new Pose(104, 60);
    private Pose controlPoint5Pose =  new Pose(97,84);
    private Pose controlPoint6Pose =  new Pose(97, 33);


    private Pose bluecontrolPoint1Pose = controlPoint1Pose.mirror();

    private Pose bluecontrolPoint2Pose = controlPoint2Pose.mirror();

    private Pose bluecontrolPoint3Pose = controlPoint3Pose.mirror();
    private Pose bluecontrolPoint4Pose =  controlPoint4Pose.mirror();
    private Pose bluecontrolPoint5Pose =  controlPoint5Pose.mirror();
    private Pose bluecontrolPoint6Pose =  controlPoint6Pose.mirror();








    private PathChain preload_spike2, gateIntake1, return1, spike1, spike3, spike1back;


    public void buildPaths() {
        preload_spike2 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                blueredCloseStartPose, bluecontrolPoint1Pose, bluepreloadScorePose
                        )
                )

                .setConstantHeadingInterpolation(bluepreloadScorePose.getHeading())
                .addPath(
                        new BezierCurve(
                                bluepreloadScorePose,
                                bluecontrolPoint2Pose,
                                blueintakeSpike2Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierCurve(
                                blueintakeSpike2Pose,
                                bluecontrolPoint3Pose,
                                blueshootPose

                        )
                )
                .setConstantHeadingInterpolation(bluepreloadScorePose.getHeading())
                .build();
        gateIntake1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                blueshootPose,
                                bluecontrolPoint4Pose,
                                bluebeforeGateIntakePose

                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                        bluebeforeGateIntakePose,
                        bluegateIntakePose
                        )
                )
                .setConstantHeadingInterpolation(bluegateIntakePose.getHeading())
                .build();
        return1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                bluegateIntakePose,
                                blueshootPose
                        )
                )
                .setConstantHeadingInterpolation(bluepreloadScorePose.getHeading())
                .build();
        spike1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                blueshootPose,
                                bluecontrolPoint5Pose,
                                blueintakeSpike1Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        spike1back = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                blueintakeSpike1Pose,
                                blueshootPose
                        )
                )
                .setConstantHeadingInterpolation(bluepreloadScorePose.getHeading())
                .build();

        spike3 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                blueshootPose,
                                bluebeforeSpike3Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                bluebeforeSpike3Pose,
                                blueintakeSpike3Pose


                        )
                )
                .setConstantHeadingInterpolation(blueintakeSpike3Pose.getHeading())
                .addPath(
                        new BezierLine(
                                blueintakeSpike3Pose,
                                bluepark
                        )
                )
                .setConstantHeadingInterpolation(bluepreloadScorePose.getHeading())

                .build();


    }

    @Override
    public void init_loop() {
        bot.getTurret().set_using_LL(20, false); /// NO limelight?
        bot.getTurret().turret_update(20, bot.getFollower().getPose(), 0, 0);
    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, blueredCloseStartPose, 20);
        buildPaths();

        schedule(
                new RunCommand(bot::close_any_autonomous_robot_update),

                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload_spike2, true)
                                .alongWith(
                                        new prep_Shooter_preload(bot, 1380, 0.05, 1180, 0.05)
                                ),
                        new WaitCommand(400),
                        new Fire_Command(bot),

// 6

                        new ParallelRaceGroup(


                                new FollowPath(bot.getFollower(), gateIntake1, true)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),


                                new WaitCommand(2000)

                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(2000)
                        ),
                        new FollowPath(bot.getFollower(), return1)
                                .alongWith(
                                        new Prep_Fire_Command(bot)
                                ),
                        new WaitCommand(200),
                        new Fire_Command(bot),

// 9

                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), gateIntake1, true)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),

                                new WaitCommand(2000)

                        ),

                        new ParallelRaceGroup(
                                new Intake_on_automatic(bot),
                                new WaitCommand(2000)
                        ),
                        new FollowPath(bot.getFollower(), return1)
                                .alongWith(
                                        new Prep_Fire_Command(bot)
                                ),

                        new WaitCommand(200),
                        new Fire_Command(bot),


// 12
                        new FollowPath(bot.getFollower(), spike1)
                                .alongWith(
                                        new prep_Shooter_velocity_only(bot, 1130, 0.05)
                                ),
                        new Prep_Fire_Command(bot),
                        new FollowPath(bot.getFollower(), spike1back)
                                .alongWith(
                                        new Prep_Fire_Command(bot)
                                ),
                        new Fire_Command(bot),

                        // 15

                        new FollowPath(bot.getFollower(), spike3)
                                .alongWith(
                                        new Intake_Timed_Command(bot, 0.1, 4.2)
                                ),
                        new WaitCommand(200),
                        new Fire_Command(bot)
                        // 18
                )
        );






///  auto cant run at 12.6 volts?? turret takes about 2 volts, shooters takes about like 2 volts

    }
}
