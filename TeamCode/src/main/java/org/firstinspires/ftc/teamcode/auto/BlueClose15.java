package org.firstinspires.ftc.teamcode.auto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_3rd_set;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter2;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter3;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter4;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_Zero;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_yes;
import org.firstinspires.ftc.teamcode.core.commands.turret_LL;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

import ftclib.OpModeCommand;
import ftclib.Opmode;

@Autonomous (name = "blueLQTsolo", group = "blue")
public class BlueClose15 extends OpModeCommand {
    Robot bot;

    public static final Pose startpose = new Pose  (16,117, Math.toRadians(140));
    public final Pose preloads = new Pose (52.46,77, Math.toRadians(180));
    public final Pose gather1 = new Pose (11.52, 77, Math.toRadians(180));
    public final Pose shoot1 = new Pose (35.15, 97.1, Math.toRadians(180));
    public final Pose get2balls1 = new Pose (35.15, 53.76, Math.toRadians(180));
    public final Pose gather2 = new Pose (19.14,53.76, Math.toRadians(180));
    public final Pose opengate = new Pose (6.74,61.3, Math.toRadians(180));
    public final Pose shoot2 = new Pose (41.61, 90.05, Math.toRadians(180));
    public final Pose get2balls2 = new Pose (41.61, 34.845, Math.toRadians(180));
    public final Pose gather3 = new Pose (13.15, 34.845, Math.toRadians(180));
    public final Pose shoot3 = new Pose (54.374,76.89, Math.toRadians(180));
    public final Pose get2balls3 = new Pose (0.675, 30.657, Math.toRadians(210));
    public final Pose gather4 = new Pose (0.675, 10.75, Math.toRadians(210));
    public final Pose shoot4 = new Pose (59.24,83.6376, Math.toRadians(180));
    private Path scorepres, intake1, score1, posit1, intake2, kaimen, score2, posit2, intake3, score3, posit3, intake4, score4;
    private Path curve3spikemark, curve2spikemark;

    private BezierPoint kaimenPoint = new BezierPoint(new Pose(128.26,66,Math.toRadians(271.91)));

    public void buildPaths() {
        scorepres = new Path(new BezierLine(startpose, preloads));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), preloads.getHeading());

        intake1 = new Path (new BezierLine(preloads, gather1));
        intake1.setLinearHeadingInterpolation(preloads.getHeading(),gather1.getHeading());

        score1 = new Path(new BezierLine(gather1, shoot1));
        score1.setLinearHeadingInterpolation(gather1.getHeading(), shoot1.getHeading());

        curve2spikemark = new Path(
                new BezierCurve(
                        shoot1,
                        new Pose(89.23475352731101, 50.477832094139686),
                        gather2
                )
        );
        curve2spikemark.setConstantHeadingInterpolation(0);

        posit1 = new Path(new BezierLine(shoot1, get2balls1));
        posit1.setLinearHeadingInterpolation(shoot1.getHeading(), get2balls1.getHeading());

        intake2 =new Path(new BezierLine(get2balls1, gather2));
        intake2.setLinearHeadingInterpolation(get2balls1.getHeading(),gather2.getHeading());

        kaimen = new Path(new BezierCurve(
                gather2,
                new Pose(115.07917383820997,63.4860029981678),
                opengate));
        kaimen.setLinearHeadingInterpolation(gather2.getHeading(),opengate.getHeading());

        score2 = new Path (new BezierLine(opengate, shoot2));
        score2.setLinearHeadingInterpolation(opengate.getHeading(), shoot2.getHeading());

        posit2 = new Path (new BezierLine(shoot2, get2balls2));
        posit2.setLinearHeadingInterpolation(shoot2.getHeading(),get2balls1.getHeading());

        intake3 = new Path (new BezierLine(get2balls2, gather3));
        intake3.setLinearHeadingInterpolation(get2balls2.getHeading(),gather3.getHeading());

        curve3spikemark = new Path(
                new BezierCurve(
                        shoot2,
                        new Pose(95, 27),
                        gather3
                )
        );
        curve3spikemark.setConstantHeadingInterpolation(0);
        score3 = new Path (new BezierLine(gather3, shoot3));
        score3.setLinearHeadingInterpolation(gather3.getHeading(),shoot3.getHeading());

        posit3 = new Path (new BezierCurve(
                shoot3,
                new Pose(137.4328455558454 ,61.307402376858285),
                get2balls3));
        posit3.setTangentHeadingInterpolation();

        intake4 = new Path(new BezierCurve(get2balls3, gather4));
        intake4.setLinearHeadingInterpolation(get2balls3.getHeading(),gather4.getHeading());

        score4 = new Path (new BezierLine(gather4, shoot4));
        score4.setLinearHeadingInterpolation(gather4.getHeading(), shoot4.getHeading());

    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,startpose, 24);
        buildPaths();

        schedule(
                new RunCommand(bot:: autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), scorepres)
                                .alongWith(
                                        new Prep_Shooter(bot)
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1)
                                                .alongWith(new Intake_on_long1(bot))
                                )
                                //
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter2(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(250)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve2spikemark)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )

                                /*

                                .andThen(
                                        new FollowPath(bot.getFollower(), posit1)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake2, .7)
                                                .alongWith(new Intake_on_long(bot))
                                )


                                 */
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimen,.5)
                                                .raceWith(new WaitCommand(2500))
                                )

                                .andThen (
                                        new FollowPath(bot.getFollower(), score2)
                                                .alongWith(new Prep_Shooter3(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(300)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve3spikemark)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                /*

                        .andThen(
                                new FollowPath(bot.getFollower(), posit2,1)
                        )
                                  .andThen(
                                new FollowPath(bot.getFollower(), intake3, .7)
                                        .alongWith(new Intake_on_3rd_set(bot))
                        )

                                 */
                                .andThen(
                                        new Intake_off(bot)
                                )


                                .andThen (
                                        new FollowPath(bot.getFollower(), score3)
                                                .alongWith(
                                                        new Prep_Shooter4(bot)
                                                )
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(),posit3)
                                                .alongWith(
                                                        new Intake_on_forever(bot),
                                                        new turret_Zero(bot)
                                                )
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake4, .7)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_off(bot),
                                        new turret_yes(bot)

                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score4)
                                                .alongWith(new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL(bot)
                                )
                                .andThen(
                                        new WaitCommand(200)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )


                ));
    }
}



