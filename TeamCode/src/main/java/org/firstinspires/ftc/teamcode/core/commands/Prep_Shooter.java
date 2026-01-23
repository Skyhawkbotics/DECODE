package org.firstinspires.ftc.teamcode.core.commands;


import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Prep_Shooter extends CommandBase { /// starts up tyhe shooter to a set velocity and puts the stopper servos out of the way
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Prep_Shooter(Robot bot) { // runs the Command
        this.bot = bot;
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
                bot.getShooter().auto_speed();
                bot.getShooter().setPos(0.13);
                bot.getIntake().servosOut();
                setState(2);
                break;
            case 2:
                if (bot.getShooter().velocityReached()) { //1
                    setState(3);
                }
                break;
            case 3:
                if(timer.getElapsedTimeSeconds() > 0.3) {
                    bot.getIntake().on();
                    setState(-1);
                }

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
