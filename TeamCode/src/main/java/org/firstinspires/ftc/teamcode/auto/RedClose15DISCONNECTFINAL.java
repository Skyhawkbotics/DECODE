package org.firstinspires.ftc.teamcode.auto;


import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_off;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_forever;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on_long1;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter2;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter3;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter4;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_Zero;
import org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands.turret_yes;
import org.firstinspires.ftc.teamcode.core.commands.turret_LL;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Disconnect", group = "red")
public class RedClose15DISCONNECTFINAL extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));

    public final Pose all_scores = new Pose (89.62580645161289,76.89032258064516, Math.toRadians(0));

    public final Pose get2balls1 = new Pose (104.35,53.76129032258066, Math.toRadians(0));
    public final Pose gather2 = new Pose (124.86451612903225, 53.76129032258066, Math.toRadians(0));

    ///  put intake here


    ///come last
    public final Pose gather1 = new Pose (128,79, Math.toRadians(0));
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));




    private Path scorepres, intake1, score1, posit1, intake2, kaimen, score2, posit2, intake3, score3, posit3, intake4, score4;
    private Path curve3spikemark, curve2spikemark;

    private BezierPoint kaimenPoint = new BezierPoint(new Pose(128.26,66,Math.toRadians(271.91)));

    public void buildPaths() {
        scorepres = new Path(new BezierLine(startpose, all_scores));
        scorepres.setLinearHeadingInterpolation(startpose.getHeading(), all_scores.getHeading());

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

