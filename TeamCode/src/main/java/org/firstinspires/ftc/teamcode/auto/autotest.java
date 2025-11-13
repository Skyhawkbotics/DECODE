package org.firstinspires.ftc.teamcode.auto; // make sure this aligns with class location


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.core.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@Autonomous(name = "Test Auto", group = "Test")
public class autotest extends OpMode {


    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;


    private int pathState, armstate;
    private Shooter shoot;
    private Elevator fire;


    private final Pose startPose = new Pose(56, 8, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose shootPose = new Pose(70, 20, Math.toRadians(50)); // moving to human intake zone
    private final Pose twirl = new Pose(16.131147540983605, 9.442622950819667, Math.toRadians(30)); // rotating to face the goal
    private final Pose move2score = new Pose(70.62295081967213, 10.622950819672127, Math.toRadians(63)); // enters tiny triangle launch zone to score






    private Path score1; ///this auto runs back and forth between the tiny triangle launch zone to the human intake zone 3 times (should take <10 seconds per iteration)
    private PathChain rotate, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3;
    public void buildPaths(){
        score1 = new Path(new BezierLine(startPose, shootPose));
        score1.setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading());


        rotate = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, twirl))
                .setLinearHeadingInterpolation(shootPose.getHeading(), twirl.getHeading())
                .build();


        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(twirl, move2score))
                .setLinearHeadingInterpolation(twirl.getHeading(),move2score.getHeading())
                .build();


        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(move2score, shootPose))
                .setLinearHeadingInterpolation(move2score.getHeading(), shootPose.getHeading())
                .build();


        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(twirl, move2score))
                .setLinearHeadingInterpolation(twirl.getHeading(),move2score.getHeading())
                .build();


        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(move2score, shootPose))
                .setLinearHeadingInterpolation(move2score.getHeading(), shootPose.getHeading())
                .build();


        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(twirl, move2score))
                .setLinearHeadingInterpolation(twirl.getHeading(),move2score.getHeading())
                .build();


    }


    public void autonomousPathUpdate(){
        switch (pathState) {
            case 0:
                follower.followPath(score1);
                setarmstate(0);
                setPathState(1);
                break;
            case 1:
                if (pathTimer.getElapsedTimeSeconds() > 5){
                    setarmstate(2);
                    setPathState(-1);
                }
                break;
            case 2:
                if (!follower.isBusy()){
                    follower.followPath(scorePickup1, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    setarmstate(0);
                    setPathState(30);
                }
                break;
            case 30:
                if(pathTimer.getElapsedTimeSeconds() > 3) {
                    follower.followPath(grabPickup2, true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(scorePickup2, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()){
                    follower.followPath(grabPickup3, true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()){
                    follower.followPath(scorePickup3, true);
                    setPathState(7);
                }
                break;
        }
    }
    public void armupdate() {
        switch (armstate) {
            case 0: // ready fire
                shoot.perf_velocity();
                shoot.update();

                break;
            case 1:
                shoot.shooterON();
            case 2:
                break;
            case 3:
                break;

        }
    }

    private void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    private void setarmstate(int aState) {
        armstate = aState;
    }




    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
        shoot = new Shooter(hardwareMap, telemetry);
        fire = new Elevator(hardwareMap, telemetry);


    }


    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        shoot.update();
        shoot.shooter_telemetry();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("follow busy", follower.isBusy());
        telemetry.addData("arm state", armstate);
        telemetry.update();
    }
}

