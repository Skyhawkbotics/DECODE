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

@Autonomous(name = "RED 12 CONSISTENT", group = "Consistent", preselectTeleOp = "CLOSE Red Drive")
public class RED_12_Consistent extends OpModeCommand {


    Robot bot;
    public static final Pose startpose = new Pose (118,131, Math.toRadians(37));
    ///  Start_pose is against the goal front wall left border
    /// when the wall of the depot hits the back wall touching the field perimeter
    /// when the angled front segment of the depot connects to the back wall of the depot that is parallel to the front wall




    public final Pose all_scores = new Pose (90,83, Math.toRadians(0));

    public final Pose curve2spikemarkcontrolpoint = new Pose(82, 57);



    /// Intake Spike Mark Two,

    public final Pose gather2 = new Pose (125, 58, Math.toRadians(0));


    /// Opengate
    public final Pose opengateSLOW_Control_Point = new Pose (120,64, Math.toRadians(270));

    public final Pose opengateSLOW = new Pose (128.5,68, Math.toRadians(270));

    public final Pose score1_5_pose = new Pose(106, 68, Math.toRadians(270));




    /// then we need to score that
    ///come second
    public final Pose get2balls1 = new Pose (96,84, Math.toRadians(0));

    public final Pose gather1 = new Pose (124,84, Math.toRadians(0));
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));

    /// Gather 3
    public final Pose semiposit = new Pose (102.74,59, Math.toRadians(0));

    public final Pose get2balls2 = new Pose (97.74193548387095,40, Math.toRadians(0));
    public final Pose gather3 = new Pose (127,40, Math.toRadians(0));

    public final Pose end = new Pose(125.2, 58, Math.toRadians(90));



    private Path scorepres, intake1, score1, score2, score1_5, posit1, intake3;
    private PathChain kaimen, posit2;
    private Path curve2spikemark;
    private PathChain kaimenslow;


    //
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


        kaimenslow = bot.getFollower().pathBuilder().addPath(
                new BezierCurve(
                        gather2,
                        opengateSLOW_Control_Point,
                        opengateSLOW

                )
        )
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        score1_5 = new Path(
                new BezierLine(
                        opengateSLOW,
                        score1_5_pose
                        ));
        score1_5.setConstantHeadingInterpolation(Math.toRadians(270));

        ///  Score Spike Mark Two
        score2 = new Path (new BezierLine(score1_5_pose,
                all_scores));
        score2.setConstantHeadingInterpolation(all_scores.getHeading());


//  Spike Mark 1 Intake
        posit1 = new Path (new BezierLine(all_scores, get2balls1));
        posit1.setLinearHeadingInterpolation(all_scores.getHeading(), get2balls1.getHeading());

        intake1 = new Path (new BezierLine(get2balls1, gather1));
        intake1.setLinearHeadingInterpolation(get2balls1.getHeading(),gather1.getHeading());

        score1 = new Path(new BezierLine(gather1, all_scores));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), all_scores.getHeading());

// Spike Mark 3 Intake
        posit2 = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                all_scores,
                                get2balls2)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setBrakingStrength(3.5)
                .build();

        intake3 = new Path (new BezierLine(get2balls2,gather3));
        intake3.setLinearHeadingInterpolation(get2balls2.getHeading(), gather3.getHeading());

        score2 = new Path(new BezierLine(gather3, all_scores));
        score2.setLinearHeadingInterpolation(gather3.getHeading(), all_scores.getHeading());


        park = new Path(
                new BezierLine(
                        all_scores,
                        end
                )
        );
        park.setLinearHeadingInterpolation(all_scores.getHeading(), end.getHeading());


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
                                        new Prep_Shooter_any(bot, auto_speed4, 0.35)
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
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
//               Gate Opening
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimenslow,true,0.65)
                                                .raceWith(new WaitCommand(5000))
                                )
                                .andThen(
                                        new WaitCommand(800)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
// Scoring Second Spike Mark

                                .andThen (
                                        new FollowPath(bot.getFollower(), score1_5, 0.65)
                                                .alongWith(
                                                        new Prep_Shooter_any(bot, auto_speed4, 0.35)
                                                )
                                                .alongWith(
                                                        new turret_yes(bot)
                                                )
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score2, 0.7)
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

//              CYCLE 4, Intake 1st Set
                                .andThen(
                                        new FollowPath(bot.getFollower(),posit1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1, 0.7)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1, 0.5)
                                                .alongWith(
                                                        new Prep_Shooter_any(bot, auto_speed4, 0.35)
                                                )
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
                                .andThen(
                                        new FollowPath(bot.getFollower(), posit2)
                                        .alongWith(
                                                new Intake_on_forever(bot)
                                        )
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake3,.6)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score2)
                                                .alongWith(
                                                        new Prep_Shooter_any(bot, auto_speed4, 0.35)
                                                )
                                )
                                .andThen(
                                        new WaitCommand(750)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                //              CYCLE 6, Leave
                                .andThen(
                                        new FollowPath(bot.getFollower(), park)
                                )
                ));
    }
}

