package org.firstinspires.ftc.teamcode.Test_Files.inspo;



import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (name = "Intake Test")

@Disabled
///  disables it so you dont see it on the driver hub (we dont want it to clutter up our screen)
public class Example_Subsystem_test_file_Active_Intake extends OpMode { /// extends opmode like a regular TELE-OP



/// Declares the subsystem as a object
///
/// Public (subsystem name) (name you want to call the subsystem)

    public Example_Subsystem_Active_Intake in;


    @Override
    public void init() {


        /// init the subsystem using the constructor method
        /// What does the constructor method need.?
        /* init method in the subsystem constructor
        public Example_Subsystem_Active_Intake(HardwareMap hardwareMap, Telemetry eric) {
        in = hardwareMap.get(DcMotorEx.class, "I");
        intake_rotate_right = hardwareMap.get(Servo.class, "IR");
        intake_rotate_left = hardwareMap.get(Servo.class, "IL");
        color = hardwareMap.get(ColorSensor.class,"CS");
        hatch = hardwareMap.get(Servo.class, "ISH");

        in.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_rotate_left.setDirection(Servo.Direction.REVERSE);

        in.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        this.telemetry = eric;
          }
         */
        /// Needs an object that is type hardwareMap and type Telemetry

        in = new Example_Subsystem_Active_Intake(hardwareMap, telemetry);

    }

    @Override
    public void loop() {

        /// Binding the functions to gamepad inputs

        /// RISING EDGE DETECTOR detects when it is PRESSED THE FIRST TIME, therefore only runs once per button press
        if(gamepad1.dpadDownWasPressed()) {
            in.down();
        } else if (gamepad1.dpadUpWasPressed()) {
            in.up();
        }


        /// THIS CHECKS CONTINUOUSLY if it is PRESSED , THEREFORE it will run CONTINUOUSLY WHILE IT IS BEING PRESSED
        /// If this is a toggle, it will continuously cycle between on and off
        if(gamepad1.right_trigger > 0.4) {
            in.intake_full();
        }
        else if(gamepad1.left_trigger > 0.4) {
            in.barf();
        }
        else { /// stays in idle if nothing is happening
            in.idle();
        }


    }
}
