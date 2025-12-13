package org.firstinspires.ftc.teamcode.core.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Intake_on extends CommandBase {
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Intake_on(Robot bot) { // runs the Command
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
                bot.getIntake().on();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 1.5) { //1
                    bot.getIntake().off();
                    setState(-1); //3
                }
                break;
            case 3: /// IF INTAKING IS INCONSISTENT USE THIS
                bot.getIntake().reverse();
                setState(4);
                break;
            case 4:
                if(timer.getElapsedTimeSeconds() > 0.4) {
                    bot.getIntake().on();
                    setState(5);
                }
                break;
            case 5:
                if(timer.getElapsedTimeSeconds() > 1) {
                    bot.getIntake().off();
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
