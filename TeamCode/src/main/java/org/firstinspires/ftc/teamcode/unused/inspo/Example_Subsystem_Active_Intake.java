package org.firstinspires.ftc.teamcode.unused.inspo;




/// Imports the Necessary Constants from the Constants file

import static org.firstinspires.ftc.teamcode.unused.inspo.Exapmle_Robot_Constants.barf_speed;
import static org.firstinspires.ftc.teamcode.unused.inspo.Exapmle_Robot_Constants.down_pos;
import static org.firstinspires.ftc.teamcode.unused.inspo.Exapmle_Robot_Constants.intake_speed;
import static org.firstinspires.ftc.teamcode.unused.inspo.Exapmle_Robot_Constants.up_pos;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Example_Subsystem_Active_Intake {
    ///Notice how this class does not extend Opmode, since it is not a runnable file. Instead the subsystem contains the info to run everything in a organized way
    /// This subsystem will have functions to initialize the subsystem, and functions that dictate how it can be controlled
    ///


    /// STEP 1:
    /// Declare your variables
    /// I like to keep them public but I think they can also be private.
    /// Format: public (thing) (thing name)
    /// public DcMotor (its a motor) , calling it in (for intake motor)
    /// I usually use DcMotorEx because it has the most features
    public DcMotorEx in;

    public ColorSensor color;

    public Servo intake_rotate_right, intake_rotate_left, hatch;


    /// Declare Telemetry. This is useful later and will need to get initialized.
    /// We will use telemetry to create a function that stores all the telemetry needed for the subsystem
    /// This avoids having to pull Individual properties in the main Opmode to check it's telemetry, we just call the function which has all of them
    private Telemetry telemetry;



    /// This is the constructor method. It initializes the entire CLASS. Therefore, we need to call it the same name as the class name
    /// Public (class name) ((parameters)) {
    /// init stuff
    ///
    /// }
    /// Parameters - HardwareMap hardwareMap, Telemetry telemetry
    /// In order to initialize our motors/servos, we need the hardwareMap. We also want to init the telemetry.
    /// When this constructor is called, it requires now two things, A object that is type hardwareMap and a object that is type telemetry
    ///
    ///
    /// Format for parameters ((OBJECT TYPE) (name of object so it can be referenced in the method) , etc
    ///
    public Example_Subsystem_Active_Intake(HardwareMap hardwareMap, Telemetry eric) { // init method

        /// More on parameters. You can change the name of the reference to anything, it just needs to have the type.
        /// Here I changed from same name as in HardwareMap hardwareMap to telemetry eric. It now has to be referenced as "eric" in the init

        in = hardwareMap.get(DcMotorEx.class, "I");
        intake_rotate_right = hardwareMap.get(Servo.class, "IR");
        intake_rotate_left = hardwareMap.get(Servo.class, "IL");
        color = hardwareMap.get(ColorSensor.class,"CS");
        hatch = hardwareMap.get(Servo.class, "ISH");

        /// Reverse motor/servo directions
        in.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_rotate_left.setDirection(Servo.Direction.REVERSE);

        /// set mode
        in.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);





        this.telemetry = eric; /// SEE

    }



    /// These are functions of the subsystem. it controls what the subsystem can do.
    /// Format is public(should be accessible by other classes) void (since it doesn't return anything) (name of function) () (no parameters usually) {
    ///
    ///  what the individual motors / servos do
    /// }
    public void intake_full() {
        in.setPower(intake_speed);
    }
    public void barf() {
        in.setPower(barf_speed);
    }
    public void idle() {
        in.setPower(0);
    }

    public void down() {
        intake_rotate_left.setPosition(down_pos);
        intake_rotate_right.setPosition(down_pos);
    }
    public void up() {
        intake_rotate_right.setPosition(up_pos);
        intake_rotate_left.setPosition(up_pos);
    }
    public void up_half() {
        intake_rotate_right.setPosition(0.7);
        intake_rotate_left.setPosition(0.7);
    }

    public void detectcolor() {
        if (color.blue() > 200) {
            hatch.setPosition(1);
        }
    }


    /// Telemetry. this can then be called in the opmode as a function to showcase the subsystems telemetry
    public void intake_telemetry() {
        telemetry.addData("intake motor power", in.getPower());
        telemetry.addData("intake rotate left", intake_rotate_left.getPosition());
        telemetry.addData("intake rotate right", intake_rotate_right.getPosition());
    }
}
