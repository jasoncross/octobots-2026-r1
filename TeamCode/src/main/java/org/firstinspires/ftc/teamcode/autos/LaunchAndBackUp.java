package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.crossbotics.ftchelper.robot.Auto;
import com.crossbotics.ftchelper.robot.Steps;
import org.firstinspires.ftc.teamcode.Actions;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Tunables;

/**
 * Robot 1, version 1 (file 5 of 5): goBILDA's autonomous in steps. It starts with its back to the goal
 * (shots leave out of the back), fires the preloaded pieces, and drives 120 mm away from the goal.
 * The intake is pushed over at START by Robot.java's atStart, which then starts it: it runs while the
 * pieces are fired and helps push them in. (The fire step must not use the intake itself: the deploy
 * owns it for its first half second and would stop the fire step. simulator/scenarios/r1-auto.sim
 * checks that all four are fired.)
 */
@Autonomous(name = "R1 Launch and Back Up", preselectTeleOp = "R1 TeleOp")
public class LaunchAndBackUp extends Auto<Robot> {

    @Override
    protected void steps(Steps s, Robot robot) {
        // Where to place it, and the spot as seen from our own wall: 56 in left of centre, 61.1 in toward
        // our wall. est.: the simulator file's start; change it to the real spot.
        s.start("Back against the goal", -56, -61.1, 0)
         .action(Actions.fireAll(robot), 12)
         // The back faces the goal, so "away from the goal" is forward (0 degrees).
         .move("Away from the goal", Tunables.Auto.backInches, 0, Tunables.Auto.backPower);
    }

    // BIOBUZZ's field is turned half a turn between the alliances (not mirrored): the same route,
    // driven from each alliance's own wall, works for both.
    @Override
    protected boolean mirrorForRed() {
        return false;
    }
}
