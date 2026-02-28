package org.firstinspires.ftc.teamcode.auto.State_Auto;


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
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_15;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.turret.Turret15_ZERO;
import org.firstinspires.ftc.teamcode.core.commands.turret.Turret15_spike3;
import org.firstinspires.ftc.teamcode.core.commands.unused.Intake_on_3rd_set;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Red 15 solo", group = "Solo")
public class RED_15 extends OpModeCommand {


    Robot bot;
    private final Pose startPose = new Pose(111.000, 134.000, Math.toRadians(90));
    private final Pose scorePose = new Pose(87, 85, Math.toRadians(330));
    private final Pose beforeSpike2 = new Pose(100, 60);
    private final Pose spike2End = new Pose(124, 60, Math.toRadians(0));
    private final Pose gate_intake = new Pose(130, 62, Math.toRadians(35));
    private final Pose gate_intake_down = new Pose(131, 60, Math.toRadians(50));
    private final Pose gate_intake_back_Pose = new Pose(92, 80);
    private final Pose spike1Pose = new Pose(115, 84, Math.toRadians(0));
    private final Pose beforeSpike3 = new Pose(103, 37);
    private final Pose spike3EndPose = new Pose(124, 37, Math.toRadians(0));
    private final Pose parkPose = new Pose(92, 111, Math.toRadians(-63));


    ///  Control Points

    private final Pose control_point1 = new Pose(87, 60.000);
    private final Pose control_point2 = new Pose(103.000, 68.000);
    private final Pose control_point3 = new Pose(96, 83);
    private final Pose control_point4 = new Pose(82, 34);


    private final Pose startPose_blue = startPose.mirror();
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




    private PathChain spike2, gateintake1, gateintake1_5, gateintake_back, spike1, spike3;
    private PathChain gate_intake_back_secondary;
    private Path preload,spike2back;

    public void buildPaths() {
        preload = new Path(
                new BezierLine(
                        startPose,
                        scorePose
                )
        );
        preload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        spike2 = bot.getFollower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose,
                                control_point1, // Control Point1
                                beforeSpike2
                        )
                )
                .setLinearHeadingInterpolation(scorePose.getHeading(), beforeSpike2.getHeading())
                .addPath(
                        new BezierLine(
                                beforeSpike2,
                                spike2End
                        )
                )
                .setConstantHeadingInterpolation(spike2End.getHeading())
                .setGlobalDeceleration()

                .build();
        spike2back = new Path(
                        new BezierCurve(
                                spike2End,
                                control_point2, /// control point 2
                                scorePose /// gate intake back pose
                        )
                );
        spike2back.setConstantHeadingInterpolation(scorePose.getHeading());



        gateintake1 = bot.getFollower().pathBuilder().addPath(
                        new BezierCurve(
                                scorePose,
                                control_point2, /// Control Point 2
                                gate_intake
                        )
                )
                .setLinearHeadingInterpolation(scorePose.getHeading(), gate_intake.getHeading())
                .setGlobalDeceleration()

                .build();

        gateintake1_5 = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                gate_intake,
                                gate_intake_down
                        )
                )
                .setConstantHeadingInterpolation(gate_intake.getHeading())
                .setGlobalDeceleration()
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
                .setGlobalDeceleration()
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
                .setGlobalDeceleration()

                .build();

        spike1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose,
                                control_point3, /// control 3
                                spike1Pose

                        )
                )
                .setConstantHeadingInterpolation(spike1Pose.getHeading())
                .addPath(
                        new BezierLine(
                                spike1Pose,
                                scorePose
                        )
                )
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        spike3 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose,
                                control_point4, /// control point 4
                                beforeSpike3
                        )
                )
                .setConstantHeadingInterpolation(beforeSpike3.getHeading())
                .addPath(
                        new BezierLine(
                                beforeSpike3,
                                spike3EndPose
                        )
                )
                .setConstantHeadingInterpolation(spike3EndPose.getHeading())
                .addPath(
                        new BezierLine(
                                spike3EndPose,
                                parkPose
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .setGlobalDeceleration()
                .build();
    }


    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startPose, 24);
        buildPaths();

        schedule(
                new RunCommand(bot::any_autonomous_robot_update),

                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload, 0.8)
                                .alongWith(
                                        new Prep_Shooter_any(bot, (1250), 0.27)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike2, 1)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike2back, 0.85)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gateintake1, true, 0.7)
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
                                        new Intake_on_15(bot)
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
//                                        new Intake_on_15(bot)
//                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike1, 0.8)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new Turret15_spike3(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), spike3, .8)

                                )
                                .andThen(
                                        new Prep_Shooter_any(bot, (1180), 0.18)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new Turret15_ZERO(bot)
                                )

                )
        );
    }
}
