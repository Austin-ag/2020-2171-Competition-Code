/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.RobotTesting;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.DrivetrainTestAlex;
import io.github.pseudoresonance.pixy2api.Pixy2;

public class RobotTesting extends TimedRobot{
    private DrivetrainTestAlex drivetrain;
} 

@Override

public void RobotTestingInit(){
    drivetrain = new Drivetrain(new TalonSRX(Constants.Left), new TalonSRX(Constants.Right));
}}