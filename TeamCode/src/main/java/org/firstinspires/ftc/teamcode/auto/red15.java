package org.firstinspires.ftc.teamcode.auto;

import android.provider.Settings;

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


import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "red15", group = "Red")
public class red15 extends OpModeCommand {
    Robot bot;

    public static final Pose startpose = new Pose (118.2020,132.528, Math.toRadians(303));
    public final Pose preloads = new Pose (89.59485530546624,87.27974276527331, Math.toRadians(49));
    public final Pose posit = new Pose (89.59485530546624,59.96141479099678, Math.toRadians(0));
    public final Pose grab1 = new Pose (113.39549839228296, 59.96141479099678, Math.toRadians(0));
    public final Pose shoot1 = new Pose (83.04823151125402,86.2700964630225, Math.toRadians(49));
    public final Pose posit2gate = new Pose (116.67,61.32, Math.toRadians(45));
    public final Pose opengate = new Pose (128.95,60.32, Math.toRadians(45));
    public final Pose grab2 = new Pose (125.95, 51.72, Math.toRadians(45));
    public final Pose posit2 = new Pose (89.59485530546624,67.13826366559485, Math.toRadians(49));
    public final Pose shoot2 = new Pose (89.36334405144694,87.2700964630225, Math.toRadians(49));
    public final Pose grab3 = new Pose (112.5144694533762,84.03858520900323, Math.toRadians(0));
    public final Pose shoot3 = new Pose (100.2443729903537,94.91961414790998, Math.toRadians(49));
    public final Pose posit3 = new Pose (98.17041800643086,39.884244372990345, Math.toRadians(0));
    public final Pose grab4 = new Pose (113.85852090032154,39.884244372990345, Math.toRadians(0));
    public final Pose shoot4 = new Pose (94.68810289389067,92.60450160771704, Math.toRadians(49));
    public final Pose end = new Pose (94.68810289389067,85.60450160771704, Math.toRadians(49));
    public final Pose tempshoot1 = new Pose (90.46,61.86281588447653, Math.toRadians(49));

     private Path scorepres, position, intake1, score1,  gateposition,   kaimen, intake2, endme, score2, intake3, score3, position3, intake4,score4, leave;
     public void buildPaths(){
         scorepres = new Path (new BezierLine(startpose,preloads));
         scorepres.setLinearHeadingInterpolation(startpose.getHeading(),preloads.getHeading());

         position = new Path (new BezierLine (preloads, posit));
         position.setLinearHeadingInterpolation(preloads.getHeading(),posit.getHeading());

         intake1 = new Path (new BezierLine(posit,grab1));
         intake1.setConstantHeadingInterpolation(Math.toRadians(0));

         score1 = new Path (new BezierLine(tempshoot1, shoot1));
         score1.setLinearHeadingInterpolation(grab1.getHeading(),shoot1.getHeading());

         gateposition = new Path (new BezierLine(grab1, posit2gate));
         gateposition.setConstantHeadingInterpolation(Math.toRadians(45));

         kaimen = new Path (new BezierLine(posit2gate, opengate));
         kaimen.setConstantHeadingInterpolation(Math.toRadians(45));

         endme = new Path (new BezierLine(opengate, tempshoot1));

         intake2 = new Path (new BezierCurve(opengate, new Pose(120, 53), grab2));
         intake2.setConstantHeadingInterpolation(Math.toRadians(45));


         score2 = new Path (new BezierCurve(grab2, new Pose (110,60), shoot2));
         score2.setConstantHeadingInterpolation(Math.toRadians(45));

         intake3 = new Path (new BezierLine(shoot2, grab3));
         intake3.setLinearHeadingInterpolation(shoot2.getHeading(),grab3.getHeading());

         score3 = new Path (new BezierLine(grab3, shoot3));
         score3.setLinearHeadingInterpolation(grab3.getHeading(),shoot3.getHeading());

         position3 = new Path (new BezierLine(shoot3, posit3));
         position3.setLinearHeadingInterpolation(shoot3.getHeading(),posit3.getHeading());

         intake4 = new Path (new BezierLine(posit3, grab4));
         intake4.setConstantHeadingInterpolation(Math.toRadians(0));

         score4 = new Path (new BezierLine(grab4, shoot4));
         score4.setLinearHeadingInterpolation(grab4.getHeading(),shoot4.getHeading());

         leave = new Path (new BezierLine(shoot4, end));
         leave.setConstantHeadingInterpolation(Math.toRadians(45));

     }

     public void initialize() {
         buildPaths();
         bot = new Robot(hardwareMap, telemetry, startpose, 24);
         schedule(
                 new RunCommand(bot::autonomous_robot_update),
                 new SequentialCommandGroup(
                         /// score preloads
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

                                 /// intake and score first set
                                 .andThen(
                                         new FollowPath(bot.getFollower(), position)
                                 )
                                 .andThen (
                                         new FollowPath(bot.getFollower(), intake1, .5)
                                                 .alongWith(new Intake_on(bot))
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), gateposition)
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), kaimen)
                                                 .raceWith(new WaitCommand(1500))
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), endme)
                                 )
                                 /// score set1
                                 .andThen(
                                         new FollowPath(bot.getFollower(), score1)
                                                 .alongWith(new Prep_Shooter(bot))
                                 )
                                 .andThen (new WaitCommand(500))
                                 .andThen(
                                         new Intake_on(bot)
                                 )
                                 .andThen(
                                         new WaitCommand(500)
                                 )
                                 .andThen(
                                         new Shooter_off(bot)
                                 )
/*
                                 /// open gate and intake gate set
                                 .andThen(
                                         new FollowPath(bot.getFollower(), gateposition)
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(),kaimen,.5)
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
