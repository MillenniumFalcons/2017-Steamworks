package team3647pistons;
import edu.wpi.first.wpilibj.Solenoid;
import team3647subsystems.Joystick007;

public class DropDown 
{	
	public Solenoid dropDownOn = new Solenoid(0);
	public Solenoid dropDownOff = new Solenoid(1);
	
	boolean joyStickValue;
	
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
	public void dropItDown()
	{
		dropDownOn.set(true);
		dropDownOff.set(false);
	}
	
	public void dropItUp()
	{
		dropDownOn.set(false);
		dropDownOff.set(true);
	}
	
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