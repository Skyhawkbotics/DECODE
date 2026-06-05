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
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_On_Toggle;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_on_15;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "blue far 12", group = "blue")
public class NewBlueFarTwelve extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(180));

    public static final Pose shotone = new Pose(43, 8, Math.toRadians(180));

    public final Pose prehumanintakeone = new Pose (15,8, Math.toRadians(180));
//i split the intake to the human into two, save time
    public final Pose humanintakeone = new Pose(8, 8, Math.toRadians(180));

    public final Pose shottwo = new Pose(43, 8, Math.toRadians(180));

    public final Pose farintakespikestart = new Pose (39.65, 35.898, Math.toRadians(180));

    public final Pose spikebeziercurve = new Pose (55.2747, 29.575);
    //control point 55.2747, 29.575
    //at the start of the spike mark

    public final Pose farintakespikeend = new Pose (13, 35.898, Math.toRadians(180));
    //at the end of the spike mark
    public final Pose shotthree = new Pose (43,8, Math.toRadians(180));

    public final Pose prehumanintaketwo = new Pose (15,8, Math.toRadians(180));

    public final Pose humanintaketwo = new Pose (8,8, Math.toRadians(180));

    public final Pose shotfour = new Pose (43,8, Math.toRadians(180));

    public final Pose park = new Pose(27, 8, Math.toRadians(180));

    private Path shootone, prehintakeone, hintakeone, shoottwo, prefarintakespike, farintakespike, shootthree, prehintaketwo, hintaketwo, dametime, end;
//hintake = humanintake
    public void buildPaths () {
        shootone = new Path (new BezierLine(startpos,shotone));
        shootone.setConstantHeadingInterpolation(Math.toRadians(180));

        prehintakeone = new Path (new BezierCurve(shotone,prehumanintakeone));
        prehintakeone.setConstantHeadingInterpolation(Math.toRadians(180));

        hintakeone = new Path (new BezierLine(prehumanintakeone, humanintakeone));
        hintakeone.setConstantHeadingInterpolation(Math.toRadians(180));

        shoottwo = new Path (new BezierLine(humanintakeone, shottwo));
        shoottwo.setConstantHeadingInterpolation(Math.toRadians(180));

        prefarintakespike = new Path (new BezierCurve(shottwo, spikebeziercurve,farintakespikestart));
        prefarintakespike.setConstantHeadingInterpolation(Math.toRadians(180));

        farintakespike = new Path (new BezierLine(farintakespikestart, farintakespikeend));
        farintakespike.setConstantHeadingInterpolation(Math.toRadians(180));

        shootthree = new Path (new BezierLine(farintakespikeend, shotthree));
        shootthree.setConstantHeadingInterpolation(Math.toRadians(180));

        prehintaketwo = new Path (new BezierLine(shotthree, prehumanintaketwo));
        prehintaketwo.setConstantHeadingInterpolation(Math.toRadians(180));

        hintaketwo = new Path (new BezierLine(prehumanintaketwo, humanintaketwo));
        hintaketwo.setConstantHeadingInterpolation(Math.toRadians(180));

        dametime = new Path (new BezierLine(humanintaketwo, shotfour));
        dametime.setConstantHeadingInterpolation(Math.toRadians(180));

        end = new Path (new BezierLine(shotfour, park));
        end.setConstantHeadingInterpolation(Math.toRadians(180));

    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startpos, 20);
        buildPaths();
        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), shootone, 0.7)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1535, 0.35)
                                )
                                .andThen(
                                        new WaitCommand(600)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(2300)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), prehintakeone)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), hintakeone)
                                )
                                .andThen(
                                        new WaitCommand(1500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), shoottwo)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(2400)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), prefarintakespike)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),farintakespike)
                                )
                                .andThen (
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),shootthree)
                                )
                                .andThen (
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(2400)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), prehintaketwo)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), hintaketwo)
                                )
                                .andThen(
                                        new WaitCommand(1500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), dametime)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(2400)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), end)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith (new Shooter_off(bot))
                                )
                )
        );
    }
}
