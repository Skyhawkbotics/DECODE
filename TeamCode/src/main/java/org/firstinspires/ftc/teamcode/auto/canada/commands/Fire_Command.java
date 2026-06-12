package org.firstinspires.ftc.teamcode.auto.canada.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Fire_Command extends CommandBase {
    ///  This is the main firing command. Servo Stopper position not specified
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();




    public Fire_Command(Robot bot ) { // runs the Command
        this.bot = bot;
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        bot.getShooter().update();
        bot.getFollower().update();
        switch (state) {
            case -1:
                break;
            case 1:

                bot.getIntaking().fire();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 0.8) { //1
                    bot.getIntaking().prep_intake();
                    setState(-1); //3
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
