package com.spartronics4915.frc2020.subsystems;

import com.spartronics4915.frc2020.Constants;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import com.spartronics4915.lib.hardware.motors.SensorModel;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.hardware.motors.SpartronicsSRX;
import com.spartronics4915.lib.hardware.motors.SpartronicsSimulatedMotor;

/**
 * This subsystem has two motors. A NEO using a Spark, while the other is a 775 PRO using a Talon.
 * The NEO motor winches the climber and the 775 PRO extends the climber.
 * The four methods used are extend(), winch(), reverseExtend(), and stop()
 */
public class Climber extends SpartronicsSubsystem
{
    private SpartronicsMotor mLiftMotor;
    private SpartronicsMotor mWinchMotor;

    public Climber()
    {
        // Hardware Contructor (Add motors and such here when I get them)
        mLiftMotor = SpartronicsSRX.makeMotor(Constants.Climber.kLiftMotorId,
            SensorModel.fromMultiplier(1));
        mWinchMotor = SpartronicsMax.makeMotor(Constants.Climber.kWinchMotorId,
            SensorModel.fromMultiplier(1));

        if (mLiftMotor.hadStartupError() || mWinchMotor.hadStartupError())
        {
            mLiftMotor = new SpartronicsSimulatedMotor();
            mWinchMotor = new SpartronicsSimulatedMotor();
            logInitialized(false);
        }
        else
        {
            logInitialized(true);
        }
    }

    public void extend()
    {
        mLiftMotor.setDutyCycle(Constants.Climber.kExtendSpeed);
        mWinchMotor.setDutyCycle(0.0);
    }

    public void winch(boolean stalled)
    {
        mLiftMotor.setDutyCycle(0.0);
        if (stalled)
            mWinchMotor.setDutyCycle(Constants.Climber.kWinchSpeed);
        else
            mWinchMotor.setDutyCycle(Constants.Climber.kReverseWinchSpeed);
    }

    public void retract()
    {
        mLiftMotor.setDutyCycle(Constants.Climber.kRetractSpeed);
        mWinchMotor.setDutyCycle(0.0);
    }

    public void stop()
    {
        mLiftMotor.setDutyCycle(0.0);
        mWinchMotor.setDutyCycle(0.0);
    }

    public boolean isStalled()
    {
        return mWinchMotor.getOutputCurrent() >= Constants.Climber.kStallThreshold;
    }
}
