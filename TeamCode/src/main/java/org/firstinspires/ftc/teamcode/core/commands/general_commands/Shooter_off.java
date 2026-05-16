package org.firstinspires.ftc.teamcode.core.commands.general_commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Shooter_off extends CommandBase { ///  stops shooter, makes servos become stoppers
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Shooter_off(Robot bot) { // runs the Command
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
                bot.getShooter().turnOFF();
                bot.getIntaking().servosIn();
                bot.getTurret().ll_pause();
                setState(-1);
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
