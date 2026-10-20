package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import io.github.jasoncross.ftchelper.robot.ControlMap;
import io.github.jasoncross.ftchelper.robot.Pad;

/**
 * Robot 1, version 1 (file 3 of 5): the BUTTONS, and the TeleOp. Like goBILDA's code, one driver does
 * everything on gamepad 1 ({@code oneDriver = true}):
 * <ul>
 *   <li>left stick forward/back drives, right stick X turns (left stick click: slow on/off)</li>
 *   <li>RT intake in, LT intake out, while held</li>
 *   <li>LB spins the flywheel up / down, RB fires one piece (it waits until the wheel is at speed),
 *       Y fires all (hold RT too, to help push them in)</li>
 *   <li>D-pad up/down: flywheel speed +/- 50 RPM</li>
 *   <li>X: push the intake over again (if it did not fall at START)</li>
 * </ul>
 */
@TeleOp(name = "R1 TeleOp")
public class Controls extends ControlMap<Robot> {
    public static boolean oneDriver = true;

    @Override
    protected void buttons(Robot robot, Pad g1, Pad g2) {
        g1.y.press(Actions.fireAll(robot)).says("fire all");
        g1.x.press(Actions.deployIntake(robot)).says("deploy intake");
    }
}
