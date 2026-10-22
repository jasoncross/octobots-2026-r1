package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.crossbotics.ftchelper.robot.ControlMap;
import com.crossbotics.ftchelper.robot.Pad;

/**
 * Robot 1, version 1 (file 3 of 5): the BUTTONS, and the TeleOp. Like goBILDA's code, one driver does
 * everything on gamepad 1 ({@code oneDriver = true}):
 * <ul>
 *   <li>left stick forward/back drives, right stick X turns. LT: slow while held (left stick click:
 *       slow on/off)</li>
 *   <li>the intake runs once it is deployed (just after START). B: intake on / off. Double-tap B: push pieces out, until B is
 *       tapped again (then it runs in).</li>
 *   <li>LB spins the flywheel up / down, RB fires one piece (it waits until the wheel is at speed;
 *       hold RB to keep firing until you let go)</li>
 *   <li>D-pad up/down: flywheel speed +/- 50 RPM</li>
 *   <li>X: push the intake over again (if it did not fall at START)</li>
 * </ul>
 */
@TeleOp(name = "R1 TeleOp")
public class Controls extends ControlMap<Robot> {
    public static boolean oneDriver = true;

    @Override
    protected void buttons(Robot robot, Pad g1, Pad g2) {
        // B: one tap switches the intake on or off (back to running in if it was pushing out); a
        // double-tap pushes out until the next tap.
        g1.b.tapOrDoubleTap(() -> {
            if (robot.intake.isPushingOut()) robot.intake.in();
            else robot.intake.toggle();
        }, robot.intake::out).says("intake on/off; double-tap: out until tapped");
        g1.rt.free();   // the intake is on B now
        g1.lt.slow();   // LT: drive slowly while held (not the intake)
        // X: push the intake over again. needs(...): off while the deploy servo is not on the robot.
        g1.x.press(Actions.deployIntake(robot)).says("deploy intake").needs(robot.intakeDeploy);
    }
}
