package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.crossbotics.ftchelper.robot.Action;
import com.crossbotics.ftchelper.robot.Drive;
import com.crossbotics.ftchelper.robot.Indexer;
import com.crossbotics.ftchelper.robot.Intake;
import com.crossbotics.ftchelper.robot.Launcher;
import com.crossbotics.ftchelper.robot.Positioner;
import com.crossbotics.ftchelper.robot.RobotDefinition;
import com.crossbotics.ftchelper.robot.Shape;

/**
 * Robot 1, version 1 (file 1 of 5): WHAT is on the robot. It is goBILDA's BIOBUZZ starter bot
 * (3200-2627-0003) with our changes:
 * - The intake folds up at the start (inside the 18 in cube). At START a servo pushes it over; then
 *   it rests on a hard stop by gravity, and floats up when a NECTAR comes under it.
 * - Its rollers are Offset surgical tubing rollers, 90 mm and 60 mm taking turns, not Gecko wheels.
 * - The two corner wheels (on CR servos before) are now two 72 mm Boot-Wheel spinners, driven from the
 *   intake shaft through miter gears: the intake motor turns them, so they are not in the code.
 * - The flywheel's motor sits on a sliding mount (springs or gravity), so a NECTAR fits too. Shots
 *   leave up a ramp out of the BACK.
 *
 * Parts are named by their parts catalog id; sizes and places are in mm. A number with "est." after
 * it is an estimate from goBILDA's starter bot CAD: measure our robot and replace it. Places: forward,
 * left, up from the middle of the chassis on the tiles.
 *
 * The simulator's robot file is made from this file: {@code ./gradlew :sim:fromRobot -PteamCode=../TeamCode}
 * (from the ftc-helper-lib folder).
 */
public class Robot extends RobotDefinition {
    public Positioner intakeDeploy;
    public Intake intake;
    public Launcher launcher;

    /**
     * Push the intake over right after START? An autonomous that starts with the robot's front against a
     * wall sets this false in its steps (the intake would fall onto the wall) and deploys the intake itself
     * once the robot has backed away (autos/LaunchAndPark.java). TeleOp makes a new Robot: true again.
     */
    public boolean deployAtStart = true;

