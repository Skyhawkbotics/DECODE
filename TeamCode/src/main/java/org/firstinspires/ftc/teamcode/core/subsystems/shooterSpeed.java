package org.firstinspires.ftc.teamcode.core.subsystems;

public class shooterSpeed {
    public shooterSpeed() {
    }

    public double calcVelocity(double d) {

        double GRAVITY = 9.81; // gravitational constant
        double VERTICAL_DISTANCE = 0.661; // 1.041 - 0.381
        double FLYWHEEL_RADIUS = 0.0457;
        double PI = 3.141592653589;

        double degrees = 60; // - 8 * (d - 1.5); // FROM THE HORIZONTAL
        double angle = Math.toRadians(degrees);

        double numerator = GRAVITY * Math.pow(d, 2); //
        double denominator = 2 * Math.pow(Math.cos(angle), 2) * (d * Math.tan(angle) - VERTICAL_DISTANCE);
        double velocity_meters = Math.sqrt(numerator/denominator);
        double velocity_rpm = (velocity_meters * 60) / (2 * PI * FLYWHEEL_RADIUS);


        // does not account for drag. because drag will reduce the distance it goes, this
        // velocity should be increased by a coefficient, maybe of like 1.05 or something minor.
        return velocity_rpm * 1.3 / 1.1;
    }

    public double calcAngle(double d) {
        double degrees = 60 - 8 * (d - 1.5);
        // 30 degrees for 1 position
        // 0.1 change in position is equal to change in 3 degrees
        double servo_position = 2 - degrees / 30;
        return servo_position;

    }
}