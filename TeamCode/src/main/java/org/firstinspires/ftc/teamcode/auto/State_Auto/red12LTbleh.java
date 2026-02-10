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
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter2;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter4;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_Zero;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_yes;
import org.firstinspires.ftc.teamcode.core.commands.turret_LL_blue;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "RED 12 Consistent", group = "Red")
public class red12LTbleh extends OpModeCommand {


    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));

    /// Main ScorePose
    public final Pose all_scores = new Pose (91,82, Math.toRadians(0));


    /// Intake Spike Mark Two,

    public final Pose gather2 = new Pose (128.86451612903225, 51.76129032258066, Math.toRadians(0));


    /// Opengate
    public final Pose kaimencontrolpoint =  new Pose(113.24977274928074,61.395258896534415);


    public final Pose opengate = new Pose (135.28,64.5, Math.toRadians(0));
    public final Pose opengateREAL = new Pose (131.28,64.5, Math.toRadians(0));
    public final Pose kaimen_back_control_point =  new Pose(103,59);
    public final Pose opengateSLOW = new Pose (135.28,64.5, Math.toRadians(270));



    /// then we need to score that
    ///come second
    public final Pose get2balls1 = new Pose (96,79, Math.toRadians(0));

    public final Pose gather1 = new Pose (127.5,79, Math.toRadians(0));
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));

    /// Gather 3
    public final Pose get2balls2 = new Pose (102.74193548387095,33.84516129032261, Math.toRadians(0));
    public final Pose gather3 = new Pose (130.85161290322581,33.84516129032261, Math.toRadians(0));
    public final Pose shoot3 = new Pose (89.62580645161289,76.89032258064516, Math.toRadians(0));
    public final Pose curve2spikemarkcontrolpoint = new Pose(89.23475352731101, 50.477832094139686);


    private Path scorepres, intake1, score1, score2, posit1, posit2, intake3;
    private PathChain kaimen;
    private Path curve2spikemark;
    private PathChain kaimenslow;


    //
    private Path get_to_gate_intake_FIRST, gate_intake, gate_intake_back, get_to_gate_intake, gate_intake_backwards;
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
        kaimenslow = bot.getFollower().pathBuilder().addPath(
                new BezierLine(
                        gather2,
                        opengateSLOW

                )
        )
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();


        ///  Score Spike Mark Two
        score2 = new Path (new BezierCurve(opengateREAL,
                kaimen_back_control_point,
                all_scores));
        score2.setLinearHeadingInterpolation(opengateREAL.getHeading(), all_scores.getHeading());


//  Spike Mark 1 Intake
        posit1 = new Path (new BezierLine(all_scores, get2balls1));
        posit1.setLinearHeadingInterpolation(all_scores.getHeading(), get2balls1.getHeading());

        intake1 = new Path (new BezierLine(get2balls1, gather1));
        intake1.setLinearHeadingInterpolation(get2balls1.getHeading(),gather1.getHeading());

        score1 = new Path(new BezierLine(gather1, last_shot));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), last_shot.getHeading());

// Spike Mark 3 Intake
        posit2 = new Path (new BezierLine(last_shot, get2balls2))    ;
        posit2.setLinearHeadingInterpolation(last_shot.getHeading(),get2balls2.getHeading());

        intake3 = new Path (new BezierLine(get2balls2,gather3));
        intake3.setLinearHeadingInterpolation(get2balls2.getHeading(), gather3.getHeading());

        score2 = new Path(new BezierLine(gather3, all_scores));
        score2.setLinearHeadingInterpolation(gather3.getHeading(), all_scores.getHeading());


        park = new Path(
                new BezierLine(
                        last_shot,
                        new Pose(35 , 129)
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
                        new FollowPath(bot.getFollower(), scorepres, 0.8)
                                .alongWith(
                                        new Prep_Shooter4(bot)
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                                .alongWith(
                                                        new turret_Zero(bot)
                                                )
                                )

//              CYCLE 2, Second Spike Mark
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve2spikemark,.8)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                //              CYCLE 3, Gate Opening
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimenslow,true,0.65)
                                                .raceWith(new WaitCommand(5000))
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )

                                .andThen(
                                        new WaitCommand(800)
                                )

                                .andThen (
                                        new FollowPath(bot.getFollower(), score2, 0.65)
                                                .alongWith(new Prep_Shooter4(bot))
                                                .alongWith(
                                                        new turret_yes(bot)
                                                )
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
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

//              CYCLE 4, Intake 1st Set

                                .andThen(new FollowPath(bot.getFollower(),posit1))
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1, 0.7)
                                                .alongWith(new Intake_on_long1(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter2(bot))
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(750)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
//              CYCLE 5, Intake 3rd Set
                                .andThen(new FollowPath(bot.getFollower(), posit2)
                                        .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(new FollowPath(bot.getFollower(), intake3,.7))
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(new FollowPath(bot.getFollower(), score2)
                                                .alongWith(
                                                        new Prep_Shooter4(bot)
                                                )
                                                .andThen(
                                                        new WaitCommand(750)
                                                )
                                                .andThen(
                                                        new Intake_on(bot)
                                                )
//              CYCLE 6, Leave



                                )
                ));
    }
}

