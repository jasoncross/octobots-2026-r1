package org.firstinspires.ftc.teamcode.finders;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.crossbotics.ftchelper.finders.DriveTunerOpMode;
import com.crossbotics.ftchelper.robot.RobotDefinition;
import org.firstinspires.ftc.teamcode.Robot;

/**
 * Measures the drive for the autonomous: the real wheel size (tape on the floor, a tape measure). It
 * shows the lines to copy ({@code Robot.java: .wheelMm(...)}); then set
 * {@code Tunables.Drive.calibrated = true} (until then the autonomous refuses to run).
 * How: ftc-helper-lib/docs/guides/finders.md.
 */
@TeleOp(name = "R1 Drive Tuner", group = "Finders")
public class DriveTuner extends DriveTunerOpMode {
    // The robot from Robot.java: the tuner uses its drive, the same one TeleOp and autonomous use.
    @Override
    protected RobotDefinition robot() {
        return new Robot();
    }
}
