package org.firstinspires.ftc.teamcode.finders;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.crossbotics.ftchelper.finders.ServoFinderOpMode;

/**
 * Finds a servo's positions. For the deploy servo ("intake_deploy"): "stowed" clear of the folded
 * intake and "deployed" far enough to push it over. It shows a {@code Tunables.IntakeDeploy: ...} line
 * to copy into Tunables.java (rename P1, P2 to stowed, deployed). How: ftc-helper-lib/docs/guides/finders.md.
 */
@TeleOp(name = "R1 Servo Finder", group = "Finders")
public class ServoFinder extends ServoFinderOpMode {
}
