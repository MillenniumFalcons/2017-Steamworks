package team3647pistons;

import edu.wpi.first.wpilibj.Solenoid;
import team3647subsystems.Joystick007;

public class MainPiston 
{
	public Solenoid mainPistonOn = new Solenoid(5);
	public Solenoid mainPistonOff = new Solenoid(4);
	
	boolean joyStickValue;
	
	public void updatePistons()
	{
		joyStickValue =  Joystick007.mainPistonButton;
		
		if(tORf(joyStickValue, true))
		{
			dropMainPiston();
		}
		
		if(tORf(joyStickValue, false))
		{
			grabMainPiston();
		}
	}
	
	
	public boolean tORf(boolean stickValue, boolean pistonStatus)
	{
		if(stickValue == false && pistonStatus == false)
		{
			return false;
		}
		else if(stickValue == true && pistonStatus == false)
		{
			return true;
		}
		else if(stickValue == true && pistonStatus == true)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void dropMainPiston()
	{
		mainPistonOn.set(false);
		mainPistonOff.set(true);
	}
	
	public void grabMainPiston()
	{
		mainPistonOn.set(true);
		mainPistonOff.set(false);
	}

}