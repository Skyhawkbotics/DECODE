package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.opencv.core.Mat;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "blue15", group = "blue")
public class blue15 extends OpModeCommand {
    Robot bot;
    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpose = new Pose(26.85530546623794, 128.7202572347267, Math.toRadians(315));
    public final Pose preloads = new Pose(56.2572347266881, 85.89067524115754, Math.toRadians(315));
    public final Pose posit = new Pose(55.562700964630224, 59.49839228295819, Math.toRadians(180));
    public final Pose grab1 = new Pose(20.372990353697748, 59.72990353697749, Math.toRadians(180));
    public final Pose shoot1 = new Pose(56.72025723472669, 85.89067524115754, Math.toRadians(180));
    public final Pose grab2 = new Pose(19.215434083601288, 83.34405144694534, Math.toRadians(180));
    public final Pose shoot2 = new Pose(42.135048231511256, 100.0128617363344, Math.toRadians(180));
    public final Pose posit2gate = new Pose(16.668810289389068, 60.655948553054664, Math.toRadians(130));
    public final Pose opengate = new Pose(12.038585209003216, 61.11897106109325, Math.toRadians(130));
    public final Pose shoot3 = new Pose(45.60771704180064, 91.67845659163989, Math.toRadians(130));
    public final Pose posit2 = new Pose(44.450160771704184, 35.884244372990345, Math.toRadians(180));
    public final Pose grab4 = new Pose(19.215434083601288, 34.958199356913184, Math.toRadians(180));
    public final Pose shoot4 = new Pose(49.774919614147905, 90.75241157556269, Math.toRadians(180));
    public final Pose end = new Pose (48.848874598070736, 75.0096463022508, Math.toRadians(180));

    private Path scorepres, position, intake1, score1, intake2, score2, gateposition, kaimen, score3, position2, intake4,score4, leave;
    public void buildPaths(){
        scorepres = new Path (new BezierLine(startpose,preloads));
        scorepres.setConstantHeadingInterpolation(Math.toRadians(225));

        position = new Path (new BezierLine(preloads,posit));
        position.setLinearHeadingInterpolation(preloads.getHeading(), posit.getHeading());

        intake1 = new Path (new BezierLine(posit,grab1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(0));

        score1 = new Path (new BezierLine(grab1, shoot1));
        score1.setConstantHeadingInterpolation(Math.toRadians(0));

        intake2 = new Path (new BezierLine(shoot1, grab2));
        intake2.setConstantHeadingInterpolation(Math.toRadians(0));

        score2 = new Path (new BezierLine(grab2, shoot2));
        score2.setConstantHeadingInterpolation(Math.toRadians(0));

        gateposition = new Path (new BezierLine(shoot2, posit2gate));
        gateposition.setLinearHeadingInterpolation(shoot2.getHeading(), posit2gate.getHeading());

        kaimen = new Path (new BezierLine(posit2gate, opengate));
        kaimen.setConstantHeadingInterpolation(Math.toRadians(35));

        score3 = new Path (new BezierLine(opengate, shoot3));
        score3.setConstantHeadingInterpolation(Math.toRadians(35));

        position2 = new Path (new BezierLine(shoot3, posit2));
        position2.setLinearHeadingInterpolation(shoot3.getHeading(),posit2.getHeading());

        intake4 = new Path (new BezierLine(posit2, grab4));
        intake4.setConstantHeadingInterpolation(Math.toRadians(0));

        score4 = new Path (new BezierLine(grab4, shoot4));
        score4.setConstantHeadingInterpolation(Math.toRadians(0));

        leave = new Path (new BezierLine(shoot4, end));
        leave.setConstantHeadingInterpolation(Math.toRadians(0));

    }

    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose);
        schedule (
                new RunCommand(bot :: robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                .alongWith(
                                        new Prep_Shooter(bot)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
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
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),intake2, 0.75)
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen (
                                        new WaitCommand(200)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), score2)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gateposition)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),kaimen,.5)
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score3)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen (
                                        new WaitCommand(3000)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), position2)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), intake4, 0.75)
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
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), leave)
                                )
                )
        );
    }
}

