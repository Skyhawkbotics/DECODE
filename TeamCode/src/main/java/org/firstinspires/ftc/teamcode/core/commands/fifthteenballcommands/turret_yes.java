package org.firstinspires.ftc.teamcode.core.commands.fifthteenballcommands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class turret_yes extends CommandBase { /// starts up tyhe shooter to a set velocity and puts the stopper servos out of the way
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public turret_yes(Robot bot) { // runs the Command
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
                if(bot.getTurret().IsTurretDone()) {
                    bot.getTurret().turretcloseNORMAL();
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
