package org.firstinspires.ftc.teamcode.auto.canada.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Intake_Timed_Command extends CommandBase {
    ///  This is the main firing command. Servo Stopper position not specified
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();
    private double timeBeforeStart, timeBeforeStop;



    public Intake_Timed_Command(Robot bot, double timeBeforeStart, double timeBeforeStop) { // runs the Command
        this.bot = bot;
        this.timeBeforeStart = timeBeforeStart;
        this.timeBeforeStop = timeBeforeStop;
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
                if(timer.getElapsedTimeSeconds() > timeBeforeStart) {
                    bot.getIntaking().intake();
                    setState(2);
                }
                break;
            case 2:
                if(timer.getElapsedTimeSeconds() > timeBeforeStop) {
                    bot.getIntaking().prep_fire();
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
