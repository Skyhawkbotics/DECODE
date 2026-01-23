package org.firstinspires.ftc.teamcode.auto;

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
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Preload_score_red;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "Disconnect Auto", group = "Red")
public class disconnect_auto extends OpModeCommand {
    Robot bot;

    public static final Pose startpose = new Pose(90, 6, Math.toRadians(0));
    public final Pose pickup1 = new Pose(132, 6, Math.toRadians(0));
    private Path pickupHP;
    public void buildPaths(){
        pickupHP = new Path (new BezierLine(startpose,pickup1));
        pickupHP.setTangentHeadingInterpolation();

    }

    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose, 24);
        schedule (
                new RunCommand(bot :: autonomous_robot_update),
                new SequentialCommandGroup(
                        new Preload_score_red(bot)
                                .andThen(
                                        new FollowPath(bot.getFollower(), pickupHP)
                                                .alongWith(
                                                        new Intake_on_long(bot)
                                                )
                                )
                )

        );
    }

}

