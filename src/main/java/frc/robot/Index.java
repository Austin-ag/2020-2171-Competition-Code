/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Add your docs here.
 */
public class Index 
{
    private Talon index;

    public Index(Talon index)
    {
        this.index = index;
    }

    public void init()
    {
        index.set(0);
    }

    public void setIndex(String mode)
    {
        if(mode == "in")
        {
            index.set(1);
        }
        else if(mode == "out")
        {
            index.set(-1);
        }
        else if(mode == "off")
        {
            index.set(0);
        }
    }

    public void controlIndex(XboxController controller)//ASK DRIVERS WHAT BUTTONS THEY WANT
    {
        if(controller.getPOV(0) == 0)
        {
            setIndex("in");
        }
        else if(controller.getPOV(0) == 180)
        {
            setIndex("out");
        }
        else
        {
            setIndex("off");
        }
    }
}