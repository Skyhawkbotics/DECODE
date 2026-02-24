package org.firstinspires.ftc.teamcode.auto.State_Auto;


import static org.firstinspires.ftc.teamcode.core.constants.auto_speed4;

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
import org.firstinspires.ftc.teamcode.core.commands.turret.turret_Zero;
import org.firstinspires.ftc.teamcode.core.commands.turret.turret_yes;
import org.firstinspires.ftc.teamcode.core.commands.turret.turret_LL_blue;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Red 15 solo", group = "Solo")
public class RED_15 extends OpModeCommand {


    Robot bot;
    private final Pose startPose = new Pose(111.000, 134.000, Math.toRadians(90));
    private final Pose beforeScorePose = new Pose(96, 103);
    private final Pose scorePose = new Pose(87, 85, Math.toRadians(320));

    private final Pose beforeSpike2 = new Pose(100, 60);
    private final Pose spike2End = new Pose(120, 60);
    private final Pose openGatePosePrep = new Pose(121, 63, Math.toRadians(0));
    private final Pose openGatePose = new Pose(127, 63, Math.toRadians(0));
    private final Pose openGateBackPose = new Pose(121, 65, Math.toRadians(0));







    private PathChain preload,spike2, opengate,spike2Return1;


    public void buildPaths() {
        preload = bot.getFollower().pathBuilder()
                .addPath(
                    new BezierLine(
                        startPose,
                        beforeScorePose
                    )
                )
                .addPath(
                        new BezierLine(
                        beforeScorePose,
                        scorePose
                        )
                )
                .setBrakingStrength(5)
                .setGlobalLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();

        spike2 = bot.getFollower().pathBuilder()
                .addPath(
                new BezierCurve(
                                scorePose,
                                new Pose(90.000, 60.000), // Control Point
                                beforeSpike2
                        )
                )
                .setLinearHeadingInterpolation(scorePose.getHeading(),beforeSpike2.getHeading())
                .addPath(
                        new BezierLine(
                                beforeSpike2,
                                spike2End
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                spike2End,
                                openGatePosePrep

                        )
                )
                .setConstantHeadingInterpolation(openGatePosePrep.getHeading())
             //   .setGlobalDeceleration()
                .build();


        opengate = bot.getFollower()
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                openGatePosePrep,
                                openGatePose
                        )
                )
                .setConstantHeadingInterpolation(openGatePose.getHeading())
                .build();



        spike2Return1 = bot.getFollower().pathBuilder().addPath(
                // maintain same heading for a bit to avoid gate stuck
                // but also make it tangental so its fastest
                // but also make it correct last minute for turret
                        new BezierLine(
                                openGatePose,

                                openGateBackPose
                        )
                )
                .setConstantHeadingInterpolation(openGatePose.getHeading())
                .addPath(
                        new BezierCurve(
                                openGateBackPose,
                                new Pose(103.000, 68.000),
                                new Pose(92.000, 80.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                new Pose(92.000, 80.000),
                                scorePose
                                )
                ).setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
    }



    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startPose, 24);
        buildPaths();

        schedule(
                new RunCommand(bot:: autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload, 0.8)
                                .andThen(
                                        new WaitCommand(2000)
                                )
                               .andThen(
                                        new FollowPath(bot.getFollower(), spike2)
                                                .alongWith(
                                                        new Intake_on_forever(bot)
                                                )
                                )

                                .andThen(
                                        new WaitCommand(2000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), opengate)
                                )
                                /*
                                .andThen(
                                        new WaitCommand(2000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike2Return1)
                                )


                               */

                ));
    }
}

