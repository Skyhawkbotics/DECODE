package org.firstinspires.ftc.teamcode.auto.State_Auto;

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
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.farzone.Prep_Shooter_far_blue;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "red far 6", group = "red")
public class REDREDFarSixAutoTest extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(90));
    public static final Pose redstartpos = startpos.mirror();

    public final Pose shootme = new Pose(55, 14.5, Math.toRadians(114));
    public final Pose redshootload = shootme.mirror();

    public final Pose humanintake = new Pose(10.5, 8.2, Math.toRadians(180));

    public final Pose redhumanintake = humanintake.mirror();
    public final Pose humanintakedos = new Pose(10.5, 8.2, Math.toRadians(215));

    public final Pose redhumanintakedos = humanintakedos.mirror();

    public final Pose shoottres = new Pose(50, 14.5, Math.toRadians(114));

    public final Pose redshoottres = shoottres.mirror();

    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));

    public final Pose redgametime = gametime.mirror();

    public final Pose curvy = new Pose(40, 11);

    public final Pose redcurvy = curvy.mirror();

    private Path scorepre1, intakeuno, dametime, end;

    public void buildPaths () {
        scorepre1 = new Path (new BezierLine(redstartpos,redshootload));
        scorepre1.setLinearHeadingInterpolation(redstartpos.getHeading(), redshootload.getHeading());

        intakeuno = new Path (new BezierCurve(redshootload, redcurvy, redhumanintake));
        intakeuno.setLinearHeadingInterpolation(redshootload.getHeading(), redhumanintake.getHeading());

        dametime = new Path (new BezierLine(redhumanintake, redshoottres));
        dametime.setConstantHeadingInterpolation(Math.toRadians(57));

        end = new Path (new BezierLine(redshoottres, redgametime));
        end.setLinearHeadingInterpolation(redshoottres.getHeading(), redgametime.getHeading());
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,redstartpos, 24);
        buildPaths();/// hi abraham, put buildpaths(); AFTER the robot has be initalized with bot = new Robot() or else it will error
        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepre1,0.85)
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorepre1)
                                                .alongWith(new Prep_Shooter_any(bot, 1600, 0.49))
                                )
                                .andThen(
                                        new WaitCommand(3000 )
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Intake_on_forever(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakeuno)
                                )
                                .andThen(
                                        new WaitCommand(4000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), dametime)
                                                .alongWith(new Intake_off(bot))
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), end)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith(new Shooter_off(bot))
                                )

                )
        );
    }
}
