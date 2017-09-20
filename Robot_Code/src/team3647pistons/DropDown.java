package team3647pistons;
import edu.wpi.first.wpilibj.Solenoid;
import team3647subsystems.Joystick007;

public class DropDown 
{	
	//These are the Objects for the dropDown pistons oon the omni wheels
	public Solenoid dropDownOn = new Solenoid(0);
	public Solenoid dropDownOff = new Solenoid(1);
	
	boolean joyStickValue;
	
	//This is the function that updates the pistons during Tele-Operated Period for X-box controller
	public void updatePistons()
	{
		joyStickValue =  Joystick007.rightBumper;
		
		if(joyStickValue)
		{
			dropItDown();
		}
		else
		{
			dropItUp();
		}
	}
	
	//This function drops the omni-wheels
	public void dropItDown()
	{
		dropDownOn.set(true);
		dropDownOff.set(false);
	}
	
	//This function lifts up the omni-wheels
	public void dropItUp()
	{
		dropDownOn.set(false);
		dropDownOff.set(true);
	}
	
	//This is the function that updates the pistons during Tele-Operated Period for Steering Wheel Controller
	public void steeringWheelDropDown()
	{
		joyStickValue =  Joystick007.Bigdropdown;
		
		if(joyStickValue)
		{
			dropItDown();
		}
		else
		{
			dropItUp();
		}
	}
}