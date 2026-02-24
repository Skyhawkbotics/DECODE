package org.firstinspires.ftc.teamcode.core.subsystems;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.arcrobotics.ftclib.geometry.Vector2d;
import com.pedropathing.geometry.Pose;

public class VelocityCalculation {
    Pose lastPose = follower.getPose();
    long lastTime = System.nanoTime();

    public Vector2d getVelocity() {

        Pose currentPose = follower.getPose();
        long currentTime = System.nanoTime();

        double dt = (currentTime - lastTime) / 1e9; // seconds

        double vx = (currentPose.getX() - lastPose.getX()) / dt;
        double vy = (currentPose.getY() - lastPose.getY()) / dt;

        lastPose = currentPose;
        lastTime = currentTime;

        return new Vector2d(vx, vy);
    }
}
