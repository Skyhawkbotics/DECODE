package org.firstinspires.ftc.teamcode.auto.old;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "blue 15", group = "blue")
@Disabled
public class blue15 extends OpModeCommand {
    Robot bot;
    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpose = new Pose(23.656465687232415, 132.528, Math.toRadians(232));
    public final Pose preloads = new Pose(47.13357400722022, 96, Math.toRadians(132));
    public final Pose posit = new Pose(47, 62, Math.toRadians(180));
    public final Pose grab1 = new Pose(24, 62, Math.toRadians(180));

    public final Pose tempostshoot1 = new Pose (58.05054151624549,73.81949458483754, Math.toRadians(129));

    public final Pose shoot1 = new Pose(54.22382671480144, 95.82671480144404, Math.toRadians(140));
    public final Pose posit2gate = new Pose(23, 68, Math.toRadians(180));
    public final Pose opengate = new Pose(10, 68, Math.toRadians(180));




    public final Pose posit2gate_intake = new Pose(23, 60, Math.toRadians(130));

    public final Pose opengate_intake = new Pose(6, 60, Math.toRadians(130));


    public final Pose grab2 = new Pose(16.215434083601288, 54.72, Math.toRadians(135));
    //public final Pose posit2 = new Pose (54.41,67.13826366559485, Math.toRadians(180));
    public final Pose shoot2 = new Pose(54.645048231511256, 96, Math.toRadians(140));
    public final Pose positwhat = new Pose (49,84.038585, Math.toRadians(180));
    public final Pose grab3 = new Pose (20.4944694533762,84.03858520900323, Math.toRadians(180));
    public final Pose shoot3 = new Pose(47.13357400722022, 96, Math.toRadians(140));
    public final Pose posit3 = new Pose(44, 39.884244372990345, Math.toRadians(180));
    public final Pose grab4 = new Pose(20.155434083601288, 39.884244372990345, Math.toRadians(180));
    public final Pose shoot4 = new Pose(47.13357400722022, 96, Math.toRadians(140));
    public final Pose end = new Pose (35.87003610108303, 80.05776173285199, Math.toRadians(129));

    private Path scorepres, position, intake1, score1, intake2, intake3, position3, score2, gateposition, kaimen, score3, endme, intake4,score4, leave, posit4intake3;
    private Path go_to_intake_from_gate, intake_from_gate, score_gate;
    public void buildPaths(){
        scorepres = new Path (new BezierLine(startpose,preloads));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), preloads.getHeading());

        position = new Path (new BezierLine(preloads,posit));
        position.setLinearHeadingInterpolation(preloads.getHeading(), posit.getHeading());

        intake1 = new Path (new BezierLine(posit,grab1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(180));

        gateposition = new Path (new BezierLine(grab1, posit2gate));
        gateposition.setLinearHeadingInterpolation(grab1.getHeading(), posit2gate.getHeading());



        kaimen = new Path (new BezierLine(posit2gate, opengate));
        kaimen.setLinearHeadingInterpolation(posit2gate.getHeading(), opengate.getHeading());




        score1 = new Path (new BezierCurve(opengate, tempostshoot1, shoot1));
        score1.setLinearHeadingInterpolation(opengate.getHeading(), shoot1.getHeading());

        go_to_intake_from_gate = new Path(new BezierCurve(shoot1, tempostshoot1, posit2gate_intake));
        go_to_intake_from_gate.setLinearHeadingInterpolation(shoot1.getHeading(), posit2gate_intake.getHeading());

        intake_from_gate = new Path (new BezierLine(posit2gate_intake, opengate_intake ));
        intake_from_gate.setConstantHeadingInterpolation(posit2gate_intake.getHeading());

        score_gate = new Path (new BezierCurve(opengate_intake, tempostshoot1, shoot1));
        score_gate.setLinearHeadingInterpolation(opengate_intake.getHeading(), shoot1.getHeading());



        intake2 = new Path (new BezierCurve(opengate, new Pose (24,53), grab2));
        intake2.setConstantHeadingInterpolation(Math.toRadians(135));




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
                        new FollowPath(bot.getFollower(), scorepres, 0.85)
                                .alongWith(
                                        new Prep_Shooter(bot)
                                )
                                .andThen(
                                        new WaitCommand(400  )
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )

                                /// GOES TO INTAKE
                                .andThen(
                                        new FollowPath(bot.getFollower(), position)
                                                .alongWith(new Shooter_off(bot))
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), intake1, 0.85)
                                                .alongWith(new Intake_on_long(bot))
                                ) ///  WORKS
                        /// clear gate
                                .andThen(
                                        new FollowPath(bot.getFollower(),gateposition)
                                )
                                .andThen(
                                new WaitCommand(200)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), kaimen)
                                                .raceWith(new WaitCommand(1200)
                                                )
                                )
                               // .andThen(
                              //          new FollowPath(bot.getFollower(), endme)
                              //  )
/// score set
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
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
                                                .raceWith(new WaitCommand(2500))
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score_gate)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )

                                /// intake and score third set
                                .andThen(
                                        new FollowPath(bot.getFollower(), posit4intake3, 0.7)
                                                .alongWith(
                                                        new Shooter_off(bot)
                                                )
                                ).andThen(
                                        new FollowPath(bot.getFollower(),intake3, 0.85)
                                                .alongWith(new Intake_on_long(bot))
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
                                        new Shooter_off(bot)
                                )

                                /// intake and score fourth set
                                .andThen (
                                        new FollowPath(bot.getFollower(), position3)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), intake4, 0.85)
                                                .alongWith(new Intake_on_long(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score4, 0.8)
                                                .alongWith(new Prep_Shooter(bot))
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

