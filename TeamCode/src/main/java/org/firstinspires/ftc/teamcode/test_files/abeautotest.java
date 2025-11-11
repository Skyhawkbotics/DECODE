package org.firstinspires.ftc.teamcode.test_files;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "abey auto", group = "auto")
public abstract class abeautotest extends OpMode {

    private static Follower follower;
    private static Timer pathTimer;
    private Timer opmodeTimer;
    private static int pathState;

    // Paths
    private static Paths paths;

    public class Paths {
        public PathChain Path1, Path2, Path3, Path4, Path5, Path6, Path7, Path8, Path9, Path10, Path11, Path12;

        public Paths(Follower follower) {
        }

        public class Path implements org.firstinspires.ftc.teamcode.test_files.Path {


            public Path(Follower follower) {
                Path1 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierLine(new Pose(117.348, 129.680), new Pose(95.000, 94.500))
                        )
                        .setLinearHeadingInterpolation(Math.toRadians(-144), Math.toRadians(180))
                        .build();

                Path2 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(95.000, 94.500),
                                        new Pose(92.088, 84.928),
                                        new Pose(101.039, 83.934)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path3 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierLine(new Pose(101.039, 83.934), new Pose(127.492, 83.536))
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path4 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(127.492, 83.536),
                                        new Pose(74.188, 78.762),
                                        new Pose(127.890, 73.790)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path5 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(127.890, 73.790),
                                        new Pose(87.912, 74.785),
                                        new Pose(95.000, 94.500)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path6 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(95.000, 94.500),
                                        new Pose(86.917, 70.807),
                                        new Pose(95.470, 60.663)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path7 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierLine(new Pose(95.470, 60.663), new Pose(128.884, 58.873))
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path8 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(128.884, 58.873),
                                        new Pose(84.928, 65.834),
                                        new Pose(95.072, 94.475)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path9 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(95.072, 94.475),
                                        new Pose(82.939, 75.779),
                                        new Pose(99.448, 35.204)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path10 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierLine(new Pose(99.448, 35.204), new Pose(128.486, 35.204))
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path11 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierCurve(
                                        new Pose(128.486, 35.204),
                                        new Pose(88.309, 75.381),
                                        new Pose(95.072, 94.475)
                                )
                        )
                        .setTangentHeadingInterpolation()
                        .build();

                Path12 = follower
                        .pathBuilder()
                        .addPath(
                                new BezierLine(new Pose(95.072, 94.475), new Pose(119.337, 94.475))
                        )
                        .setTangentHeadingInterpolation()
                        .build();
            }

            public void autonomousPathUpdate() {
                switch (pathState) {
                    case 0:
                        follower.followPath(paths.Path1);
                        setPathState(1);
                        break;
                    case 1:
                        if (!follower.isBusy()) {
                            // inputrightcode(); //shoot
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path2, true);
                                setPathState(2);
                            }
                        }
                        break;
                    case 2:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path3, true);
                                setPathState(3);
                            }
                        }
                        break;
                    case 3:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path4, true);
                                setPathState(4);
                            }
                        }
                        break;
                    case 4:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path5, true);
                                setPathState(5);
                            }
                        }
                        break;
                    case 5:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path6, true);
                                setPathState(6);
                            }
                        }
                        break;
                    case 6:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path7, true);
                                setPathState(7);
                            }
                        }
                        break;
                    case 7:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path8, true);
                                setPathState(8);
                            }
                        }
                        break;
                    case 8:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path9, true);
                                setPathState(9);
                            }
                        }
                        break;
                    case 9:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path10, true);
                                setPathState(10);
                            }
                        }
                        break;
                    case 10:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path10, true);
                                setPathState(11);
                            }
                        }
                        break;
                    case 11:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                follower.followPath(paths.Path10, true);
                                setPathState(12);
                            }
                        }
                        break;

                    case 12:
                        if (!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2) {
                                setPathState(-1); // done
                            }
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

                telemetry.addData("Path State", pathState);
                telemetry.addData("X", follower.getPose().getX());
                telemetry.addData("Y", follower.getPose().getY());
                telemetry.addData("Heading", follower.getPose().getHeading());
                telemetry.update();
            }

            @Override
            public void init() {
                pathTimer = new Timer();
                opmodeTimer = new Timer();
                opmodeTimer.resetTimer();

                follower = Constants.createFollower(hardwareMap);
                paths = new abeautotest.Paths(follower);
                follower.setStartingPose(new Pose(120, 120, Math.toRadians(-143)));
            }

            @Override
            public void init_loop() {
            }

            @Override
            public void start() {
                opmodeTimer.resetTimer();
                setPathState(0);
            }

            @Override
            public void stop() {
            }
        }
    }
}