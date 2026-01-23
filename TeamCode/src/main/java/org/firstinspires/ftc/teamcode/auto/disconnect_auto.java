package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
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

    public static final Pose startpose = new Pose(88, 7, Math.toRadians(0));
    // start pose is the tip of the triangle / the inner line of the next tile
    public final Pose pickup1 = new Pose(115, 7, Math.toRadians(0));
    public final Pose pickup2 = new Pose(120, 7, Math.toRadians(0));
    private Path pickupHP;
    private PathChain pickuphp1;
    public void buildPaths(){
        pickupHP = new Path (new BezierLine(startpose,pickup1));
        pickupHP.setTangentHeadingInterpolation();
        pickuphp1 = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 7.000),

                                new Pose(115.000, 7.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .setGlobalDeceleration()

                .build();


    }

    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpose, 24);
        schedule (
                new RunCommand(bot :: far_autonomous_robot_update),
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

