package org.firstinspires.ftc.teamcode.auto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter2;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter3;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter4;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_Zero;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_yes;
import org.firstinspires.ftc.teamcode.core.commands.turret_LL;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Disconnect", group = "red")
public class RedClose15DISCONNECTFINAL extends OpModeCommand {


    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));

    /// Main ScorePose
    public final Pose all_scores = new Pose (89.62580645161289,76.89032258064516, Math.toRadians(0));

    /// Intake Spike Mark Two,

    public final Pose gather2 = new Pose (124.86451612903225, 53.76129032258066, Math.toRadians(0));
    public final Pose opengate = new Pose (137.25806451612902,70, Math.toRadians(0));


    ///  put intake here

    public final Pose gateintake1 = new Pose(125, 61, Math.toRadians(25));
    public final Pose gateintakeFINAL = new Pose(135, 61, Math.toRadians(25));

    ///come last
    public final Pose gather1 = new Pose (128,79, Math.toRadians(0));
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));
    public final Pose curve2spikemarkcontrolpoint = new Pose(89.23475352731101, 50.477832094139686);

    public final Pose kaimencontrolpoint =  new Pose(115.07917383820997,63.4860029981678);



    private Path scorepres, intake1, score1, kaimen, score2;
    private Path curve2spikemark;


//
    private Path get_to_gate_intake_FIRST, gate_intake, gate_intake_back, get_to_gate_intake;


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

        kaimen = new Path(new BezierCurve(
                gather2,
                kaimencontrolpoint,
                opengate));
        kaimen.setLinearHeadingInterpolation(gather2.getHeading(),opengate.getHeading());


        ///  Score Spike Mark Two
        score2 = new Path (new BezierLine(opengate, all_scores));
        score2.setLinearHeadingInterpolation(opengate.getHeading(), all_scores.getHeading());

        /// Travel to Gate Intake

        get_to_gate_intake_FIRST = new Path(
                new BezierLine(
                all_scores,
                gateintake1
        )
        );
        get_to_gate_intake_FIRST.setLinearHeadingInterpolation(all_scores.getHeading(), gateintake1.getHeading());

        ///  Gate Intake
        gate_intake = new Path(new BezierLine(
                gateintake1,
                gateintakeFINAL
        ));
        gate_intake.setConstantHeadingInterpolation(gateintakeFINAL.getHeading());

        ///  Go back to score Position
        gate_intake_back = new Path(new BezierLine(
                gateintakeFINAL,
                all_scores
        ));
        gate_intake_back.setConstantHeadingInterpolation(Math.toRadians(25));

        /// Go back to gate intake
        get_to_gate_intake = new Path(
                new BezierLine(
                        all_scores,
                        gateintake1
                )
        );
        get_to_gate_intake.setConstantHeadingInterpolation(gateintake1.getHeading());

//  Spike Mark 1 Intake, Will be intaked Last
        intake1 = new Path (new BezierLine(all_scores, gather1));
        intake1.setLinearHeadingInterpolation(all_scores.getHeading(),gather1.getHeading());

        score1 = new Path(new BezierLine(gather1, last_shot));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), last_shot.getHeading());

        }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startpose, 24);
        buildPaths();

        schedule(
        new RunCommand(bot:: autonomous_robot_update),
                new SequentialCommandGroup(
//              CYCLE 1, Preloads
                        new FollowPath(bot.getFollower(), scorepres)
                                .alongWith(
                                        new Prep_Shooter4(bot)
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
//              CYCLE 2, Second Spike Mark
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve2spikemark)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
//              CYCLE 3, Gate Opening
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimen,.5)
                                                .raceWith(new WaitCommand(2500))
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), score2)
                                                .alongWith(new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
//              CYCLE 4, Gate Opening FIRST TIME
                                .andThen(
                                        new FollowPath(bot.getFollower(), get_to_gate_intake_FIRST)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake)
                                                .raceWith(new WaitCommand(2500))
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
//              CYCLE 5, Gate Opening Second Time
                                .andThen(
                                        new FollowPath(bot.getFollower(), get_to_gate_intake)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake)
                                                .raceWith(new WaitCommand(2500))
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
//              CYCLE 6, Intaking Spike Mark One
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1)
                                                .alongWith(new Intake_on_long1(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter2(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(250)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

                ));
    }
}

