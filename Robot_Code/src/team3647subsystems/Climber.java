package team3647subsystems;

import edu.wpi.first.wpilibj.Talon;
import team3647subsystems.Joystick007;

public class Climber 
{
	Talon climber = new Talon(0);
	Talon climberTwo = new Talon(3);
	
	public void theClimber()
	{
		double joyStickValues = fixedJoystick();
		if(joyStickValues == 0)
		{
			climber.set(0);
			climberTwo.set(0);
		}
		else
		{
			climber.set(joyStickValues);
			climberTwo.set(joyStickValues);
		}
	}
	public double fixedJoystick()
	{
		double joyStickValues;
		joyStickValues = Joystick007.climberValue;
		if( joyStickValues < .15)
		{
			return 0;
		}
		else
		{
			return joyStickValues;
		}
	}
}