package org.firstinspires.ftc.teamcode.core.commands.general_commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Prep_Shooter_any_preload extends CommandBase {
    ///  This command will set a shooter speed and servo pos that you give it
    /// this replaces need for creation of new commands for new velocity/angles
    /// no more repetitiveness! 2/20/2026
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();
    private int speed;
    private double servoPos;


    public Prep_Shooter_any_preload(Robot bot, int speed, double servoPos) { // runs the Command
        this.bot = bot;
        this.speed = speed;
        this.servoPos = servoPos;
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        bot.getShooter().update();

        switch (state) {
            case -1:
                break;
            case 1:
                bot.getShooter().shooterON();
                bot.getIntaking().servosOut();
                bot.getShooter().autonomous_speed(speed);
                bot.getShooter().setPos(servoPos);
                setState(2);
                break;
            case 2:
                if (bot.getShooter().velocityReached()) { //1
                    bot.getIntaking().on();
                    setState(35);
                }
                break;
            case 35:
                if(timer.getElapsedTimeSeconds() > 0.2) {
                    setState(3);
                }
                break;
            case 3:
                if(timer.getElapsedTimeSeconds() > 1.3) {
                    bot.getIntaking().off();
                    bot.getIntaking().servosIn();
                    setState(-1);
                }
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return state == -1;
    }
    public void setState(int x) {
        state = x;
        timer.resetTimer();
    }
}
