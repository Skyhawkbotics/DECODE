package org.firstinspires.ftc.teamcode.unused;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/*

@Autonomous(name = "Andy's amazing auto", group = "tests")
@Disabled
public class Super_awesome_and_very_complex_auto extends OpMode {

    private Follower follower;

    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startpos = new Pose(116, 132, Math.toRadians(37));
    private final Pose pos1 = new Pose(96, 83.6, Math.toRadians(0));
    private final Pose pos2 = new Pose(120, 83.6, Math.toRadians(0));

    private Path path1;
    private Path path2;

    private Path path3;

    public void buildPaths() {
        path1 = new Path(new BezierLine(startpos, pos1));
        path1.setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(0));

        path2 = new Path(new BezierLine(pos1, pos2));
        path2.setConstantHeadingInterpolation(0);

        path3 = new Path(new BezierLine(pos2, startpos));
        path3.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(37));

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(path1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy ()) {
                    follower.followPath(path2);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(path3);
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

 */
