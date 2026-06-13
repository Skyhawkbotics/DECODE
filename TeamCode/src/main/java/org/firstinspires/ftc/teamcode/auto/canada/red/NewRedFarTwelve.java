package org.firstinspires.ftc.teamcode.auto.canada.red;

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

@Autonomous(name = "RED far 12", group = "red")
public class NewRedFarTwelve extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(180));

    public static final Pose redstartpos = startpos.mirror();//new Pose(88,8, Math.toRadians(180));

    public static final Pose shotone = new Pose(40.5, 8, Math.toRadians(180));

    public static final Pose redshotone = shotone.mirror();//new Pose(103, 8, Math.toRadians(180));

    public final Pose prehumanintakeone = new Pose (7,8, Math.toRadians(180));
    //i split the intake to the human into two, save time

    public final Pose redprehumanintakeone = prehumanintakeone.mirror();//new Pose(134, 8, Math.toRadians(180));
    public final Pose humanintakeone = new Pose(5.8, 8, Math.toRadians(180));

    public final Pose redhumanintakeone = humanintakeone.mirror();//new Pose(136.5, 8, Math.toRadians(180));//

    public final Pose humanintakewiggleo = new Pose (8,8, Math.toRadians(180));

    public final Pose redwiggleo = humanintakewiggleo.mirror();

    public final Pose humanintakewiggt = new Pose (5.8,8, Math.toRadians(180));

    public final Pose redwiggt = humanintakewiggt.mirror();

    public final Pose shottwo = new Pose(41, 8, Math.toRadians(180));

    public final Pose redshottwo = shottwo.mirror();//new Pose(103, 8, Math.toRadians(180));//shottwo.mirror();

    public final Pose farintakespikestart = new Pose (39.65, 35.898, Math.toRadians(180));

    public final Pose redfarintakespikestart = farintakespikestart.mirror();//new Pose(104.35, 35.898, Math.toRadians(180));//

    public final Pose spikebeziercurve = new Pose (55.2747, 29.575);
    //control point 55.2747, 29.575
    //at the start of the spike mark

    public final Pose redspikebeziercurve = spikebeziercurve.mirror();// new Pose(88.7253, 29.575, Math.toRadians(180));//spikebeziercurve.mirror();

    public final Pose farintakespikeend = new Pose (13, 35.898, Math.toRadians(180));
    //at the end of the spike mark

    public final Pose redfarintakespikeend = farintakespikeend.mirror();//ew Pose(131, 35.898, Math.toRadians(180));
    public final Pose shotthree = new Pose (40.5,8, Math.toRadians(180));

    public final Pose redshotthree = shotthree.mirror();//new Pose(103, 8, Math.toRadians(180)); //

    public final Pose prehumanintaketwo = new Pose (8,8, Math.toRadians(180));

    public final Pose redprehumanintaketwo = prehumanintaketwo.mirror();//new Pose(103, 8, Math.toRadians(180));

    public final Pose humanintaketwo = new Pose (5.8,8, Math.toRadians(180));

    public final Pose redhumanintaketwo = humanintaketwo.mirror();//new Pose(136.5, 8, Math.toRadians(180));

    public final Pose humanintakewiggle = new Pose (7,8, Math.toRadians(180));

    public final Pose rediggle = humanintakewiggle.mirror();

    public final Pose humanintakewigg = new Pose (5.8,8, Math.toRadians(180));

    public final Pose redwigglet = humanintakewigg.mirror();

    public final Pose shotfour = new Pose (40.5,8, Math.toRadians(180));

    public final Pose redshotfour = shotfour.mirror();//new Pose(103, 8, Math.toRadians(180));

    public final Pose park = new Pose(31, 8, Math.toRadians(180));

    public final Pose redpark = park.mirror();//new Pose(113, 8, Math.toRadians(180));

    private Path rshootone, rprehintakeone, rhintakeone, rwiggleone, rwiggletwo, rshoottwo, rprefarintakespike, rfarintakespike, rshootthree, rprehintaketwo, rhintaketwo, rwiggletre, rwigglefour, rdametime, rend;
    //hintake = humanintake
    public void buildPaths () {
        rshootone = new Path (new BezierLine(redstartpos,redshotone));
        rshootone.setConstantHeadingInterpolation(Math.toRadians(0));

        rprehintakeone = new Path (new BezierLine(redshotone,redprehumanintakeone));
        rprehintakeone.setConstantHeadingInterpolation(Math.toRadians(0));

        rhintakeone = new Path (new BezierLine(redprehumanintakeone, redhumanintakeone));
        rhintakeone.setConstantHeadingInterpolation(Math.toRadians(0));

        rwiggleone = new Path (new BezierLine(redhumanintakeone,redwiggleo));
        rwiggleone.setConstantHeadingInterpolation(Math.toRadians(0));

        rwiggletwo = new Path (new BezierLine(redwiggleo, redwiggt));
        rwiggletwo.setConstantHeadingInterpolation(Math.toRadians(0));

        rshoottwo = new Path (new BezierLine(redwiggt, redshottwo));
        rshoottwo.setConstantHeadingInterpolation(Math.toRadians(0));

        rprefarintakespike = new Path (new BezierCurve(redshottwo, redspikebeziercurve,redfarintakespikestart));
        rprefarintakespike.setConstantHeadingInterpolation(Math.toRadians(0));

        rfarintakespike = new Path (new BezierLine(redfarintakespikestart, redfarintakespikeend));
        rfarintakespike.setConstantHeadingInterpolation(Math.toRadians(0));

        rshootthree = new Path (new BezierLine(redfarintakespikeend, redshotthree));
        rshootthree.setConstantHeadingInterpolation(Math.toRadians(0));

        rprehintaketwo = new Path (new BezierLine(redshotthree, redprehumanintaketwo));
        rprehintaketwo.setConstantHeadingInterpolation(Math.toRadians(0));

        rhintaketwo = new Path (new BezierLine(redprehumanintaketwo, redhumanintaketwo));
        rhintaketwo.setConstantHeadingInterpolation(Math.toRadians(0));

        rwiggletre = new Path (new BezierLine(redhumanintaketwo, rediggle));
        rwiggletre.setConstantHeadingInterpolation(Math.toRadians(0));

        rwigglefour = new Path (new BezierLine(rediggle,redwigglet));
        rwigglefour.setConstantHeadingInterpolation(Math.toRadians(0));

        rdametime = new Path (new BezierLine(redwigglet, redshotfour));
        rdametime.setConstantHeadingInterpolation(Math.toRadians(0));

        rend = new Path (new BezierLine(redshotfour, redpark));
        rend.setConstantHeadingInterpolation(Math.toRadians(0));

    }


    public void init_loop() {
        bot.getTurret().set_using_LL(24, false); /// NO limelight?
        bot.getTurret().turret_update(24, bot.getFollower().getPose(), 0, 0);
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redstartpos, 24);
        buildPaths();
        schedule (
                new RunCommand(bot :: far_red_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), rshootone, 0.75)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1534, 0.35)
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
                                        new WaitCommand(100)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rhintakeone)
                                )
                                .andThen(
                                        new WaitCommand(100)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), rwiggleone)
                                )
                                .andThen(
                                        new WaitCommand(100)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), rwiggletwo)
                                )
                                .andThen(
                                        new WaitCommand(1450)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rshoottwo)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(1700)
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
                                        new WaitCommand(30)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rhintaketwo)
                                )
                                .andThen(
                                        new WaitCommand(30)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), rwiggletre)
                                )
                                .andThen(
                                        new WaitCommand(30)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), rwigglefour)
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
