package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "redFarNEW", group = "Red")
public class ahanaRedFarNEW extends OpModeCommand {

    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    Robot bot;
    public static final Pose startpose = new Pose(95.60655737704917,7.868852459016386, Math.toRadians(90));
    public final Pose presets = new Pose (95.80327868852459,7.672131147540984,Math.toRadians(75));
    public final Pose posit = new Pose (95.80327868852459,34.42622950819672, Math.toRadians(0));
    public final Pose gather1 = new Pose (122.36065573770493,34.42622950819672,Math.toRadians(0));
    public final Pose scoreuno = new Pose (90.68852459016394,13.573770491803286, Math.toRadians(60));
    public final Pose posit2 = new Pose (91.08196721311475,59.213114754098356, Math.toRadians(0));
    public final Pose gather2 = new Pose (122.95081967213115,59.213114754098356, Math.toRadians(0));
    public final Pose score2 = new Pose (87.14754098360655,83.60655737704919, Math.toRadians(45));
    public final Pose posit3 = new Pose (87.14754098360655,83.60655737704919, Math.toRadians(0));
    public final Pose gather3 = new Pose (124.72131147540983,83.21311475409834, Math.toRadians(0));
    public final Pose score3 = new Pose (106.0327868852459,102.09836065573771, Math.toRadians(45));

    private Path scorepres, get2balls, intake1, shoot1, get2balls2, intake2, shoot2, get2balls3, intake3, shoot3;
    public void buildPaths (){
        scorepres = new Path (new BezierLine(startpose,presets));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(),presets.getHeading());

        get2balls = new Path (new BezierLine (presets, posit));
        get2balls.setLinearHeadingInterpolation(presets.getHeading(),posit.getHeading());

        intake1 = new Path (new BezierLine(posit, gather1));
        intake1.setConstantHeadingInterpolation(Math.toRadians(0));

        shoot1 = new Path (new BezierLine(gather1,scoreuno));
        shoot1.setLinearHeadingInterpolation(gather1.getHeading(),scoreuno.getHeading());

        get2balls2 = new Path (new BezierLine (scoreuno,posit2));
        get2balls.setLinearHeadingInterpolation(scoreuno.getHeading(),posit2.getHeading());

        intake2 = new Path (new BezierLine(posit2, gather2));
        intake2.setConstantHeadingInterpolation(Math.toRadians(0));

        shoot2 = new Path (new BezierLine(gather2, score2));
        shoot2.setLinearHeadingInterpolation(gather2.getHeading(),score2.getHeading());

        get2balls3 = new Path (new BezierLine(score2, posit3));
        get2balls3.setLinearHeadingInterpolation(score2.getHeading(),posit3.getHeading());

        intake3 = new Path (new BezierLine (posit3, gather3));
        intake3.setConstantHeadingInterpolation(Math.toRadians(0));

        shoot3 = new Path (new BezierLine(gather3, score3));
        shoot3.setLinearHeadingInterpolation(gather3.getHeading(),score3.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot (hardwareMap,panelsTelemetry,startpose);
        schedule (
                new RunCommand(bot ::robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                .alongWith(new Prep_Shooter(bot) /// preps servos
                                )
                                .andThen(
                                        new Intake_on(bot) //firing
                                )/// need shooter
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),get2balls)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1) /// need intake
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),shoot1)/// need shooter
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),get2balls2)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),intake2) /// need intake
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),shoot2)/// need shooter
                                )
                                .andThen(
                                        new WaitCommand(3000)
                                )
                                .andThen(
                                        new FollowPath (bot.getFollower(), get2balls3)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),intake3)/// need intake
                                )
                                .andThen(
                                        new FollowPath (bot.getFollower(),shoot3)/// need shooter
                                )

                )
        );
    }
}
