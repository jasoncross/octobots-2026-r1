# Robot 1 (Octobots 2026-27): instructions for AI agents

Students do the work with a mentor. Explain plainly, with the "why".

- The robot is defined in `TeamCode/.../Robot.java` (parts by catalog id, shapes for the simulator) and
  `Tunables.java`. Name real parts; give a size or a place only when the catalog does not know it, and
  say where each number came from (`note("est.")`, `note("measured 2026-10-20")`).
- After any change to Robot.java or Tunables.java: regenerate `simulator/robot.properties` and run the
  scenarios in `simulator/scenarios/` (commands in README.md). They must pass.
- `ftc-helper-lib` is a separate, public library for any team: never put team names or team-only code
  in it. A change it needs goes to that repository (with docs, a tutorial line and tests), then this
  repository moves its submodule to the new commit or tag.
- The robot's mechanical design (CAD, design logs) is in the octobots-2026 repository.
- Commit or push only when the mentor asks.
