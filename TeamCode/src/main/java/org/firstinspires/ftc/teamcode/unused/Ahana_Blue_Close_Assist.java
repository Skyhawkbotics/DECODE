package org.firstinspires.ftc.teamcode.unused;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;
/*
@Autonomous (name = "assist blue close", group = "gf")
@Disabled
public class Ahana_Blue_Close_Assist extends OpModeCommand {

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    Robot bot;
    public static final Pose startpose = new Pose (31.95,135.6, Math.toRadians(90));
    public final Pose presets = new Pose (40.56,110.82, Math.toRadians(135));
    public final Pose pos4ophalf = new Pose (53.31,72, Math.toRadians(180));
    public final Pose pos4op1 = new Pose (29.31, 71.6, Math.toRadians(180));
    public final Pose gateopn = new Pose (15.93,71.6, Math.toRadians(180));
    public final Pose pos4gathalf = new Pose (49.18, 71.6, Math.toRadians(180));
    public final Pose pos4gat1 = new Pose (48.98,84.59, Math.toRadians(180));
    public final Pose gather1 = new Pose (18.69, 84.2, Math.toRadians(180));
    public final Pose shoot1 = new Pose (53.9, 84.78, Math.toRadians(140));
    public final Pose pos4gat2 = new Pose (54.098, 60.199, Math.toRadians(180));
    public final Pose gather2 = new Pose (19.28, 59.61, Math.toRadians(180));
    public final Pose shoot2 = new Pose (54.2, 84.59, Math.toRadians(140));

    private Path scorepres, gategetready, gategetsteady, gatemustgo, getreadyhalf, getready1, load1, score1, getready2, load2, score2;
    public void buildPaths(){
        scorepres = new Path(new BezierLine(startpose,presets));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(),presets.getHeading());///add delay here

        gategetready = new Path(new BezierLine(presets,pos4ophalf));
        gategetready.setLinearHeadingInterpolation(presets.getHeading(),pos4ophalf.getHeading());

        gategetsteady = new Path (new BezierLine(pos4ophalf,pos4op1));
        gategetready.setConstantHeadingInterpolation(180);

        gatemustgo = new Path (new BezierLine(pos4op1, gateopn));
        gatemustgo.setConstantHeadingInterpolation(180); ///slowdown

        getreadyhalf = new Path (new BezierLine(gateopn,pos4gathalf));
        getreadyhalf.setConstantHeadingInterpolation(180);

        getready1 = new Path (new BezierLine(pos4ophalf,pos4gat1));
        getready1.setConstantHeadingInterpolation(180);

        load1 = new Path (new BezierLine (pos4gat1, gather1));
        load1.setConstantHeadingInterpolation(180);

        score1 = new Path (new BezierLine(gather1, shoot1));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), shoot1.getHeading()); ///add delay here

        getready2 = new Path (new BezierLine(shoot1, pos4gat2));
        getready2.setLinearHeadingInterpolation(shoot1.getHeading(), pos4gat2.getHeading());

        load2 = new Path(new BezierLine(pos4gat2, gather2));
        load2.setConstantHeadingInterpolation(180);

        score2 = new Path (new BezierLine(gather2, shoot2));
        score2.setLinearHeadingInterpolation(gather2.getHeading(), shoot2.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot(hardwareMap, panelsTelemetry, startpose);
        bot.getFollower().setStartingPose(startpose);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepres)
                                /*
                                .andThen(
                                        new WaitCommand(7000)
                                )/// time for bot to score pres
                                .andThen(
                                        new FollowPath(bot.getFollower(),gategetready)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),gategetsteady)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),gatemustgo)
                                )
                                .andThen(
                                        new WaitCommand(10000)
                                )/// allows balls to roll down
                                .andThen(
                                        new FollowPath(bot.getFollower(),getreadyhalf)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),getready1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),load1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),score1)
                                )
                                .andThen(
                                        new WaitCommand(7000)
                                )/// allows bot to score 1
                                .andThen(
                                        new FollowPath(bot.getFollower(),getready2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),load2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),score2)
                                )
                                .andThen(new WaitCommand(7000)
                                )/// allows time for bot to score 2


                )

        );
    }

}

 */