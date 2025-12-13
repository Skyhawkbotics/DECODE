package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.core.commands.Intake_on;
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Simple Far Red", group = "Red")
public class simplefarred extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose(87.6,8.7, Math.toRadians(90));
    public final Pose presets = new Pose (102.5,14.5,Math.toRadians(90));


    private Path leavelaunch;
    public void buildPaths (){
        leavelaunch = new Path (new BezierLine(startpose,presets));
        leavelaunch.setLinearHeadingInterpolation(startpose.getHeading(),presets.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot (hardwareMap,telemetry,startpose);
        schedule (
                new RunCommand(bot ::robot_update),
                new SequentialCommandGroup(
                        new Prep_Shooter(bot)
                                .andThen(
                                        new Intake_on(bot) //firing
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), leavelaunch)
                                )

                )
        );
    }
}
