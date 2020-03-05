/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import com.analog.adis16448.frc.ADIS16448_IMU;


public class Drivetrain
{
    private TalonSRX leftFront;
    private TalonSRX leftBack;
    private TalonSRX leftTop;
    private TalonSRX rightFront;
    private TalonSRX rightBack;
    private TalonSRX rightTop;
    public static ADIS16448_IMU gyro;

    double joyTurn;
    double joyTrans;
    double joyRight;
    double joyLeft;

    Drivetrain(TalonSRX leftFront, TalonSRX leftBack, TalonSRX leftTop, TalonSRX rightFront, TalonSRX rightBack, TalonSRX rightTop)
    {
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.leftTop = leftTop;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.rightTop = rightTop;
        gyro = new ADIS16448_IMU();
    }

    public void init()
    {
        leftFront.follow(leftTop);
        leftBack.follow(leftTop);
        rightFront.follow(rightTop);
        rightBack.follow(rightTop);
        leftTop.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        rightTop.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        leftTop.setSensorPhase(true);
        rightTop.setSensorPhase(false);
        leftTop.config_kP(Constants.PIDSlotID, Constants.velocKpDrive, Constants.timeoutMS);
        leftTop.config_kI(Constants.PIDSlotID, Constants.velocKiDrive, Constants.timeoutMS);
        leftTop.config_kD(Constants.PIDSlotID, Constants.velocKdDrive, Constants.timeoutMS);
        leftTop.config_kF(Constants.PIDSlotID, Constants.velocKfDriveleft, Constants.timeoutMS);
        rightTop.config_kP(Constants.PIDSlotID, Constants.velocKpDrive, Constants.timeoutMS);
        rightTop.config_kI(Constants.PIDSlotID, Constants.velocKiDrive, Constants.timeoutMS);
        rightTop.config_kD(Constants.PIDSlotID, Constants.velocKdDrive, Constants.timeoutMS);
        rightTop.config_kF(Constants.PIDSlotID, Constants.velocKfDriveright, Constants.timeoutMS);

        //gyro.calibrate();
        //gyro.reset();    
        
        stopAll();
    }

    public void setSidePower(char side, double power)
    {
        if(side == 'l')
        {
            leftTop.set(ControlMode.PercentOutput, -power);
        }
        else if(side == 'r')
        {
            rightTop.set(ControlMode.PercentOutput, power);
        }
    }

    public double getRPMToU100Ms(double RPM)
    {
        return RPM * .1333;//.1333 is composed of (80 u / 60 s / 1000 ms * 100)
    }

    public void setSideVeloc(char side, double RPM)
    {
        if(side == 'l')
        {
            leftTop.set(ControlMode.Velocity, -getRPMToU100Ms(RPM));
        }
        else if(side == 'r')
        {
            rightTop.set(ControlMode.Velocity, getRPMToU100Ms(RPM));
        }
    }

    public void stopAll()
    {
        rightTop.set(ControlMode.PercentOutput, 0);
        leftTop.set(ControlMode.PercentOutput, 0);
    }

    public void controlFallback2Stick(XboxController controller)
    {
        joyRight = controller.getY(Hand.kRight);
        joyLeft = controller.getY(Hand.kLeft);
        if(Math.abs(joyRight) < .2 && Math.abs(joyLeft) < .2)
        {
            stopAll();
        }
        else
        {
            setSidePower('l', joyLeft);
            setSidePower('r', joyRight);
        }
    }

    public void controlStraight2StickVelocity(XboxController controller)
    {
        joyTurn = controller.getX(Hand.kRight);
        joyTrans = controller.getY(Hand.kLeft);
        if(Math.abs(joyTurn) < .2 && Math.abs(joyTrans) < .2)
        {
            stopAll();
        }
        else if(Math.abs(joyTrans) > .2 && Math.abs(joyTurn) < .2)
        {
            setSideVeloc('r', joyTrans * 5330);
            setSideVeloc('l', joyTrans * 5330);
        }
        else if(Math.abs(joyTurn) > .2 && Math.abs(joyTrans) < .2)
        {
            setSidePower('r', joyTurn);
            setSidePower('l', joyTurn);
        }
        else
        {
            setSidePower('l', joyTrans - joyTurn);
            setSidePower('r', joyTrans + joyTurn);
        }
        SmartDashboard.putNumber("lVelU100", leftTop.getSelectedSensorVelocity());
        SmartDashboard.putNumber("rVelU100", rightTop.getSelectedSensorVelocity());
        SmartDashboard.putNumber("lTRPM", joyLeft * 5330);
        SmartDashboard.putNumber("rTRPM", joyRight* 5330);
        SmartDashboard.putNumber("lTU100", getRPMToU100Ms(joyLeft * 5330));
        SmartDashboard.putNumber("rTU100", getRPMToU100Ms(joyRight * 5330));
        
    }

    public void driveStraight(int feet, String direction)
    {
        leftTop.setSelectedSensorPosition(0);
        rightTop.setSelectedSensorPosition(0);
        double target = ((feet * 12) / (6 * Math.PI)) * 80;
        int lAvgTicks = 0;
        int rAvgTicks = 0;
        int avgTicks = 0;
        double currentPower = 0;
        double lPower = 0;
        double rPower = 0;
        double distErr = 0;
        double alignErr = 0;
        double distKp = 0.15;
        double alignKp = 0.15;
        final double SLEW = .003;//AKA the acceleration in rpm/cycle
        while(avgTicks < target)
        {
            lAvgTicks = Math.abs(leftTop.getSelectedSensorPosition());
            rAvgTicks = Math.abs(rightTop.getSelectedSensorPosition());
            avgTicks = (lAvgTicks + rAvgTicks) / 2;
      
            //Make sure we dont accelerate/decelerate too fast with slew
            distErr = (target - avgTicks) * distKp;
            if(distErr > SLEW)
            {
                distErr = SLEW;
            }
      
            //Decide wether to accelerate or decelerate
            if(currentPower * 3.5 > (target - avgTicks))
            {
                distErr = distErr * -1;
                if(currentPower < 5)
                {
                  distErr = 5 - currentPower;
                }
            }
      
            //Decide which side is too far ahead, apply alignment and speed corretions
            alignErr = Math.abs((lAvgTicks - rAvgTicks)) * alignKp;
            if(lAvgTicks > rAvgTicks)
            {
            lPower = (currentPower + distErr) - alignErr;
            rPower = currentPower + distErr;
            }
            else if(rAvgTicks > lAvgTicks)
            {
                rPower = (currentPower + distErr) - alignErr;
                lPower = currentPower + distErr;
            }
            else
            {
                lPower = currentPower + distErr;
                rPower = currentPower + distErr;
            }
      
            //Check what direction we should go, change motor velocities accordingly
            if(direction == "backward")
            {
                lPower = lPower * -1;
                rPower = rPower * -1;
            }
      
            //Send velocity targets to both sides of the drivetrain
            setSideVeloc('l', lPower);
            setSideVeloc('r', rPower);
      
            //Set current power for next cycle, make sure it doesn't get too high/low
            /*As a side note, the distance(in ticks) at which deceleration starts is
            determined by the upper limit on currentPower*/
            currentPower = currentPower + distErr;
            if(currentPower > 4797)
            {
                currentPower = 4797;
            }
            else if(currentPower < 0)
            {
                currentPower = 0;
            }
        }
        stopAll();
    }

}