package org.firstinspires.ftc.teamcode.auto.State_Auto;


import static org.firstinspires.ftc.teamcode.core.constants.auto_speed4;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Red 15 solo", group = "Solo")
public class RED_15 extends OpModeCommand {


    Robot bot;
    private final Pose startPose = new Pose(111.000, 134.000, Math.toRadians(90));
    private final Pose beforeScorePose = new Pose(96, 103);
    private final Pose scorePose = new Pose(87, 85, Math.toRadians(320));
    private final Pose beforeSpike2 = new Pose(100, 60);
    private final Pose spike2End = new Pose(122, 60);
    private final Pose gate_intake = new Pose(129, 62, Math.toRadians(35));
    private final Pose gate_intake_down = new Pose(131, 60, Math.toRadians(45));
    private final Pose gate_intake_back_Pose = new Pose(92, 80);
    private final Pose spike1Pose = new Pose(115, 84, Math.toRadians(0));




    private final Pose control_point1 = new Pose(90.000, 60.000);
    private final Pose control_point2 = new Pose(103.000, 68.000);
    private final Pose control_point3 = new Pose(96, 83);
    private final Pose control_point4 = new Pose(92, 80);
    private final Pose control_point5 = new Pose(92, 80);

    private PathChain preload,spike2, gateintake1, gateintake1_5, gateintake_back, gate_intake_back_secondary,spike1;

//    private PathChain open_gate, spike2Return1;
//    private final Pose openGatePosePrep = new Pose(121, 63, Math.toRadians(0));
//    private final Pose openGatePose = new Pose(128, 63, Math.toRadians(0));
//    private final Pose openGateBackPose = new Pose(121, 65, Math.toRadians(0));


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
                .setBrakingStrength(1.6)
                .setGlobalLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();


        spike2 = bot.getFollower().pathBuilder()
                .addPath(
                new BezierCurve(
                        scorePose,
                        control_point1, // Control Point1
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
                        new BezierCurve(
                                spike2End,
                                control_point2, /// control point 2
                                gate_intake_back_Pose /// gate intake back pose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                gate_intake_back_Pose,
                                scorePose
                        )
                )
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .setGlobalDeceleration(1)
                .build();


        gateintake1 = bot.getFollower().pathBuilder().addPath(
                        new BezierCurve(
                                scorePose,
                                control_point2, /// Control Point 2
                                gate_intake
                        )
                )
                .setLinearHeadingInterpolation(scorePose.getHeading(), gate_intake.getHeading())
                .setBrakingStrength(1.6)
                .build();


        gateintake1_5 = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                gate_intake,
                                gate_intake_down
                        )
                )
                .setConstantHeadingInterpolation(gate_intake.getHeading())
                .build();


        gateintake_back = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                gate_intake_down,
                                control_point2,///  control point 2
                                gate_intake_back_Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                gate_intake_back_Pose,
                                scorePose
                        )
                )
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .setGlobalDeceleration(1.6)
                .build();


        gate_intake_back_secondary = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                gate_intake,
                                control_point2, /// control point 2
                                gate_intake_back_Pose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                gate_intake_back_Pose,
                                scorePose
                        )
                )
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .setGlobalDeceleration(1.6)
                .build();


        spike1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose,
                                control_point3, /// control 3
                                spike1Pose

                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                        spike1Pose,
                        scorePose
                        )
                )
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
    }



    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startPose, 24);
        buildPaths();

        schedule(
                new RunCommand(bot:: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload, 0.8)
                                .alongWith(
                                        new Prep_Shooter_any(bot, (1300), 0.25)
                                                .raceWith(
                                                        new WaitCommand(4000)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike2, true, 0.85)
                                                .setCompletionThreshold(0.98)
                                                .alongWith(
                                                        new Intake_on_long(bot)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),gateintake1, true, 0.7)
                                                .alongWith(
                                                        new Intake_on_forever(bot)
                                                )
                                                .raceWith(
                                                        new WaitCommand(4000)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gateintake1_5, true, 0.7)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gateintake_back, 0.8)
                                                .setCompletionThreshold(0.95)


                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                        /// ///////
//                                .andThen(
//                                        new FollowPath(bot.getFollower(),gateintake1, true,0.7)
//                                                .alongWith(
//                                                        new Intake_on_forever(bot)
//                                                )
////                                                .raceWith(
////                                                        new WaitCommand(4000)
////                                                )
//                                )
//                                .andThen(
//                                        new WaitCommand(1000)
//                                )
//                                .andThen(
//                                        new Intake_off(bot)
//                                )
//                                .andThen(
//                                        new FollowPath(bot.getFollower(), gate_intake_back_secondary)
//                                                .setCompletionThreshold(0.98)
//
//                                )
//                                .andThen(
//                                        new WaitCommand(200)
//                                )
//                                .andThen(
//                                        new Intake_on(bot)
//                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike1, 0.8)
                                                .setCompletionThreshold(0.95)
                                                .alongWith(
                                                        new Intake_on_long(bot)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )








                ));
    }
}

