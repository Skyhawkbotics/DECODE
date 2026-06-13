package org.firstinspires.ftc.teamcode.unused;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.core.Robot;


import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Kristine AUto", group = "Example", preselectTeleOp = "Example TeleOp ") /// @Autonomous to show it's an auto, Name of OpMode on driver hub
/// You can additionally preselect a teleOp that will appear after the auto ends
@Disabled
public class Babysfirstauto extends OpModeCommand { ///  FTC lib opMode structure

    /// Declare the "Robot" class here. This is our compiled code of all the robot's subsystems, esp. the drivetrain
    Robot bot;

    /// Declare your poses here
    public final Pose startPose = new Pose(32, 136, Math.toRadians(90));

    public final Pose secondPose = new Pose(58, 109, Math.toRadians(155));

    public final Pose thirdPose = new Pose (46, 60, Math.toRadians(180));

    public final Pose fourthPose = new Pose (12, 60, Math.toRadians(180));

    public final Pose controlPose2 = new Pose (57,67);

    public final Pose fifthpose = new Pose (49, 105, Math.toRadians(155));

    public final Pose controlPose3 = new Pose (48, 86);

    public final Pose sixthPose = new Pose (37, 84, Math.toRadians(180));

    public final Pose seventhPose = new Pose (14, 84, Math.toRadians(180));

    public final Pose eighthPose = new Pose (46, 110, Math.toRadians(135));

    public final Pose ninthPose = new Pose (43, 36, Math.toRadians(180));

    public final Pose tenthPose = new Pose (11, 36, Math.toRadians(180));

    public final Pose eleventhPose = new Pose (53, 109, Math.toRadians(135));

    private Path path1, path2, path3, path4, path5, path6, path7, path8, path9, path10; /// declare your paths///



    public void buildPaths () {
        path1 = new Path(
                new BezierLine(startPose, secondPose));
        path1.setLinearHeadingInterpolation(startPose.getHeading(), secondPose.getHeading());

        path2 = new Path(
                new BezierLine(secondPose, thirdPose));
        path2.setLinearHeadingInterpolation(secondPose.getHeading(), thirdPose.getHeading());

        path3 = new Path(
                new BezierLine(thirdPose,fourthPose));
        path3.setConstantHeadingInterpolation(fourthPose.getHeading());

        path4 = new Path(
                new BezierCurve(fourthPose,controlPose2,fifthpose));
        path4.setLinearHeadingInterpolation(fourthPose.getHeading(), fifthpose.getHeading());

        path5 = new Path(
                new BezierCurve(fifthpose, controlPose3, sixthPose));
        path5.setLinearHeadingInterpolation(fifthpose.getHeading(), sixthPose.getHeading());

        path6 = new Path(
                new BezierLine(sixthPose, seventhPose));
        path6.setConstantHeadingInterpolation(Math.toRadians(180));

        path7 = new Path(
                new BezierLine(seventhPose, eighthPose));
        path7.setLinearHeadingInterpolation(seventhPose.getHeading(), eighthPose.getHeading());

        path8 = new Path(
                new BezierLine(eighthPose, ninthPose));
        path8.setLinearHeadingInterpolation(eighthPose.getHeading(), ninthPose.getHeading());

        path9 = new Path(
                new BezierLine(ninthPose, tenthPose));
        path9.setConstantHeadingInterpolation(Math.toRadians(180));

        path10 = new Path(
                new BezierLine(tenthPose, eleventhPose));
        path10.setLinearHeadingInterpolation(tenthPose.getHeading(), eleventhPose.getHeading());




    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startPose); /// robot init.
        buildPaths(); // put this after robot constructor the initialization of the FOLLOWER is used for the paths
        schedule (
                new RunCommand(bot :: drivetrain_update), // this allows the follower to update throughout the scheduler
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), path1),
                        new WaitCommand(3000),
                        new FollowPath(bot.getFollower(), path2),
                        new FollowPath(bot.getFollower(), path3),
                        new WaitCommand(1000),
                        new FollowPath(bot.getFollower(), path4),
                        new WaitCommand(3000),
                        new FollowPath(bot.getFollower(), path5),
                        new FollowPath(bot.getFollower(), path6),
                        new WaitCommand(1000),
                        new FollowPath(bot.getFollower(), path7),
                        new FollowPath(bot.getFollower(), path8),
                        new FollowPath(bot.getFollower(), path9),
                        new FollowPath(bot.getFollower(), path10)
                )
        );
    }
}
