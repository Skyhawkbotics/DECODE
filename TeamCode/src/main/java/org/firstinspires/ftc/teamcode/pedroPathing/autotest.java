package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;


@Autonomous(name = "Test Auto", group = "Test")
public class autotest extends OpMode {


    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;


    private int pathState;


    private final Pose startPose = new Pose(56, 8, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose intake1 = new Pose(16.131147540983605, 9.442622950819667, Math.toRadians(180)); // moving to human intake zone
    private final Pose twirl = new Pose(16.131147540983605, 9.442622950819667, Math.toRadians(30)); // rotating to face the goal
    private final Pose move2score = new Pose(70.62295081967213, 10.622950819672127, Math.toRadians(63)); // enters tiny triangle launch zone to score






    private Path autoScoring; ///this auto runs back and forth between the tiny triangle launch zone to the human intake zone 3 times (should take <10 seconds per iteration)
    private PathChain rotate, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3;
    public void buildPaths(){
        autoScoring = new Path(new BezierLine(startPose, intake1));
        autoScoring.setLinearHeadingInterpolation(startPose.getHeading(), intake1.getHeading());


        rotate = follower.pathBuilder()
                .addPath(new BezierLine(intake1, twirl))
                .setLinearHeadingInterpolation(intake1.getHeading(), twirl.getHeading())
                .build();


        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(twirl, move2score))
                .setLinearHeadingInterpolation(twirl.getHeading(),move2score.getHeading())
                .build();


        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(move2score,intake1))
                .setLinearHeadingInterpolation(move2score.getHeading(),intake1.getHeading())
                .build();


        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(twirl, move2score))
                .setLinearHeadingInterpolation(twirl.getHeading(),move2score.getHeading())
                .build();


        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(move2score,intake1))
                .setLinearHeadingInterpolation(move2score.getHeading(),intake1.getHeading())
                .build();


        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(twirl, move2score))
                .setLinearHeadingInterpolation(twirl.getHeading(),move2score.getHeading())
                .build();


    }


    public void autonomousPathUpdate(){
        switch (pathState) {
            case 0:
                follower.followPath(autoScoring);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()){
                    follower.followPath(rotate, true);
                    setPathState(2);
                }
            case 2:
                if (!follower.isBusy()){
                    follower.followPath(scorePickup1, true);
                    setPathState(3);
                }
            case 3:
                if (!follower.isBusy()){
                    follower.followPath(grabPickup2, true);
                    setPathState(4);
                }
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(scorePickup2, true);
                    setPathState(5);
                }
            case 5:
                if (!follower.isBusy()){
                    follower.followPath(grabPickup3, true);
                    setPathState(6);
                }
            case 6:
                if (!follower.isBusy()){
                    follower.followPath(scorePickup3, true);
                    setPathState(7);
                }
        }
    }


    private void setPathState(int pState) {
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
    public void loop() {
        follower.update();
        autonomousPathUpdate();


        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}

