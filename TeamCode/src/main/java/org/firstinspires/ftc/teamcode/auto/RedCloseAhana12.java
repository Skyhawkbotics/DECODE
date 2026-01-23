package org.firstinspires.ftc.teamcode.auto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.HoldPoint;
import ftclib.OpModeCommand;

@Autonomous(name = "redLQTsolo", group = "red")
public class RedCloseAhana12 extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));
    public final Pose pres = new Pose (96,77, Math.toRadians(0));
    public final Pose gather1 = new Pose (125.47909967845658,77, Math.toRadians(0));

    private Path scorepres, intake1;

    public void buildPaths() {
        scorepres = new Path(new BezierLine(startpose, pres));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), pres.getHeading());

        intake1 = new Path (new BezierLine(pres, gather1));
        intake1.setLinearHeadingInterpolation(pres.getHeading(),gather1.getHeading());

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
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1, .65)
                                                .alongWith(new Intake_on_long(bot))
                                )

                ));
    }
}

