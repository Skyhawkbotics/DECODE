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

import ftclib.OpModeCommand;
import ftclib.Opmode;

@Autonomous (name = "blueLQTsolo", group = "blue")
public class BlueClose15 extends OpModeCommand {
    Robot bot;

    public static final Pose startpose = new Pose  (128,117, Math.toRadians(140));
    public final Pose preloads = new Pose (19.54447837432682,84.05322303037032, Math.toRadians(180));

    private Path scorepres;
    public void buildPaths(){
        scorepres = new Path (new BezierLine(startpose, preloads));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), preloads.getHeading());
    }

    public void initialize() {
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose, 20);
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
                ));

    }
}



