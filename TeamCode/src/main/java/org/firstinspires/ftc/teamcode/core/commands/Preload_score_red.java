package org.firstinspires.ftc.teamcode.core.commands;


import static org.firstinspires.ftc.teamcode.core.constants.red_ID;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Preload_score_red extends CommandBase { /// starts up tyhe shooter to a set velocity and puts the stopper servos out of the way
private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Preload_score_red(Robot bot) { // runs the Command
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
                bot.getShooter().far_side_red();
                bot.getIntake().servosOut();
                setState(2);
                break;
            case 2:
                if (bot.getShooter().velocityReached()) { //1
                    setState(4);
                }
                break;
            case 4:
                if(timer.getElapsedTimeSeconds() > 0.2) {
                    bot.getIntake().on_slow();
                    setState(3);
                }
                break;
            case 3:
                if(timer.getElapsedTimeSeconds() > 1.5) {
                    bot.getIntake().servosIn();
                    bot.getIntake().off();
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
