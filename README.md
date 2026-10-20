# Octobots 2026-27, robot 1

The code for Indianola Robotics' (FTC team 8938) first robot of the BIOBUZZ season. It is the FTC SDK
(FtcRobotController 12.0) with our helper library, [ftc-helper-lib](https://github.com/jasoncross/ftc-helper-lib),
in the `ftc-helper-lib` folder (a git submodule).

## Get it

```bash
git clone --recurse-submodules https://github.com/jasoncross/octobots-2026-r1.git
```

Open the folder in Android Studio (Narwhal 3 Feature Drop or newer). Builds need JDK 21.

## The robot in five files

All in `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`:

| File | What it says |
| --- | --- |
| `Robot.java` | what is on the robot: each part's name in the robot configuration, its catalog part, how it looks and where it sits |
| `Tunables.java` | every number, one section per part |
| `Controls.java` | the buttons, and the TeleOp ("R1 TeleOp") |
| `Actions.java` | steps that use several parts (deploy the intake, fire all) |
| `autos/` | the autonomous programs |

How the library works: `ftc-helper-lib/docs/modules/robot.md`.

## Version 1

goBILDA's BIOBUZZ starter bot (3200-2627-0003) with our changes: an intake that folds up at the start
and is pushed over by a servo at START (then gravity holds it on a hard stop), Offset surgical tubing
rollers (90 mm and 60 mm taking turns), two 90 mm tubing spinners at the front corners driven from the
intake through miter gears, a flywheel on a sliding mount (so a NECTAR fits), and a ramp that sends
shots out of the back. Device names are goBILDA's (`left_drive`, `right_drive`, `intake`, `launcher`,
`windmill`) plus `intake_deploy` (the deploy servo).

**Still estimates (marked "est." in Robot.java):** the intake's hinge and arm, the spinners' place, the
deploy servo's arm, the flywheel slide, the ramp, the shot angle. Measure the robot and replace them.
**Still to find on the robot:** the deploy servo's positions (Servo Finder, `Tunables.IntakeDeploy`),
how the Control Hub is mounted (`imu(...)` in Robot.java), the drive's numbers with the Drive Tuner
(then `Tunables.Drive.calibrated = true`: until then the autonomous refuses to run), and every number
marked "est." in Robot.java (measure the robot).

## The simulator

`simulator/robot.properties` is made from Robot.java. After changing Robot.java, from the
`ftc-helper-lib` folder:

```bash
./gradlew :sim:fromRobot -PteamCode=../TeamCode
```

Check it (size rules, the intake drops at START, picks up a POLLEN, fires it):

```bash
./gradlew :sim:scenario -Pfile=../simulator/scenarios/r1-v1.sim
```

Drive it in a browser (then open http://localhost:8780 and pick "R1 TeleOp"):

```bash
./gradlew :sim:run -Probot=../simulator/robot.properties
```
