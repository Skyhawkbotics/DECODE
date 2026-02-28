package org.firstinspires.ftc.teamcode.auto.State_Auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
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

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "TheyWantTheShooter", group = "blue")
public class BLUE_IsaidTheyWantTheShooter extends OpModeCommand {

    Robot bot;

    public static final Pose starttime = new Pose(56, 8, Math.toRadians(90));

    public final Pose treballuno = new Pose(56, 17, Math.toRadians(114));

    public final Pose dimeuno = new Pose(10.8, 9.5);

    public final Pose treballdos = new Pose(56, 17, Math.toRadians(114));

    public final Pose dimedos = new Pose(10.8, 9.5);

    public final Pose treballtres = new Pose(56, 17, Math.toRadians(114));

    public final Pose dimetres = new Pose(10.8, 9.5);

    public final Pose dametime = new Pose(56, 17, Math.toRadians(114));

    public final Pose cellytime = new Pose(42.2, 16.3);

    private Path greengiantuno, intakeuno, greengiantdos, intakedos, greengianttres, intaketres, buzzerbeater, celly;

    public void buildPaths() {
        greengiantuno = new Path(new BezierLine(starttime, treballuno));
        greengiantuno.setLinearHeadingInterpolation(starttime.getHeading(), treballuno.getHeading());

        intakeuno = new Path(new BezierCurve(treballuno, new Pose(41.2, 10.2), dimeuno));
        intakeuno.setConstantHeadingInterpolation(0);

        greengiantdos = new Path(new BezierLine(dimeuno, treballdos));
        greengiantdos.setLinearHeadingInterpolation(dimeuno.getHeading(), treballdos.getHeading());

        intakedos = new Path(new BezierCurve(treballdos, new Pose(41.2, 10.2), dimedos));
        intakedos.setConstantHeadingInterpolation(0);

        greengianttres = new Path(new BezierLine(dimedos, treballtres));
        greengianttres.setLinearHeadingInterpolation(dimedos.getHeading(), treballtres.getHeading());

        intaketres = new Path(new BezierCurve(treballtres, new Pose(41.2, 10.2), dimetres));
        intaketres.setConstantHeadingInterpolation(0);

        buzzerbeater = new Path(new BezierLine(dimetres, dametime));
        buzzerbeater.setLinearHeadingInterpolation(dimetres.getHeading(), dametime.getHeading());

        celly = new Path(new BezierLine(dametime, cellytime));
        celly.setLinearHeadingInterpolation(dametime.getHeading(), cellytime.getHeading());
    }
    @Override
    public void init_loop() {
        bot.getTurret().set_using_LL(20);
        bot.getTurret().turret_update(20, bot.getFollower().getPose(), 0);
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, starttime, 20);
        buildPaths();

        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), greengiantuno, 0.85)
                                .andThen(
                                        new FollowPath(bot.getFollower(), greengiantuno)
                                                .alongWith(new Prep_Shooter_any(bot, 1600, 0.49))
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Intake_on_forever(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intakeuno),
                                        new WaitCommand(2000)
                                .andThen(
                                        new FollowPath(bot.getFollower(), greengiantdos),
                                        new WaitCommand(2000)
                                )
                                .andThen(

                                        )      ,

                                        new FollowPath(bot.getFollower(), intakedos),
                                        new WaitCommand(2000),

                                        new FollowPath(bot.getFollower(), greengianttres),
                                        new WaitCommand(2000),

                                        new FollowPath(bot.getFollower(), intaketres),
                                        new WaitCommand(2000),

                                        new FollowPath(bot.getFollower(), buzzerbeater),
                                        new WaitCommand(2000),
                                        new Intake_off(bot),
                                        new Shooter_off(bot),

                                        new FollowPath(bot.getFollower(), celly),
                                        new WaitCommand(2000)




                                        )
                )
                )
        );
    }
}
