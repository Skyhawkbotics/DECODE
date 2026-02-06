package org.firstinspires.ftc.teamcode.Test_Files;

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
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "red far 6", group = "red")
@Disabled
public class REDFarSixAutoTest extends OpModeCommand {

    Robot bot;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(90));

    public final Pose shootload = new Pose(55, 16, Math.toRadians(57));

    public final Pose humanintake = new Pose(10.5, 9.8, Math.toRadians(180));

    public final Pose shoottres = new Pose(55, 16, Math.toRadians(57));

    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));

    private Path scorepre1, intakeuno, dametime, end;

    public void buildPaths () {
        scorepre1 = new Path (new BezierLine(startpos,shootload));
        scorepre1.setLinearHeadingInterpolation(startpos.getHeading(), shootload.getHeading());

        intakeuno = new Path (new BezierCurve(shootload, new Pose (40,9.88), humanintake));
        intakeuno.setLinearHeadingInterpolation(shootload.getHeading(), humanintake.getHeading());

        dametime = new Path (new BezierLine(humanintake, shoottres));
        dametime.setConstantHeadingInterpolation(Math.toRadians(57));

        end = new Path (new BezierLine(shoottres, gametime));
        end.setLinearHeadingInterpolation(shoottres.getHeading(), gametime.getHeading());
    }
    public void initialize() {
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpos, 20);
        schedule (
                new RunCommand(bot :: autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepre1,0.85)
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorepre1)
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new WaitCommand(400  )
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakeuno)
                                                .alongWith(new Shooter_off(bot))
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), dametime)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new WaitCommand(400)
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
