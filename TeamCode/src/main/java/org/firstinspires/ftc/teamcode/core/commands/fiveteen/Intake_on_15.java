package org.firstinspires.ftc.teamcode.core.commands.fiveteen;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Intake_on_15 extends CommandBase {
    ///  This is the main firing command. Servo Stopper position not specified
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();




    public Intake_on_15(Robot bot ) { // runs the Command
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
                bot.getIntaking().slow_Fire();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 1.5 ) { //1
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
