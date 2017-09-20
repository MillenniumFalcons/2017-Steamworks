package team3647pistons;

import edu.wpi.first.wpilibj.Solenoid;
import team3647subsystems.Joystick007;

public class MainPiston 
{
	//These are the Objects for the Main pistons 
	public Solenoid mainPistonOn = new Solenoid(5);
	public Solenoid mainPistonOff = new Solenoid(4);
	
	boolean joyStickValue;
	
	//This is the function that updates the pistons during Tele-Operated Period
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
	
	//This is a function I created...
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
	
	//This function drops the Main Piston
	public void dropMainPiston()
	{
		mainPistonOn.set(false);
		mainPistonOff.set(true);
	}
	
	//This function grabs the Main Piston
	public void grabMainPiston()
	{
		mainPistonOn.set(true);
		mainPistonOff.set(false);
	}

}