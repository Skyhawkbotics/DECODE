package org.firstinspires.ftc.teamcode.test_files;
import static org.firstinspires.ftc.teamcode.core.constants.blue_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "assist blue far", group = "gf")
@Disabled
public class Ahana_Blue_Side_Assist extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose (46.23, 8.26, Math.toRadians(90));
    public final Pose presets = new Pose (46.03, 9.245, Math.toRadians(110));
    public final Pose pos4op = new Pose (46.296, 71.55, Math.toRadians(180));
    public final Pose opengate = new Pose (16.53, 70.95, Math.toRadians(180));
    public final Pose retract = new Pose (51.71, 71.25, Math.toRadians(180));
    public final Pose pos4gat = new Pose(52.008, 35.173, Math.toRadians(180));
    public final Pose gather1 = new Pose (18.64, 34.87, Math.toRadians(180));
    public final Pose shoot1= new Pose(59.52, 11.72, Math.toRadians(115));
    public final Pose gather2 = new Pose (13.83, 10.82, Math.toRadians(180));
    public final Pose shoot2 = new Pose (59.82, 11.42, Math.toRadians(115));

    private Path scorepre, gategetready, gatemustgo, getaway, getready1, load1, score1, load2, score2;

    public void buildPaths(){
        scorepre = new Path(new BezierLine(startpose,presets));
        scorepre.setLinearHeadingInterpolation(startpose.getHeading(), presets.getHeading()); ///seet delay

        gategetready = new Path (new BezierLine(presets,pos4op));
        gategetready.setLinearHeadingInterpolation(presets.getHeading(), pos4op.getHeading());

        gatemustgo = new Path (new BezierLine(pos4op, opengate));
        gatemustgo.setConstantHeadingInterpolation(180);///set slower

        getaway = new Path (new BezierLine(opengate,retract));
        getaway.setConstantHeadingInterpolation(180);

        getready1 = new Path (new BezierLine(retract,pos4gat));
        getready1.setConstantHeadingInterpolation(180);

        load1 = new Path (new BezierLine (pos4gat, gather1));
        load1.setConstantHeadingInterpolation(180);

        score1 = new Path (new BezierLine (gather1, shoot1));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), shoot1.getHeading());///set delay

        load2 = new Path (new BezierLine(shoot1, gather2));
        load2.setLinearHeadingInterpolation(shoot1.getHeading(), gather2.getHeading());///set delay

        score2 = new Path (new BezierLine(gather2, shoot2));
        score2.setLinearHeadingInterpolation(gather2.getHeading(), shoot2.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, blue_ID, startpose);
        bot.getFollower().setStartingPose(startpose);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(),scorepre
                        )
                                .andThen(
                                        new WaitCommand(7000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gategetready)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),gatemustgo)
                                )
                                .andThen(
                                        new WaitCommand(10000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), getaway)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),getready1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),load1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),score1)
                                )
                                .andThen(
                                        new WaitCommand(7000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),load2)
                                )
                                .andThen(
                                        new WaitCommand(7000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),score2)
                                )
                                .andThen(
                                        new WaitCommand(7000)
                                )

                )
        );
    }
}
