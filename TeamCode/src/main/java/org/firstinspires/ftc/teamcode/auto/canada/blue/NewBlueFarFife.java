package org.firstinspires.ftc.teamcode.auto.canada.blue;

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

@Autonomous(name = "This BLUE far 15", group = "blue")
public class NewBlueFarFife extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(180));

    public static final Pose shotpos = new Pose(40.5, 8, Math.toRadians(180));

    public final Pose farintakespikestart = new Pose (39.65, 31, Math.toRadians(180));

    public final Pose spikebeziercurve = new Pose (55.2747, 29.575);
    //control point 55.2747, 29.575
    //at the start of the spike mark
    public final Pose farintakespikeend = new Pose (6, 31, Math.toRadians(180));
    //at the end of the spike mark

    public final Pose humanintake = new Pose(14, 8, Math.toRadians(180));

    public final Pose humanwiggle = new Pose (17,8, Math.toRadians(180));

    public final Pose intakewiggle = new Pose (10,8, Math.toRadians(180));


    public final Pose park = new Pose(31, 8, Math.toRadians(180));
    private PathChain shotone, thirdspike, shottwo, hintake, shotthree,end;
    //hintake = humanintake
    public void buildPaths () {
        shotone = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(startpos,shotpos)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        thirdspike = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                shotpos,
                                spikebeziercurve,
                                farintakespikestart
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))


                .addPath(
                        new BezierLine(farintakespikestart,farintakespikeend)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        shottwo = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(farintakespikeend, shotpos)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        hintake = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(shotpos, humanintake)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierLine(humanintake,humanwiggle)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierLine(humanwiggle, intakewiggle)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        shotthree = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(intakewiggle, shotpos)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        end = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(shotpos, park)
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public void init_loop() {
        bot.getTurret().set_using_LL(20); /// NO limelight?
        bot.getTurret().turret_update(20, bot.getFollower().getPose(), 0, 1);
    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startpos, 20);
        buildPaths();
        schedule (
                new RunCommand(bot :: far_blue_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), shotone, 0.6)
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
                                        new FollowPath(bot.getFollower(), thirdspike)
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), shottwo)
                                )
                                .andThen(
                                        new WaitCommand(600)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(600)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                ),
                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), hintake)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),
                                new WaitCommand(3000)
                        )
                                .andThen(
                                        new FollowPath(bot.getFollower(), shotthree)
                                )
                                .andThen(
                                        new WaitCommand(600)
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
                                new FollowPath(bot.getFollower(), hintake)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),
                                new WaitCommand(3000)
                        )

                                .andThen(
                                        new FollowPath(bot.getFollower(), shotthree)
                                )
                                .andThen(
                                        new WaitCommand(600)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(600)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                ),
                        new ParallelRaceGroup(
                                new FollowPath(bot.getFollower(), hintake)
                                        .alongWith(
                                                new Intake_Command(bot)
                                        ),
                                new WaitCommand(3000)
                        )

                                .andThen(
                                        new FollowPath(bot.getFollower(), shotthree)
                                )
                                .andThen(
                                        new WaitCommand(600)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new WaitCommand(600)
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
