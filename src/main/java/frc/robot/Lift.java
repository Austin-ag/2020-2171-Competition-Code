/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Talon;

public class Lift
{
    private Talon liftMaster;
    private Talon liftSlave;

    public Lift(Talon liftMaster, Talon liftSlave)
    {
        this.liftMaster = liftMaster;
        this.liftSlave = liftSlave;
    }

    public void init()
    {
       setLift("off");
    }

    public void setLift(String mode)
    {
        if(mode == "up")
        {
            liftMaster.set(.75);
            liftSlave.set(.75);
        }
        else if(mode == "down")
        {
            liftMaster.set(-.75);
            liftSlave.set(-.75);
        }
        else if(mode == "off")
        {
            liftMaster.set(0);
            liftSlave.set(0);
        }
    }


    public void controllLift(XboxController controller)
    {
        if(controller.getXButton())//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            setLift("up");
        }
        else if(controller.getYButton())//ASK DRIVERS WHAT BUTTONS THEY WANT
        {
            setLift("down");
        }
        else
        {
            setLift("off");
        }

    }




}