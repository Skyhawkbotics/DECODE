package org.firstinspires.ftc.teamcode.auto.canada;

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

@Autonomous(name = "RED far 6", group = "red")
public class NewRedFarSix extends OpModeCommand {

    Robot bot;

    public static final Pose startpos = new Pose(56, 8, Math.toRadians(180));
    public static final Pose redstartpos = startpos.mirror();

    public static final Pose shotone = new Pose(56, 8, Math.toRadians(180));
    public static final Pose redshotone = shotone.mirror();

    public final Pose humanintake = new Pose(12, 8, Math.toRadians(180));

    public final Pose redhumanintake = humanintake.mirror();
    public final Pose treshot = new Pose(56, 8, Math.toRadians(180));
    public final Pose redtreshot = treshot.mirror();

    public final Pose park = new Pose(36, 8, Math.toRadians(180));
    public final Pose redpark = park.mirror();
    private Path shootone, intakeone, dametime, end;

    public void buildPaths () {
        shootone = new Path (new BezierLine(redstartpos,redshotone));
        shootone.setLinearHeadingInterpolation(redstartpos.getHeading(), redshotone.getHeading());

        intakeone = new Path (new BezierCurve(redshotone,redhumanintake));
        intakeone.setLinearHeadingInterpolation(redshotone.getHeading(), redhumanintake.getHeading());

        dametime = new Path (new BezierLine(redhumanintake, redtreshot));
        dametime.setLinearHeadingInterpolation(redtreshot.getHeading(), redtreshot.getHeading());

        end = new Path (new BezierLine(redtreshot, redpark));
        end.setLinearHeadingInterpolation(redtreshot.getHeading(), redpark.getHeading());

    }
    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redstartpos, 24);
        buildPaths();
        schedule (
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), shootone, 0.8)
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
                                        new FollowPath(bot.getFollower(), intakeone)
                                )
                                .andThen(
                                        new WaitCommand(2500)
                                )
                                .andThen(
                                        new Intake_on_15(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),dametime)
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
