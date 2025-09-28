package org.firstinspires.ftc.teamcode.inspo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


/// FIRST STEP IS SIGNIFYING IF IT IS @AUTO or @TELE OP, and then naming it.
/// This is so on the driver hub it will show up on the right side dropdown (TELE OP) or Left side (AUTO) and be named the name in " "
///
///  Name of file:Example_Motor_Test_Basic_Opmode
/// Name of Tele op on driver hub : Motor Test
/// On tele op or auto dropdown : @TeleOp
/// Tele Ops and Autos with the same group will appear next to each other
@TeleOp (name = "Motor Test", group = "Tests")

public class Example_Motor_Test_Basic_Opmode extends OpMode { /// EXTENDS OPMODE since it is a opmode

    /// STEP 1:
    /// Declare your variables
    /// I like to keep them public but I think they can also be private.
    /// Format: public (thing) (thing name)
    /// public DcMotor (its a motor) , calling it in (for intake motor)
    /// I usually use DcMotorEx because it has the most features
    private DcMotorEx motor;
//ee
    @Override
    public void init() {  ///  java requires a lot of inits
        motor = hardwareMap.get(DcMotorEx.class, "motor"); /// Init the motor , by using hardwareMap to
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); /// Resets encoder so it will start recording at 0 position each time
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); /// sets it to run without PID modes such as at a set velocity in ticks per second or to a specific position

    }

    @Override
    public void loop() {

        /// Telemetry : feedback to the driver hub. Format:
        /// Format: telemetry.addData(" (text describing what it is) " , (value wanting to display) ;
        telemetry.addData("Motor current power", motor.getPower());
        telemetry.addData("Motor Current position", motor.getCurrentPosition());
        /// make sure to update it
        telemetry.update();



        /// gamepad joy stick has values from -1 to 1 (pointing all the way or  up)
        if(gamepad1.left_stick_y > 0.1) { /// if the gamepad1.left stick is pointing up
            motor.setPower(gamepad1.left_stick_y); /// sets the power to the motor, meaning that as i move the joystick up it will have more power = faster
        } /// c click / hover the methods to learn about parameters and purpose


        /// you can increase the value to have tolerances (example 0.5 would mean it wouldn't activate until the joystick is halfway up. I just use 0.1 since i want more sensitive controls

        if(gamepad1.left_stick_y > -0.1) { /// if the gamepad1.left stick is pointing down
            motor.setPower(gamepad1.left_stick_y);
        }




    }
}
