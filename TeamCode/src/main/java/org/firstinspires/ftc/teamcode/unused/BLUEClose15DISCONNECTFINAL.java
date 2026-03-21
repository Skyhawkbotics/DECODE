package org.firstinspires.ftc.teamcode.unused;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import ftclib.OpModeCommand;

@Autonomous(name = "Alliance Blue", group = "Blue")
@Disabled
public class BLUEClose15DISCONNECTFINAL extends OpModeCommand {
    @Override
    public void initialize() {

    }
    /*


    Robot bot;
    public static final Pose startpose = new Pose  (128,117, Math.toRadians(40));
    public static final Pose bluestartpose = startpose.mirror();

    /// Main ScorePose
    public final Pose all_scores = new Pose (91,82, Math.toRadians(0));

    public final Pose blueall_scores = all_scores.mirror();


    /// Intake Spike Mark Two,

    public final Pose gather2 = new Pose (128.86451612903225, 51.76129032258066, Math.toRadians(0));

    public final Pose bluegather2 = gather2.mirror();
    public final Pose kaimencontrolpoint =  new Pose(113.24977274928074,61.395258896534415);

    public final Pose bluekaimencontrolpoint =  kaimencontrolpoint.mirror();

    public final Pose opengate = new Pose (135.28,65, Math.toRadians(0));
    public final Pose blueopengate = opengate.mirror();
    public final Pose opengateREAL = new Pose (131.28,65, Math.toRadians(0));
    public final Pose blueopengateREAL = opengateREAL.mirror();

    public final Pose kaimen_back_control_point =  new Pose(103,59);
    public final Pose bluekaimen_back_control_point =  kaimen_back_control_point.mirror();
    public final Pose kaimen_back_control_point1 =  new Pose(102,64);
    public final Pose bluekaimen_back_control_point1 =  kaimen_back_control_point1.mirror();


    ///  put intake here

    public final Pose gateintake1 = new Pose(125, 59, Math.toRadians(30));
    public final Pose bluegateintake1 = gateintake1.mirror();
    public final Pose gateintakeFINAL = new Pose(139, 58.5, Math.toRadians(30));

    public final Pose bluegateintakeFINAL = gateintakeFINAL.mirror();

    public final Pose gateintakeFINAL1 = new Pose(139, 57, Math.toRadians(30));
    public final Pose bluegateintakeFINAL1 = gateintakeFINAL1.mirror();


    ///come last
    public final Pose gather1 = new Pose (127.5,79, Math.toRadians(0));
    public final Pose bluegather1 = gather1.mirror();
    public final Pose last_shot = new Pose (108.85,97.1, Math.toRadians(0));
    public final Pose bluelast_shot = last_shot.mirror();
    public final Pose curve2spikemarkcontrolpoint = new Pose(89.23475352731101, 50.477832094139686);

    public final Pose bluecurve2spikemarkcontrolpoint = curve2spikemarkcontrolpoint.mirror();


    private Path scorepres, intake1, score1, score2;
    private PathChain kaimen;
    private Path curve2spikemark;


    //
    private Path get_to_gate_intake_FIRST, gate_intake, gate_intake_back, get_to_gate_intake, gate_intake_backwards;
    private PathChain openGate;
    private Path park;

    public void buildPaths() {
        /// FIRST Preload Firing

        scorepres = new Path(new BezierLine(bluestartpose, blueall_scores));
        scorepres.setLinearHeadingInterpolation(bluestartpose.getHeading(), blueall_scores.getHeading());

        ///  Intakes the 2nd Spike Mark

        curve2spikemark = new Path(
                new BezierCurve(
                        blueall_scores,
                        bluecurve2spikemarkcontrolpoint,
                        bluegather2
                )
        );
        curve2spikemark.setConstantHeadingInterpolation(bluegather2.getHeading());

        ///  Open Gate

        kaimen = bot.getFollower().pathBuilder().addPath(
                        new BezierCurve(
                                bluegather2,
                                bluekaimencontrolpoint,
                                blueopengate)
                )
                .setLinearHeadingInterpolation(bluegather2.getHeading(),blueopengate.getHeading())
                .build();


        ///  Score Spike Mark Two
        score2 = new Path (new BezierCurve(blueopengateREAL,
                bluekaimen_back_control_point,
                blueall_scores));
        score2.setLinearHeadingInterpolation(blueopengateREAL.getHeading(), blueall_scores.getHeading());

        /// Travel to Gate Intake

        get_to_gate_intake_FIRST = new Path(
                new BezierCurve(
                        blueall_scores,
                        bluekaimen_back_control_point1,
                        bluegateintake1
                )
        );
        get_to_gate_intake_FIRST.setLinearHeadingInterpolation(blueall_scores.getHeading(), bluegateintake1.getHeading());

        ///  Gate Intake

        openGate = bot.getFollower().pathBuilder().addPath(
                        new BezierLine(
                                bluegateintake1,
                                bluegateintakeFINAL
                        )
                )
                .setLinearHeadingInterpolation(bluegateintake1.getHeading(),bluegateintakeFINAL.getHeading())
                .addPath(
                        new BezierLine(
                                bluegateintakeFINAL,
                                bluegateintakeFINAL1
                        )
                )
                .setLinearHeadingInterpolation(bluegateintakeFINAL.getHeading(), bluegateintakeFINAL1.getHeading())
                .build();

        gate_intake = new Path(new BezierLine(
                bluegateintake1,
                bluegateintakeFINAL
        ));
        gate_intake.setConstantHeadingInterpolation(bluegateintakeFINAL.getHeading());

        gate_intake_backwards = new Path(new BezierLine(
                bluegateintakeFINAL,
                bluegateintakeFINAL1
        ));
        gate_intake_backwards.setConstantHeadingInterpolation(bluegateintakeFINAL.getHeading());

        ///  Go back to score Position
        gate_intake_back = new Path(new BezierCurve(
                bluegateintakeFINAL1,
                bluekaimen_back_control_point1,
                blueall_scores
        ));
        gate_intake_back.setLinearHeadingInterpolation(bluegateintakeFINAL.getHeading(),blueall_scores.getHeading());
        gate_intake_back.setBrakingStart(2.5);

        /// Go back to gate intake
        get_to_gate_intake = new Path(
                new BezierCurve(
                        blueall_scores,
                        bluekaimen_back_control_point1,
                        bluegateintake1
                )
        );
        get_to_gate_intake.setLinearHeadingInterpolation(blueall_scores.getHeading(), bluegateintake1.getHeading());

//  Spike Mark 1 Intake, Will be intaked Last
        intake1 = new Path (new BezierLine(blueall_scores, bluegather1));
        intake1.setLinearHeadingInterpolation(blueall_scores.getHeading(),bluegather1.getHeading());

        score1 = new Path(new BezierLine(bluegather1, bluelast_shot));
        score1.setLinearHeadingInterpolation(bluegather1.getHeading(), bluelast_shot.getHeading());
        park = new Path(
                new BezierLine(
                        bluelast_shot,
                        new Pose(35 , 129)
                )
        );
        park.setConstantHeadingInterpolation(Math.toRadians(90));


    }

    public void initialize() {
        bot = new Robot(hardwareMap, telemetry,bluestartpose, 20);
        buildPaths();

        schedule(
                new RunCommand(bot:: blue_autonomous_robot_update),
                new SequentialCommandGroup(
//              CYCLE 1, Preloads
                        new FollowPath(bot.getFollower(), scorepres, 0.8)
                                .alongWith(
                                        new Prep_Shooter4(bot)
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

//              CYCLE 2, Second Spike Mark
                                .andThen(
                                        new FollowPath(bot.getFollower(), curve2spikemark,.8)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
         //              CYCLE 3, Gate Opening
                                .andThen (
                                        new FollowPath(bot.getFollower(), kaimen,true,0.75)
                                                .raceWith(new WaitCommand(3300))
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )

                                .andThen(
                                        new WaitCommand(800)
                                )

                                .andThen (
                                        new FollowPath(bot.getFollower(), score2, 0.65)
                                                .alongWith(new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )

//              CYCLE 4, Gate Opening FIRST TIME
                                .andThen(
                                        new FollowPath(bot.getFollower(), get_to_gate_intake_FIRST, 0.8)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                /* .andThen(
                                         new FollowPath(
                                                 bot.getFollower(),
                                                 openGate
                                         )
                                 )





                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake, 0.8)
                                                .raceWith(new WaitCommand(800))
                                )
                                .andThen(
                                        new WaitCommand(100)
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake_backwards)
                                                .raceWith(new WaitCommand(600))
                                )


                                .andThen(
                                        new WaitCommand(2000)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )

                                .andThen (
                                        new FollowPath(bot.getFollower(), gate_intake_back, 0.8)
                                                .alongWith(
                                                        new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(500)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )


/*
//              CYCLE 5, Gate Opening Second Time
                                .andThen(
                                        new FollowPath(bot.getFollower(), get_to_gate_intake)
                                                .alongWith(new Intake_on_forever(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake)
                                                .raceWith(new WaitCommand(800))
                                )

                                .andThen(
                                        new FollowPath(bot.getFollower(), gate_intake_backwards)
                                                .raceWith(new WaitCommand(600))

                                )


                                .andThen(
                                        new WaitCommand(1500)
                                )
                                .andThen(
                                        new Intake_off(bot)
                                )


                                .andThen (
                                        new FollowPath(bot.getFollower(), gate_intake_back)
                                                .alongWith(
                                                        new Prep_Shooter4(bot))
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
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





//              CYCLE 6, Intaking Spike Mark One
                                .andThen(
                                        new FollowPath(bot.getFollower(), intake1, 0.7)
                                                .alongWith(new Intake_on_long1(bot))
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), score1)
                                                .alongWith(new Prep_Shooter2(bot))
                                )
                                .andThen(
                                        new turret_LL_blue(bot)
                                )
                                .andThen(
                                        new WaitCommand(750)
                                )
                                .andThen(
                                        new Intake_on(bot)
                                )
                                .andThen(
                                        new Shooter_off(bot)
                                )
                                .andThen(
                                        new FollowPath(bot.getFollower(), park, 0.5)
                                )
                                .andThen(
                                        new turret_Zero(bot)
                                )





                ));
    }
    */
}

