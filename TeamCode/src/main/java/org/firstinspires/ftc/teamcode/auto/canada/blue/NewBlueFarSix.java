package org.firstinspires.ftc.teamcode.auto.canada.blue;

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

@Autonomous(name = "BLUE FAR 6", group = "blue")
public class NewBlueFarSix extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(180));

    public static final Pose shotone = new Pose(56, 8, Math.toRadians(180));

    public final Pose humanintake = new Pose(12, 8, Math.toRadians(180));

    public final Pose treshot = new Pose(56, 8, Math.toRadians(180));

    public final Pose park = new Pose(36, 8, Math.toRadians(180));

    private Path bshootone, bintakeone, bdametime, bend;

    public void buildPaths () {
        bshootone = new Path (new BezierLine(startpos,shotone));
        bshootone.setLinearHeadingInterpolation(startpos.getHeading(), shotone.getHeading());

        bintakeone = new Path (new BezierCurve(shotone,humanintake));
        bintakeone.setLinearHeadingInterpolation(shotone.getHeading(), humanintake.getHeading());

        bdametime = new Path (new BezierLine(humanintake, treshot));
        bdametime.setLinearHeadingInterpolation(treshot.getHeading(), treshot.getHeading());

        bend = new Path (new BezierLine(treshot, park));
        bend.setLinearHeadingInterpolation(treshot.getHeading(), park.getHeading());

    }

    public void init_loop() {
        bot.getTurret().set_using_LL(20, false); /// NO limelight?
        bot.getTurret().turret_update(20, bot.getFollower().getPose(), 0, 2);
    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startpos, 20);
        buildPaths();
        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), bshootone, 0.8)
                                .alongWith(
                                        new Prep_Shooter_any(bot, 1600, 0.35)
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new Intake_On_Toggle(bot)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), bintakeone)
                                )
                                .andThen(
                                        new WaitCommand(2500)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),bdametime)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), bend)
                                                .alongWith(new Intake_off(bot))
                                                .alongWith (new Shooter_off(bot))
                                )
                )
        );
    }
}
