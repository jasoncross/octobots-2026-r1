package org.firstinspires.ftc.teamcode;

import com.crossbotics.ftchelper.robot.Action;

/** Robot 1, version 1 (file 4 of 5): steps that use several parts together. */
public class Actions {

    /**
     * Pushes the folded intake over, then starts the intake and moves the servo back out of the way.
     * The intake stays down by gravity (nothing holds it).
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
     * is at speed. It uses only the launcher, so the driver can still run the intake (RT) to help push
     * pieces in. (An action that also ran the intake would stop the moment the driver touched RT.)
     */
    public static Action fireAll(Robot r) {
        return Action.named("Fire all")
                .then(() -> r.launcher.fire(4))
                .waitUntil("the shots", () -> !r.launcher.isShooting(), 10.0);
    }

    /** For the autonomous: the intake helps push the pieces in while they are fired. */
    public static Action fireAllWithIntake(Robot r) {
        return Action.named("Fire all")
                .then(r.intake::in, () -> r.launcher.fire(4))
                .waitUntil("the shots", () -> !r.launcher.isShooting(), 10.0)
                .then(r.intake::stop);
    }
}
