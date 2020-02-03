/**
 * Sample-PanelRotator Commands
 */


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
   *   TODO: for safety, should we automatically lower if robot speed exceeds
   *   certain value?
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
   *   TODO: for safety, should we automatically lower if robot speed exceeds
   *   certain value?
   */
  public class PositionAlignToFMSColor extends FunctionalCommand
  {
      // initialization

      // execute

      // isFinished

      // end -- should it be interruptable?
  }

