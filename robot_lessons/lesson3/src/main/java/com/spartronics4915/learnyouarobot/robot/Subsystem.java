package com.spartronics4915.learnyouarobot.robot;

import com.spartronics4915.learnyouarobot.robot.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.spartronics4915.learnyouarobot.robot.loops.ILooper;

/**
 * The Subsystem abstract class, which serves as a basic framework for all robot
 * subsystems. Each subsystem outputs
 * commands to SmartDashboard, has a stop routine (for after each match), and a
 * routine to zero all sensors, which helps
 * with calibration.
 * <p>
 * All Subsystems only have one instance (after all, one robot does not have two
 * drivetrains), and functions get the
 * instance of the drivetrain and act accordingly. Subsystems are also a state
 * machine with a desired state and actual
 * state; the robot code will try to match the two states with actions. Each
 * Subsystem also is responsible for
 * instantializing all member components at the start of the match.
 */
public abstract class Subsystem
{

    // all subsystems should set mInitialized upon successful init.
    private boolean mInitialized = false;
    private String mName = null;

    public boolean isInitialized()
    {
        return mInitialized;
    }

    protected Subsystem() // means only subclasses can exist, not Subsystems
    {
        String classname = this.getClass().getName();
        int tail = classname.lastIndexOf('.');
        if (tail == -1)
        {
            mName = classname;
        }
        else
        {
            mName = classname.substring(tail + 1);
        }
    }

    public String getName()
    {
        return mName;
    }

    public void logInitialized(boolean success)
    {
        mInitialized = success;
        if (success)
            this.logNotice("init SUCCEEDED");
        else
            this.logWarning("init FAILED");
        SmartDashboard.putString(mName + "/Status", mInitialized ? "OK" : "ERROR");
    }

    // broadcast methods are for smartdashboard with conventionalized keys
    public void dashboardPutState(String state)
    {
        SmartDashboard.putString(mName + "/State", state);
    }

    public void dashboardPutWantedState(String state)
    {
        SmartDashboard.putString(mName + "/WantedState", state);
    }

    public void dashboardPutString(String nm, String value)
    {
        SmartDashboard.putString(mName + "/" + nm, value);
    }

    public String dashboardGetString(String nm, String defValue)
    {
        return SmartDashboard.getString(mName + "/" + nm, defValue);
    }

    public void dashboardPutNumber(String nm, Number value)
    {
        SmartDashboard.putNumber(mName + "/" + nm, value.doubleValue());
    }

    public Number dashboardGetNumber(String nm, Number defaultValue)
    {
        return SmartDashboard.getNumber(mName + "/" + nm, defaultValue.doubleValue());
    }

    public void dashboardPutBoolean(String nm, Boolean value)
    {
        SmartDashboard.putBoolean(mName + "/" + nm, value);
    }

    public boolean dashboardGetBoolean(String nm, Boolean defValue)
    {
        return SmartDashboard.getBoolean(mName + "/" + nm, defValue);
    }

    // log methods are for conventionalizing format across subsystems 
    public void logException(String msg, Throwable e)
    {
        Logger.logThrowableCrash(this.getName() + " " + msg, e);
    }

    public void logError(String msg)
    {
        Logger.error(this.getName() + " " + msg);
    }

    public void logWarning(String msg)
    {
        Logger.warning(this.getName() + " " + msg);
    }

    public void logNotice(String msg)
    {
        Logger.notice(this.getName() + " " + msg);
    }

    public void logInfo(String msg)
    {
        Logger.info(this.getName() + " " + msg);
    }

    public void logDebug(String msg)
    {
        Logger.debug(this.getName() + " " + msg);
    }

    public abstract boolean checkSystem();

    public abstract void outputTelemetry();

    public abstract void stop();

    public void zeroSensors()
    {
    }

    public void registerEnabledLoops(ILooper enabledLooper)
    {
    }

    // Optional design pattern for caching periodic reads to avoid hammering the HAL/CAN.
    public void readPeriodicInputs()
    {
    }

    // Optional design pattern for caching periodic writes to avoid hammering the HAL/CAN.
    public void writePeriodicOutputs()
    {
    }

    // Optional override invoked by SubsystemManager
    public void writeToLog()
    {
    }
}
