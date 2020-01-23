/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Add your docs here.
 */
public class Intake 
{
    private TalonFX intakeMaster;
    private TalonFX intakeSlave;

    public Intake(TalonFX intakeMaster, TalonFX intakeSlave)
    {
        this.intakeMaster = intakeMaster;
        this.intakeSlave = intakeSlave;
    }

    public void init()
    {
        intakeSlave.follow(intakeMaster);
        return;
    }

    public void setIntake(String mode)
    {
        if(mode == "in")
        {
            intakeMaster.set(ControlMode.PercentOutput, 1);
        }
        else if(mode == "out")
        {
            intakeMaster.set(ControlMode.PercentOutput, -1);
        }
        else if(mode == "stop")
        {
            intakeMaster.set(ControlMode.PercentOutput, 0);
        }
    }

    public void controlIntake(XboxController controller)
    {
        if(controller.getAButton())
        {
            setIntake("in");
        }
        else if(controller.getYButton())
        {
            setIntake("out");
        }
        else
        {
            setIntake("off");
        }
    }
}
