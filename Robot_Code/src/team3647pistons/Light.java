package team3647pistons;

import edu.wpi.first.wpilibj.Solenoid;

public class Light 
{
	public Solenoid clampOff = new Solenoid(6);
	
	public void On()
	{
		clampOff.set(true);
	}
	public void Off()
	{
		clampOff.set(false);
	}

}
