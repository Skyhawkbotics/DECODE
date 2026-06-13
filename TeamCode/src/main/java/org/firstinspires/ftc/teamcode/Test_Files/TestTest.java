package org.firstinspires.ftc.teamcode.Test_Files;


import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.auto.canada.commands.Fire_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_Timed_Command;
import org.firstinspires.ftc.teamcode.auto.canada.commands.Intake_on_automatic;
import org.firstinspires.ftc.teamcode.auto.canada.commands.prep_Shooter_preload;
import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.general_commands.Prep_Shooter_any;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "TEST TEST TESt ", group = "Solo", preselectTeleOp = "18 CLOSE Red Drive")
@Configurable
@Disabled
public class TestTest extends OpModeCommand {


    Robot bot;
    private Pose redCloseStartPose = new Pose(52.000, 70.000, Math.toRadians(0));
    private Pose preloadScorePose = new Pose(89, 87, Math.toRadians(310));
    private Pose intakeSpike2Pose = new Pose(129, 62, Math.toRadians(0));
    private Pose shootPose = new Pose(87, 77);
    private static Pose beforeGateIntakePose = new Pose(120, 61);
        private static Pose gateIntakePose = new Pose(134,66, Math.toRadians(25));

    private Pose intakeSpike1Pose = new Pose(124, 84, Math.toRadians(0));
    private Pose shootPose2 = new Pose(93, 84);
    private Pose intakeSpike3Pose = new Pose(129, 34, Math.toRadians(0));
    private Pose park = new Pose(91, 16);





    private Pose controlPoint1Pose = new Pose(124, 119);

    private Pose controlPoint2Pose = new Pose(88, 63);

    private Pose controlPoint3Pose = new Pose(102, 65);
    private Pose controlPoint4Pose =  new Pose(99, 52);
    private Pose controlPoint5Pose =  new Pose(97,84);
    private Pose controlPoint6Pose =  new Pose(93.33030852994557, 29.818511796733212);











    private PathChain preload_spike2, gateIntake1, return1, spike1, spike3;


    public void buildPaths() {
        preload_spike2 = bot.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(52.000, 70.000),
                                new Pose(94.000, 70.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        new BezierLine(
                                new Pose(94.000, 70.000),
                                new Pose(112.000, 70.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(30))
                .build();


    }



    public void initialize() {
        bot = new Robot(hardwareMap, telemetry, redCloseStartPose);
        buildPaths();

        schedule(
                new RunCommand(bot::drivetrain_update),

                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), preload_spike2, true)
                )

        );





///  auto cant run at 12.6 volts?? turret takes about 2 volts, shooters takes about like 2 volts

    }
}
