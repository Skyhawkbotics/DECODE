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
import org.firstinspires.ftc.teamcode.core.commands.Prep_Shooter_Far;
import org.firstinspires.ftc.teamcode.core.commands.Shooter_off;

import ftclib.FollowPath;
import ftclib.OpModeCommand;

@Autonomous(name = "Simple Far Red", group = "Red")
public class simplefarred extends OpModeCommand {
    Robot bot;
    public static final Pose startpose = new Pose(87.6,8.7, Math.toRadians(90));
    public static final Pose shootpose = new Pose(91.3,13, Math.toRadians(60));
    public final Pose presets = new Pose (102.5,14.5,Math.toRadians(90));


    private Path gotoscore, leavelaunch;
    public void buildPaths (){
        gotoscore = new Path (new BezierLine(startpose,shootpose));
        gotoscore.setLinearHeadingInterpolation(startpose.getHeading(),presets.getHeading());
        
        leavelaunch = new Path (new BezierLine(shootpose, presets));
        leavelaunch.setLinearHeadingInterpolation(shootpose.getHeading(),presets.getHeading());
    }

    public void initialize (){
        buildPaths();
        bot = new Robot (hardwareMap,telemetry,startpose);
        schedule (
                new RunCommand(bot ::autonomous_robot_update),
                new SequentialCommandGroup(
                        new FollowPath(bot.getFollower(), gotoscore)
                                .alongWith(
                                        new Prep_Shooter_Far(bot))
                                .andThen(
                                        new Intake_on(bot) //firingvvbhub
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), leavelaunch)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

                )
        );
    }
}
