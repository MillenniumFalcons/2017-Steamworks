package team3647pistons;

import edu.wpi.first.wpilibj.Solenoid;

public class LightPiston 
{
	public Solenoid clampOff = new Solenoid(6);
	
	//This function will clamp the pistons
		public void clampThePiston()
		{
			clampOff.set(true);
		}
		
		//This function will un-clamp the pistons
		public void unClampThePiston()
		{
			clampOff.set(false);
		}
}
