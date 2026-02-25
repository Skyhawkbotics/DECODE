package org.firstinspires.ftc.teamcode.core.commands.general_commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;


public class Intake_off extends CommandBase { ///  runs the intake , either for firing or for intaking (servos stops are not specified at all)
    private int state = 1;

    private final Robot bot;
    private Timer timer = new Timer();



    public Intake_off(Robot bot) { // runs the Command
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
                    bot.getIntake().off();
                    bot.getIntake().servosOut();
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
