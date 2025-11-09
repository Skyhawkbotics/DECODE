package org.firstinspires.ftc.teamcode.unused;

import static org.firstinspires.ftc.teamcode.core.constants.*;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class spindexer {
    private Servo axon_spindex, fire;

    private AnalogInput axon_encoder;
    private DistanceSensor distance_left, distance_right;

    public spindexer(HardwareMap hardwareMap, Telemetry telemetry) {
        axon_encoder = hardwareMap.get(AnalogInput.class, "encoder");
        axon_spindex = hardwareMap.get(Servo.class, "axon");
        fire = hardwareMap.get(Servo.class, "fire");
        distance_left = hardwareMap.get(DistanceSensor.class, "left");
        distance_right = hardwareMap.get(DistanceSensor.class, "right");
    }
    public void set_index1() {
        axon_spindex.setPosition(index1);
    }
    public void set_index2() {
        axon_spindex.setPosition(index2);
    }
    public void set_index3() {
        axon_spindex.setPosition(index3);
    }
    public boolean spindexer_Is_not_Busy() {
        double right = distance_right.getDistance(DistanceUnit.MM);
        double left  = distance_left.getDistance(DistanceUnit.MM);
        boolean right_true = (Math.abs(right - target_distance) < distance_tolerance);
        boolean left_true = (Math.abs(left - target_distance) < distance_tolerance);

        return right_true == left_true;

    }
    public Object spindexer_IsBusy(double value) {
        return null;
    }
    public void fire_dependent() {
        if(spindexer_Is_not_Busy()) {
            fire.setPosition(fire_ball);
        }
    }
}
