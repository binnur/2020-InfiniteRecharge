/**
 * Subsystem: Panel Rotator
 * - Manages hardware system for raising/lowering panel motors
 * - Receives FMS color data and notifies driver via dashboard
 * - Handles state controls for completing full rotations
 * - Handles state controls for positioning wheel to FMS color
 */

public class PanelRotator extends SpartronicsSubsystem
{
    /** Class member variables */

    // Motor controls for raising and spinning mechanisms
    private SpartronicsMotor mSpinMotor;
    private SpartronicsMotor mExtendMotor;

    // TODO: How do we know if mechanism is raised/lowered fully??
    private final DigitalInput mBeamSensorUp;
    private final DigitalInput mBeamSensorDown;

    // Define colors that sensor will match against
    // Note: we can define multiple shades of blues, etc. for color matching
    private final Color kBlueTarget1 = ColorMatch.makeColor(0, 0, 1);
    private final Color kBlueTarget2 = ColorMatch.makeColor(0.143, 0.427, 0.429);
    // do more colors...

    // Define the color sensor & mather objects
    private final ColorSensorV3 mColorSensor;
    private final ColorMatch m_colorMatcher = new ColorMatch();

    /** FUTURE COLOR CALIBRATION
     * TODO: To calibrate on the field, we need to read in color data
     * from smartdashboard or file system
     * --> ColorSensorV3.getColor() returns Color object that can be passed to ColorMatch.makeColor()
     */

    // Store FMS assigned color -- this will be checked periodically
    // initializing to null explicitly for checks
    private String mFMSAssignedColor = null;

    // Store state data for rotations
    // todo...

    // FUTURE TODO: color sensor can have proximity distance -- we may want that?

    /* --- */

    public Sample-PanelRotator()
    {
        /** Class initialization */

        // TODO: verify fromMultiplier() values for motor creation
        mSpinMotor = SpartronicsMax.makeMotor(Constants.PanelRotator.kSpinMotorId, SensorModel.fromMultiplier(1));
        mExtendMotor = SpartronicsSRX.makeMotor(Constants.PanelRotator.kExtendMotorId, SensorModel.fromMultiplier(1));

        // TODO: verify sensor connections for testing raised/lowered values
        mBeamSensorUp = new DigitalInput(Constants.PanelRotator.kBeamSensorUpId);
        mBeamSensorDown = new DigitalInput(Constants.PanelRotator.kBeamSensorDownId);

        // make sure to report exceptions if initializations fail
        if (mSpinMotor.hadStartupError() || mExtendMotor.hadStartupError())
        {
            logException("PanelRotator: Motor nitialization failed");
            logInitialized(false);
        }
        else
        {
            logInitialized(true);
        }

        // Initialize color sensor & register color values for matching
        mColorSensor = new ColorSensorV3(I2C.Port.kOnboard);

        mColorMatcher.addColorMatch(kBlueTarget1);
        mColorMatcher.addColorMatch(kBlueTarget2);
        // add other colors.... 

        // TODO: color sensor can have proximity distance -- we may want that?
    }

    /* --- */
    /**
     * FMS will announce color data out of bounds -- listen for it
     */
    @Override
    public void periodic()
    {
        // once we receive color data, we don't want to repeat this loop
        if (mFMSAssignedColor == null)
        {
            // this will either be a color value or null
            String fmsColor = getFMSAssignedColor();
            if (fmsColor != null)
            {
                // we received a proper color data --
                // TODO: verify the string assignment syntax is correct
                mFMSAssignedColor = fmsColor;
                SmartDashboard.putString("FMS Color:", mFMSAssignedColor);
            }
        }
    }

    /**
     * Returns the FMS color in a way that is expected for comparison test
     * @return RED, BLUE, ... or null
     */
    public String getFMSAssignedColor()
    {
        return mFMSAssignedColor;
    }

    /**
     * Retrieves the FMS assigned color and parses it for future matching
     *
     * @return A String color - either RED, BLUE, YELLOW, or GREEN or null if
     * not assigned or corrupted data
     */
    private String parseFMSAssignedColor()
    {
        // implement:
        // https://docs.wpilib.org/en/latest/docs/software/wpilib-overview/2020-Game-Data.html

        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0)
        {
            // Only need to check for starting values --
            switch (gameData.charAt(0))
            {
                case 'B':
                    return "BLUE";
                case 'G':
                    return "GREEN";
                case 'R': // Red
                case 'Y': // Yellow
                default:
                    return null; // This is corrupt data
            }
        }

        // FMS color is not assigned
        return null;
    }

    /* --- */
    /**
     * Write methods to:
     *  - raise and lower mechanism
     *  - detect when mechanism properly raised & lowered
     *  - start & stop spinning of the wheel
     */

    /**
     * Assuming panel positioning sensors are at 90 degrees to positioning of
     * the robot
     * Panel positioning requires a corresponding color that sensor should read
     * in order to correctly align to the FMS color
     * TODO: verify color sensor position against FMS positioning sensor
     *
     * @return A String color: RED, BLUE, YELLOW, GREEN, or ERROR
     */
    public String getFMStoTargetColor()
    {
        // TODO: it would be good to log target color here or at the caller

        // if FMS color is not set, return ERROR
        // TODO: caller should ensure right stage before initiating this call!!
        if (mFMSAssignedColor == null)
        {
            return "ERROR";
        }

        // Given FMS color, identify what robot sensor will read at 90 degrees
        // TODO: verify color placement is correct!
        if (mFMSAssignedColor.equals("RED"))
        {
            return "BLUE";
        }
        else if (mFMSAssignedColor.equals("BLUE"))
        {
            return "RED";
        }
        else if (mFMSAssignedColor.equals("GREEN"))
        {
            return "YELLOW";
        }
        else if (mFMSAssignedColor.equals("YELLOW"))
        {
            return "GREEN";
        }
        else
        {
            return "ERROR";
        }
    }

    /* --- */
    /**
     * Color matching is a two step process:
     * 1. get the sensor color
     * 2. match against the registered colors
     *
     * @return A String color: RED, BLUE, YELLOW, GREEN, or UNKNOWN
     * https://github.com/REVrobotics/Color-Sensor-v3-Examples/blob/master/Java/Color%20Match/src/main/java/frc/robot/Robot.java
     */
    public String getDetectedColor()
    {
        // read sensor color
        Color detectedColor = m_colorSensor.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        // TODO: should create constant colors "RED", "BLUE", etc.
        if (match.color == kBlueTarget)
        {
            colorString = "BLUE";
        }
        else if (match.color == kRedTarget)
        {
            colorString = "RED";
        }
        else if (match.color == kGreenTarget)
        {
            colorString = "GREEN";
        }
        else if (match.color == kYellowTarget)
        {
            colorString = "YELLOW";
        }
        else
        {
            colorString = "UNKNOWN";
        }

        // log the color data & matched color
        // TODO: detectedColor may not log correctly -- verify
        Logger.notice("PanelRotator: m_colorSensor.getColor: " + detectedColor);
        Logger.notice("PanelRotator: matched color: " + colorString);

        return colorString;
    }

    /* --- */
    /**
     * Manage panel rotations:
     * - need to provide 3-5 rotation guarantee
     * - need to provide a single rotation guarantee
     */

}
