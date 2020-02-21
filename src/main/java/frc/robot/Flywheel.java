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
        flywheel2.setInverted(false);
    }

    public double getRPMToU100Ms(int RPM)
    {
        return RPM * 34.13;//34.13 is composed of (2048 u / 60 s / 1000 ms * 100)
    }

    public void setFyWheelVelocity(int RPM)
    {
        flywheel1.set(ControlMode.Velocity, getRPMToU100Ms(RPM));
    }

    public void flyWheelTest(XboxController controller)
    {
        if(Math.abs(controller.getY(Hand.kRight)) < .15)
        {
            flywheel1.set(ControlMode.PercentOutput, 0);
        }
        else
        {

            flywheel1.set(ControlMode.Velocity, 20000);
            SmartDashboard.putNumber("Master", flywheel1.getSelectedSensorVelocity());
            SmartDashboard.putNumber("Slave: ", flywheel2.getSelectedSensorVelocity());
        }
    }
}
