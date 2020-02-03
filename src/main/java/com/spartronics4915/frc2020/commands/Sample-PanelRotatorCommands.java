/**
 * Sample-PanelRotator Commands
 */

public class PanelRotatorCommands
{
    /**
     * Raise panel rotator mechanism
     * Safety note: robot should stop some distance from the control panel, and slowly
     * approach the panel with mechanism extended.
     *     TODO: command to trigger raise to reduce robot speed
     *     TODO: should robot be in complete stop before raising the mechanism?
     *
     * Alignment note: sensor needs to be correctly positioned under the control
     * panel.
     *     TODO: assume this is a driver responsibility
     *     TODO: investigate using color sensor for distance measurement for alignment
     *
     * Important note: assumption is that Raise command will follow w/
     * PositionToFMSColor or Rotation command
     */
    public class Raise extends FunctionalCommand
    {
        // initialization
        // TODO: any safety checks or other initializations?

        // execute
        // TODO: connect to subsystem's raise method

        // isFinished
        // TODO: connect to subsystem's checks to determine if raise complete
        // TODO: future: add distance sensor info to ensure correct alignment?

        // end -- should it be interrupted?
        // TODO: call subsystem's stop method
        // TODO: if interrupted, should we lower the mechanism?
    }

    /**
     * Lower panel rotator mechanism
     * This command should be:
     * - connected to a joystick button for manual control
     * - be callable as a command group after successful rotation or position control
     *
     *   TODO: for safety, should we automatically lower if robot speed exceeds
     *   certain value?
     *   TODO: when is it NOT safe to lower -- can we lower the panel always, or
     *   do we need to back up from the control panel first?
     */
    public class Lower extends FunctionalCommand
    {
        // .... fill in the blanks
    }

    /**
     * Position align to FMS color
     * This command should be:
     * - connected to a joystick button for manual control
     * - be callable as a command group after successful rotation or position control
     *   TODO: assumes Raise handles all the pre-conditions for safety and
     *   approach to the control panel
     */
    public class PositionToFMSColor extends FunctionalCommand
    {
        // initialization
        // make sure FMS color is received -- punt otherwise --> ::getFMSAssignedColor()
        // TODO: how to verify color sensor correctly positioned -- camera?
        // proximity sensor in the color sensor? -- otherwise punt
        // identify which color the sensor should be reading for correct
        // positioning --> ::getFMStoTargetColor()

        // execute
        // spin panel until position achieved

        // isFinished
        // test the ::getDetectedColor() against the required target color
        // received w/ getFMStoTargetColor()

        // end -- should it be interruptible?
        // TODO what is the logic if command is interrupted -- ignore the interruption?
    }

    /**
     * Rotate panel 3-5 times
     *
     * Assumes Raise command will perform the needed safety checks and alignment
     */
    public class SpinRequiredRotations extends CommandBase
    {
        // initialization
        // assumes sensor positioned correctly for readings --
        // TODO: in-progress....


        // execute

        // isFinished

        // end -- should it be interruptable?
        // TODO -- thinking subsystem should have some state information for counting
        // rotations to deal with interruptions. Even if we don't allow
        // interrupts, panel may over spin and we may need to reset & start
        // again

    }

    /**
     * Rotate panel one full 360 cycle
     *
     * TODO: This is similar to SpinRequiredRotations -- can we just leverage
     * the commands?
     *
     */
}
