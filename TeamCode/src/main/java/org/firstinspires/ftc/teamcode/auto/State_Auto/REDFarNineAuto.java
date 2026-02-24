package org.firstinspires.ftc.teamcode.auto.State_Auto;


import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.Robot;

import ftclib.OpModeCommand;

@Autonomous(name = "RED NINE FAR", group = "Test")
public class REDFarNineAuto extends OpModeCommand {
    Robot bot;
    public static final Pose startpoes = new Pose(56, 8, Math.toRadians(90));

    public final Pose preloadgreen = new Pose (56,16, Math.toRadians(114));

    public final Pose loadprep = new Pose (44,35.6);

    public final Pose firstintake = new Pose (12.8, 35.5, Math.toRadians(180));

    public final P ose greengiant = new Pose (56, 15.2, Math.toRadians(113));

    public final Pose humanin = new Pose (10.5, 9.6);

    public final Pose curry = new Pose (56,16, Math.toRadians(113));

    public final Pose beepbeep = new Pose (39, 15, Math.toRadians(0));

    private Path scorepreset1, loadtres, inwithballs,greenbean, ninthballs, nightnight, park;

    public void buildPaths() {
        scorepreset1 = new Path (new BezierLine(startpoes, preloadgreen));
        scorepreset1.setLinearHeadingInterpolation(startpoes.getHeading(), preloadgreen.getHeading());

        loadtres = new Path (new BezierLine(preloadgreen, new Pose (55.4, 29.8), loadprep));
        loadtres.setLinearHeadingInterpolation(preloadgreen.getHeading(), loadprep.getHeading());
    }

}
