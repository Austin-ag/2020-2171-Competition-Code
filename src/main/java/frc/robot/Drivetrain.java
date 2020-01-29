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

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        rightTop.setSensorPhase(true);
        leftTop.config_kP(Constants.PIDSlotID, Constants.velocKpDrive, Constants.timeoutMS);
        leftTop.config_kI(Constants.PIDSlotID, Constants.velocKiDrive, Constants.timeoutMS);
        leftTop.config_kD(Constants.PIDSlotID, Constants.velocKdDrive, Constants.timeoutMS);
        leftTop.config_kF(Constants.PIDSlotID, Constants.velocKfDrive, Constants.timeoutMS);
        rightTop.config_kP(Constants.PIDSlotID, Constants.velocKpDrive, Constants.timeoutMS);
        rightTop.config_kI(Constants.PIDSlotID, Constants.velocKiDrive, Constants.timeoutMS);
        rightTop.config_kD(Constants.PIDSlotID, Constants.velocKdDrive, Constants.timeoutMS);
        rightTop.config_kF(Constants.PIDSlotID, Constants.velocKfDrive, Constants.timeoutMS);

        gyro.calibrate();
        gyro.reset();       
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

    public void setSideVeloc(char side, double velocUPer100ms)
    {
        if(side == 'l')
        {
            leftTop.set(ControlMode.Velocity, -velocUPer100ms);
        }
        else if(side == 'r')
        {
            rightTop.set(ControlMode.Velocity, velocUPer100ms);
        }
    }

    public void stopAll()
    {
        rightTop.set(ControlMode.PercentOutput, 0);
        leftTop.set(ControlMode.PercentOutput, 0);
    }

    public void control1Stick(XboxController controller)
    {
        double joyY = controller.getY(Hand.kRight);
        double joyX = controller.getX(Hand.kRight);
        if(Math.abs(joyY) < .2 && Math.abs(joyX) < .2)
        {
            stopAll();
        }
        else
        {
            setSidePower('l', (joyY - joyX)/1.2);
            setSidePower('r', (joyY + joyX)/1.2);
        }
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
        double targetUPer100ms;
        joyRight = controller.getY(Hand.kRight);
        joyLeft = controller.getY(Hand.kLeft);
        if(Math.abs(joyRight) < .2 && Math.abs(joyLeft) < .2)
        {
            stopAll();
        }
        else
        {
            if(Math.abs(joyLeft - joyRight) < .20)
            {
                targetUPer100ms = (((joyLeft)) * 5330 * 80) / 600;
                setSideVeloc('r', targetUPer100ms);
                setSideVeloc('l', targetUPer100ms);
            }
            else
            {
                setSidePower('r', joyRight);
                setSidePower('l', joyLeft);
            }
        }
    }

    public void controlStraight2StickGyro(XboxController controller)
    {
        joyRight = controller.getY(Hand.kRight);
        joyLeft = controller.getY(Hand.kLeft);
        if(Math.abs(joyRight) < .2 && Math.abs(joyLeft) < .2)
        {
            stopAll();
        }
        else
        {
            if(Math.abs(joyLeft - joyRight) < .15)
            {
                gyro.reset();
                final double turnKp = .02;
                double lPower;
                double rPower;
                while(Math.abs(joyLeft - joyRight) < .1)
                {
                    joyRight = controller.getY(Hand.kRight);
                    joyLeft = controller.getY(Hand.kLeft);
                    if(gyro.getAngle() > 1)
                    {
                        lPower = (joyLeft + joyRight) / 2 - (gyro.getAngle() * turnKp);
                        rPower = ((joyLeft + joyRight) / 2);
                    }
                    else if(gyro.getAngle() < -1)
                    {
                        lPower = (joyLeft + joyRight) / 2;
                        rPower = ((joyLeft + joyRight) / 2) - (gyro.getAngle() * turnKp);
                    }
                    else
                    {
                        lPower = (joyLeft + joyRight) / 2;
                        rPower = (joyLeft + joyRight) / 2;
                    }
                    SmartDashboard.putNumber("Gyro: ", gyro.getAngle());
                    SmartDashboard.putNumber("lPower: ", lPower);
                    SmartDashboard.putNumber("rPower: ", rPower);
                    setSidePower('r', rPower);
                    setSidePower('l', lPower);
                }
            }
            else
            {
                setSidePower('r', joyRight);
                setSidePower('l', joyLeft);
            }
        }
    }
}