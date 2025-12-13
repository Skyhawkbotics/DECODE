package org.firstinspires.ftc.teamcode.auto;

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
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "blue close side ", group = "Blue")
public class abeBlueClose extends OpModeCommand {
    Robot bot;
    public static final Pose startPose  = new Pose(24.566243194192378, 128.58076225045372, Math.toRadians(143));
    public  final Pose pres  = new Pose(45.21234119782214, 108.19600725952813, Math.toRadians(143));

    public  final Pose posit1  = new Pose(43.475, 90, Math.toRadians(180));

    public  final Pose gather1  = new Pose(20, 90, Math.toRadians(180));


    public  final Pose get2gate  = new Pose(49.279, 72.19672131147541, Math.toRadians(0));
    public  final Pose kaimen  = new Pose(20.934, 71.40983606557378, Math.toRadians(0));
    public  final Pose posit  = new Pose(43.082, 72.78688524590164, Math.toRadians(180));
    public  final Pose scoreuno  = new Pose(43.279, 100.72131147540983, Math.toRadians(130));
    public  final Pose posit2  = new Pose(47.082, 59.80327868852459, Math.toRadians(180));
    public  final Pose gather2 = new Pose(28.67, 59.60655737704917, Math.toRadians(180));
    public  final Pose score2 = new Pose(55.475, 87.73770491803279, Math.toRadians(130));
    public  final Pose posit3 = new Pose (55.0819,37.18032786885246, Math.toRadians(180));
    public final Pose gather3 = new Pose (28.672,36.59016393442622, Math.toRadians(180));
    public final Pose score3 = new Pose (55.475,87.73770491803279, Math.toRadians(130));
    public final Pose finalrestingplace = new Pose (28.6721311475,83.60655737704919, Math.toRadians(90));
    private Path score_preload, get2balls1, intake1, opengate, shoot1, get2balls2, intake2, shoot2, get2balls3, intake3, shoot3;
    public void buildPaths(){
        score_preload = new Path(new BezierLine(startPose, pres));
        score_preload.setLinearHeadingInterpolation(startPose.getHeading(), pres.getHeading());

        get2balls1 = new Path(new BezierLine(pres, posit1));
        get2balls1.setLinearHeadingInterpolation(pres.getHeading(),posit.getHeading());

        intake1 = new Path(new BezierLine(posit1, gather1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(180));

        opengate = new Path (new BezierLine(gather1,kaimen));
        opengate.setLinearHeadingInterpolation(gather1.getHeading(),kaimen.getHeading());

        shoot1 = new Path (new BezierLine(kaimen, scoreuno));
        shoot1.setLinearHeadingInterpolation(kaimen.getHeading(),scoreuno.getHeading());

        get2balls2 = new Path(new BezierLine(scoreuno, posit2));
        get2balls2.setLinearHeadingInterpolation(scoreuno.getHeading(), posit2.getHeading());

        intake2 = new Path (new BezierLine (posit2, gather2));
        intake2.setConstantHeadingInterpolation(180);

        shoot2 = new Path(new BezierLine(gather2, score2));
        shoot2.setLinearHeadingInterpolation(gather2.getHeading(),score2.getHeading());

        get2balls3 = new Path (new BezierLine (score2, posit3));
        get2balls3.setLinearHeadingInterpolation(score2.getHeading(),posit3.getHeading());

        intake3 = new Path(new BezierLine(posit3, gather3));
        intake3.setConstantHeadingInterpolation(Math.toRadians(180));

        shoot3 = new Path (new BezierLine (gather3, score3));
        shoot3.setLinearHeadingInterpolation(gather3.getHeading(),score3.getHeading());
    }


    public void initialize(){
        buildPaths();
        bot = new Robot(hardwareMap, telemetry, startPose);

        schedule(
                new RunCommand(bot::autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), score_preload)
                               // .alongWith(
                               //         new Prep_Shooter(bot)
                               // )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                //.andThen(
                                //        new Intake_on(bot)
                                //)
                                /// Test1
                                .andThen(
                                        new FollowPath(bot.getFollower(), get2balls1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1, 0.5)//intake pls
                                                //.alongWith(
                                                //        new Intake_on(bot))
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                /// Test 2
                               /* .andThen(
                                        new FollowPath(bot.getFollower(),opengate,.5) //shooter
                                )
                                .andThen(
                                        new WaitCommand(1500)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),shoot1)
                                               // .alongWith (new Prep_Shooter(bot))
                                )
                                .andThen(
                                        new WaitCommand(1000)
                                )
                                //.andThen(
                                //        new Intake(bot)
                                //
                                //)

                                /// Test 3
                                .andThen(
                                        new FollowPath(bot.getFollower(), get2balls2)
                                        // .alongWith(
                                        //        new Shooter_off(bot)
                                        // )
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake2,.5)
                                        // .alongWith(
                                        //           new Intake_on(bot)
                                )
                                .andThen (
                                        new WaitCommand(1000)
                                )
                                // .andThen(
                                // new Intake_on(bot)
                                // )

                                /// Test 4
                                .andThen(
                                        new FollowPath(bot.getFollower(), get2balls3
                                                //  .alongWith(new Shooter_off(bot)))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake3,.5)
                                        // .alongWith(new Intake_on(bot))
                                )
                                                .andThen(
                                                        new WaitCommand(1000))
                                .andThen(
                                        new FollowPath(bot.getFollower(), shoot3)
                                        //  .alongWith(new Prep_Shooter(bot))
                                )
                                                .andThen (
                                        new WaitCommand(1000) //shoot
                                )
                                        //.andThen(
                                        // new Intake_on(bot)
                )

                                */
                )
        );
    }

}
