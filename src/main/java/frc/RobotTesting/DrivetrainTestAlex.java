/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//1
package frc.RobotTesting;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import com.analog.adis16448.frc.ADIS16448_IMU;

public class Drivetrain{
    private TalonSRX Left;
    private TalonSRX Right;
    
    double JoyTurn;
    double JoyTrans;
    double JoyLeft;
    double JoyRight;

    Drivetrain(TalonSrx Left, TalonSrx Right){
        this.Left = Left;
        this.Right = Right;
    }
    public void init(){


    }
    
}