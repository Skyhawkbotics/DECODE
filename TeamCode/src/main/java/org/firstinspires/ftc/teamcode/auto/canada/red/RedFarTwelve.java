package org.firstinspires.ftc.teamcode.auto.canada.red;

import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_Command;
import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_On_Toggle;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_on_15;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "This Red far 15", group = "red")
public class RedFarTwelve extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(0));

    public static final Pose redstartpos = startpos.mirror();

    public static final Pose shotpos = new Pose(40.5, 8, Math.toRadians(0));

    public static final Pose redshotpos = shotpos.mirror();

    public final Pose farintakespikestart = new Pose (39.65, 31, Math.toRadians(0));

    public final Pose redfarintakespikestart = farintakespikestart.mirror();

    public final Pose spikebeziercurve = new Pose (55.2747, 29.575);

    public final Pose redspikebeziercurve = spikebeziercurve.mirror();
    //control point 55.2747, 29.575
    //at the start of the spike mark
    public final Pose farintakespikeend = new Pose (6, 31, Math.toRadians(0));
    //at the end of the spike mark

    public final Pose redfarintakespikeend = farintakespikeend.mirror();

    public final Pose humanintake = new Pose(14, 8, Math.toRadians(0));

    public final Pose redhumanintake = humanintake.mirror();

    public final Pose humanwiggle = new Pose (17,8, Math.toRadians(0));

    public final Pose redhumanwiggle = humanwiggle.mirror();

    public final Pose intakewiggle = new Pose (12,8, Math.toRadians(0));

    public final Pose redintakewiggle = intakewiggle.mirror();


    public final Pose park = new Pose(31, 8, Math.toRadians(0));

    public final Pose redpark = park.mirror();
    private PathChain rshotone, rthirdspike, rshottwo, rhintake, rshotthree,rend;
    //hintake = humanintake
    public void buildPaths () {
        rshotone = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(redstartpos,redshotpos)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rthirdspike = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                redshotpos,
                                redspikebeziercurve,
                                redfarintakespikestart
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))


                .addPath(
                        new BezierLine(redfarintakespikestart,redfarintakespikeend)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rshottwo = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(redfarintakespikeend, redshotpos)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rhintake = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(redshotpos, redhumanintake)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        new BezierLine(redhumanintake,redhumanwiggle)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        new BezierLine(redhumanwiggle, redintakewiggle)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rshotthree = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(redintakewiggle, redshotpos)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rend = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(redshotpos, redpark)
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public void init_loop() {
        bot.getTurret().set_using_LL(24); /// NO limelight?
        bot.getTurret().turret_update(24, bot.getFollower().getPose(), 0, 1);
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startpos, 24);
        buildPaths();
        schedule (
                new RunCommand(bot :: far_red_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), rshotone, 0.6)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1504, 0.35)
                                )
                                .andThen(
                                        new WaitCommand(750)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rthirdspike)
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rshottwo)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                ),
                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), rhintake)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),
                                new WaitCommand(3000)
                        )
                                .andThen(
                                        new FollowPath(bot.getFollower(), rshotthree)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(800)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                ),
                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), rhintake)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),
                                new WaitCommand(3000)
                        )

                                .andThen(
                                        new FollowPath(bot.getFollower(), rshotthree)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                ),
                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), rhintake)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),
                                new WaitCommand(3000)
                        )

                                .andThen(
                                        new FollowPath(bot.getFollower(), rshotthree)
                                )
                                .andThen(
                                        new WaitCommand(700)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(700)
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
