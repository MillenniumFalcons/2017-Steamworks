package org.usfirst.frc.team3647.robot;

//These are the import statements...
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import team3647pistons.Clamp;
import team3647pistons.DropDown;
import team3647pistons.MainPiston;
import team3647subsystems.Climber;
import team3647subsystems.DigitalInputs;
import team3647subsystems.Motors007;
import team3647subsystems.Encoders;
import team3647subsystems.Joystick007;

public class Robot extends IterativeRobot {
	
	//Variables used for conditions in Autonomous
	double autoSelected;
	double leftEncoderValues;
	double rightEncoderValues;
	boolean leftSide, rightSide;
	int currentState;
	
	//Variables used for moving robot autonomously
	double straightdist = 1900;
	double straightDistBR = 1940;
	double straightDistRR = 1840;
	double bigTurn = 1600;
	double smallTurn = 930;
	double distanceforGearFromMiddle = 1600;
	double backUpDisatnceFromMiddle = 2300;
	double goForwToCross = 5000;

	//Variables used for conditions in Tele-Op
	boolean steeringWheelDrive = false;
	int prevState = 0;
	String switchWiggle = "left";
	
	//Creating objects...
	Encoders enc;
	Clamp clampObj;
	DropDown dropdown;
	MainPiston mainpiston;
	Climber climberObj;
	Joystick007 joyStickValues;
	CameraServer server;
	
	//This is the code for the robot initialization
	//This function runs once before the whole robot starts running
	//Assign classes for all the objects
	public void robotInit() 
	{
		enc = new Encoders();
		joyStickValues = new Joystick007();
		clampObj = new Clamp();
		dropdown = new DropDown();
		mainpiston = new MainPiston();
		climberObj = new Climber();

		//This piece of code runs the camera
		server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0", 0);
	}

	//This is the code for the robot initialization
	//This function runs once before we start the autonomous period\
	//We reset encoders, make sure the robot is not moving, and all the variables we are going to use are set to the correct value
	public void autonomousInit() 
	{
		currentState = 1;
		enc.resetEncoders();	
		Motors007.leftTalon.set(0);
		Motors007.rightTalon.set(0);
		leftSide = false;
		rightSide = false; 
	}

	// This is the piece of code that runs during the Autonomous Period
	//We use a Digital Pin system to determine what type of autonomous we would like to run so that we would not have to upload code every time
	public void autonomousPeriodic() 
	{
		while(DriverStation.getInstance().isAutonomous() && !DriverStation.getInstance().isDisabled())
		{
			autoSelected = DigitalInputs.digitalPins();
			if(autoSelected == 0)
			{
				centerAuto();
			}
			else if(autoSelected == 3)
			{
				newBlueRetrieval();
			}
			else if(autoSelected == 5)
			{
				newRedRetrieval();
			}
			else
			{
				moveForw();
			}
		}
	}