    @Override
    protected void define() {
        // The drive: goBILDA's 6-wheel drop-center chassis, one 312 RPM motor per side, 96 mm wheels
        // (Hogback; the 96 mm omni wheels at the intake end are the same size). The right motor runs
        // reversed (goBILDA's code). Wheel centres: 402 mm side to side, 288 mm front to back (CAD).
        // "left_drive", "right_drive", "imu" = names in the robot configuration (Driver Station), exactly.
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
        // optional(): until "intake_deploy" is in the robot configuration, the servo is off (INIT goes
        // on, with a note; at events a loud warning) and the intake is put down by hand before the match.
        // The deploy action then just starts the intake, and X does nothing. No code to change when the
        // servo is added: put it in the configuration and INIT again.
        // "intake_deploy" = the servo's name in the robot configuration. Its numbers: Tunables.IntakeDeploy.
        intakeDeploy = add(Positioner.servo("intake_deploy").part("gobilda-2000-speed").optional()
                .looks(Shape.arm().name("deploy_kicker").atMm(120, 130, 150).sizeMm(50, 15, 15)
                        .angle("stowed", 90).angle("deployed", 30).note("est.")));

        // The intake: a 117 RPM motor (50.9:1). Its arm hinges 120 mm ahead of the centre, 150 mm up
        // (est.); straight up (95 degrees) when folded, and down on its hard stop at -40 degrees, where
        // the roller sits where goBILDA's did (187 mm ahead, 93 mm up). Gravity holds it there: passive.
        // The arm is drawn only 95 mm long and 240 mm wide (the roller's shaft): drawn longer, its box
        // reaches down past the roller and pushes pieces away in the simulator before the roller can take
        // them; drawn wider, it would hold the spinners out of the FLOWER. The size rules still count
        // every shape.
        // It carries up to four pieces (goBILDA's page). It starts running once it is deployed
        // (Actions.deployIntake), not before: B stops and starts it.
        // "intake" = the motor's name in the robot configuration. Its numbers: Tunables.Intake.
        intake = add(Intake.motor("intake").part("gobilda-5203-117").holds(4)
                .looks(Shape.arm().name("intake_arm").atMm(120, 0, 150).sizeMm(95, 240, 15)
                                .range(-40, 95).startAt(95).dropsWhen(intakeDeploy, "deployed", -40).note("est."),
                        // The main roller: 90 mm and 60 mm tubing rollers taking turns on a 240 mm shaft. The
                        // tubing is soft: it bends against a FLOWER instead of stopping the robot (photos,
                        // 2026-10-20), so only pieces touch it in the simulator. It is too tall to go into a
                        // FLOWER's opening (99 mm), so it takes nothing there itself.
                        Shape.roller("offset-tubing-90", "offset-tubing-60").on("intake_arm").atMm(88, 0, 0)
                                .lengthMm(240).soft().note("est."),
                        // The spinners: goBILDA 72 mm Boot-Wheels lying flat at the front corners (mentor,
                        // 2026-10-20), turned by the intake shaft through miter gears, 156 mm out to each side.
                        // Their fronts are flush with the roller's front (photos) and they sit low, about
                        // 13-38 mm above the tiles, so they reach into a FLOWER's Retrieval Opening (9-99 mm).
                        // On the arm (down at -40 degrees) that is 139 mm along it and 47 mm below it, tilted
                        // 40 degrees so they lie flat. The miter gears turn them opposite ways, so both pull
                        // pieces in: the left one turns the other way from the motor.
                        Shape.roller("gobilda-boot-72-rex").name("left_spinner").on("intake_arm").atMm(139, 156, -47)
                                .axis("up").tiltDeg(40).lengthMm(25).reversed().note("est."),
                        Shape.roller("gobilda-boot-72-rex").name("right_spinner").on("intake_arm").atMm(139, -156, -47)
                                .axis("up").tiltDeg(40).lengthMm(25).note("est.")));

        // The launcher: goBILDA's 312 RPM motor with its gearbox taken off (1:1, so the 6000 RPM motor's
        // numbers), one 96 mm Hogback wheel. Fed by the "windmill" CR servo (Torque), reversed as in
        // goBILDA's code. The wheel's mount slides up about 20 mm (NECTAR 91 mm, POLLEN 71 mm). Shots
        // leave up the ramp out of the back: 147 degrees from forward (est., from goBILDA's hood angle).
        // "launcher", "windmill" = names in the robot configuration. Numbers: Tunables.Launcher, .Windmill.
        launcher = add(Launcher.flywheel("launcher", "gobilda-5203-6000")
                .feeder(Indexer.crServo("windmill").part("gobilda-2000-torque").reversed())
                .spinAtStart()   // at speed from START, ready for the first shot (LB lets it coast)
                .looks(Shape.slide().name("launcher_slide").atMm(-40, -5, 120).sizeMm(30, 30, 48)
                                .passive().travelMm(20).note("est."),
                        Shape.flywheel("gobilda-hogback-96").on("launcher_slide").atMm(0, 0, 48))
                .shotAngle(147));

        // Parts that do not move: goBILDA's two upright U-channels behind the launcher (CAD), and our
        // ramp out of the back (est.: drawn flat; the real one slopes up toward the back). The tower is
        // drawn as one solid box, so the flywheel (which really turns between the two channels) looks
        // like it is inside it: a picture only, the size rules are not changed by it.
        fixed(Shape.box("tower").atMm(-88, 0, 40).sizeMm(48, 184, 264),
              Shape.box("ramp").atMm(-180, 0, 200).sizeMm(120, 150, 10).note("est."));

        // Right after START (never in INIT: nothing may move before START), push the intake over.
        // (No deploy servo in the configuration: the push is skipped and the intake just starts.)
        atStart(() -> deployAtStart ? Actions.deployIntake(this)
                : Action.named("Intake stays folded (the autonomous deploys it)"));
    }
}
