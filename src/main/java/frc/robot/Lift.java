/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.XboxController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * Add your docs here.
 */
public class Lift 
{
    private TalonFX master;
    private TalonFX slave;
    private boolean holdFlag;
    private int currentPos;

    public Lift(TalonFX master, TalonFX slave)
    {
        this.master = master;
        this.slave = slave;
        holdFlag = false;
    }

    public void init()
    {
        slave.follow(master);
        master.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.PIDSlotID, Constants.timeoutMS);
        master.setSensorPhase(true);
        master.config_kP(Constants.PIDSlotID, Constants.posKpLift, Constants.timeoutMS);
        master.config_kI(Constants.PIDSlotID, Constants.posKiLift, Constants.timeoutMS);
        master.config_kD(Constants.PIDSlotID, Constants.posKdLift, Constants.timeoutMS);
        master.config_kF(Constants.PIDSlotID, Constants.posKfLift, Constants.timeoutMS);
    }

    public void setLiftPower(double power)
    {
        master.set(ControlMode.PercentOutput, power);
    }

    public void holdPos()
    {
        currentPos = 0;
        master.set(ControlMode.Position, currentPos);
    }

    
    public void controlLift(XboxController controller)
    {
        if(controller.getAButton())
        {
            setLiftPower(1);
        }
        else if(controller.getYButton())
        {
            setLiftPower(-1);
        }
        else if(controller.getAButton() && controller.getYButton())
        {
            if(holdFlag)
            {
                setLiftPower(0);
            }
            else
            {
                holdPos();
            }
            holdFlag = !holdFlag;
        }
        else if(!controller.getYButton() && !controller.getAButton() && holdFlag != true)
        {
            setLiftPower(0);
        }
    }
}
