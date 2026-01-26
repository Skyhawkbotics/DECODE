package org.firstinspires.ftc.teamcode.auto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "redLQTsolo", group = "red")
public class RedClose15 extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));
    public final Pose pres = new Pose (96,77, Math.toRadians(0));
    public final Pose gather1 = new Pose (132.48,77, Math.toRadians(0));
    public final Pose shoot1 = new Pose (108.85,97.1, Math.toRadians(0));
    public final Pose get2balls1 = new Pose (104.35,53.76129032258066, Math.toRadians(0));
    public final Pose gather2= new Pose (124.86451612903225, 53.76129032258066, Math.toRadians(0));
    public final Pose opengate = new Pose (137.25806451612902,61.29677419354837, Math.toRadians(0));
    public final Pose shoot2 = new Pose (102.39354838709677,90.0516129032258, Math.toRadians(0));
    public final Pose get2balls2 = new Pose (100.74193548387095,35.7225806451613, Math.toRadians(0));

    private Path scorepres, intake1, score1, posit1, kaimen, score2, posit2;
    private PathChain intake2_gateopen;

    public void buildPaths() {
        scorepres = new Path(new BezierLine(startpose, pres));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), pres.getHeading());

        intake1 = new Path (new BezierLine(pres, gather1));
        intake1.setLinearHeadingInterpolation(pres.getHeading(),gather1.getHeading());

        score1 = new Path(new BezierLine(gather1, shoot1));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), shoot1.getHeading());

        posit1 = new Path(new BezierLine(shoot1, get2balls1));
        posit1.setLinearHeadingInterpolation(shoot1.getHeading(), get2balls1.getHeading());

        intake2_gateopen = bot.getFollower().pathBuilder()
                .addPath(
                new BezierLine(get2balls1,gather2)
        )
                .setLinearHeadingInterpolation(get2balls1.getHeading(),gather2.getHeading()
                )
                .addPath(
                        new BezierLine(gather2,opengate)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setGlobalDeceleration()
                .build();



        kaimen = new Path (new BezierLine(gather2, opengate));
        kaimen.setLinearHeadingInterpolation(gather2.getHeading(),opengate.getHeading());

        score2 = new Path (new BezierLine(opengate, shoot2));
        score2.setLinearHeadingInterpolation(opengate.getHeading(), shoot2.getHeading());

        posit2 = new Path (new BezierLine(shoot2, get2balls2));
        posit2.setLinearHeadingInterpolation(shoot2.getHeading(),get2balls1.getHeading());

        }

    public void initialize() {
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose, 24);
        schedule(
        new RunCommand(bot:: autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                .alongWith(
                                        new Prep_Shooter(bot)
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
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1)
                                                .alongWith(new Intake_on_long(bot))
                                )
                        //
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(new WaitCommand(250))
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), posit1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake2_gateopen, .65)
                                                .alongWith(new Intake_on_long(bot))
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimen)
                                                .raceWith(new WaitCommand(2000))
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), score2)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), posit2)
                                )
                ));
    }
}

