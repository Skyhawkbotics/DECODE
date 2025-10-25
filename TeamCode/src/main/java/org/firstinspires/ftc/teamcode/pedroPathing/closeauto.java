package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Close Auto", group = "Test")
public class closeauto extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private final Pose startPose = new Pose(122.5, 124.4, Math.toRadians(36)); // Start Pose of our robot.

    private final Pose shootPos = new Pose(94,95, Math.toRadians(46)); // shooting position

    private final Pose parkPos = new Pose(38.214,14.7, Math.toRadians(90)); // park position

    private int pathState;

    private Path gotoShooterSpot;
    private Path gotoPark;

    public void buildPaths(){
        gotoShooterSpot = new Path(new BezierLine(startPose, shootPos));
        gotoShooterSpot.setLinearHeadingInterpolation(startPose.getHeading(), shootPos.getHeading());

        gotoPark = new Path(new BezierLine(shootPos, parkPos));
        gotoPark.setLinearHeadingInterpolation(shootPos.getHeading(), parkPos.getHeading());
    }

    public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                follower.followPath(gotoShooterSpot);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {
                    follower.followPath(gotoPark, true);
                    setPathState(2);
                }
                break;
            case 2:
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }
    @Override
    public void loop(){
        follower.update();
        telemetry.update();

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {}
}
