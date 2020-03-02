/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * Add your docs here.
 */
public class Intake 
{
    private Talon intakeMaster;

    private Solenoid intake;

    public Intake(Talon intakeMaster)
    {
        this.intakeMaster = intakeMaster;
    }

    public void init()
    {
        setIntake("off");
    }

    public void setIntake(String mode)
    {
        if(mode == "in")
        {
            intakeMaster.set(.75);
        }
        else if(mode == "out")
        {
            intakeMaster.set(-.75);
        }
        else if(mode == "off")
        {
            intakeMaster.set(0);
        }
    }

    public void moveIntake(String pos)
    {
        if(pos == "in")
        {
            intake.set(true);
        }
        else if(pos == "out")
        {
            intake.set(false);
        }
    }

    public void controlIntake(XboxController controller)
    {
        if(controller.getAButton())//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            setIntake("in");
        }
        else if(controller.getYButton())//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            setIntake("out");
        }
        else
        {
            setIntake("off");
        }

        if(controller.getAButton())//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            moveIntake("in");
        }
        else if(controller.getAButton())//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            moveIntake("out");
        }
    }
}
