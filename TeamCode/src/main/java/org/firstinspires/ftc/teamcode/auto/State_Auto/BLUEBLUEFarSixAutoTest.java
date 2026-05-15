package org.firstinspires.ftc.teamcode.auto.State_Auto;

import static org.firstinspires.ftc.teamcode.core.constants.auto_speed4;

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
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.turret.turret_LL_blue;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "blue far 6", group = "blue")
public class BLUEBLUEFarSixAutoTest extends OpModeCommand {

    Robot bot;


    public static final Pose startpos = new Pose(56, 8, Math.toRadians(90));

    public final Pose shootload = new Pose(55, 14.5, Math.toRadians(114));

    public final Pose humanintake = new Pose(13.5, 7.7, Math.toRadians(195));

    public final Pose shoottres = new Pose(55, 14.5, Math.toRadians(114));

    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));

    private Path scorepre1, intakeuno, dametime, end;

    public void buildPaths () {
        scorepre1 = new Path (new BezierLine(startpos,shootload));
        scorepre1.setLinearHeadingInterpolation(startpos.getHeading(), shootload.getHeading());

        intakeuno = new Path (new BezierCurve(shootload, new Pose (40,11), humanintake));
        intakeuno.setLinearHeadingInterpolation(shootload.getHeading(), humanintake.getHeading());

        dametime = new Path (new BezierLine(humanintake, shoottres));
        dametime.setConstantHeadingInterpolation(Math.toRadians(114));

        end = new Path (new BezierLine(shoottres, gametime));
        end.setLinearHeadingInterpolation(shoottres.getHeading(), gametime.getHeading());
    }
    @Override
    public void init_loop() {
      bot.getTurret().set_using_LL(20);
      bot.getTurret().turret_update(20, bot.getFollower().getPose(), 0);
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startpos, 20);
        buildPaths();
        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepre1,0.8)
                                .alongWith(
                                        //new FollowPath(bot.getFollower(), scorepre1)
                                        new Prep_Shooter_any(bot, auto_speed4, 0.35)
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Intake_on_forever(bot)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakeuno)
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen (
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), dametime)
                                                .alongWith(new Intake_off(bot))
                                )
                                .andThen(
                                        new WaitCommand(300)
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
