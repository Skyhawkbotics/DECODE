package org.firstinspires.ftc.teamcode.core.ericauto;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.unused.whole_robot;


public class score_command_close extends CommandBase {
    private int state = 1;

    private final whole_robot bot;
    private Timer timer = new Timer();



    public score_command_close(whole_robot bot) { // runs the Command
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
            case 1:
                bot.getShooter().closest();
                setState(2);
                break;
            case 2:
                if(timer.getElapsedTimeSeconds() > 3) {
                    bot.getElevator().off();
                    setState(3);
                }
                break;
            case 3:
                if(timer.getElapsedTimeSeconds() > 1) {
                    bot.getElevator().off();
                    setState(4);
                }
                break;
            case 4:
                if(timer.getElapsedTimeSeconds() > 3) {
                    bot.getElevator().off();
                    setState(5);
                }
                break;
            case 5:
                if(timer.getElapsedTimeSeconds() > 2) {
                    bot.getElevator().off();
                    bot.getShooter().shooterON();
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
