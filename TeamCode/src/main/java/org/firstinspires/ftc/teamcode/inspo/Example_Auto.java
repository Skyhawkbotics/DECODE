package org.firstinspires.ftc.teamcode.inspo;

import  com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Example Auto", group = "tests")
public class Example_Auto extends OpMode {


    private Follower follower;

    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startpos = new Pose(84, 10);

    private final Pose line1 = new Pose(120, 35);

    private final Pose line2 = new Pose(71, 76);

    private final Pose line3 = new Pose(120, 60);

    private final Pose line4 = new Pose(71, 76);

    private final Pose line5 = new Pose(120, 84);

    private final Pose line6 = new Pose(71, 76);

    private final Pose line7 = new Pose(38.5, 33);

    private final Pose controlPoint1 = new Pose(80, 41);
    private final Pose controlPoint2 = new Pose(67, 58);
    private final Pose controlPoint3 = new Pose(70, 85);

    private Path path1;
    private Path path2;
    private Path path3;
    private Path path4;
    private Path path5;
    private Path path6;
    private Path path7;


    public void buildPaths() {
        path1 = new Path(new BezierCurve(startpos, controlPoint1, line1));
        path1.setConstantHeadingInterpolation(0);

        path2 = new Path(new BezierLine(line1, line2));
        path2.setLinearHeadingInterpolation(0, 45);

        path3 = new Path(new BezierCurve(line2, controlPoint2, line3));
        path3.setConstantHeadingInterpolation(0);

        path4 = new Path(new BezierLine(line3, line4));
        path4.setConstantHeadingInterpolation(0);

        path5 = new Path(new BezierCurve(line4, controlPoint3, line5));
        path5.setConstantHeadingInterpolation(45);

        path6 = new Path(new BezierLine(line5, line6));
        path6.setLinearHeadingInterpolation(0, 45);
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(path1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(path2);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(path3);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(path4);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(path5);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(path6);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    setPathState(-1);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startpos);
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
}