package com.spartronics4915.frc2020.subsystems;

import com.spartronics4915.frc2020.Constants;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;
import com.spartronics4915.lib.hardware.motors.SpartronicsSRX;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/** 
 * The Intake subsystem takes balls from 
 * the playing field and outputs them to storage.
 */
public class Intake extends SpartronicsSubsystem 
{
    private final CANSparkMax mHarvestMotor;
    private final CANSparkMax mIngestMotor;

    // FIXME: are the sensor for ball detection missing?

    /** constructor **/
    public Intake()
    {
        mHarvestMotor = new CANSparkMax(Constants.Intake.kHarvestMotorId, MotorType.kBrushless);
        mIngestMotor = new CANSparkMax(Constants.Intake.kIngestMotorId, MotorType.kBrushless);
    }

    /** 
     * activates the mechanum "vector" wheels in partial intake
     * hopefully in tandem with the prism roller 
    **/
    public void harvestIntake() 
    {
        mHarvestMotor.set(Constants.Intake.kHarvestSpeed);
    }

    /** 
     * in partial intake
     * hopefully in tandem with the mechanum "vector" wheels 
    **/
    public void ingestIntake() 
    {
        mIngestMotor.set(Constants.Intake.kIngestSpeed);
    }

    /** reverses vector wheels **/
    public void harvestReverse() 
    {
        // FIXME: for readability, use Const specifically for reversing, not '-'
        mHarvestMotor.set(-Constants.Intake.kHarvestSpeed);
    }

    /** reverses prism roller **/
    public void ingestReverse() 
    {
        // FIXME: for readability, use Const specifically for reversing, not '-'
        mIngestMotor.set(-Constants.Intake.kIngestSpeed);
    }

    /** stops vector wheels **/
    public void harvestStop() 
    {
        mHarvestMotor.set(0.0);
    }

    /** stops prism roller **/
    public void ingestStop() 
    {
        mIngestMotor.set(0.0);
    }

    /** checks to see if ball is held in intake chamber **/
    // FIXME: who determines if the ball is held or not?
    public void isBallHeld() 
    {

    }

    /** universal stop method **/
    public void stop()
    {
        mHarvestMotor.set(0.0);
        mIngestMotor.set(0.0);
    }
}
