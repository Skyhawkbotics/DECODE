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

@Autonomous(name = "TheyWantTheShooter_Red", group = "red")
public class RED_IsaidTheyWantTheShooter extends OpModeCommand {

    Robot bot;

    public static final Pose starttime = new Pose(56, 7.5, Math.toRadians(90));

    public static final Pose redstarttime = starttime.mirror();

    public final Pose treballuno = new Pose(56, 17, Math.toRadians(114));

    public final Pose redtreballuno = treballuno.mirror();

    public final Pose dimeuno = new Pose(10.8, 7.5);

    public final Pose reddimeuno = dimeuno.mirror();

    public final Pose treballdos = new Pose(56, 17, Math.toRadians(114));

    public final Pose redtreballdos = treballdos.mirror();

    public final Pose dimedos = new Pose(10.8, 7.5);

    public final Pose reddimedos = dimedos.mirror();

    public final Pose treballtres = new Pose(56, 17, Math.toRadians(114));

    public final Pose redtreballtres = treballtres.mirror();

    public final Pose dimetres = new Pose(10.8, 7.5);

    public final Pose reddimetres = dimetres.mirror();

    public final Pose dametime = new Pose(56, 17, Math.toRadians(114));

    public final Pose reddametime = dametime.mirror();

    public final Pose cellytime = new Pose(42.2, 16.3);

    public final Pose redcellytime = cellytime.mirror();

    private Path greengiantuno, intakeuno, greengiantdos, intakedos, greengianttres, intaketres, buzzerbeater, celly;

    public void buildPaths() {
        greengiantuno = new Path(new BezierLine(redstarttime, redtreballuno));
        greengiantuno.setLinearHeadingInterpolation(redstarttime.getHeading(), redtreballuno.getHeading());

        intakeuno = new Path(new BezierCurve(redtreballuno, new Pose(30.8, 10.2), reddimeuno));
        //intakeuno.setConstantHeadingInterpolation(Math.toRadians(180));
        intakeuno.setLinearHeadingInterpolation(redtreballuno.getHeading(), reddimeuno.getHeading());

        greengiantdos = new Path(new BezierLine(reddimeuno, redtreballdos));
        greengiantdos.setLinearHeadingInterpolation(reddimeuno.getHeading(), redtreballdos.getHeading());

        intakedos = new Path(new BezierCurve(redtreballdos, new Pose(30.8, 10.2), reddimedos));
        //  intakedos.setConstantHeadingInterpolation(Math.toRadians(180));
        intakedos.setLinearHeadingInterpolation(redtreballdos.getHeading(), reddimedos.getHeading());

        greengianttres = new Path(new BezierLine(reddimedos, redtreballtres));
        greengianttres.setLinearHeadingInterpolation(reddimedos.getHeading(), redtreballtres.getHeading());

        intaketres = new Path(new BezierCurve(redtreballtres, new Pose(30.8, 10.2), reddimetres));
        // intaketres.setConstantHeadingInterpolation(Math.toRadians(180));
        intaketres.setLinearHeadingInterpolation(redtreballtres.getHeading(), reddimetres.getHeading());

        buzzerbeater = new Path(new BezierLine(reddimetres, reddametime));
        buzzerbeater.setLinearHeadingInterpolation(reddimetres.getHeading(), reddametime.getHeading());

        celly = new Path(new BezierLine(reddametime, redcellytime));
        celly.setLinearHeadingInterpolation(reddametime.getHeading(), redcellytime.getHeading());
    }
    @Override
    public void init_loop() {
        bot.getTurret().set_using_LL(24);
        bot.getTurret().turret_update(24, bot.getFollower().getPose(), 0, 0);
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redstarttime, 24);
        buildPaths();

        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), greengiantuno, 0.85)
                                .alongWith(new Prep_Shooter_any(bot, 1680, 0.49))
                                .andThen(
                                        new WaitCommand(500)
                                                )
                                                .andThen(
                                                        new Intake_on(bot)
                                                )
                                                .andThen(
                                                        new Shooter_off(bot))

                                                .andThen(
                                                        new FollowPath(bot.getFollower(), intakeuno)
                                                                .alongWith (new Intake_on_forever(bot))

                                                )
                                                .andThen(
                                                        new WaitCommand(300))

                                                .andThen(
                                                        new Intake_off(bot))

                                                .andThen(
                                                        new Prep_Shooter_any(bot, 1680, .49))

                                                .andThen(
                                                        new FollowPath(bot.getFollower(), greengiantdos)
                                                                )
                                                              )
                                                .andThen(
                                                        new WaitCommand(2000)
                                                )
                                                .andThen(
                                                        new Intake_on(bot)
                                                )
                                                .andThen(
                                                        new Shooter_off(bot)
                                                )

                                                .andThen(
                                                        new FollowPath(bot.getFollower(), intakedos)
                                                )

                                                .andThen(
                                                        new Intake_off(bot)
                                                )
                                                .andThen(
                                                        new WaitCommand(2000)
                                                )
                                                .andThen(
                                                        new Intake_on(bot)
                                                )
                                                .andThen(
                                                        new FollowPath(bot.getFollower(), greengianttres)
                                                        .alongWith(new Prep_Shooter_any(bot, 1680,4.9))
                                                )
                                                .andThen (
                                                        new WaitCommand(200))
                                                .andThen(
                                                        new Intake_on(bot)
                                                )
                                                .andThen(
                                                        new Shooter_off(bot)
                                                )
                                                .andThen(
                                                        new FollowPath(bot.getFollower(), intaketres)
                                                )
                                                .andThen(
                                                        new WaitCommand(2000)
                                                )
                                                .andThen(
                                                        new Intake_off(bot)
                                                )

                                                .andThen(
                                                        new FollowPath(bot.getFollower(), buzzerbeater)
                                                        .alongWith(new Prep_Shooter_any(bot, 1680,4.9)
                                                        )
                                                )
                                                .andThen(
                                                        new WaitCommand(2000)
                                                )
                                                .andThen(
                                                        new Intake_on(bot)
                                                )
                                                .andThen(
                                                        new Intake_off(bot)
                                                )
                                                .andThen(new FollowPath(bot.getFollower(), celly)
                                                )


        );
    }
}
