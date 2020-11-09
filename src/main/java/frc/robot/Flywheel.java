/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Flywheel 
{
    private int targetRPM = 0;

    private TalonFX flywheel1;
    private TalonFX flywheel2;

    public Flywheel(TalonFX flywheel1, TalonFX flyhweel2)
    {
        this.flywheel1 = flywheel1;
        this.flywheel2 = flyhweel2;
    }

    public void init()
    {
        flywheel1.config_kP(Constants.PIDSlotID, Constants.velocKpFly, Constants.timeoutMS);
        flywheel1.config_kI(Constants.PIDSlotID, Constants.velocKiFly, Constants.timeoutMS);
        flywheel1.config_kD(Constants.PIDSlotID, Constants.velocKdFly, Constants.timeoutMS);
        flywheel1.config_kF(Constants.PIDSlotID, Constants.velocKfFly, Constants.timeoutMS);

        flywheel1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 30);
        flywheel1.setSensorPhase(true);

        flywheel2.follow(flywheel1);
        flywheel2.setInverted(true);

        setFyWheelVelocity(0);
    }

    public double getRPMToU100Ms(int RPM)
    {
        return RPM * -34.13;//34.13 is composed of (2048 u / 60 s / 1000 ms * 100)
    }

    public void setFyWheelVelocity(int RPM)
    {
        if(RPM == 0)
        {
            flywheel1.set(ControlMode.PercentOutput, 0);
        }
        else
        {
            flywheel1.set(ControlMode.Velocity, getRPMToU100Ms(RPM));
        }
    }

    public void controlFlywheel(XboxController controller)//ASK DRIVERS WHAT BUTTONS THEY WANT
    {
       if(controller.getBButtonPressed())
        {
             targetRPM += 586;
        }
        else if(controller.getXButtonPressed())
        {
            targetRPM -= 586;
        }
        if(targetRPM > 6500)
        {
            targetRPM = 6500;
        }
        else if(targetRPM < 5328)
        {
            targetRPM = 5328;
        }
        SmartDashboard.putNumber("Master U/100ms: ", -1* flywheel1.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Master RPMS: ", -1 *(flywheel1.getSelectedSensorVelocity()*600)/2048);
        SmartDashboard.putNumber("Slave U/100s: ", -1 *flywheel2.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Slave RPMS: ", -1 *(flywheel2.getSelectedSensorVelocity()*600)/2048);
        SmartDashboard.putNumber("Target U/100ms: ", getRPMToU100Ms(targetRPM));
        SmartDashboard.putNumber("Target RPM: ", targetRPM);
        setFyWheelVelocity(targetRPM);
    }


    public void flyWheelTest(XboxController controller)
    {
        int target = 0;
        if(Math.abs(controller.getY(Hand.kRight)) < .15)
        {
            setFyWheelVelocity(0);
        }
        else
        {
            if(controller.getAButton())
            {
                target = 1000;
            }
            else if(controller.getBButton())
            {
                target = 2000;
            }
            else if(controller.getYButton())
            {
                target = 3250;
            }
            else if(controller.getXButton())
            {
                target = 6500;
            }
            else if(controller.getStartButton())
            {
                target = 0;
            }

            setFyWheelVelocity(target);

            
        }
    }
}
