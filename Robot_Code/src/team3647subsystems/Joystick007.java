package team3647subsystems;

import edu.wpi.first.wpilibj.Joystick;

public class Joystick007 
{
	public Joystick mainController = new Joystick(0);
	public Joystick guitarController = new Joystick(1);
	public Joystick steeringWheel = new Joystick(2);
	public Joystick bigJoystick = new Joystick(3);
	
	
	public static boolean rightBumper;
	public static boolean leftBumper;
	public static boolean buttonA;
	public static boolean buttonB;
	public static boolean buttonX;
	public static boolean buttonY;
	public static double rightTrigger;
	public static double leftTrigger;	
	public static double leftJoySticky;
	public static double rightJoyStickx;
	public double rightJoySticky;
	public double leftJoyStickx;
	
	
	public static  boolean guitarClampButton;
	public static boolean mainPistonButton;
	public static double climberValue;
	public static boolean clampMotorIn;
	public static boolean clampMotorOut;
	

	public static boolean stwlButtonA;
	public static boolean stwlButtonY;
	public static boolean stwlButtonB;
	public static boolean stwlButtonX;
	public static boolean stwlButtonRT;
	public static boolean stwlButtonRB;
	public static boolean stwlButtonLT;
	public static boolean stwlButtonLB;
	public static double stwlXAxis;	
	
	public static double yaxis;
	public static boolean Bigdropdown;
	
	
	public void updateContollerValues()
	{
		updateMainController();
		updateSteeringWheel();
		updateBigJoystick();
		updateGuitarController();
	}
	
	
	public void updateMainController()
	{
		rightBumper =	mainController.getRawButton(6);
		leftBumper =	mainController.getRawButton(5);
		leftTrigger = fixJoystickValue(mainController.getRawAxis(2));
		buttonA =	mainController.getRawButton(1);
		buttonB = mainController.getRawButton(2);
		rightTrigger = fixJoystickValue(mainController.getRawAxis(3));
		buttonY = mainController.getRawButton(4);
		leftJoySticky = fixJoystickValue(-mainController.getRawAxis(1));
		leftJoyStickx = fixJoystickValue(mainController.getRawAxis(0));
		rightJoyStickx = fixJoystickValue(mainController.getRawAxis(4));
		rightJoySticky = fixJoystickValue(mainController.getRawAxis(5));
		
	}
	
	
	public void updateSteeringWheel()
	{
		stwlButtonA = steeringWheel.getRawButton(3);
		stwlButtonY = steeringWheel.getRawButton(2);
		stwlButtonB = steeringWheel.getRawButton(4);
		stwlButtonX = steeringWheel.getRawButton(1);
		stwlButtonRT = steeringWheel.getRawButton(8);
		stwlButtonRB = steeringWheel.getRawButton(6);
		stwlButtonLT = steeringWheel.getRawButton(7);
		stwlButtonLB = steeringWheel.getRawButton(5);
		stwlXAxis = fixJoystickValue(steeringWheel.getRawAxis(0));
	}
	
	
	public void updateBigJoystick()
	{
		yaxis = -fixJoystickValue(bigJoystick.getRawAxis(1));
		Bigdropdown = bigJoystick.getRawButton(1);
	}
	
	
	public void updateGuitarController()
	{
		clampMotorIn = guitarController.getRawButton(13);
		clampMotorOut = guitarController.getRawButton(14);
		guitarClampButton = guitarController.getRawButton(3);
		mainPistonButton = guitarController.getRawButton(2);
		climberValue = guitarController.getRawAxis(0);
	}
	
	
	public static double fixJoystickValue(double jValue)
	{
		if(jValue < .15 && jValue > -.15)
		{
			return 0;
		}
		else
		{
			return jValue;
		}
	}
}
