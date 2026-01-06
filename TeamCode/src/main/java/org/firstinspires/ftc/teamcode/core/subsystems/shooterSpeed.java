package org.firstinspires.ftc.teamcode.core.subsystems;

import com.pedropathing.geometry.Pose;

public class shooterSpeed {
    public shooterSpeed() {
    }

    public double calcVelocity(double d) {

        double GRAVITY = 9.80665; // gravitational constant
        double VERTICAL_DISTANCE = 0.741; // 1.041 - 0.300
        ///  remeasured constants: 0.3 m for robot flywheel
        /// 0.99 for depot
        ///  old : 0.661  // 1.041 - 0.381
        ///  new = 0.69??

        double FLYWHEEL_RADIUS = 0.0457; // used for linear velocity -> angular velocity
        double PI = 3.14159265358979234;
        double shooter_constant = 1.15;
        double dampening_constant = 0.96;
        double sharpening_constant = 1.016;

        double degrees = 60 - 7 * (d - 1.5); // FROM THE HORIZONTAL
        double angle = Math.toRadians(degrees);

        double numerator = GRAVITY * Math.pow(d, 2); //
        double denominator = 2 * Math.pow(Math.cos(angle), 2) *
                (d * Math.tan(angle) - VERTICAL_DISTANCE);
        double velocity_meters = Math.sqrt(numerator/denominator);
        double velocity_rpm = (velocity_meters * 60) / (2 * PI * FLYWHEEL_RADIUS);
        /*
        if (d < 4) {
            velocity_rpm = velocity_rpm * dampening_constant;
        } else if (d > 4) {
            velocity_rpm = velocity_rpm * sharpening_constant;
        } */


        // does not account for drag. because drag will reduce the distance it goes, this
        // velocity should be increased by a coefficient, maybe of like 1.05 or something minor.
        return velocity_rpm * shooter_constant;
    }

    public double calcAngle(double d) {
        double degrees = 60 - 7 * (d - 1.5);
        // this is based off of regression
        // 30 degrees for 1 position
        // 0.1 change in position is equal to change in 3 degrees
        double servo_position = - 0.85 / 30 * degrees + 1.7;
        return servo_position;

    }

}