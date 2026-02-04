package org.firstinspires.ftc.teamcode.auto.LT;


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
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter2;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter4;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_Zero;
import org.firstinspires.ftc.teamcode.core.commands.turret_LL;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Red", group = "red")
public class RedClose15FINAL extends OpModeCommand {


    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));

    /// Main ScorePose
    public final Pose all_scores = new Pose (91,82, Math.toRadians(0));

    /// Intake Spike Mark Two,

    public final Pose gather2 = new Pose (126.86451612903225, 51.76129032258066, Math.toRadians(0));
    public final Pose kaimencontrolpoint =  new Pose(113.24977274928074,61.395258896534415);

    public final Pose opengate = new Pose (137.28165798255372,69, Math.toRadians(0));
    public final Pose opengateREAL = new Pose (131.28165798255372,70, Math.toRadians(0));

    public final Pose kaimen_back_control_point =  new Pose(103,59);
    public final Pose kaimen_back_control_point1 =  new Pose(102,64);


    ///  put intake here

    public final Pose gateintake1 = new Pose(125, 59, Math.toRadians(30));
    public final Pose gateintakeFINAL = new Pose(137, 58.5, Math.toRadians(30));

    public final Pose gateintakeFINAL1 = new Pose(137, 57, Math.toRadians(30));


    ///come last
    public final Pose gather1 = new Pose (128,79, Math.toRadians(0));
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));
    public final Pose curve2spikemarkcontrolpoint = new Pose(89.23475352731101, 50.477832094139686);


    public final Pose gather3 = new Pose (130.85161290322581,29.84516129032261, Math.toRadians(0));


    private Path scorepres, intake1, score1, score2;
    private PathChain kaimen;
    private Path curve2spikemark;


//
    private Path get_to_gate_intake_FIRST, gate_intake, gate_intake_back, get_to_gate_intake, gate_intake_backwards, curve3spikemark, final_score;
    private PathChain openGate;
    private Path park;

    public void buildPaths() {
        /// FIRST Preload Firing

        scorepres = new Path(new BezierLine(startpose, all_scores));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), all_scores.getHeading());

        ///  Intakes the 2nd Spike Mark

        curve2spikemark = new Path(
                new BezierCurve(
                        all_scores,
                        curve2spikemarkcontrolpoint,
                        gather2
                )
        );
        curve2spikemark.setConstantHeadingInterpolation(gather2.getHeading());

        ///  Open Gate

        kaimen = bot.getFollower().pathBuilder().addPath(
                new BezierCurve(
                gather2,
                kaimencontrolpoint,
                opengate)
                )
                .setLinearHeadingInterpolation(gather2.getHeading(),opengate.getHeading())
                .build();


        ///  Score Spike Mark Two
        score2 = new Path (new BezierCurve(opengateREAL,
                kaimen_back_control_point,
                all_scores));
        score2.setLinearHeadingInterpolation(opengateREAL.getHeading(), all_scores.getHeading());

        /// Travel to Gate Intake

        get_to_gate_intake_FIRST = new Path(
                new BezierCurve(
                all_scores,
                kaimen_back_control_point1,
                gateintake1
        )
        );
        get_to_gate_intake_FIRST.setLinearHeadingInterpolation(all_scores.getHeading(), gateintake1.getHeading());

        ///  Gate Intake

        openGate = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                gateintake1,
                                gateintakeFINAL
                                )
                )
                .setLinearHeadingInterpolation(gateintake1.getHeading(),gateintakeFINAL.getHeading())
                .addPath(
                        new BezierLine(
                                gateintakeFINAL,
                                gateintakeFINAL1
                        )
                )
                .setLinearHeadingInterpolation(gateintakeFINAL.getHeading(), gateintakeFINAL1.getHeading())
                .build();

        gate_intake = new Path(new BezierLine(
                gateintake1,
                gateintakeFINAL
        ));
        gate_intake.setConstantHeadingInterpolation(gateintakeFINAL.getHeading());

        gate_intake_backwards = new Path(new BezierLine(
                gateintakeFINAL,
                gateintakeFINAL1
        ));
        gate_intake_backwards.setConstantHeadingInterpolation(gateintakeFINAL.getHeading());

        ///  Go back to score Position
        gate_intake_back = new Path(new BezierCurve(
                gateintakeFINAL1,
                kaimen_back_control_point1,
                all_scores
        ));
        gate_intake_back.setLinearHeadingInterpolation(gateintakeFINAL.getHeading(),all_scores.getHeading());
        gate_intake_back.setBrakingStart(2.5);

        /// Go back to gate intake
        get_to_gate_intake = new Path(
                new BezierCurve(
                        all_scores,
                        kaimen_back_control_point1,
                        gateintake1
                )
        );
        get_to_gate_intake.setLinearHeadingInterpolation(all_scores.getHeading(), gateintake1.getHeading());

