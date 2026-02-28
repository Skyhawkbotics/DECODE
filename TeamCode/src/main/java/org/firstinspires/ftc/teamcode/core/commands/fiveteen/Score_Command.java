package org.firstinspires.ftc.teamcode.core.commands.fiveteen;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.core.Robot;

public class Score_Command extends CommandBase {
    private int state = 1, speed;
    private final Robot robot;
    private final Timer timer = new Timer();
    private double servoPos;



    public Score_Command(Robot bot, int speed, double servoPos) { // runs the Command
        this.robot = bot;
        this.speed = speed;
        this.servoPos = servoPos;
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        robot.getShooter().update();
        robot.getFollower().update();

        switch (state) {
            case -1:
                break;
            case 1:
                robot.getShooter().autonomous_speed(speed);
                robot.getShooter().setPos(servoPos);
                setState(2);
                break;
            case 2:
                if(robot.getShooter().velocityReached() && !robot.getFollower().isBusy()) {
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
