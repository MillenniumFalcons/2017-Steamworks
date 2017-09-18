package team3647pistons;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import team3647subsystems.Joystick007;

public class Clamp 
{
	public Solenoid clamp= new Solenoid(2);
	public Solenoid clampOff = new Solenoid(3);
	public Spark clampMotor = new Spark(4);
	
	boolean joyStickValue;
		
	public void updatePistons()
	{
		joyStickValue =  Joystick007.guitarClampButton;
		
		if(joyStickValue)
		{
			unClampThePiston();
		}
		else
		{
			clampThePiston();
		}
		if(Joystick007.clampMotorOut)
		{
			clampMotor.set(.6);
		}
		else if(Joystick007.clampMotorIn)
		{
			clampMotor.set(-.6);
		}
		else
		{
			clampMotor.set(0);
		}
	}
	
	public void clampThePiston()
	{
		clamp.set(false);
		clampOff.set(true);
	}
	
	public void unClampThePiston()
	{
		clamp.set(true);
		clampOff.set(false);
	}
}