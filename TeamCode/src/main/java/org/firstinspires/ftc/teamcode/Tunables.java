package org.firstinspires.ftc.teamcode;

/**
 * Robot 1, version 1 (file 2 of 5): the NUMBERS, one section per part. The launcher's come from
 * goBILDA's code: it spins to 1250 counts per second (2679 RPM) and feeds above 1200 (107 RPM below),
 * with the hub's speed control at P 40, F 12.5.
 *
 * <p>Tune on the robot with the tuning menu (GUIDE on gamepad 1, PRACTICE profile): INIT lists every
 * value that differs from this file, ready to copy back. This file stays the record.
 */
public class Tunables {
    /** calibrated: set true after the Drive Tuner (autonomous refuses to run before). */
    public static class Drive {
        public static double maxPower = 1.0, practiceMaxPower = 0.6, slowSpeed = 0.3;
        public static double strafeCorrection = 1.15, turnKp = 0.012, turnKd = 0.003, moveMinPower = 0.30;
        public static boolean calibrated = false;
    }

    /** Intake power, 0 to 1. goBILDA's code runs it at full power both ways. */
    public static class Intake {
        public static double inPower = 1.0, outPower = 1.0;
    }

    /**
     * The deploy servo's positions (0 to 1). TODO find them with the Servo Finder: "stowed" clear of the
     * folded intake, "deployed" far enough to push it over. travelSec: about how long the push takes.
     */
    public static class IntakeDeploy {
        public static double stowed = 0.0, deployed = 0.6;
        public static double travelSec = 0.5;
    }

    public static class Launcher {
        public static double speedRpm = 2679, stepRpm = 50, readyBandRpm = 107, idleRpm = 0;
        public static double kP = 40, kI = 0, kD = 0, kF = 12.5;
    }

    /** The windmill: full power, for feedSec per piece (a starting guess: watch it and tune it). */
    public static class Windmill {
        public static double power = 1.0, feedSec = 0.3;
    }

    public static class Auto {
        public static double backInches = 4.7, backPower = 0.4;   // goBILDA's auto: 120 mm back
    }
}
