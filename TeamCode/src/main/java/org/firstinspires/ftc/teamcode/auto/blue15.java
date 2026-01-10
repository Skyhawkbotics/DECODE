package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.opencv.core.Mat;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "blue15", group = "blue")
public class blue15 extends OpModeCommand {
    Robot bot;
    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpose = new Pose(23.656465687232415, 132.528, Math.toRadians(232));
    public final Pose preloads = new Pose(47.13357400722022, 95.82671480144404, Math.toRadians(129));
    public final Pose posit = new Pose(46.96028880866426, 59.78339350180506, Math.toRadians(180));
    public final Pose grab1 = new Pose(24.372990353697748, 59.96141479099678, Math.toRadians(180));
    public final Pose shoot1 = new Pose(54.22382671480144, 95.82671480144404, Math.toRadians(129));
    public final Pose tempostshoot1 = new Pose (53.545126353790614,61.86281588447653, Math.toRadians(129));
    public final Pose posit2gate = new Pose(13.668810289389068, 61.325948553054664, Math.toRadians(180));
    public final Pose opengate = new Pose(10.038585209003216, 60.32897106109325, Math.toRadians(180));

    public final Pose grab2 = new Pose(16.215434083601288, 54.72, Math.toRadians(135));
    //public final Pose posit2 = new Pose (54.41,67.13826366559485, Math.toRadians(180));
    public final Pose shoot2 = new Pose(54.645048231511256, 95.82671480144404, Math.toRadians(129));
    public final Pose positwhat = new Pose (49,84.038585, Math.toRadians(180));
    public final Pose grab3 = new Pose (20.4944694533762,84.03858520900323, Math.toRadians(180));
    public final Pose shoot3 = new Pose(47.13357400722022, 91.82671480144404, Math.toRadians(129));
    public final Pose posit3 = new Pose(41.450160771704184, 39.884244372990345, Math.toRadians(180));
    public final Pose grab4 = new Pose(20.155434083601288, 39.884244372990345, Math.toRadians(180));
    public final Pose shoot4 = new Pose(47.13357400722022, 91.82671480144404, Math.toRadians(129));
    public final Pose end = new Pose (48.848874598070736, 85.60450160771704, Math.toRadians(129));

    private Path scorepres, position, intake1, score1, intake2, intake3, position3, score2, gateposition, kaimen, score3, endme, intake4,score4, leave, posit4intake3;
    public void buildPaths(){
        scorepres = new Path (new BezierLine(startpose,preloads));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), preloads.getHeading());

        position = new Path (new BezierLine(preloads,posit));
        position.setLinearHeadingInterpolation(preloads.getHeading(), posit.getHeading());

        intake1 = new Path (new BezierLine(posit,grab1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(180));
/// chance first back to grab 1 if 15 ball
        score1 = new Path (new BezierLine(tempostshoot1, shoot1));
        score1.setLinearHeadingInterpolation(grab1.getHeading(), shoot1.getHeading());

        /// change first back to shoot 2 if 15 ball
        gateposition = new Path (new BezierLine(grab1, posit2gate));
        gateposition.setLinearHeadingInterpolation(shoot2.getHeading(), posit2gate.getHeading());

        kaimen = new Path (new BezierLine(posit2gate, opengate));
        kaimen.setLinearHeadingInterpolation(posit2gate.getHeading(), opengate.getHeading());

        intake2 = new Path (new BezierCurve(opengate, new Pose (24,53), grab2));
        intake2.setConstantHeadingInterpolation(Math.toRadians(135));

        endme = new Path (new BezierLine(opengate,tempostshoot1));
        endme.setLinearHeadingInterpolation(opengate.getHeading(), tempostshoot1.getHeading());


        score2 = new Path (new BezierCurve(grab2, new Pose (49,60), shoot2));
        score2.setLinearHeadingInterpolation(grab2.getHeading(),shoot2.getHeading());

       ///change this to shoot2 if opneing gate
        posit4intake3 = new Path (new BezierLine(shoot1,positwhat));
        posit4intake3.setLinearHeadingInterpolation(shoot1.getHeading(),positwhat.getHeading());

        intake3 = new Path (new BezierLine(positwhat, grab3));
        intake3.setLinearHeadingInterpolation(positwhat.getHeading(), grab3.getHeading());

        score3 = new Path (new BezierLine(grab3, shoot3));
        score3.setLinearHeadingInterpolation(grab3.getHeading(),shoot3.getHeading());

        position3 = new Path (new BezierLine(shoot3, posit3));
        position3.setLinearHeadingInterpolation(shoot3.getHeading(),posit3.getHeading());

        intake4 = new Path (new BezierLine(posit3, grab4));
        intake4.setConstantHeadingInterpolation(Math.toRadians(180));

        score4 = new Path (new BezierLine(grab4, shoot4));
        score4.setLinearHeadingInterpolation(grab4.getHeading(),shoot4.getHeading());

        leave = new Path (new BezierLine(shoot4, end));
        leave.setLinearHeadingInterpolation(shoot4.getHeading(),end.getHeading());

    }

    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose, 20);
        schedule (
                new RunCommand(bot :: autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres, 0.8)
                                .alongWith(
                                        new Prep_Shooter(bot)
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

                                /// GOES TO INTAKE
                                .andThen(
                                        new FollowPath(bot.getFollower(), position)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), intake1, .5)
                                                .alongWith(new Intake_on(bot))
                                ) ///  WORKS
                        /// clear gate
                                .andThen(
                                        new FollowPath(bot.getFollower(),gateposition)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), kaimen)
                                                .raceWith(new WaitCommand(6000))
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(), endme)
                                )
/// score set1
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                               /* ///open gate and intakeset
                                .andThen(new FollowPath(bot.getFollower(), gateposition))

                                .andThen(
                                        new FollowPath(bot.getFollower(),kaimen, 0.5)
                                                .alongWith(new Intake_on_long(bot))
                                                .raceWith(new WaitCommand(1500)
                                ))
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake2,.5)
                                                .alongWith(new Intake_on_long(bot))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score2)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(new WaitCommand(500))
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen (
                                        new WaitCommand(500))
                                .andThen(
                                        new Shooter_off(bot))
*/

                                /// intake and score third set
                                .andThen(
                                        new FollowPath(bot.getFollower(), posit4intake3, 0.5
                                        )
                                ).andThen(
                                        new FollowPath(bot.getFollower(),intake3, 0.5)
                                                .alongWith(new Intake_on(bot))
                                )
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), score3)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

                                /// intake and score fourth set
                                .andThen (
                                        new FollowPath(bot.getFollower(), position3)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), intake4, 0.5)
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score4)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), leave)
                                )


        );
    }
}

