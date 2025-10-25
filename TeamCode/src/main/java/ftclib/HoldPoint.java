package ftclib;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierPoint;

public class HoldPoint extends CommandBase {

    private final BezierPoint holdPoint;
    private final double heading;
    private final Follower follower;


    public HoldPoint(Follower follower, BezierPoint holdPoint, double heading) {
        this.follower = follower;
        this.holdPoint = holdPoint;
        this.heading = heading;
    }
/*
    public HoldPoint(Follower follower, Point holdPoint, double heading) {
        this(follower, new BezierPoint(holdPoint), heading);
    }

    public HoldPoint(Follower follower, Pose holdPose) {
        this(follower, new BezierPoint(new Point(holdPose)), holdPose.getHeading());
    }


 */
    @Override
    public void initialize() {
        follower.holdPoint(holdPoint, heading);
    }

    @Override
    public void end(boolean interrupted) {
        follower.breakFollowing();
    }
}