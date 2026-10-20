package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import io.github.jasoncross.ftchelper.robot.Auto;
import io.github.jasoncross.ftchelper.robot.Steps;
import org.firstinspires.ftc.teamcode.Actions;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Tunables;

/**
 * Robot 1, version 1 (file 5 of 5): goBILDA's autonomous in steps. It starts with its back to the goal
 * (shots leave out of the back), fires the preloaded pieces, and drives 120 mm away from the goal.
 * The intake is pushed over at START by Robot.java's atStart, so it is down while this runs.
 */
@Autonomous(name = "R1 Launch and Back Up", preselectTeleOp = "R1 TeleOp")
public class LaunchAndBackUp extends Auto<Robot> {

    @Override
    protected void steps(Steps s, Robot robot) {
        s.start("Back against the goal", 0)
         .action(Actions.fireAllWithIntake(robot), 12)
         // The back faces the goal, so "away from the goal" is forward (0 degrees).
         .move("Away from the goal", Tunables.Auto.backInches, 0, Tunables.Auto.backPower);
    }
}
