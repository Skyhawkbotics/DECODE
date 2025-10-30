package org.firstinspires.ftc.teamcode.core.subsystems;

public class shooterSpeed {
    public shooterSpeed() {
    }

    public double velocity(double d) {

        double GRAVITY = 9.81; // gravitational constant
        double VERTICAL_DISTANCE = 0.661; // 1.041 - 0.381
        double degrees = 60 - 8 * (d - 1.5); // FROM THE HORIZONTAL
        double angle = Math.toRadians(degrees);
        double numerator = GRAVITY * Math.pow(d, 2); //
        double denominator = 2 * Math.pow(Math.cos(angle), 2) * (d * Math.tan(angle) - VERTICAL_DISTANCE);
        double velocity = Math.sqrt(numerator/denominator);

        return velocity;
    }
}
