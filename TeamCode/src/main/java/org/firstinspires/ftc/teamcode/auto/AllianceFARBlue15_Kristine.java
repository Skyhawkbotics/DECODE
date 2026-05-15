package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Intake_on_15;
import org.firstinspires.ftc.teamcode.core.commands.fiveteen.Score_Command;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "KristineFar12", group = "auto")
public class AllianceFARBlue15_Kristine extends OpModeCommand {

    Robot bot;

    public final Pose startPose = new Pose(56, 10, Math.toRadians(180));

    public final Pose firstPose = new Pose (14, 36, Math.toRadians(180));

    public final Pose controlPose1 = new Pose(62,  39);

    public final Pose secondPose = new Pose(56, 10, Math.toRadians(180));
/// wait time here to shoot first three balls
    public final Pose thirdPose = new Pose(6, 10, Math.toRadians(180));
/// get sixth balls from human player zone
    public final Pose fourthPose = new Pose(56, 10, Math.toRadians(180));
/// wait time here shoot sixth ball
    public final Pose fifthPose = new Pose(6, 10, Math.toRadians(180));
/// intaking 6-9 balls
    public final Pose sixthPose = new Pose(56, 10, Math.toRadians(180));
/// wait time here to shoot ninth balls
    public final Pose seventhPose = new Pose(6, 10, Math.toRadians(180));
///intaking 9-12 balls
    public final Pose eighthPose = new Pose(56, 10, Math.toRadians(180));
///shooting 12th ball

    private Path path1, path2, path3, path4, path5, path6, path7, path8;

    public void buildPaths () {
        path1 = new Path(
                new BezierCurve(startPose, controlPose1, firstPose));
        path1.setConstantHeadingInterpolation(Math.toRadians(180));

        path2 = new Path (
                new BezierLine(firstPose, secondPose));
        path2.setConstantHeadingInterpolation(Math.toRadians(180));

        path3 = new Path (
                new BezierLine(secondPose, thirdPose));
        path3.setConstantHeadingInterpolation(Math.toRadians(180));

        path4 = new Path (
                new BezierLine(thirdPose, fourthPose));
        path4.setConstantHeadingInterpolation(Math.toRadians(180));

        path5 = new Path(
                new BezierLine(fourthPose, fifthPose));
        path5.setConstantHeadingInterpolation(Math.toRadians(180));

        path6 = new Path(
                new BezierLine(fifthPose, sixthPose));
        path6.setConstantHeadingInterpolation(Math.toRadians(180));

        path7 = new Path(
                new BezierLine(sixthPose, seventhPose));
        path7.setConstantHeadingInterpolation(Math.toRadians(180));

        path8 = new Path(
                new BezierLine(seventhPose, eighthPose));
        path8.setConstantHeadingInterpolation(Math.toRadians(180));


    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, startPose, 20);
        buildPaths();
        schedule(
                new RunCommand(bot :: any_autonomous_robot_update),
                new SequentialCommandGroup(
                        new WaitCommand(2000)
                          .alongWith(
                                  new Score_Command(bot, 1250, 0.27))
                            .andThen(
                                    new WaitCommand (300),
                                    new Intake_on_15(bot)),
                        /// shoots preloads

                        new FollowPath(bot.getFollower(), path1, 0.8)
                                .alongWith(
                                        new Intake_on_15(bot)),
                        new WaitCommand(1000),
                        /// intakes third spike

                        new FollowPath(bot.getFollower(), path2, 0.8),
                        new WaitCommand(2000)
                                .alongWith(
                                        new Score_Command(bot, 1250, 0.27))
                                .andThen(
                                        new WaitCommand(300),
                                        new Intake_on_15(bot)),
                        /// should shoot third spike

                        new FollowPath(bot.getFollower(), path3, 0.8)
                                .alongWith(
                                    new Intake_on_15(bot)),
                        new WaitCommand(1000),
                        /// first intake from human player zone

                        new FollowPath(bot.getFollower(), path4, 0.8)
                                .alongWith(
                                        new Score_Command(bot, 1250, 0.27))
                                .andThen(
                                        new Intake_on_15(bot)),
                        new WaitCommand(2000),
                        /// shoots first human player zone cycle

                        new FollowPath(bot.getFollower(), path5, 0.8)
                                .alongWith(
                                        new Intake_on_15(bot)),
                        new WaitCommand(1000),
                        /// second human player zone cycle

                        new FollowPath(bot.getFollower(), path6, 0.8)
                                .alongWith(
                                        new Score_Command(bot, 1250, 0.27))
                                .andThen(
                                        new Intake_on_15(bot)),
                        new WaitCommand(2000),
                        /// shoots second cycle (at this point should have shot 12 balls)

                        new FollowPath(bot.getFollower(), path7, 0.8)
                                .alongWith(
                                        new Intake_on_15(bot)),
                        new WaitCommand(1000),
                        /// intakes third cycle from human player zone

                        new FollowPath(bot.getFollower(), path8, 0.8)
                                .alongWith(
                                        new Score_Command(bot, 1250, 0.27))
                                .andThen(
                                        new Intake_on_15(bot)),
                        new WaitCommand(2000))
                        /// 15th ball in theory

        );
    }
}
