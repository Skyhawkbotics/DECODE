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
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.farzone.Prep_Shooter_far_blue;
import org.firstinspires.ftc.teamcode.core.commands.farzone.Prep_Shooter_far_red;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "blue far 6", group = "blue")
public class BLUEFarSixAutoTest extends OpModeCommand {

    Robot bot;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(90));

    public static final Pose bluestartpos = startpos.mirror();

    public final Pose shootload = new Pose(55, 16, Math.toRadians(114));

    public final Pose blueshootload = shootload.mirror();

    public final Pose humanintake = new Pose(10.5, 9.8, Math.toRadians(180));

    public final Pose bluehumanintake = humanintake.mirror();

    public final Pose shoottres = new Pose(55, 16, Math.toRadians(114));

    public final Pose blueshoottres = shoottres.mirror();

    public final Pose gametime = new Pose(41, 16, Math.toRadians(180));

    public final Pose bluegametime = gametime.mirror();

    public final Pose curvy = new Pose(40, 11);

    public final Pose bluecurvy = curvy.mirror();

    private Path scorepre1, intakeuno, dametime, end;

    public void buildPaths () {
        scorepre1 = new Path (new BezierLine(bluestartpos,blueshootload));
        scorepre1.setLinearHeadingInterpolation(bluestartpos.getHeading(), blueshootload.getHeading());

        intakeuno = new Path (new BezierCurve(blueshootload, bluecurvy, bluehumanintake));
        intakeuno.setLinearHeadingInterpolation(blueshootload.getHeading(), bluehumanintake.getHeading());

        dametime = new Path (new BezierLine(bluehumanintake, blueshoottres));
        dametime.setConstantHeadingInterpolation(Math.toRadians(114));

        end = new Path (new BezierLine(blueshoottres, bluegametime));
        end.setLinearHeadingInterpolation(blueshoottres.getHeading(), bluegametime.getHeading());
    }
    public void initialize() {
        buildPaths();
        bot = new Robot(hardwareMap, telemetry,startpos, 20);
        schedule (
                new RunCommand(bot :: far_autonomous_robot_update_blue),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepre1,0.85)
                                .andThen(
                                        new FollowPath(bot.getFollower(), scorepre1)
                                                .alongWith(new Prep_Shooter_far_blue(bot))
                                )
                                .andThen(
                                        new WaitCommand(4000  )
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakeuno)
                                                .alongWith(new Shooter_off(bot))
                                                .alongWith(new Intake_on_forever(bot))
                                                .alongWith(new WaitCommand(2000))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), dametime)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith(new Prep_Shooter_far_blue(bot))
                                )
                                .andThen(
                                        new WaitCommand(4000)
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
