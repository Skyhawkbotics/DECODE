package org.firstinspires.ftc.teamcode.core.commands.unused;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class close_score_command extends CommandBase {
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public close_score_command(Robot bot) { // runs the Command
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
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 0.75) { //1
                    setState(3);
                }
                break;
            case 3:
                if (timer.getElapsedTimeSeconds() > 0.75) {//2
                    bot.getIntake().on();
                    setState(4);
                }
                break;
            case 4:
                if (timer.getElapsedTimeSeconds() > 1) {//3.5
                    bot.getIntake().off();
                    setState(5);
                }
                break;
            case 5:
                if (timer.getElapsedTimeSeconds() > 0.5) {//4
                    setState(6);
                }
                break;
            case 6:
                if (timer.getElapsedTimeSeconds() > 1) { //5
                    setState(7);
                }
                break;
            case 7:
                if (timer.getElapsedTimeSeconds() > 1) { //6
                    bot.getIntake().on();
                    setState(8);
                }
                break;
            case 8:
                if (timer.getElapsedTimeSeconds() > 2) { //7.5
                    setState(10);
                }
                break;
            case 10:
                if (timer.getElapsedTimeSeconds() > 1) { // 9
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
