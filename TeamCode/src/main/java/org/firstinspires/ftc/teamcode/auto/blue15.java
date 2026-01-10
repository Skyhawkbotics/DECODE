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

    public static final Pose startpose = new Pose(26.446465687232415, 132.528, Math.toRadians(234));
    public final Pose preloads = new Pose(54.2572347266881, 87.27974276527331, Math.toRadians(234));
    public final Pose posit = new Pose(54.562700964630224, 59.96141479099678, Math.toRadians(180));
    public final Pose grab1 = new Pose(31.372990353697748, 59.96141479099678, Math.toRadians(180));
    public final Pose shoot1 = new Pose(11.72025723472669, 86.2700964630225, Math.toRadians(180));
    public final Pose posit2gate = new Pose(16.668810289389068, 61.325948553054664, Math.toRadians(135));
    public final Pose opengate = new Pose(19.038585209003216, 60.32897106109325, Math.toRadians(135));

    public final Pose grab2 = new Pose(18.215434083601288, 51.72, Math.toRadians(135));
    public final Pose posit2 = new Pose (54.41,67.13826366559485, Math.toRadians(180));
    public final Pose shoot2 = new Pose(54.645048231511256, 87.2700964630225, Math.toRadians(180));
    public final Pose grab3 = new Pose (31.4944694533762,84.03858520900323, Math.toRadians(180));
    public final Pose shoot3 = new Pose(43.76771704180064, 94.91961414790998, Math.toRadians(180));
    public final Pose posit3 = new Pose(41.450160771704184, 39.884244372990345, Math.toRadians(180));
    public final Pose grab4 = new Pose(30.155434083601288, 39.884244372990345, Math.toRadians(180));
    public final Pose shoot4 = new Pose(49.324919614147905, 92.60450160771704, Math.toRadians(180));
    public final Pose end = new Pose (48.848874598070736, 85.60450160771704, Math.toRadians(180));

    private Path scorepres, position, intake1, score1, intake2, intake3, position3, score2, gateposition, kaimen, score3, position2, intake4,score4, leave;
    public void buildPaths(){
        scorepres = new Path (new BezierLine(startpose,preloads));
        scorepres.setConstantHeadingInterpolation(Math.toRadians(234));

        position = new Path (new BezierLine(preloads,posit));
        position.setLinearHeadingInterpolation(preloads.getHeading(), posit.getHeading());

        intake1 = new Path (new BezierLine(posit,grab1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(180));

        score1 = new Path (new BezierLine(grab1, shoot1));
        score1.setConstantHeadingInterpolation(Math.toRadians(180));

        gateposition = new Path (new BezierLine(shoot2, posit2gate));
        gateposition.setLinearHeadingInterpolation(shoot2.getHeading(), posit2gate.getHeading());

        kaimen = new Path (new BezierLine(posit2gate, opengate));
        kaimen.setConstantHeadingInterpolation(Math.toRadians(135));

        intake2 = new Path (new BezierCurve(opengate, new Pose (24,53), grab2));
        intake2.setConstantHeadingInterpolation(Math.toRadians(135));

        position2 = new Path (new BezierCurve(grab2, posit2));
        position2.setConstantHeadingInterpolation(Math.toRadians(135));

        score2 = new Path (new BezierLine(posit2, shoot2));
        score2.setConstantHeadingInterpolation(Math.toRadians(135));

        intake3 = new Path (new BezierLine(shoot2, grab3));
        intake3.setLinearHeadingInterpolation(shoot2.getHeading(), grab3.getHeading());

        score3 = new Path (new BezierLine(opengate, shoot3));
        score3.setConstantHeadingInterpolation(Math.toRadians(135));

        position3 = new Path (new BezierLine(shoot3, posit3));
        position3.setLinearHeadingInterpolation(shoot3.getHeading(),posit3.getHeading());

        intake4 = new Path (new BezierLine(posit3, grab4));
        intake4.setConstantHeadingInterpolation(Math.toRadians(180));

        score4 = new Path (new BezierLine(grab4, shoot4));
        score4.setConstantHeadingInterpolation(Math.toRadians(180));

        leave = new Path (new BezierLine(shoot4, end));
        leave.setConstantHeadingInterpolation(Math.toRadians(0));

    }

    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose, 20);
        schedule (
                new RunCommand(bot :: autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                .alongWith(
                                        new Prep_Shooter(bot)
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                ///
                                .andThen(
                                        new FollowPath(bot.getFollower(), position)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), intake1, .75)
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen(new WaitCommand(200))
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
                                ///open gate and intakeset
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
                                        new FollowPath(bot.getFollower(),position2)
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


                                /// intake and score third set
                                .andThen(
                                        new FollowPath(bot.getFollower(),intake3, 0.75)
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen (
                                        new WaitCommand(200)
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
                                        new FollowPath(bot.getFollower(), intake4, 0.65)
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

                )
        );
    }
}

