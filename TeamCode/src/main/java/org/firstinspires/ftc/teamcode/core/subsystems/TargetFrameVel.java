package org.firstinspires.ftc.teamcode.core.subsystems;

import com.arcrobotics.ftclib.geometry.Vector2d;
import com.pedropathing.geometry.Pose;

public class TargetFrameVel {
    public double vAway;  // + = moving away from target
    public double vSide;  // + = moving left/CCW around target

    public TargetFrameVel(double vAway, double vSide) {
        this.vAway = vAway;
        this.vSide = vSide;
    }

    public TargetFrameVel getTargetRelativeVelocityAway(Pose robotPose, Vector2d velField, Vector2d target) {
    
        double dx = target.getX() - robotPose.getX();
        double dy = target.getY() - robotPose.getY();
    
        double r = Math.hypot(dx, dy);
        if (r < 1e-6) {
            return new TargetFrameVel(0.0, 0.0);
        }
    
        // Unit vector from robot -> target
        double ux = dx / r;
        double uy = dy / r;
    
        // Left/CCW perpendicular
        double px = -uy;
        double py =  ux;
    
        double vx = velField.getX();
        double vy = velField.getY();
    
        double vToward = vx * ux + vy * uy;
        double vAway   = -vToward;
        double vSide   = vx * px + vy * py;
    
        return new TargetFrameVel(vAway, vSide);
    }
}
