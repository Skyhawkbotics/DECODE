package org.firstinspires.ftc.teamcode.Test_Files;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter2;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter4;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.turret.turret_LL_blue;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "BLUE 12 USE THIS", group = "Blue")
@Disabled
public class test extends OpModeCommand {


    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));
    public static final Pose bluestartpose = startpose.mirror();

    /// Main ScorePose
    public final Pose all_scores = new Pose (91,82, Math.toRadians(0));

    public final Pose blueall_scores = all_scores.mirror();


    /// Intake Spike Mark Two,

    public final Pose gather2 = new Pose (128.86451612903225, 51.76129032258066, Math.toRadians(0));

    public final Pose bluegather2 = gather2.mirror();

    /// Opengate
    public final Pose kaimencontrolpoint =  new Pose(113.24977274928074,61.395258896534415);
    public final Pose bluekaimencontrolpoint =  kaimencontrolpoint.mirror();

    public final Pose opengate = new Pose (135.28,66, Math.toRadians(0));
    public final Pose blueopengate = opengate.mirror();
    public final Pose opengateREAL = new Pose (131.28,66, Math.toRadians(0));
    public final Pose blueopengateREAL = opengateREAL.mirror();
    public final Pose kaimen_back_control_point =  new Pose(103,59);
    public final Pose bluekaimen_back_control_point =  kaimen_back_control_point.mirror();
    public final Pose kaimen_back_control_point1 =  new Pose(102,64);
    public final Pose bluekaimen_back_control_point1 =  kaimen_back_control_point1.mirror();

/// then we need to score that
    ///come second
    public final Pose get2balls1 = new Pose (96,79, Math.toRadians(0));
    public final Pose blueget2balls1 = get2balls1.mirror();

    public final Pose gather1 = new Pose (127.5,79, Math.toRadians(0));
    public final Pose bluegather1 = gather1.mirror();
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));
    public final Pose bluelast_shot = last_shot.mirror();

    /// Gather 3
    public final Pose get2balls2 = new Pose (102.74193548387095,39.84516129032261, Math.toRadians(0));
    public final Pose blueget2balls2 = get2balls2.mirror();
    public final Pose gather3 = new Pose (130.85161290322581,39.84516129032261, Math.toRadians(0));
    public final Pose bluegather3 = gather3.mirror();
    public final Pose shoot3 = new Pose (89.62580645161289,76.89032258064516, Math.toRadians(0));
    public final Pose blueshoot3 = shoot3.mirror();
    public final Pose curve2spikemarkcontrolpoint = new Pose(89.23475352731101, 50.477832094139686);

    public final Pose bluecurve2spikemarkcontrolpoint = curve2spikemarkcontrolpoint.mirror();


    private Path scorepres, intake1, score1, score2, posit1, posit2, intake3;
    private PathChain kaimen;
    private Path curve2spikemark;


    //
    private Path get_to_gate_intake_FIRST, gate_intake, gate_intake_back, get_to_gate_intake, gate_intake_backwards;
    private PathChain openGate;
    private Path park;

    public void buildPaths() {
        /// FIRST Preload Firing

        scorepres = new Path(new BezierLine(bluestartpose, blueall_scores));
        scorepres.setLinearHeadingInterpolation(bluestartpose.getHeading(), blueall_scores.getHeading());

        ///  Intakes the 2nd Spike Mark

        curve2spikemark = new Path(
                new BezierCurve(
                        blueall_scores,
                        bluecurve2spikemarkcontrolpoint,
                        bluegather2
                )
        );
        curve2spikemark.setConstantHeadingInterpolation(bluegather2.getHeading());

        ///  Open Gate

        kaimen = bot.getFollower().pathBuilder().addPath(
                        new BezierCurve(
                                bluegather2,
                                bluekaimencontrolpoint,
                                blueopengate)
                )
                .setLinearHeadingInterpolation(bluegather2.getHeading(),blueopengate.getHeading())
                .build();


        ///  Score Spike Mark Two
        score2 = new Path (new BezierCurve(blueopengateREAL,
                bluekaimen_back_control_point,
                blueall_scores));
        score2.setLinearHeadingInterpolation(blueopengateREAL.getHeading(), blueall_scores.getHeading());


//  Spike Mark 1 Intake
        posit1 = new Path (new BezierLine(blueall_scores, blueget2balls1));
        posit1.setLinearHeadingInterpolation(blueall_scores.getHeading(), blueget2balls1.getHeading());

        intake1 = new Path (new BezierLine(blueget2balls1, bluegather1));
        intake1.setLinearHeadingInterpolation(blueget2balls1.getHeading(),bluegather1.getHeading());

        score1 = new Path(new BezierLine(bluegather1, bluelast_shot));
        score1.setLinearHeadingInterpolation(bluegather1.getHeading(), bluelast_shot.getHeading());

// Spike Mark 3 Intake
        posit2 = new Path (new BezierLine(bluelast_shot, blueget2balls2))    ;
        posit2.setLinearHeadingInterpolation(bluelast_shot.getHeading(),blueget2balls2.getHeading());

        intake3 = new Path (new BezierLine(blueget2balls2, bluegather3));
        intake3.setLinearHeadingInterpolation(blueget2balls2.getHeading(), bluegather3.getHeading());

        score2 = new Path(new BezierLine(bluegather3, blueall_scores));
        score2.setLinearHeadingInterpolation(bluegather3.getHeading(), blueall_scores.getHeading());


        park = new Path(
                new BezierLine(
                        bluelast_shot,
                        new Pose(35 , 129)
                )
        );
        park.setConstantHeadingInterpolation(Math.toRadians(90));


    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,bluestartpose, 20);
        buildPaths();

        schedule(
                new RunCommand(bot:: blue_autonomous_robot_update),
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
                                        new FollowPath(bot.getFollower(), kaimen,true,0.65)
                                                .raceWith(new WaitCommand(3300))
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

