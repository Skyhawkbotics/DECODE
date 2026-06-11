package org.firstinspires.ftc.teamcode.auto.canada.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class prep_Shooter_preload extends CommandBase {
    ///  This command will set a shooter speed and servo pos that you give it
    /// this replaces need for creation of new commands for new velocity/angles
    /// no more repetitiveness! 2/20/2026
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();
    private int speed;
    private double servoPos;
    private int secondSpeed;
    private double secondPos;


    public prep_Shooter_preload(Robot bot, int speed, double servoPos, int secondSpeed, double secondPos) { // runs the Command
        this.bot = bot;
        this.speed = speed;
        this.servoPos = servoPos;
        this.secondSpeed = secondSpeed;
        this.secondPos = secondPos;
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
                bot.getIntaking().servosOut();
                bot.getShooter().autonomous_speed(speed);
                bot.getShooter().setPos(servoPos);
                setState(2);
                break;
            case 2:
                if (bot.getShooter().velocityReached(1050)) { //1
                    bot.getIntaking().fire();
                    setState(3);
                }
                break;

            case 3:
                if(timer.getElapsedTimeSeconds() > 0.8) {
                    bot.getShooter().autonomous_speed(secondSpeed);
                    bot.getShooter().setPos(secondPos);
                    bot.getIntaking().intake();
                    setState(4);
                }
                break;
            case 4:
                if(bot.getFollower().getChainIndex() == 2) {

                    setState(5);
                }
                break;
            case 5:
                if(timer.getElapsedTimeSeconds() > 0.3) {
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
