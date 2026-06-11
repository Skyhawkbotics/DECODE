package org.firstinspires.ftc.teamcode.core.subsystems;

import com.pedropathing.geometry.Pose;
import static org.firstinspires.ftc.teamcode.core.constants.*;

public class shooterSpeed {
    double shooterC = shooter_constant;
    double sharpenC = sharpening_constant;
    double dampenC = dampening_constant;
    public shooterSpeed() {
    }

    ///  I want to be able to increase or decrease the shooterconstant using gamepad inputs
    /// I need an update loop
    public double calcVelocity(double d, boolean farZone) {
        // i'm trying to make sure I can edit it on the panels and so it will update. Each time it calcualtes it has to reestablish the values so if they are changed in panels it should be changed in here as well? - eric 5/4


        double GRAVITY = 9.80665; // g (m/s^2)
        double VERTICAL_DISTANCE = 0.756; // 1.041 - 0.300 = 0.741 + a little for jazz
        ///  remeasured constants: 0.3 m for robot flywheel
        /// 0.99 for depot
        ///  old : 0.661  // 1.041 - 0.381
        ///  new = 0.69??

        double FLYWHEEL_RADIUS = 0.0457; // used for linear velocity -> angular velocity meters
        double PI = 3.14159265358979234;
      // prev 1.0375

        double degrees = 60 - 7 * (d - 1.5); // FROM THE HORIZONTAL
        double angle = Math.toRadians(degrees);


        double numerator = GRAVITY * Math.pow(d, 2); //
        double denominator = 2 * Math.pow(Math.cos(angle), 2) *
                (d * Math.tan(angle) - VERTICAL_DISTANCE);
        double velocity_meters = Math.sqrt(numerator/denominator);
        double velocity_rpm = (velocity_meters * 60) / (2 * PI * FLYWHEEL_RADIUS);

        if (farZone) {
            velocity_rpm *= sharpenC;
        }

        // does not account for drag. because drag will reduce the distance it goes, this
        // velocity should be increased by a coefficient, maybe of like 1.05 or something minor.
        return velocity_rpm * shooterC;
    }

    public double calcAngle(double d) {
        double degrees = 60 - 7 * (d - 1.5);

        // this is based off of regression
        // 30 degrees for 1 position
        // 0.1 change in position is equal to change in 3 degrees
        double servo_position = - 0.85 / 30 * degrees + 1.7;
        return servo_position;

    }
    public void updateShooterConstants() {
         shooterC = shooter_constant;
         sharpenC = sharpening_constant;
         dampenC = dampening_constant;
    }
    public void increaseShooterConstant() {
        shooter_constant = shooter_constant + 0.1;
    }
    public void decreaseShooterConstant() {
        shooter_constant = shooter_constant - 0.1;
    }
    public void increaseSharpeningConstant() {
        sharpening_constant = sharpening_constant + 0.1;
    }
    public void decreaseSharpeningConstant() {
        sharpening_constant = sharpening_constant - 0.1;
    }

}