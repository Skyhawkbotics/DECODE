package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter_red;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "red 15  ", group = "Red")
public class red15 extends OpModeCommand {
    Robot bot;

    public static final Pose startpose = new Pose (118.2020,132.528, Math.toRadians(303));
    public final Pose preloads = new Pose (90,87, Math.toRadians(46));
    public final Pose posit = new Pose (90,60, Math.toRadians(0));
    public final Pose grab1 = new Pose (113, 60, Math.toRadians(0));
    public final Pose posit2gate = new Pose (116.67,67, Math.toRadians(0));


    public final Pose opengate = new Pose (128.95,67, Math.toRadians(0));



    public final Pose tempshoot1 = new Pose (90.46,61.86281588447653, Math.toRadians(49));

    public final Pose shoot1 = new Pose (90,87, Math.toRadians(49));


    public final Pose posit2gate_intake = new Pose (116.67,60, Math.toRadians(45));

    public final Pose opengate_intake = new Pose (128.95,60, Math.toRadians(45));
    public final Pose grab2 = new Pose (125.95, 51.72, Math.toRadians(0));
    public final Pose posit2 = new Pose (90,67.13826366559485, Math.toRadians(49));
    public final Pose shoot2 = new Pose (90,87, Math.toRadians(46));
    public final Pose grab3 = new Pose (115.5144694533762,84.03858520900323, Math.toRadians(0));

    public final Pose beforegrab3 = new Pose(95, 83.69675090252709, 0);
    public final Pose shoot3 = new Pose (90,87, Math.toRadians(46));
    public final Pose posit3 = new Pose (95,39.884244372990345, Math.toRadians(0));
    public final Pose grab4 = new Pose (115,39.884244372990345, Math.toRadians(0));
    public final Pose shoot4 = new Pose (90,87, Math.toRadians(46));
    public final Pose end = new Pose (94.68810289389067,85.60450160771704, Math.toRadians(49));

     private Path scorepres, position, intake1, score1,  gateposition,   kaimen, go_to_intake_from_gate, intake_from_gate, score_gate, intake2, endme, score2, intake3, score3, position3, intake4,score4, leave, transition3;
     public void buildPaths(){
         scorepres = new Path (new BezierLine(startpose,preloads));
         scorepres.setLinearHeadingInterpolation(startpose.getHeading(),preloads.getHeading());

         position = new Path (new BezierLine (preloads, posit));
         position.setLinearHeadingInterpolation(preloads.getHeading(),posit.getHeading());

         intake1 = new Path (new BezierLine(posit,grab1));
         intake1.setConstantHeadingInterpolation(Math.toRadians(0));

         gateposition = new Path (new BezierLine(grab1, posit2gate));
         gateposition.setConstantHeadingInterpolation(Math.toRadians(0));


         kaimen = new Path (new BezierLine(posit2gate, opengate));
         kaimen.setConstantHeadingInterpolation(Math.toRadians(0));


         score1 = new Path (new BezierCurve(opengate, tempshoot1, shoot1));
         score1.setLinearHeadingInterpolation(opengate.getHeading(),shoot1.getHeading());

         go_to_intake_from_gate = new Path(new BezierCurve(shoot1, tempshoot1, posit2gate_intake));
         go_to_intake_from_gate.setLinearHeadingInterpolation(shoot1.getHeading(), opengate_intake.getHeading());

         intake_from_gate = new Path (new BezierLine(posit2gate_intake, opengate_intake ));
         intake_from_gate.setConstantHeadingInterpolation(opengate_intake.getHeading());

         score_gate = new Path (new BezierCurve(opengate_intake, tempshoot1, shoot1));
         score_gate.setLinearHeadingInterpolation(opengate_intake.getHeading(), shoot1.getHeading());



         transition3 = new Path(new BezierLine(shoot1, beforegrab3));
         transition3.setLinearHeadingInterpolation(shoot1.getHeading(),beforegrab3.getHeading());

         intake3 = new Path (new BezierLine(beforegrab3, grab3));
         intake3.setLinearHeadingInterpolation(beforegrab3.getHeading(),grab3.getHeading());

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
                         new FollowPath(bot.getFollower(), scorepres, 0.85)
                                 .alongWith(
                                         new Prep_Shooter_red(bot)
                                 )
                                 .andThen(
                                         new WaitCommand(400)
                                 )
                                 .andThen(
                                         new Intake_on(bot)
                                 )

                                 /// intake and score first set
                                 .andThen(
                                         new FollowPath(bot.getFollower(), position)
                                                 .alongWith(new Shooter_off(bot))
                                 )
                                 .andThen (
                                         new FollowPath(bot.getFollower(), intake1, 0.85)
                                                 .alongWith(new Intake_on_long(bot))
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), gateposition)
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), kaimen)
                                                 .raceWith(new WaitCommand(1200))
                                 )

                                 /// score set1
                                 .andThen(
                                         new FollowPath(bot.getFollower(), score1)
                                                 .alongWith(new Prep_Shooter_red(bot))
                                 )
                                 .andThen (new WaitCommand(300))
                                 .andThen(
                                         new Intake_on(bot)
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), go_to_intake_from_gate)
                                                 .alongWith(new Shooter_off(bot))
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(), intake_from_gate)
                                                 .alongWith(new Intake_on_forever(bot))
                                                 .raceWith(new WaitCommand(2000))

                                 )

                                 .andThen(
                                         new FollowPath(bot.getFollower(), score_gate)
                                                 .alongWith(new Intake_off(bot))
                                                 .alongWith(new Prep_Shooter_red(bot))
                                 )
                                 .andThen(
                                         new Intake_on(bot)
                                 )

                                 /// intake and score third set
                                 .andThen( new FollowPath(bot.getFollower(), transition3, 0.7)
                                         .alongWith( new Shooter_off(bot))
                                 )
                                 .andThen(
                                         new FollowPath(bot.getFollower(),intake3, 0.85)
                                                 .alongWith(new Intake_on(bot))
                                 )
                                 .andThen (
                                         new WaitCommand(200)
                                 )
                                 .andThen (
                                         new FollowPath(bot.getFollower(), score3)
                                                .alongWith(new Prep_Shooter_red(bot))
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
                                               .alongWith(new Prep_Shooter_red(bot))
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
                                 .andThen(
                                         new FollowPath(bot.getFollower(), leave)
                                 )

                 )
         );
     }

}
