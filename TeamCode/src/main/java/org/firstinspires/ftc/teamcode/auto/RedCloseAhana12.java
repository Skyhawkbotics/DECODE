package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Point;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.HoldPoint;
import ftclib.OpModeCommand;

@Autonomous(name = "12 Red Close", group = "gf")
public class RedCloseAhana12 extends OpModeCommand {
    Robot bot;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpose = new Pose(119.17241379310344, 128.58076225045372, Math.toRadians(38));
    public final Pose pres = new Pose(100.52459016393443, 100.91803278688523, Math.toRadians(47));
    public final Pose get2gate = new Pose(94.72131147540983, 80.19672131147541, Math.toRadians(0));

    public final Pose controlpoint1 = new Pose(114, 71, Math.toRadians(90));

    public final Pose posit = new Pose(100.91803278688523, 72.78688524590164, Math.toRadians(0));
    public final Pose posit1 = new Pose(100.52459016393443, 83.80327868852459, Math.toRadians(0));
    public final Pose gather1 = new Pose(116.327868852459, 83.60655737704919, Math.toRadians(0));

    public final Pose reversepose = new Pose(112, 73, Math.toRadians(0));

    public final Pose kaimen = new Pose(118, 73, 0);


    public final Pose scoreuno = new Pose(100.72131147540983, 100.72131147540983, Math.toRadians(45));
    public final Pose posit2 = new Pose(96.91803278688523, 59.80327868852459, Math.toRadians(0));
    public final Pose gather2 = new Pose(115.327868852459, 59.60655737704917, Math.toRadians(0));
    public final Pose score2 = new Pose(88.52459016393443, 87.73770491803279, Math.toRadians(45));
    public final Pose posit3 = new Pose(88.91803278688525, 37.18032786885246, Math.toRadians(0));
    public final Pose gather3 = new Pose(114.1311475409836, 36.59016393442622, Math.toRadians(0));
    public final Pose score3 = new Pose(88.72131147540983, 87.73770491803279, Math.toRadians(0));
    public final Pose finalrestingplace = new Pose(115.327868852459, 83.60655737704919, Math.toRadians(90));


    private Path scorepres, posit2gate, opengate, posithalf, get2balls, intake1, shoot1, get2balls2, intake2, shoot2, get2balls3, intake3, shoot3, leave;
    private Path reverse;

