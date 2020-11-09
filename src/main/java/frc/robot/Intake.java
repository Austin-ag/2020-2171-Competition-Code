/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid;


/**
 * Add your docs here.
 */
public class Intake 
{
    private Talon intakeMaster;

    private DoubleSolenoid intake;

    public Intake(Talon intakeMaster, DoubleSolenoid intake)
    {
        this.intakeMaster = intakeMaster;
        this.intake = intake;
    }

    public void init()
    {
        setIntake("off");
        moveIntake("in");
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
            intake.set(Value.kForward);
        }
        else if(pos == "out")
        {
            intake.set(Value.kReverse);
        }
    }

    public void controlIntake(XboxController controller)
    {
        if(controller.getTriggerAxis(Hand.kLeft) > .2)
        {
            setIntake("in");
        }
        else if(controller.getBumper(Hand.kLeft))
        {
            setIntake("out");
        }
        else
        {
            setIntake("off");
        }

        if(controller.getBumper(Hand.kRight))//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            moveIntake("in");
        }
        else if(controller.getTriggerAxis(Hand.kRight) > .50)//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            moveIntake("out");
        }
    }
}
