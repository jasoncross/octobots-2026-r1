package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import io.github.jasoncross.ftchelper.robot.Drive;
import io.github.jasoncross.ftchelper.robot.Indexer;
import io.github.jasoncross.ftchelper.robot.Intake;
import io.github.jasoncross.ftchelper.robot.Launcher;
import io.github.jasoncross.ftchelper.robot.Positioner;
import io.github.jasoncross.ftchelper.robot.RobotDefinition;
import io.github.jasoncross.ftchelper.robot.Shape;

/**
 * Robot 1, version 1 (file 1 of 5): WHAT is on the robot. It is goBILDA's BIOBUZZ starter bot
 * (3200-2627-0003) with our changes:
 * <ul>
 *   <li>The intake folds up at the start (inside the 18 in cube). At START a servo pushes it over; then
 *       it rests on a hard stop by gravity, and floats up when a NECTAR comes under it.</li>
 *   <li>Its rollers are Offset surgical tubing rollers, 90 mm and 60 mm taking turns, not Gecko wheels.</li>
 *   <li>The two corner wheels (on CR servos before) are now two 90 mm tubing spinners, driven from the
 *       intake shaft through miter gears: the intake motor turns them, so they are not in the code.</li>
 *   <li>The flywheel's motor sits on a sliding mount (springs or gravity), so a NECTAR fits too. Shots
 *       leave up a ramp out of the BACK.</li>
 * </ul>
 *
 * <p>Parts are named by their parts catalog id; sizes and places are in mm. A number with "est." after
 * it is an estimate from goBILDA's starter bot CAD: measure our robot and replace it. Places: forward,
 * left, up from the middle of the chassis on the tiles.
 *
 * <p>The simulator's robot file is made from this file: {@code ./gradlew :sim:fromRobot -PteamCode=../TeamCode}
 * (from the ftc-helper-lib folder).
 */
public class Robot extends RobotDefinition {
    public Positioner intakeDeploy;
    public Intake intake;
    public Launcher launcher;

    @Override
    protected void define() {
        // The drive: goBILDA's 6-wheel drop-center chassis, one 312 RPM motor per side, 96 mm wheels
        // (Hogback; the 96 mm omni wheels at the intake end are the same size). The right motor runs
        // reversed (goBILDA's code). Wheel centres: 402 mm side to side, 288 mm front to back (CAD).
        drive(Drive.tank("left_drive", "right_drive")
                .motor("gobilda-5203-312").wheel("gobilda-hogback-96")
                .wheelsAtMm(402, 288)
                .reversed("right_drive")
                // TODO check how our Control Hub is mounted: the way its logo and USB port face.
                .imu("imu", LogoFacingDirection.UP, UsbFacingDirection.FORWARD));

        // The chassis box (side rails and wheels) and the whole robot's height, from goBILDA's CAD.
        chassisMm(384, 426, 97);
        heightMm(305);

        // The deploy servo: a Speed servo taken off the old corner wheels, in servo mode. Its short arm
        // pushes the folded intake over at START, then goes back (Actions.deployIntake). If it is too
        // weak to push the intake over, use the Torque servo: part("gobilda-2000-torque").
        intakeDeploy = add(Positioner.servo("intake_deploy").part("gobilda-2000-speed")
                .looks(Shape.arm().name("deploy_kicker").atMm(120, 130, 150).sizeMm(50, 15, 15)
                        .angle("stowed", 90).angle("deployed", 30).note("est.")));

        // The intake: a 117 RPM motor (50.9:1). Its arm hinges 120 mm ahead of the centre, 150 mm up
        // (est.); straight up (95 degrees) when folded, and down on its hard stop at -40 degrees, where
        // the roller sits where goBILDA's did (187 mm ahead, 93 mm up). Gravity holds it there: passive.
        // It carries up to four pieces (goBILDA's page).
        intake = add(Intake.motor("intake").part("gobilda-5203-117").holds(4)
                .looks(Shape.arm().name("intake_arm").atMm(120, 0, 150).sizeMm(95, 312, 15)
                                .range(-40, 95).startAt(95).dropsWhen(intakeDeploy, "deployed", -40).note("est."),
                        // The main roller: 90 mm and 60 mm tubing rollers taking turns on a 240 mm shaft.
                        Shape.roller("offset-tubing-90", "offset-tubing-60").on("intake_arm").atMm(88, 0, 0)
                                .lengthMm(240).note("est."),
                        // The spinners: 90 mm tubing lying flat at the front corners, turned by the intake
                        // shaft through miter gears (where goBILDA's corner wheels were: 224 mm ahead, 156 mm out).
                        Shape.roller("offset-tubing-90").name("left_spinner").on("intake_arm").atMm(154, 156, 0)
                                .axis("up").lengthMm(25).note("est."),
                        Shape.roller("offset-tubing-90").name("right_spinner").on("intake_arm").atMm(154, -156, 0)
                                .axis("up").lengthMm(25).note("est.")));

        // The launcher: goBILDA's 312 RPM motor with its gearbox taken off (1:1, so the 6000 RPM motor's
        // numbers), one 96 mm Hogback wheel. Fed by the "windmill" CR servo (Torque), reversed as in
        // goBILDA's code. The wheel's mount slides up about 20 mm (NECTAR 91 mm, POLLEN 71 mm). Shots
        // leave up the ramp out of the back: 147 degrees from forward (est., from goBILDA's hood angle).
        launcher = add(Launcher.flywheel("launcher", "gobilda-5203-6000")
                .feeder(Indexer.crServo("windmill").part("gobilda-2000-torque").reversed())
                .looks(Shape.slide().name("launcher_slide").atMm(-40, -5, 120).sizeMm(30, 30, 48)
                                .passive().travelMm(20).note("est."),
                        Shape.flywheel("gobilda-hogback-96").on("launcher_slide").atMm(0, 0, 48))
                .shotAngle(147));

        // Parts that do not move: goBILDA's two upright U-channels behind the launcher (CAD), and our
        // ramp out of the back (est.: drawn flat; the real one slopes up toward the back).
        fixed(Shape.box("tower").atMm(-88, 0, 40).sizeMm(48, 184, 264),
              Shape.box("ramp").atMm(-180, 0, 200).sizeMm(120, 150, 10).note("est."));

        // Right after START (never in INIT: nothing may move before START), push the intake over.
        atStart(() -> Actions.deployIntake(this));
    }
}
