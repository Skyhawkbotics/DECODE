package org.firstinspires.ftc.teamcode.core.commands.turret;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Turret15_spike3_blue extends CommandBase { /// starts up tyhe shooter to a set velocity and puts the stopper servos out of the way
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Turret15_spike3_blue(Robot bot) { // runs the Command
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
                bot.getTurret().turret15PoseSpike3();
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
