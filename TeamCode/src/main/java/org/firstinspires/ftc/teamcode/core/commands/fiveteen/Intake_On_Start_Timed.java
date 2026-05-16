package org.firstinspires.ftc.teamcode.core.commands.fiveteen;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Intake_On_Start_Timed extends CommandBase { ///  runs the intake , either for firing or for intaking (servos stops are not specified at all)
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();
    private double time;



    public Intake_On_Start_Timed(Robot bot, double time) { // runs the Command
        this.bot = bot;
        this.time = time;
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
                if(timer.getElapsedTimeSeconds() >= time) {
                    bot.getIntaking().fire();
                    setState(2);
                }
                break;
            case 2:
                if(timer.getElapsedTimeSeconds() >= 1.2) {
                    bot.getIntaking().prep_intake();
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
