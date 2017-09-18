package team3647subsystems;
import edu.wpi.first.wpilibj.DigitalInput;
public class DigitalInputs
{
	static DigitalInput pinNine = new DigitalInput(9);
	static DigitalInput pinEight = new DigitalInput(8);
	static DigitalInput pinSeven = new DigitalInput(7);

	public static double digitalPins()
	{
		if(pinSeven.get() == false && pinEight.get() == false && pinNine.get() == false)
		{
			return 0;
		}
		else if(pinSeven.get() == true && pinEight.get() == false && pinNine.get() == false)
		{
			return 1;
		}
		else if(pinSeven.get() == false && pinEight.get() == true && pinNine.get() == false)
		{
			return 2;
		}
		else if(pinSeven.get() == false && pinEight.get() == false && pinNine.get() == true)
		{
			return 3;
		}
		else if(pinSeven.get() == true && pinEight.get() == false && pinNine.get() == true)
		{
			return 4;
		}
		else if(pinSeven.get() == true && pinEight.get() == true && pinNine.get() == false)
		{
			return 5;
		}
		else if(pinSeven.get() == false && pinEight.get() == true && pinNine.get() == true)
		{
			return 6;
		}
		else
		{
			return 7;
		}
	}
}
	
	/*
	 1,2,3
	 3,2
	 1,2
	 1,3-
	 1-
	 2-
	 3-
	 
	 
	 0. If just middle auto without crossing midField:
	 	all pins sends 0
	 
	 1. If middle auto with crossing midField through the left direction:
	 	pinSeven sends 1, pin Eight sends 0, and pinNine sends 0
	 	
	 2. If middle auto with crossing midField through the right direction:
	 	pinSeven sends 0, pin Eight sends 1, and pinNine sends 0
	 	
	 3. If we are blue alliance and are next to the retrieval zone:
	 	pinSeven sends 0, pin Eight sends 0, and pinNine sends 1
	 
	 4. If we are blue alliance and are next to the boiler:
	 	pinSeven sends 1, pin Eight sends 0, and pinNine sends 1
	 
	 5. If we are red alliance and are next to the retrieval zone:
	 	pinSeven sends 1, pin Eight sends 1, and pinNine sends 0
	 	
	 6. If we are red alliance and are next to the boiler:
	 	pinSeven sends 0, pin Eight sends 1, and pinNine sends 1
	 	
	 7. If we just want to go forward for a set distance
	 	all pins send 1
	 */