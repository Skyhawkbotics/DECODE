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
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_Off_Timed;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_On_Timed;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_On_Toggle;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_on_15;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Score_Command;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Blue 18", group = "Solo", preselectTeleOp = "18 CLOSE Blue Drive")
public class Blue_18 extends OpModeCommand {


    Robot bot;
    private final Pose startPose = new Pose(111.000, 134.000, Math.toRadians(90));
    private final Pose scorePose = new Pose(89, 88, Math.toRadians(330));
    private final Pose beforeSpike2 = new Pose(98, 61);
    private final Pose spike2End = new Pose(123, 61, Math.toRadians(0));
    private final Pose gate_intake = new Pose(131, 60, Math.toRadians(30));
    private final Pose gate_intake_down = new Pose(133, 57, Math.toRadians(40));
    private final Pose spike1Pose = new Pose(118, 84, Math.toRadians(0));
    private final Pose beforeSpike3 = new Pose(103, 37);
    private final Pose spike3EndPose = new Pose(124, 37, Math.toRadians(0));
    private final Pose parkPose = new Pose(92, 114, Math.toRadians(-67));


    ///  Control Points

    private final Pose control_point1 = new Pose(90, 62);
    private final Pose control_point2 = new Pose(101, 61);
    private final Pose control_point3 = new Pose(96, 83);
    private final Pose control_point4 = new Pose(82, 34);


    private final Pose startPose_blue = startPose.mirror();
    private final Pose scorePose_blue = scorePose.mirror();
    private final Pose beforeSpike2_blue = beforeSpike2.mirror();
    private final Pose spike2End_blue = spike2End.mirror();
    private final Pose gate_intake_blue = gate_intake.mirror();
    private final Pose gate_intake_down_blue = gate_intake_down.mirror();
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
                        startPose_blue,
                        scorePose_blue
                )
        );
        preload.setLinearHeadingInterpolation(startPose_blue.getHeading(), scorePose_blue.getHeading());

        spike2 = bot.getFollower()
                .pathBuilder()
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
                .setConstantHeadingInterpolation(spike2End_blue.getHeading())
                .addPath(
                        new BezierCurve(
                                spike2End_blue,
                                control_point2_blue, /// control point 2
                                scorePose_blue /// gate intake back pose
                        )
                ).setLinearHeadingInterpolation(spike2End_blue.getHeading(), scorePose_blue.getHeading())
                .setGlobalDeceleration()
                .build();



        gateintake1 = bot.getFollower().pathBuilder().addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point2_blue, /// Control Point 2
                                gate_intake_blue
                        )
                )
                .setLinearHeadingInterpolation(scorePose_blue.getHeading(), gate_intake_blue.getHeading())
                .setBrakingStrength(0.8)
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
                                scorePose_blue
                        )
                )
                .setLinearHeadingInterpolation(gate_intake_down_blue.getHeading(), scorePose_blue.getHeading())
                .build();


        gate_intake_back_secondary = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                gate_intake_blue,
                                control_point2_blue, /// control point 2
                                scorePose_blue
                        )
                )
                .setLinearHeadingInterpolation(gate_intake_blue.getHeading(), scorePose_blue.getHeading())
                .build();

        spike1 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point3_blue, /// control 3
                                spike1Pose_blue

                        )
                )
                .setConstantHeadingInterpolation(spike1Pose_blue.getHeading())
                .addPath(
                        new BezierLine(
                                spike1Pose_blue,
                                scorePose_blue
                        )
                )
                .setConstantHeadingInterpolation(scorePose_blue.getHeading())
                .setBrakingStrength(0.8)
                .build();
        spike3 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                scorePose_blue,
                                control_point4_blue, /// control point 4
                                beforeSpike3_blue
                        )
                )
                .setConstantHeadingInterpolation(beforeSpike3_blue.getHeading())
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

    @Override
    public void init_loop() {
        bot.getTurret().set_using_LL(20);
        bot.getTurret().turret_update(20, bot.getFollower().getPose(), 0);
    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startPose_blue, 20);
        buildPaths();

        schedule(
                new RunCommand(bot::any_autonomous_robot_update),

                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload, 0.9)
                                .alongWith(
                                        new Score_Command(bot, 1250, 0.27))
                                .andThen(
                                        new WaitCommand(300),
                                        new Intake_on_15(bot),



                                        new FollowPath(bot.getFollower(), spike2)
                                                .alongWith(
                                                        new Intake_On_Toggle(bot)
                                                        ),

                                        new WaitCommand(200),
                                        new Intake_on_15(bot),


                                        new FollowPath(bot.getFollower(), gateintake1, true, 0.85)
                                                .alongWith(
                                                        new Intake_On_Toggle(bot)
                                                ),

                                        new WaitCommand(900),

                                        new FollowPath(bot.getFollower(), gateintake1_5, true),

                                        new WaitCommand(500),

                                        new FollowPath(bot.getFollower(), gateintake_back, 0.85)
                                                .alongWith(
                                                        new Intake_Off_Timed(bot, 0.2)
                                                ),

                                        new WaitCommand(400),
                                        new Intake_on_15(bot),

                                        new FollowPath(bot.getFollower(), gateintake1, true, 0.85)
                                                .alongWith(
                                                        new Intake_On_Toggle(bot)
                                                ),
                                        new WaitCommand(1700),
                                        new FollowPath(bot.getFollower(), gate_intake_back_secondary)
                                                .alongWith(
                                                        new Intake_Off_Timed(bot, 0.2)
                                                ),

                                        new WaitCommand(200),
                                        new Intake_on_15(bot),


                                        new FollowPath(bot.getFollower(), spike1, 0.95)
                                                .alongWith(
                                                        new Intake_On_Timed(bot, 2)
                                                ),

                                        new WaitCommand(200),
                                        new Intake_on_15(bot),

                                        new FollowPath(bot.getFollower(), spike3, 1)
                                                .alongWith(
                                                        new Intake_On_Toggle(bot),
                                                        new Score_Command(bot, 1140, 0.18)
                                                ),


                                        new WaitCommand(100),
                                        new Intake_off(bot),
                                        new Intake_on_15(bot)
                                )
                )
        );
    }
}
