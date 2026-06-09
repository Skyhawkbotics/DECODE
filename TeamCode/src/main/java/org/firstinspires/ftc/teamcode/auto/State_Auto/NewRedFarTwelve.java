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

@Autonomous(name = "red far 12", group = "red")
public class NewRedFarTwelve extends OpModeCommand {

    Robot bot;

    //public static final Pose startpos = new Pose(56, 8, Math.toRadians(180));

    public static final Pose redstartpos = new Pose(88,8, Math.toRadians(180));//startpos.mirror();

    public static final Pose shotone = new Pose(41, 8, Math.toRadians(180));

    public static final Pose redshotone = new Pose(103, 8, Math.toRadians(180));//shotone.mirror();

    public final Pose prehumanintakeone = new Pose (14,8, Math.toRadians(180));
    //i split the intake to the human into two, save time

    public final Pose redprehumanintakeone = new Pose(134, 8, Math.toRadians(180));//prehumanintakeone.mirror();
    public final Pose humanintakeone = new Pose(6.5, 8, Math.toRadians(180));

    public final Pose redhumanintakeone = new Pose(137.5, 8, Math.toRadians(180));//humanintakeone.mirror();

    public final Pose shottwo = new Pose(41, 8, Math.toRadians(180));

    public final Pose redshottwo = new Pose(103, 8, Math.toRadians(180));//shottwo.mirror();

    public final Pose farintakespikestart = new Pose (39.65, 35.898, Math.toRadians(180));

    public final Pose redfarintakespikestart = new Pose(104.35, 35.898, Math.toRadians(180));//farintakespikestart.mirror();

    public final Pose spikebeziercurve = new Pose (55.2747, 29.575);
    //control point 55.2747, 29.575
    //at the start of the spike mark

    public final Pose redspikebeziercurve = new Pose(88.7253, 29.575, Math.toRadians(180));//spikebeziercurve.mirror();

    public final Pose farintakespikeend = new Pose (13, 35.898, Math.toRadians(180));
    //at the end of the spike mark

    public final Pose redfarintakespikeend = new Pose(131, 35.898, Math.toRadians(180));//farintakespikeend.mirror();
    public final Pose shotthree = new Pose (41,8, Math.toRadians(180));

    public final Pose redshotthree = new Pose(103, 8, Math.toRadians(180)); //shotthree.mirror();

    public final Pose prehumanintaketwo = new Pose (14,8, Math.toRadians(180));

    public final Pose redprehumanintaketwo = new Pose(103, 8, Math.toRadians(180));//prehumanintaketwo.mirror();

    public final Pose humanintaketwo = new Pose (6.5,8, Math.toRadians(180));

    public final Pose redhumanintaketwo = new Pose(137.5, 8, Math.toRadians(180));//humanintaketwo.mirror();

    public final Pose shotfour = new Pose (41,8, Math.toRadians(180));

    public final Pose redshotfour = new Pose(103, 8, Math.toRadians(180));//shotfour.mirror();

    public final Pose park = new Pose(31, 8, Math.toRadians(180));

    public final Pose redpark = new Pose(113, 8, Math.toRadians(180));//park.mirror();

    private Path rshootone, rprehintakeone, rhintakeone, rshoottwo, rprefarintakespike, rfarintakespike, rshootthree, rprehintaketwo, rhintaketwo, rdametime, rend;
    //hintake = humanintake
    public void buildPaths () {
        rshootone = new Path (new BezierLine(redstartpos,redshotone));
        rshootone.setConstantHeadingInterpolation(Math.toRadians(180));

        rprehintakeone = new Path (new BezierLine(redshotone,redprehumanintakeone));
        rprehintakeone.setConstantHeadingInterpolation(Math.toRadians(180));

        rhintakeone = new Path (new BezierLine(redprehumanintakeone, redhumanintakeone));
        rhintakeone.setConstantHeadingInterpolation(Math.toRadians(180));

        rshoottwo = new Path (new BezierLine(redhumanintakeone, redshottwo));
        rshoottwo.setConstantHeadingInterpolation(Math.toRadians(180));

        rprefarintakespike = new Path (new BezierCurve(redshottwo, redspikebeziercurve,redfarintakespikestart));
        rprefarintakespike.setConstantHeadingInterpolation(Math.toRadians(180));

        rfarintakespike = new Path (new BezierLine(redfarintakespikestart, redfarintakespikeend));
        rfarintakespike.setConstantHeadingInterpolation(Math.toRadians(180));

        rshootthree = new Path (new BezierLine(redfarintakespikeend, redshotthree));
        rshootthree.setConstantHeadingInterpolation(Math.toRadians(180));

        rprehintaketwo = new Path (new BezierLine(redshotthree, redprehumanintaketwo));
        rprehintaketwo.setConstantHeadingInterpolation(Math.toRadians(180));

        rhintaketwo = new Path (new BezierLine(redprehumanintaketwo, redhumanintaketwo));
        rhintaketwo.setConstantHeadingInterpolation(Math.toRadians(180));

        rdametime = new Path (new BezierLine(redhumanintaketwo, redshotfour));
        rdametime.setConstantHeadingInterpolation(Math.toRadians(180));

        rend = new Path (new BezierLine(redshotfour, redpark));
        rend.setConstantHeadingInterpolation(Math.toRadians(180));

    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redstartpos, 24);
        buildPaths();
        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), rshootone, 0.8)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1540, 0.35)
                                )
                                .andThen(
                                        new WaitCommand(650)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rprehintakeone)
                                )
                                .andThen(
                                        new WaitCommand(150)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rhintakeone)
                                )
                                .andThen(
                                        new WaitCommand(2000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rshoottwo)
                                )
                                .andThen(
                                        new WaitCommand(650)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(1800)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rprefarintakespike)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),rfarintakespike)
                                )
                                .andThen (
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),rshootthree)
                                )
                                .andThen (
                                        new WaitCommand(900)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(2000)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rprehintaketwo)
                                )
                                .andThen(
                                        new WaitCommand(250)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rhintaketwo)
                                )
                                .andThen(
                                        new WaitCommand(1200)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rdametime)
                                )
                                .andThen(
                                        new WaitCommand(800)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rend)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith (new Shooter_off(bot))
                                )
                )
        );
    }
}