//  Spike Mark 1 Intake, Will be intaked Last
        intake1 = new Path (new BezierLine(all_scores, gather1));
        intake1.setLinearHeadingInterpolation(all_scores.getHeading(),gather1.getHeading());

        score1 = new Path(new BezierLine(gather1, last_shot));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), last_shot.getHeading());
        curve3spikemark = new Path(
                new BezierCurve(
                        last_shot,
                        new Pose(95, 27),
                        gather3
                )
        );
        curve3spikemark.setConstantHeadingInterpolation(0);

        final_score = new Path(
                new BezierLine(
                       gather3,
                       all_scores
                )
        );
        final_score.setConstantHeadingInterpolation(0);
        park = new Path(
                new BezierLine(
                        all_scores,
                        new Pose(108, 120)
                )
        );
        park.setConstantHeadingInterpolation(Math.toRadians(90));


        }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startpose, 24);
        buildPaths();

        schedule(
        new RunCommand(bot:: autonomous_robot_update),
                new SequentialCommandGroup(
//              CYCLE 1, Preloads
                        new FollowPath(bot.getFollower(), scorepres,.8)
                                .alongWith(
                                        new Prep_Shooter4(bot)
                                )
                                .andThen(
                                        new turret_LL(bot)
                                                .raceWith(new WaitCommand(500))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

//              CYCLE 2, Second Spike Mark
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve2spikemark, 0.9)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
//              CYCLE 3, Gate Opening
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimen,true,0.7)
                                                .raceWith(new WaitCommand(3300))
                                )
                                .andThen(
                                        new WaitCommand(800)
                                )

                                .andThen (
                                        new FollowPath(bot.getFollower(), score2, 0.65)
                                                .alongWith(new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                                .raceWith(new WaitCommand(500))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

//              CYCLE 4, Gate Opening FIRST TIME
                                .andThen(
                                        new FollowPath(bot.getFollower(), get_to_gate_intake_FIRST, 0.8)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                               /* .andThen(
                                        new FollowPath(
                                                bot.getFollower(),
                                                openGate
                                        )
                                )

                                */

                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake, 0.8)
                                                .raceWith(new WaitCommand(800))
                                )
                                .andThen(
                                        new WaitCommand(100)
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake_backwards)
                                                .raceWith(new WaitCommand(600))
                                )


                                .andThen(
                                        new WaitCommand(2000)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )

                                .andThen (
                                        new FollowPath(bot.getFollower(), gate_intake_back, 0.8)
                                                .alongWith(
                                                        new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                                .raceWith(new WaitCommand(500))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
/*
//              CYCLE 5, Gate Opening Second Time
                                .andThen(
                                        new FollowPath(bot.getFollower(), get_to_gate_intake)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake)
                                                .raceWith(new WaitCommand(800))
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake_backwards)
                                                .raceWith(new WaitCommand(600))

                                )
                                .andThen(
                                        new WaitCommand(1500)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), gate_intake_back)
                                                .alongWith(
                                                        new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

 */

//              CYCLE 6, Intaking Spike Mark One
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1)
                                                .alongWith(new Intake_on_long1(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter2(bot))
                                )
                              //  .andThen(
                               //         new turret_LL(bot)
                             //   )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                //
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve3spikemark)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), final_score)
                                                .alongWith(
                                                        new Prep_Shooter4(bot)
                                                )
                                )
                                //.andThen(
                               //         new turret_LL(bot)
                               // )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), park)
                                )
                                .andThen(
                                        new turret_Zero(bot)
                                )



                ));
    }
}

