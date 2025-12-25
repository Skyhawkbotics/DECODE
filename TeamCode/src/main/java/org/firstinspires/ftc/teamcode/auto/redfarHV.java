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
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous (name = "redFarHV", group ="Red")
public class redfarHV extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose (80.45901639344262,8.852459016393444, Math.toRadians(90));
    public static Pose pres = new Pose (90.68852459016394,12.786885245901646, Math.toRadians(60));
    public static Pose posit1 = new Pose (88.1311475409836,24.786885245901637, Math.toRadians(0));
    public static Pose gather1 = new Pose (125.50819672131146,25.770491803278695, Math.toRadians(0));
    public static Pose shoot1 = new Pose (90.68852459016394,12.786885245901646, Math.toRadians(60));
    public static Pose gather2 = new Pose (125.70491803278689,11.409836065573767, Math.toRadians(45));
    public static Pose gatherer = new Pose (136.72131147540983,36, Math.toRadians(90));
    public static Pose shoot2 = new Pose (90.68852459016394,12.786885245901646, Math.toRadians(60));
    public static Pose gather3 = new Pose (125.70491803278689,11.606557377049185, Math.toRadians(45));
    public static Pose gathersan = new Pose (136.72131147540983,35.8032786885246, Math.toRadians(90));
    public static Pose shoot3 = new Pose (90.49180327868854,12.590163934426227, Math.toRadians(60));
    public static Pose myfinalrestingplace = new Pose (90.49180327868854,22.819672131147534, Math.toRadians(90));


    private Path scorepres, get2balls, intake1, score1, intake2, intakeer, score2, intake3, intakesan, score3, end;
    public void buildPaths(){
        scorepres = new Path (new BezierLine(startpose,pres));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(),pres.getHeading());

        get2balls = new Path (new BezierLine (pres, posit1));
        get2balls.setLinearHeadingInterpolation(pres.getHeading(),posit1.getHeading());

        intake1 = new Path (new BezierLine(posit1,gather1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(0));

        score1 = new Path (new BezierLine (gather1,shoot1));
        score1.setLinearHeadingInterpolation(gather1.getHeading(),shoot1.getHeading());

        intake2 = new Path (new BezierCurve(
                shoot1,
                new Pose (110.36065573770492,1.3770491803278624),
                gather2));
        intake2.setLinearHeadingInterpolation(shoot1.getHeading(),gather2.getHeading());

        intakeer = new Path (new BezierCurve(
                gather2,
                new Pose(139.67213114754097,15.54098360655737),
                gatherer
        ));
        intakeer.setLinearHeadingInterpolation(gather2.getHeading(),gatherer.getHeading());

        score2 = new Path (new BezierLine(gatherer,shoot2));
        score2.setLinearHeadingInterpolation(gatherer.getHeading(), shoot2.getHeading());

        intake3 = new Path (new BezierCurve(
                shoot2,
                new Pose (110.36065573770492,1.3770491803278624),
                gather3
        ));
        intake3.setLinearHeadingInterpolation(shoot2.getHeading(),gather3.getHeading());

        intakesan = new Path (new BezierCurve(
                gather3,
                new Pose (139.67213114754097,15.54098360655737),
                gathersan
        ));
        intakesan.setLinearHeadingInterpolation(gather3.getHeading(),gathersan.getHeading());

        score3 = new Path (new BezierLine(gathersan, shoot3));
        score3.setLinearHeadingInterpolation(gathersan.getHeading(),shoot3.getHeading());

        end = new Path (new BezierLine (shoot3, myfinalrestingplace));
        end.setLinearHeadingInterpolation(shoot3.getHeading(),myfinalrestingplace.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot(hardwareMap,telemetry,startpose);
        schedule (
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)/// need shooter
                               // .alongWith(new Prep_Shooter(bot))
                                .andThen (new WaitCommand(3000))
                              //  .andThen (new Intake_on(bot))
                               // .andThen (new Shooter_off(bot))


                                .andThen(new FollowPath(bot.getFollower(),get2balls))
                                .andThen (new WaitCommand(500))// to get ready to posit
                                .andThen(new FollowPath(bot.getFollower(),intake1, 0.5)) /// need intake
                                //.alongWith (new Intake_on(bot))
                                .andThen (new FollowPath(bot.getFollower(),score1))/// need shooter
                                //.alongWith(new Prep_Shooter(bot))
                                .andThen (new WaitCommand(1000))
                                //.andThen(new Intake_on(bot))

                                .andThen (new FollowPath(bot.getFollower(),intake2,.5))
                                .andThen (new FollowPath(bot.getFollower(),intakeer,.5))/// need intake
                                .andThen (new FollowPath(bot.getFollower(),score2))/// need shooter
                                .andThen (new WaitCommand(1000))

                                .andThen (new FollowPath(bot.getFollower(),intake3,.5))
                                .andThen (new FollowPath(bot.getFollower(),intakesan,.5))/// need intake
                                .andThen (new FollowPath(bot.getFollower(),score3))/// need shooter
                                .andThen (new WaitCommand(3000))

                                .andThen (new FollowPath(bot.getFollower(),end))

        ));
    }
}
