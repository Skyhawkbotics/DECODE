package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter_Blue_Far;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.subsystems.Intake;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

/// I lowkenuinely just mirrored all the points and headings from red side, i'll test tmr and make changes
@Autonomous(name = "blueFARHV", group = "Blue")
public class bluefarHV extends OpModeCommand {
    Robot bot;

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    public static final Pose startpose = new Pose (63.540983606557376,8.852459016393444, Math.toRadians(90));
    public static Pose pres = new Pose (53.31147540983606,12.786885245901646, Math.toRadians(120));
    public static Pose posit1 = new Pose (55.8688524590164,24.786885245901637, Math.toRadians(180));
    public static Pose gather1 = new Pose (18.49180327868854,25.770491803278695, Math.toRadians(180));
    public static Pose shoot1 = new Pose (53.31147540983606,12.786885245901646, Math.toRadians(120));
    public static Pose gather2 = new Pose (13.573770491803277,10.229508196721307, Math.toRadians(135));
    public static Pose gatherer = new Pose (7.27868852459017,36, Math.toRadians(90));
    public static Pose shoot2 = new Pose (53.31147540983606,12.786885245901646, Math.toRadians(120));
    public static Pose gather3 = new Pose (13.770491803278688,10.032786885245905, Math.toRadians(135));
    public static Pose gathersan = new Pose (7.081967213114754,36, Math.toRadians(90));
    public static Pose shoot3 = new Pose (53.31147540983606,12.786885245901646, Math.toRadians(120));
    public static Pose myfinalrestingplace = new Pose (53.50819672131146,22.819672131147534, Math.toRadians(90));

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
                new Pose (33.63934426229508,1.3770491803278624),
                gather2));
        intake2.setLinearHeadingInterpolation(shoot1.getHeading(),gather2.getHeading());

        intakeer = new Path (new BezierCurve(
                gather2,
                new Pose(5.704918032786885,15.737704918032788),
                gatherer
        ));
        intakeer.setLinearHeadingInterpolation(gather2.getHeading(),gatherer.getHeading());

        score2 = new Path (new BezierLine(gatherer,shoot2));
        score2.setLinearHeadingInterpolation(gatherer.getHeading(), shoot2.getHeading());

        intake3 = new Path (new BezierCurve(
                shoot2,
                new Pose (33.63934426229508,1.3770491803278624),
                gather3
        ));
        intake3.setLinearHeadingInterpolation(shoot2.getHeading(),gather3.getHeading());

        intakesan = new Path (new BezierCurve(
                gather3,
                new Pose (5.704918032786885,15.737704918032788),
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
        bot = new Robot(hardwareMap, telemetry,startpose);
        schedule (
                new RunCommand(bot::robot_update),
                new SequentialCommandGroup(
                        /// this section scores the preloads
                        new FollowPath(bot.getFollower(), scorepres)/// need shooter
                                    .alongWith (new Prep_Shooter_Blue_Far(bot))
                                .andThen(new Intake_on(bot))
                                .andThen (new WaitCommand(3000))
                                .andThen (new Shooter_off(bot))

                        /// this section intakes and scores load1
                                .andThen(new FollowPath(bot.getFollower(),get2balls))
                                .andThen (new WaitCommand(500))// to get ready to posit
                                .andThen(new FollowPath(bot.getFollower(),intake1, 0.5)) /// need intake
                                    .alongWith(new Intake_on(bot))
                                .andThen (new WaitCommand(500))
                                .andThen (new FollowPath(bot.getFollower(),score1))/// need shooter
                                    .alongWith (new Prep_Shooter(bot))
                                .andThen(new Intake_on(bot))
                                .andThen (new WaitCommand(1000))
                                .andThen (new Shooter_off(bot))

                                /// this section intakes and scores collection 1
                                .andThen (new FollowPath(bot.getFollower(),intake2,.5))
                                    .alongWith (new Intake_on(bot))
                                .andThen (new FollowPath(bot.getFollower(),intakeer,.5))/// need intake
                                    .alongWith (new Intake_on(bot))
                                .andThen (new FollowPath(bot.getFollower(),score2))/// need shooter
                                    .alongWith (new Prep_Shooter(bot))
                                .andThen(new Intake_on(bot))
                                .andThen (new WaitCommand(1000))
                                .andThen (new Shooter_off(bot))

                                ///  this section intakes and scores collection 2
                                .andThen (new FollowPath(bot.getFollower(),intake3,.5))
                                    .alongWith(new Intake_on(bot))
                                .andThen (new FollowPath(bot.getFollower(),intakesan,.5))/// need intake
                                    .alongWith(new Intake_on(bot))
                                .andThen (new FollowPath(bot.getFollower(),score3))/// need shooter
                                       .alongWith (new Prep_Shooter(bot))
                                .andThen(new Intake_on(bot))
                                .andThen (new WaitCommand(1000))
                                .andThen (new Shooter_off(bot))
                                .andThen (new FollowPath(bot.getFollower(),end))

                ));
    }

}


