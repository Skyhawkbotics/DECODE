package org.firstinspires.ftc.teamcode.core.commands;


import static org.firstinspires.ftc.teamcode.core.constants.auto_speed4;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Prep_Shooter4 extends CommandBase { /// starts up tyhe shooter to a set velocity and puts the stopper servos out of the way
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Prep_Shooter4(Robot bot) { // runs the Command
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
                bot.getShooter().autonomous_speed(auto_speed4);
                bot.getShooter().setPos(0.35);
                bot.getIntaking().servosOut();
                setState(2);
                break;
            case 2:
                if (bot.getShooter().velocityReached()) { //1
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
