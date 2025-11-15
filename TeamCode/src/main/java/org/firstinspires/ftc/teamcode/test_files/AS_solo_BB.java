package org.firstinspires.ftc.teamcode.test_files;

import static org.firstinspires.ftc.teamcode.core.constants.blue_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "solo blue far", group = "gf")
public class AS_solo_BB extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose (46.23, 8.26, Math.toRadians(90));
    public final Pose presets = new Pose (46.033, 9.246, Math.toRadians(105));
    public final Pose pos4gat1 = new Pose (45.836, 35.41, Math.toRadians(180));
    public final Pose gather1 = new Pose (19.08, 35.41, Math.toRadians(180));
    public final Pose launch1 = new Pose (57.64, 8.26, Math.toRadians(115));
    public final Pose pos4gat2 = new Pose (51.93, 59.02, Math.toRadians(180));
    public final Pose gather2 = new Pose (19.475, 59.41, Math.toRadians(180));
    public final Pose launch2 = new Pose (57.24, 83.803, Math.toRadians(140));

    private Path scorepres, getready1, load1, score1, getready2, load2, score2;
    public void buildPaths (){
        scorepres = new Path (new BezierLine(startpose,presets));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(),presets.getHeading());///set delay

        getready1 = new Path (new BezierLine(presets,pos4gat1));
        getready1.setLinearHeadingInterpolation(presets.getHeading(),pos4gat1.getHeading());

        load1 = new Path (new BezierLine(pos4gat1, gather1));
        load1.setConstantHeadingInterpolation(180);

        score1 = new Path (new BezierLine(gather1, launch1));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), launch1.getHeading());///set delay

        getready2 = new Path (new BezierLine(launch1,pos4gat2));
        getready2.setLinearHeadingInterpolation(launch1.getHeading(), pos4gat2.getHeading());

        load2 = new Path (new BezierLine(pos4gat2, gather2));
        load2.setConstantHeadingInterpolation(180);

        score2 = new Path (new BezierLine(gather2, launch2));
        score2.setLinearHeadingInterpolation(gather2.getHeading(), launch2.getHeading());
    }
    public void initialize (){
        buildPaths();
        bot = new Robot(hardwareMap,telemetry, blue_ID, startpose);
        bot.getFollower().setStartingPose(startpose);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepres)
                )
                        .andThen(
                                new WaitCommand(7000)
                        )
                        .andThen(
                                new FollowPath(bot.getFollower(),getready1)
                        )
                        .andThen(
                                new FollowPath(bot.getFollower(),load1)
                        )
                        .andThen(
                                new FollowPath(bot.getFollower(), score1)
                        )
                        .andThen(
                                new WaitCommand(7000)
                        )
                        .andThen(
                                new FollowPath(bot.getFollower(),getready2)
                        )
                        .andThen(
                                new FollowPath(bot.getFollower(), load2)
                        )
                        .andThen(
                                new FollowPath(bot.getFollower(), score2)
                        )
                        .andThen(
                                new WaitCommand(7000)
                        )
        );
    }
}