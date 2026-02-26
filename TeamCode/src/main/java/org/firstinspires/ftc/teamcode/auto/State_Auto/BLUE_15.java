package org.firstinspires.ftc.teamcode.auto.State_Auto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.unused.Intake_on_3rd_set;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Blue 15 solo", group = "Solo")
public class BLUE_15 extends OpModeCommand {


    Robot bot;
    private final Pose startPose = new Pose(111.000, 134.000, Math.toRadians(90));
    private final Pose beforeScorePose = new Pose(96, 103);
    private final Pose scorePose = new Pose(87, 85, Math.toRadians(320));
    private final Pose beforeSpike2 = new Pose(100, 60);
    private final Pose spike2End = new Pose(124, 60);
    private final Pose gate_intake = new Pose(130, 62, Math.toRadians(35));
    private final Pose gate_intake_down = new Pose(131, 60, Math.toRadians(50));
    private final Pose gate_intake_back_Pose = new Pose(92, 80);
    private final Pose spike1Pose = new Pose(115, 84, Math.toRadians(0));
    private final Pose beforeSpike3 = new Pose(95, 36);
    private final Pose spike3EndPose = new Pose(123, 36, Math.toRadians(0));
    private final Pose parkPose = new Pose(92, 111, Math.toRadians(-63));


    ///  Control Points

    private final Pose control_point1 = new Pose(90.000, 60.000);
    private final Pose control_point2 = new Pose(103.000, 68.000);
    private final Pose control_point3 = new Pose(96, 83);
    private final Pose control_point4 = new Pose(89, 33);


    private final Pose startPose_blue = startPose.mirror();
    private final Pose beforeScorePose_blue = beforeScorePose.mirror();
    private final Pose scorePose_blue = scorePose.mirror();
    private final Pose beforeSpike2_blue = beforeSpike2.mirror();
    private final Pose spike2End_blue = spike2End.mirror();
    private final Pose gate_intake_blue = gate_intake.mirror();
    private final Pose gate_intake_down_blue = gate_intake_down.mirror();
    private final Pose gate_intake_back_Pose_blue = gate_intake_back_Pose.mirror();
    private final Pose spike1Pose_blue= spike1Pose.mirror();
    private final Pose beforeSpike3_blue = beforeSpike3.mirror();
    private final Pose spike3EndPose_blue = spike3EndPose.mirror();
    private final Pose parkPose_blue = parkPose.mirror();


    ///  Control Points

    private final Pose control_point1_blue = control_point1.mirror();
    private final Pose control_point2_blue = control_point2.mirror();
    private final Pose control_point3_blue = control_point3.mirror();
    private final Pose control_point4_blue = control_point4.mirror();




    private PathChain preload, spike2, gateintake1, gateintake1_5, gateintake_back, spike1, spike3;
    private PathChain gate_intake_back_secondary;

    public void buildPaths() {

        preload = bot.getFollower().pathBuilder() ///  this can be optimized if i need
                .addPath(
                        new BezierLine(
                                startPose_blue,
                                beforeScorePose_blue
                        )
                )
                .addPath(
                        new BezierLine(
                                beforeScorePose_blue,
                                scorePose_blue
                        )
                )
                .setBrakingStrength(1.6)
                .setGlobalLinearHeadingInterpolation(startPose_blue.getHeading(), scorePose_blue.getHeading())
                .build();


        spike2 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point1_blue, // Control Point1
                                beforeSpike2_blue
                        )
                )
                .setLinearHeadingInterpolation(scorePose_blue.getHeading(), beforeSpike2_blue.getHeading())
                .addPath(
                        new BezierLine(
                                beforeSpike2_blue,
                                spike2End_blue
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierCurve(
                                spike2End_blue,
                                control_point2_blue, /// control point 2
                                gate_intake_back_Pose_blue/// gate intake back pose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                gate_intake_back_Pose_blue,
                                scorePose_blue
                        )
                )
                .setConstantHeadingInterpolation(scorePose_blue.getHeading())
                .setGlobalDeceleration(1)
                .build();


        gateintake1 = bot.getFollower().pathBuilder().addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point2_blue, /// Control Point 2
                                gate_intake_blue
                        )
                )
                .setLinearHeadingInterpolation(scorePose_blue.getHeading(), gate_intake_blue.getHeading())
                .setBrakingStrength(1.6)
                .build();

        gateintake1_5 = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                gate_intake_blue,
                                gate_intake_down_blue
                        )
                )
                .setConstantHeadingInterpolation(gate_intake_blue.getHeading())
                .build();


        gateintake_back = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                gate_intake_down_blue,
                                control_point2_blue,///  control point 2
                                gate_intake_back_Pose_blue
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                gate_intake_back_Pose_blue,
                                scorePose_blue
                        )
                )
                .setConstantHeadingInterpolation(scorePose_blue.getHeading())
                .setGlobalDeceleration(1.6)
                .build();


        gate_intake_back_secondary = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                gate_intake_blue,
                                control_point2_blue, /// control point 2
                                gate_intake_back_Pose_blue
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .addPath(
                        new BezierLine(
                                gate_intake_back_Pose_blue,
                                scorePose_blue
                        )
                )
                .setConstantHeadingInterpolation(scorePose_blue.getHeading())
                .setGlobalDeceleration(1.6)
                .build();

        spike1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point3_blue, /// control 3
                                spike1Pose_blue

                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                spike1Pose_blue,
                                scorePose_blue
                        )
                )
                .setConstantHeadingInterpolation(scorePose_blue.getHeading())
                .build();
        spike3 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point4_blue, /// control point 4
                                beforeSpike3_blue
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                beforeSpike3_blue,
                                spike3EndPose_blue
                        )
                )
                .setConstantHeadingInterpolation(spike3EndPose_blue.getHeading())
                .addPath(
                        new BezierLine(
                                spike3EndPose_blue,
                                parkPose_blue
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .setGlobalDeceleration()
                .build();
    }


    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startPose_blue, 24);
        buildPaths();


        schedule(
                new RunCommand(bot::any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload, 0.8)
                                .alongWith(
                                        new Prep_Shooter_any(bot, (1250), 0.27)
                                                .raceWith(
                                                        new WaitCommand(4000)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(400)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Intake_on_forever(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike2, 0.85)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gateintake1, true, 0.7)
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
                                )
                                .andThen(
                                        new WaitCommand(400)
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
                                                .alongWith(
                                                        new Intake_on_3rd_set(bot)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike3, .8)
                                                .alongWith(
                                                        new Intake_on_long1(bot)
                                                )
                                )
                                .andThen(
                                        new Prep_Shooter_any(bot, (1180), 0.18)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )

                )
        );
    }
}
