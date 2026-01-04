package org.firstinspires.ftc.teamcode.unused;
/*
public class whole_robot {
/*
    private Shooter shooter;

    private Elevator elevator;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Follower follower;
    public Pose startPose = new Pose(56   ,8, Math.toRadians(90));


    private Gamepad g1, g2, dummyg1, dummyg2, prevg1, prevg2;

    public whole_robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad game1, Gamepad game2) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);
        this.dummyg1 = game1;
        this.dummyg2 = game2;
        shooter = new Shooter(hardwareMap, telemetry);
        elevator = new Elevator(hardwareMap, telemetry);
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.prevg1 = new Gamepad();
        this.prevg2 = new Gamepad();
        follower.setStartingPose(startPose);
        follower.startTeleopDrive();
        follower.update();


    }
    public whole_robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        follower = Constants.createFollower(hardwareMap);

        shooter = new Shooter(hardwareMap, telemetry);
        shooter.positionMax();


    }


    public void solo_controls() {
        g1.copy(dummyg1);
        g2.copy(dummyg2);
        follower.setTeleOpDrive(-dummyg1.left_stick_y, -dummyg1.left_stick_x, -dummyg1.right_stick_x * 0.4, false);
        follower.update();



      /*  if (dummyg2.bWasPressed()) {
            shooter.to_zero();
        }
        if (dummyg2.yWasPressed()) {
            ///Increase the position 0.1
            shooter.increasePosition();
        }
        if (dummyg2.xWasPressed()) {
            // Decrease the position 0.1
            shooter.decreasePosition();
        }


        ///  to the max angles

        if(dummyg1.right_bumper) {
            elevator.on();
        } else {
            elevator.off();
        }
        if(dummyg2.rightBumperWasPressed()) {
            shooter.shooterON();
        }


        if (dummyg2.squareWasPressed()) {
            shooter.closest();
        }
        if (dummyg2.triangleWasPressed()) {
            shooter.middle();
        }
        if (dummyg2.circleWasPressed()) {
            shooter.perf_velocity();
        }
        if (dummyg2.crossWasPressed()) {
            shooter.back_perf();
        }

    }

        public void robot_update () {
            shooter.shooter_telemetry();
            shooter.update();
            telemetry.update();
            follower.update();


        }
        public Follower getFollower() {
        return follower;
        }
        public Shooter getShooter() {
        return shooter;
        }
        public Elevator getElevator() {
        return elevator;
        }
        public Telemetry getTelemetry() {
        return telemetry;
        }



}


 */