	// This piece of code is an emergency auto in case the robot can't read any digital inputs
	//Case 1: Make sure the Main piston is in position to score a gear
	//Case 2: Drive straight fast for 2300 ticks
	//Case 3: Stop the robot and unclamp the piston
	public void moveForw()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1);
				mainpiston.grabMainPiston();
				Timer.delay(1);
				currentState = 2;
				break;
			case 2:
				if(enc.runOrNotForStraight(2000))
				{
					enc.slowDrive(2000);

				}
				else
				{
					currentState = 3;
				}
				break;		
			case 3:
				clampObj.unClampThePiston();
				Motors007.leftTalon.set(0);
				Motors007.rightTalon.set(0);
				break;
		}
	}
		
	//This is the piece of code that will run if we start the robot from the middle position for either color
	//Case 1: Make sure the Main piston is in position to score a gear
	//Case 2: Drive straight for 1600 ticks
	//Case 3: Wiggle and deliver the gear
	//Case 4: Drive backward for 2 seconds
	//Case 5: Stop the robot
	//Case 6: End Case
	public void centerAuto()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1);
				mainpiston.grabMainPiston();
				Timer.delay(1);
				currentState = 2;
				break;
			case 2:
				if(enc.runOrNotForStraight(distanceforGearFromMiddle))
				{
					enc.encoderDrive(distanceforGearFromMiddle, 1);
				}
				else
				{
					Motors007.leftTalon.set(0);
					Motors007.rightTalon.set(0);
					currentState = 3;
				}
				break;
			case 3:
				autoWiggle();
				mainpiston.dropMainPiston();
				clampObj.unClampThePiston();
				Timer.delay(.6);
				enc.resetEncoders();
				currentState = 4;
				break;
			case 4: 
				Motors007.leftTalon.set(-.3);
				Motors007.rightTalon.set(.3);
				Timer.delay(2);
				currentState = 5;
				break;
			case 5:
				Motors007.leftTalon.set(0);
				Motors007.rightTalon.set(0);
				Timer.delay(.2);
				mainpiston.grabMainPiston();
				Timer.delay(.3);
				enc.resetEncoders();
				currentState = 6;
				break;
			case 6:
				Motors007.leftTalon.set(0);
				Motors007.rightTalon.set(0);
				break;
		}
	}
	
	//This is the piece of code that will run if we start the robot from blue retrieval
	//Case 1: Make sure the Main piston is in position to score a gear
	//Case 2: Drive straight fast for 1580 ticks
	//Case 3: Drive straight slow for 360 ticks
	//Case 4: ResetEncoders
	//Case 5: Turn left
	//Case 6: Set sides to false
	//Case 7: Wiggle
	//Case 8: Deliver the gear
	//Case 9: Turn backward
	//Case 10: Stop the Robot
	//Case 11: Cross MidField
	//Case 12: End Case
	public void newBlueRetrieval()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1);
				mainpiston.grabMainPiston();
				Timer.delay(1);
				currentState = 2;
				break;
			case 2:
				if(enc.runOrNotForStraight(straightDistBR -360))
				{
					enc.encoderDrive(straightDistBR-360, 1);

				}
				else
				{
					Motors007.leftTalon.set(0);
					Motors007.rightTalon.set(0);
					Timer.delay(.2);
					currentState = 3;
				}
				break;			
			case 3:
				if(enc.runOrNotForStraight(straightDistBR ))
				{
					enc.encoderDrive(straightDistBR, 2);
				}
				else
				{
					currentState = 4;
				}
				break;
			case 4:
				enc.resetEncoders();
				currentState =5;
				break;
			case 5:
				mainpiston.grabMainPiston();
				if(enc.returnrightEncValue()<(bigTurn-330))
				{
					Motors007.rightTalon.set(-.6);
				}
				else
				{
					Motors007.rightTalon.set(0);
					rightSide = true;
				}
				if(enc.returnleftEncValue()<(smallTurn-170))
				{
					Motors007.leftTalon.set(.3);
				}
				else
				{
					Motors007.leftTalon.set(0);
					leftSide = true;
				}
				if(rightSide && leftSide)
				{
					Timer.delay(.2);
					currentState = 6;
				}
				break;
			case 6:
				rightSide = false;
				leftSide = false;
				currentState = 7;
				break;
			case 7:
				autoWiggle();
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				currentState = 8;
				break;
			case 8:
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				Timer.delay(.1);
				mainpiston.dropMainPiston();
				clampObj.unClampThePiston();
				Timer.delay(.3);
				enc.resetEncoders();
				currentState = 9;
				break;
			case 9:
				leftEncoderValues = Math.abs(enc.returnleftEncValue());
				rightEncoderValues = Math.abs(enc.returnrightEncValue());
				if(rightEncoderValues < bigTurn)
				{
					Motors007.rightTalon.set(.4);
				}
				else
				{
					Motors007.rightTalon.set(0);
					rightSide = true;
				}
				if(leftEncoderValues < smallTurn)
				{
					Motors007.leftTalon.set(-.2);
				}
				else
				{
					Motors007.leftTalon.set(0);
					leftSide = true;
				}
				if(leftSide && rightSide)
				{
					Timer.delay(.5);
					currentState = 10;
				}
				break;
			case 10:
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				mainpiston.grabMainPiston();
				clampObj.clampThePiston();
				Timer.delay(.5);
				enc.resetEncoders();
				currentState = 11;
				break;
			case 11:
				if(enc.runOrNotForStraight(goForwToCross))
				{
					enc.encoderDrive(goForwToCross, 1);
				}
				else
				{
					Motors007.leftTalon.set(0);
					Motors007.rightTalon.set(0);
					Timer.delay(.5);
					currentState = 12;
				}
				break;
			case 12 :
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				break;
			}
	}
	
	//This is the piece of code that will run if we start the robot from blue retrieval
	//Case 1: Make sure the Main piston is in position to score a gear
	//Case 2: Drive straight fast for 1480 ticks
	//Case 3: Drive straight slow for 360 ticks
	//Case 4: ResetEncoders
	//Case 5: Turn right
	//Case 6: Set sides to false
	//Case 7: Wiggle
	//Case 8: Deliver the gear
	//Case 9: Turn backward
	//Case 10: Stop the Robot
	//Case 11: Cross MidField
	//Case 12: End Case
	public void newRedRetrieval()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1);
				mainpiston.grabMainPiston();
				Timer.delay(1);
				currentState = 2;
				break;
			case 2:
				if(enc.runOrNotForStraight(straightDistRR -360))
				{
					enc.encoderDrive(straightDistRR-360, 1);
				}
				else
				{
					Motors007.leftTalon.set(0);
					Motors007.rightTalon.set(0);
					Timer.delay(.2);
					currentState = 3;
				}
				break;		
			case 3:
				if(enc.runOrNotForStraight(straightDistRR ))
				{
					enc.encoderDrive(straightDistRR, 2);
				}
				else
				{
					currentState = 4;
				}
				break;
			case 4:
				enc.resetEncoders();
				currentState =5;
				break;
			case 5:
				if(enc.returnleftEncValue()<(bigTurn-260))
				{
					Motors007.leftTalon.set(.48);
				}
				else
				{
					Motors007.leftTalon.set(0);
					leftSide = true;
				}
				if(enc.returnrightEncValue()<(smallTurn-170))
				{
					Motors007.rightTalon.set(-.22);
				}
				else
				{
					Motors007.rightTalon.set(0);
					rightSide = true;
				}
				if(rightSide && leftSide)
				{
					Timer.delay(.2);
					currentState = 6;
				}
				break;
			case 6:
				autoWiggle();
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				currentState = 7;
				break;
			case 7:
				rightSide = false;
				leftSide = false;
				currentState = 8;
				break;
			case 8:
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				Timer.delay(.1);
				mainpiston.dropMainPiston();
				clampObj.unClampThePiston();
				Timer.delay(.3);
				enc.resetEncoders();
				currentState = 9;
				break;
			case 9:
				leftEncoderValues = Math.abs(enc.returnleftEncValue());
				rightEncoderValues = Math.abs(enc.returnrightEncValue());
				if(leftEncoderValues< 100)
				{
					Motors007.leftTalon.set(-1);
				}
				else
				{
					enc.resetEncoders();
					Timer.delay(.1);
					currentState = 10;
				}
				break;
			case 10:
				leftEncoderValues = Math.abs(enc.returnleftEncValue());
				rightEncoderValues = Math.abs(enc.returnrightEncValue());
				if(leftEncoderValues < bigTurn)
				{
					Motors007.leftTalon.set(-.4);
				}
				else
				{
					Motors007.leftTalon.set(0);
					leftSide = true;
				}
				if(rightEncoderValues < smallTurn)
				{
					Motors007.rightTalon.set(.2);
				}
				else
				{
					Motors007.rightTalon.set(0);
					rightSide = true;
				}
				if(leftSide && rightSide)
				{
					Timer.delay(.5);
					currentState = 11;
				}
				break;
			case 11:
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				mainpiston.grabMainPiston();
				clampObj.clampThePiston();
				Timer.delay(.5);
				enc.resetEncoders();
				currentState = 12;
				break;
			case 12:
				if(enc.runOrNotForStraight(goForwToCross))
				{
					enc.encoderDrive(goForwToCross, 1);
				}
				else
				{
					Motors007.leftTalon.set(0);
					Motors007.rightTalon.set(0);
					Timer.delay(.5);
					currentState = 13;
				}
				break;
			case 13:
				Motors007.rightTalon.set(0);
				Motors007.leftTalon.set(0);
				break;
			}
	}
	
	//This is the function that runs during Tele-Operated Period
	public void teleopPeriodic() 
	{
		runTeleOp();
	}
	
	//This is the function thats being called during Tele-Op
	//The functions we call depend upon what kind of controller we are using to control the robot
	//We can either control the robot with a normal x-box Joystick or with a steering wheel
	public void runTeleOp()
	{
		if(steeringWheelDrive)
		{
			joyStickValues.updateContollerValues();
			updateAllPistonsForSteeringWheel();
			climberObj.theClimber();
			driveTrainForSteeringWheel();
		}
		else
		{
			joyStickValues.updateContollerValues();
			updateAllPistons();
			climberObj.theClimber();
			driveTrain();
		}
	}
	
	//This is the piece of code that updates all the pistons for Tele-operated period
	//This function assumes you are using a normal Xbox Controller
	public void updateAllPistons()
	{
		clampObj.updatePistons();
		mainpiston.updatePistons();
		dropdown.updatePistons();
	}
		
	//This is the piece of code that updates all the pistons for Tele-operated period
	//This function assumes you are using the Big Joy-stick and the Steering Wheel
	public void updateAllPistonsForSteeringWheel()
	{
		clampObj.updatePistons();
		mainpiston.updatePistons();
		dropdown.steeringWheelDropDown();
	}
	
	//This is the code for the Drive-train
	//This is the function that determines what type of drive-train we would like to use
	public void driveTrain()
	{
		if(Joystick007.leftBumper)
		{
			teleOpWiggle();
		}
		else if(Joystick007.leftTrigger > 0)
		{
		
			sloMoDrive();
		}
		else
		{
			updatedArcadeDrive();
		}
	}
	
	//This function tells us to use the arcade drive for the Steering Wheel Joy-stick
	public void driveTrainForSteeringWheel()
	{
		updatedArcadeDriveForSteeringWheelDrive();
	}
	
	
	public void testPeriodic() 
	{
		// Does N O T H I N G
	}
	
	//This function is when the driver would like to drive the robot in slow motion
	//This function does not involve a P loop
	public void sloMoDrive()
	{
		double leftValue, rightValue;
		double checks,checkt,speed,turn;
		leftValue = sinx(Joystick007.leftJoySticky);
		rightValue = Joystick007.rightJoyStickx;
		
		checks = Math.abs(leftValue);
		checkt = Math.abs(rightValue);
		
		if (checks <.15) 	
		{
			speed = 0;
		}
		else 			
		{
			speed = .3*(checks * leftValue);
		}
		if (checkt <.15) 	
		{
			turn = 0;
		}
		else 			
		{
			if(leftValue == 0)
			{
				turn = .3*(rightValue);
			}
			else
			{
				turn = .3*(rightValue);
			}
			
		}    
		Motors007.leftTalon.set(((speed+turn)));
	    Motors007.rightTalon.set(((-speed+turn)));
	}
	
	//This is the function we use for our normal drive-train for the X-box controller
	//In this drive-train, we also use a P loop so that the robot can drive straight
	//We use the P loop when prevState = 1 or 2
	public void updatedArcadeDrive()
	{
		double leftValue, rightValue;
		double checks,checkt,speed,turn;
		leftValue = sinx(Joystick007.leftJoySticky);
		rightValue =  (Joystick007.rightJoyStickx);
		
		checks = Math.abs(leftValue);
		checkt = Math.abs(rightValue);
		
		if(leftValue > 0 && rightValue == 0 )
		{
			prevState = 1;
		}
		else if(leftValue < 0 && rightValue == 0)
		{
			prevState = 2;
		}
		else
		{
			enc.resetEncoders();
			prevState = 0;
		}
		
		if(prevState == 0)
		{
			if (checks <.15) 	
			{
				speed = 0;
			}
			else 			
			{
				speed = 1*(checks * leftValue);
			}
			if (checkt <.15) 	
			{
				turn = 0;
			}
			else 			
			{
				if(leftValue == 0)
				{
					turn = .5*(rightValue);
				}
				else
				{
					turn = .5*(rightValue);
				}
				
			}    
			Motors007.leftTalon.set(((speed+turn)));
		    Motors007.rightTalon.set(((-speed+turn)));
		    
		}
		else if(prevState == 1)
		{
				double rightEncValue, leftEncValue;
				rightEncValue = enc.returnrightEncValue();
				leftEncValue = enc.returnleftEncValue();
				if(leftValue < .3)
				{
					enc.resetEncoders();
					Motors007.leftTalon.set(leftValue);
					Motors007.rightTalon.set(-leftValue);
					
				}
				
				else
				{
						if(Math.abs(rightEncValue - leftEncValue) < 6)
						{
							Motors007.leftTalon.set(leftValue);
						    Motors007.rightTalon.set(-leftValue);
						}
						else if(rightEncValue > leftEncValue)
						{
							Motors007.leftTalon.set(leftValue);
						    Motors007.rightTalon.set(-leftValue + .34);
						}
						else
						{
							Motors007.leftTalon.set(leftValue - .34);
						    Motors007.rightTalon.set(-leftValue);
						}
					
				}
		}
		else
		{
			double rightEncValue, leftEncValue;
			rightEncValue = enc.returnrightEncValue();
			leftEncValue = enc.returnleftEncValue();
			if(Math.abs(leftValue)< .3)
			{
				enc.resetEncoders();
				Motors007.leftTalon.set(leftValue);
				Motors007.rightTalon.set(-leftValue);
			}
			else
			{
					if(Math.abs(rightEncValue - leftEncValue) < 6)
					{
						Motors007.leftTalon.set(leftValue);
					    Motors007.rightTalon.set(-leftValue);
					}
					else if(rightEncValue > leftEncValue)
					{
						Motors007.leftTalon.set(leftValue + .34);
					    Motors007.rightTalon.set(-leftValue);
					}
					else
					{
						Motors007.leftTalon.set(leftValue);
					    Motors007.rightTalon.set(-leftValue - .34);
					
					}
			}
		}	
	}
	
	//This is the function we use for our normal drive-train for the Steering Wheel controller
	//In this drive-train, we also use a P loop so that the robot can drive straight
	//We use the P loop when prevState = 1 or 2
	public void updatedArcadeDriveForSteeringWheelDrive()
	{
		double leftValue, rightValue;
		double checks,checkt,speed,turn;
		leftValue = sinx(Joystick007.yaxis);
		rightValue =  (Joystick007.stwlXAxis);
		
		checks = Math.abs(leftValue);
		checkt = Math.abs(rightValue);
		
		if(leftValue > 0 && rightValue == 0 )
		{
			prevState = 1;
		}
		else if(leftValue < 0 && rightValue == 0)
		{
			prevState = 2;
		}
		else
		{
			enc.resetEncoders();
			prevState = 0;
		}
		
		if(prevState == 0)
		{
			if (checks <.15) 	
			{
				speed = 0;
			}
			else 			
			{
				speed = 1*(checks * leftValue);
			}
			if (checkt <.15) 	
			{
				turn = 0;
			}
			else 			
			{
				if(leftValue == 0)
				{
					turn = rightValue;
				}
				else
				{
					turn = rightValue;
				}
				
			}    
			Motors007.leftTalon.set(((speed+turn)));
		    Motors007.rightTalon.set(((-speed+turn)));
		    
		}
		else if(prevState == 1)
		{
				double rightEncValue, leftEncValue;
				rightEncValue = enc.returnrightEncValue();
				leftEncValue = enc.returnleftEncValue();
				if(leftValue < .3)
				{
					enc.resetEncoders();
					Motors007.leftTalon.set(leftValue);
					Motors007.rightTalon.set(-leftValue);
					
				}
				
				else
				{
						if(Math.abs(rightEncValue - leftEncValue) < 6)
						{
							Motors007.leftTalon.set(leftValue);
						    Motors007.rightTalon.set(-leftValue);
						}
						else if(rightEncValue > leftEncValue)
						{
							Motors007.leftTalon.set(leftValue);
						    Motors007.rightTalon.set(-leftValue + .34);
						}
						else
						{
							Motors007.leftTalon.set(leftValue - .34);
						    Motors007.rightTalon.set(-leftValue);
						}
					
				}
		}
		else
		{
			double rightEncValue, leftEncValue;
			rightEncValue = enc.returnrightEncValue();
			leftEncValue = enc.returnleftEncValue();
			if(Math.abs(leftValue)< .3)
			{
				enc.resetEncoders();
				Motors007.leftTalon.set(leftValue);
				Motors007.rightTalon.set(-leftValue);
			}
			else
			{
					if(Math.abs(rightEncValue - leftEncValue) < 6)
					{
						Motors007.leftTalon.set(leftValue);
					    Motors007.rightTalon.set(-leftValue);
					}
					else if(rightEncValue > leftEncValue)
					{
						Motors007.leftTalon.set(leftValue + .34);
					    Motors007.rightTalon.set(-leftValue);
					}
					else
					{
						Motors007.leftTalon.set(leftValue);
					    Motors007.rightTalon.set(-leftValue - .34);
					
					}
			}
		}	
	}

	//This function takes the sin of the stickValue
	public double sinx(double stickValue)
	{
		double calculatedValue = Math.PI * stickValue;
		double secondValue = calculatedValue/2;
		if(stickValue > -.15 && stickValue < .15)
		{
			return 0;
		}
		else
		{
			return Math.sin(secondValue);
		}
	}
	
	//This function makes the robot Wiggle during the Tele-Operated Period
	public void teleOpWiggle()
	{
		switch(switchWiggle)
		{
			case "left":
				if(enc.returnleftEncValue() < 100)
				{
					Motors007.leftTalon.set(.45);
				}
				else
				{
					Motors007.leftTalon.set(0);
					enc.resetEncoders();
					switchWiggle = "right";
				}
				break;
			case "right":
				if(enc.returnrightEncValue() < 100)
				{
					Motors007.rightTalon.set(-.45);
				}
				else
				{
					Motors007.rightTalon.set(0);
					enc.resetEncoders();
					switchWiggle = "left";
				}
				break;
		}
	}
	
	//This function makes the robot Wiggle during the Autonomous Period
	public void autoWiggle()
	{
		for (int i = 1; i<=5;i++)
		{
				Motors007.leftTalon.set(.6);
				Timer.delay(.15);
				Motors007.leftTalon.set(-.3);
				enc.resetRight();
			
			
				Motors007.rightTalon.set(-.6);
				Timer.delay(.12);
				Motors007.rightTalon.set(.3);
				enc.resetLeft();	
		}
	}
	
	//This is the function we use to test our encoders
	//We spin the encoders physically and get reading of the encoder values
	public void testEncoders()
	{
		System.out.println("Right encoder value: " + enc.returnrightEncValue());
		System.out.println("Left encoder value: " + enc.returnleftEncValue());
	}
	
	//This is the function we use to test what type of Autonomous we will be running
	public void printAutoMode()
	{
		autoSelected = DigitalInputs.digitalPins();
		
		if(autoSelected == 0)
		{
			System.out.println("Running Center Auto");
		}
		else if(autoSelected == 3)
		{
			System.out.println("Running Blue Retrieval Auto");
		}
		else if(autoSelected == 5)
		{
			System.out.println("Running Red Retrieval Auto");
		}
		else
		{
			System.out.println("Invalid Digital Input");
			System.out.println("Just gonna move forw for a bit");
		}
	}
}
