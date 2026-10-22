package org.firstinspires.ftc.teamcode;

import com.crossbotics.ftchelper.robot.Action;

/** Robot 1, version 1 (file 4 of 5): steps that use several parts together. */
public class Actions {

    /**
     * Pushes the folded intake over, then starts the intake and moves the servo back out of the way.
     * The intake stays down by gravity (nothing holds it). With no deploy servo in the configuration
     * (optional() in Robot.java) the servo steps and their wait are skipped: the intake was put down by
     * hand, and this just starts it.
     */
    public static Action deployIntake(Robot r) {
        return Action.named("Deploy intake")
                .then(() -> r.intakeDeploy.goTo("deployed"))
                // A servo cannot say where it is: isDone() guesses from Tunables.IntakeDeploy.travelSec.
                .waitUntil("the deploy servo", r.intakeDeploy::isDone, 2.0)
                // The intake is down now: start it (it keeps running when this action ends).
                .then(r.intake::in, () -> r.intakeDeploy.goTo("stowed"));
    }

    /**
     * Fires every piece it holds (up to four): spins up, then feeds them one by one as soon as the wheel
     * is at speed. It uses only the launcher, so the intake keeps doing what it does: in the autonomous
     * it is already running (deployIntake starts it), so it helps push the pieces in; in TeleOp the
     * driver can still switch it with B. (An action that also used the intake would be stopped by
     * deployIntake at START, or by the driver touching B: one part, one owner.)
     */
    public static Action fireAll(Robot r) {
        return Action.named("Fire all")
                .then(() -> r.launcher.fire(4))
                .waitUntil("the shots", () -> !r.launcher.isShooting(), 10.0);
    }
}
