package org.firstinspires.ftc.teamcode.core.ericauto;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "assist red! close", group = "gf")
@Configurable
public class Ahana_Red_Close_Assist extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose (112.95,135.6, Math.toRadians(90));
    public final Pose presets = new Pose (103.44,110.82, Math.toRadians(135));
    public final Pose pos4ophalf = new Pose (90.69,72, Math.toRadians(180));
    public final Pose pos4op1 = new Pose (114.69, 71.6, Math.toRadians(90));
    public final Pose gateopn = new Pose (128.07,71.6, Math.toRadians(180));
    public final Pose pos4gathalf = new Pose (94.82, 71.6, Math.toRadians(180));
    public final Pose pos4gat1 = new Pose (95.02,84.59, Math.toRadians(0));
    public final Pose gather1 = new Pose (125.31, 84.2, Math.toRadians(0));
    public final Pose shoot1 = new Pose (90.1, 84.78, Math.toRadians(140));
    public final Pose pos4gat2 = new Pose (89.902, 60.199, Math.toRadians(180));
    public final Pose gather2 = new Pose (124.72, 59.61, Math.toRadians(180));
    public final Pose shoot2 = new Pose (89.8, 84.59, Math.toRadians(140));
    private Path to_load, score1;

    private Path scorepres, gategetready, gategetsteady, gatemustgo, getreadyhalf, getready1, load1, getready2, load2;
    public void buildPaths(){
        scorepres = new Path(new BezierLine(startpose,presets));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(),presets.getHeading());///add delay here


      /*  gategetready = new Path(new BezierLine(presets,pos4ophalf));
        gategetready.setLinearHeadingInterpolation(presets.getHeading(),pos4ophalf.getHeading());

        gategetsteady = new Path (new BezierLine(pos4ophalf,pos4op1));
        gategetready.setConstantHeadingInterpolation(180);

        gatemustgo = new Path (new BezierLine(pos4op1, gateopn));
        gatemustgo.setConstantHeadingInterpolation(180); ///slowdown

        getreadyhalf = new Path (new BezierLine(gateopn,pos4gathalf));
        getreadyhalf.setConstantHeadingInterpolation(180);

        getready1 = new Path (new BezierLine(pos4ophalf,pos4gat1));
        getready1.setConstantHeadingInterpolation(180);

       */
        to_load = new Path(new BezierLine(presets, pos4gat1));
        to_load.setLinearHeadingInterpolation(presets.getHeading(), pos4gat1.getHeading());

        load1 = new Path (new BezierLine (pos4gat1, gather1));
        load1.setConstantHeadingInterpolation(pos4gat1.getHeading());

        score1 = new Path (new BezierLine(gather1, presets));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), presets.getHeading()); ///add delay here

        gategetsteady = new Path (new BezierLine(presets,pos4op1));
        gategetsteady.setLinearHeadingInterpolation(presets.getHeading(), pos4gat1.getHeading());
    }
    public void initialize (){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, red_ID, startpose);

        schedule(
                new RunCommand(bot::auto_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepres)
                                .alongWith(
                                        new intake_swallow(bot),
                                        new Prep_Shooter(bot) // this is the final decider of when everything ends
                                                        // this is the final decider of when everything ends

                                )
                                .andThen(
                                        new close_score_command(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), to_load),
                                        new FollowPath(bot.getFollower(), load1, 0.8)
                                                .alongWith(
                                                        new intake_swallow(bot)
                                                )

                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(
                                                        new intake_swallow(bot)
                                                )
                                )
                                .andThen(
                                        new close_score_command(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gategetsteady)
                                )


                                /*.andThen(
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

                                 */
                )

        );
    }

}
