package org.firstinspires.ftc.teamcode.unused;

///  Idea of turret: Two modes of correction
/// One using Limelight
/// One using the Heading of the robot vs the average heading of the depot
/// We can use the april tags to detect the depot then align using PID
/// We can use heading of the robot to then point to the depot knowing the depots direction
/// Example we want the turret to point forward no matter what direction we in
/// therefore the turret ideal pose is 0 degrees
/// However as the robot's heading changes we calculate the difference in headings
/// Example 50 degrees off (50)
/// The difference in headings is used to then calculate number of ticks it changed by
/// example 50 degrees is 148 ticks
/// then calculate the position it is now 0+148 = 148 ticks is the position
/// Move the turret to the position using a PID
/// pid.calculate(position, current position)
///
/// Then we add a way to use limelight as secondary correction?
/// maybe after a range like when the PID is outputting little to no correction
/// then we turn off the heading turret PID and then use the limelight PID
public class Turret {
    /*
    private LL limelight;
    public DcMotorEx turret;
    public Boolean usingHeadingCorrection;
    private PIDController heading_turret_pid;

    int LEFT_LIMIT = -500; // This is about 45 degrees
    int RIGHT_LIMIT = 500; // TICKS. RIGHT IS MORE TICKS, LEFT IS LESS TICKS

    public Turret(HardwareMap hardwareMap, Telemetry telemetry, int ID) {
      //  limelight = new LL(hardwareMap, telemetry);
        turret = hardwareMap.get(DcMotorEx.class, turret_name);

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);

        heading_turret_pid = new PIDController(turret_p, turret_i, turret_d);
        ID = ID;
    }

    public double calculate_difference(Pose followerPose) {
        double current_heading = Math.toDegrees(followerPose.getHeading());
        double difference = current_heading - turret_align_heading;
        return 0 + (difference * degrees_to_ticks);
    }

    public double targetBlue() {
        return 20;
    }

    public double targetRed() {
        return 24;
    }


    public int degreesToTicks(double degrees) {
        double ticksPerRev = 537.6;  // GET VALUES FOR THESE
        double gearRatio = 5.0;      // GET VALUES FOR THESE
        return (int)(degrees / 360.0 * ticksPerRev * gearRatio);
    }
    public void setTurretPower(double power) {
        int pos = turret.getCurrentPosition();

        if (pos <= LEFT_LIMIT && power < 0 ) {
            power = 0;
        } else if (pos >= RIGHT_LIMIT && power > 0) {
            power = 0;
        }

        turret.setPower(power);
         // ok so we can turn it off in necessary. How do you align it properly though?
        // manual button to realign?
    }

    public void setTurretTarget(double degrees) {
        int target = degreesToTicks(degrees);

        // clamp target inside limits
        target = Math.max(LEFT_LIMIT, Math.min(RIGHT_LIMIT, target));

        turret.setTargetPosition(target);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turret.setPower(0.5);
    }


    public int getPosition() {
        return turret.getCurrentPosition();
    }

    public double getPower() {
        return turret.getPower();
    }
    public void manual_move(Gamepad gamepad) {
        // limelight.LL_correction_OFF();
        usingHeadingCorrection = false;

        setTurretPower(gamepad.left_stick_x * 0.33);
    } // this could be for moving it away from limit

    public void zeroTurret() {
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void updateTurret(Telemetry telemetry, int ID) {


        telemetry.addData("Current ID", ID);

        double pid_output = limelight.turret_pid_update(telemetry, turret, ID);
        setTurretPower(pid_output);
        // return pid_output;
    }

     */
}