    public void buildPaths() {
        scorepres = new Path(new BezierLine(startpose, pres)); // preload
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), pres.getHeading());

        get2balls = new Path(new BezierLine(pres, posit1)); // intake
        get2balls.setConstantHeadingInterpolation(Math.toRadians(0));

        intake1 = new Path(new BezierLine(posit1, gather1));
        intake1.setConstantHeadingInterpolation(0);

        reverse = new Path(new BezierLine(gather1, reversepose));
        reverse.setConstantHeadingInterpolation(0);


        // posit2gate = new Path (new BezierLine(gather1,get2gate));
        // posit2gate.setLinearHeadingInterpolation(gather1.getHeading(), get2gate.getHeading());

        opengate = new Path(new BezierLine(reversepose, kaimen));
        opengate.setConstantHeadingInterpolation(Math.toRadians(0));

        // posithalf = new Path (new BezierLine(kaimen, posit));
        // posithalf.setConstantHeadingInterpolation(Math.toRadians(0));

        shoot1 = new Path(new BezierLine(kaimen, pres));
        shoot1.setLinearHeadingInterpolation(kaimen.getHeading(), pres.getHeading());

        get2balls2 = new Path(new BezierLine(pres, posit2));
        get2balls2.setLinearHeadingInterpolation(pres.getHeading(), posit2.getHeading());

        intake2 = new Path(new BezierLine(posit2, gather2));
        intake2.setConstantHeadingInterpolation(Math.toRadians(0));

        shoot2 = new Path(new BezierLine(gather2, pres));
        shoot2.setLinearHeadingInterpolation(gather2.getHeading(), pres.getHeading());

        get2balls3 = new Path(new BezierLine(pres, posit3));
        get2balls3.setLinearHeadingInterpolation(pres.getHeading(), posit3.getHeading());

        intake3 = new Path(new BezierLine(posit3, gather3));
        intake3.setConstantHeadingInterpolation(Math.toRadians(0));

        shoot3 = new Path(new BezierLine(gather3, pres));
        shoot3.setLinearHeadingInterpolation(gather3.getHeading(), pres.getHeading());

        leave = new Path(new BezierLine(pres, finalrestingplace));
        leave.setLinearHeadingInterpolation(pres.getHeading(), finalrestingplace.getHeading());

    }

    public void initialize() {
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, startpose);

        schedule(
                new RunCommand(bot::autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                //.alongWith(new Prep_Shooter(bot)) ///  also preps servos
                                .andThen(new WaitCommand(1000))
                                .andThen(new Intake_on(bot)) // Firing /// Test 1
                                .andThen(new Shooter_off(bot), new FollowPath(bot.getFollower(), get2balls))///  also servo
/*
                        ///intake 1
                                .andThen(new FollowPath(bot.getFollower(),intake1, 0.5)
                                        .alongWith(new Intake_on(bot))
                                )

                        ///open gate
                                .andThen(new WaitCommand (750))
                                .andThen(new FollowPath(bot.getFollower(), reverse, 0.4))
                                .andThen(new WaitCommand(200))
                                .andThen(new FollowPath(bot.getFollower(), opengate, 0.4).raceWith(new WaitCommand(1500))
                                )
                                //.andThen(new WaitCommand(1500))

                        ///score intake1
                                .andThen(new FollowPath(bot.getFollower(), shoot1) ///  need shooter
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(new WaitCommand(1000))
                                .andThen(new Intake_on(bot)) ///  firing

                        ///intake2
                                .andThen(new FollowPath(bot.getFollower(), get2balls2)
                                                .alongWith(new Shooter_off(bot))

                                )
                                .andThen(new FollowPath(bot.getFollower(), intake2, 0.5) /// need intake
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen(new WaitCommand(200))

                        ///score intake2
                                .andThen(new FollowPath(bot.getFollower(),shoot2)/// shooter
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                 .andThen(new WaitCommand(1000))

                                 .andThen(new Intake_on(bot))

                        ///intake 3
                                .andThen(new FollowPath(bot.getFollower(), get2balls3)
                                                .alongWith(new Shooter_off(bot))
                                )
                                .andThen (new FollowPath(bot.getFollower(), intake3, 0.5) /// need intake
                                                .alongWith(new Intake_on(bot))
                                )
                                .andThen(new WaitCommand(750))

                        ///score intake 3
                                .andThen(new FollowPath(bot.getFollower(),shoot3) /// need shoot
                                                .alongWith(new Prep_Shooter(bot))
                                )
                                .andThen(new WaitCommand(400))
                                .andThen(new Intake_on(bot))
                                .andThen(new WaitCommand(200))

                        ///leave
                                .andThen(new FollowPath(bot.getFollower(), leave))


                )*/
                ));
    }
}




    /*public static final Pose startpose = new Pose (114.29508196721312,129.63934426229508,Math.toRadians(45));
    public final Pose pres = new Pose (100.609848, 117.20528211, Math.toRadians(30));
    public final Pose get2balls = new Pose (94.905162,84.0144057,Math.toRadians(0));
    public final Pose gather1 = new Pose (124.86315789473684,83.5578947368421, Math.toRadians(0));
    public final Pose score = new Pose (90.56842105263158,92.84210526315789, Math.toRadians(45));
    public final Pose get2balls2 = new Pose (90.18947368421053,59.684210526315795,Math.toRadians(0));
    public final Pose gather2 = new Pose (124.67368421052632,59.494736842105254, Math.toRadians(0));
    public final Pose posit4gate = new Pose (109.32631578947368,69.72631578947367,Math.toRadians(0));

    private Path scorepres, posit1, load1, score1, posit2, load2, score2, get2gate;
    public void buildPaths (){
        scorepres = new Path(new BezierLine(startpose,pres));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(),pres.getHeading());

        posit1 = new Path (new BezierLine (pres,get2balls));
        posit1.setLinearHeadingInterpolation(pres.getHeading(),get2balls.getHeading());

        load1 = new Path (new BezierLine (get2balls,gather1));
        load1.setConstantHeadingInterpolation(Math.toRadians(0));

        score1 = new Path (new BezierLine (gather1, score));
        score1.setLinearHeadingInterpolation(gather1.getHeading(),score.getHeading());

        posit2 = new Path (new BezierLine (score,get2balls2));
        posit2.setLinearHeadingInterpolation(score.getHeading(),get2balls2.getHeading());

        load2 = new Path (new BezierLine (get2balls2, gather2));
        load2.setConstantHeadingInterpolation(Math.toRadians(0));

        score2 = new Path (new BezierLine (gather2, score));
        score2.setLinearHeadingInterpolation(gather2.getHeading(), score.getHeading());

        get2gate = new Path (new BezierLine (score,posit4gate));
        get2gate.setLinearHeadingInterpolation(score.getHeading(),posit4gate.getHeading());
    }

    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, red_ID, startpose);

        schedule(
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                .andThen(
                                        new WaitCommand(3000)
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(),posit1)
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(),load1, 0.42)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),score1)
                                )
                                .andThen(
                                        new WaitCommand(3500)
                                )
                                .andThen (
                                        new FollowPath(bot.getFollower(), posit2) /// bring the posit closer to intake
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),load2, 0.42)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),score2)
                                )
                                .andThen(
                                        new WaitCommand(3500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),get2gate)
                                )


                )
        );
    }

*/
