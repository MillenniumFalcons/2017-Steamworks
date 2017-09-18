package org.usfirst.frc.team3647.robot;

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
	
	double autoSelected;
	double leftEncoderValues;
	double rightEncoderValues;
	boolean leftSide, rightSide;
	int currentState;
	int prevState = 0;
	boolean steeringWheelDrive = false;
	
	
	double straightdist = 1900;
	double straightDistBR = 2020;
	double straightDistRR = 1840;
	double bigTurn = 1600;
	double smallTurn = 930;
	double distanceforGearFromMiddle = 1600;
	double backUpDisatnceFromMiddle = 2300;
	double goForwToCross = 5000;
	
	Encoders enc;
	Clamp clampObj;
	DropDown dropdown;
	MainPiston mainpiston;
	Climber climberObj;
	Joystick007 joyStickValues;
	CameraServer server;
	
	String switchWiggle = "left";
	
	
	public void robotInit() 
	{
		enc = new Encoders();
		joyStickValues = new Joystick007();
		clampObj = new Clamp();
		dropdown = new DropDown();
		mainpiston = new MainPiston();
		climberObj = new Climber();

		server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0", 0);
	}

	
	public void autonomousInit() 
	{
		currentState = 1;
		enc.resetEncoders();	
		Motors007.leftTalon.set(0);
		Motors007.rightTalon.set(0);
		leftSide = false;
		rightSide = false; 
	}

	
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

	
	public void moveForw()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(.5);
				mainpiston.grabMainPiston();
				currentState = 2;
				break;
			case 2:
				if(enc.returnleftEncValue() < 1900)
				{
					Motors007.leftTalon.set(.3);
				}
				else
				{
					leftSide = true;
					Motors007.leftTalon.set(0);
				}
				if(enc.returnrightEncValue() < 1900)
				{
					Motors007.rightTalon.set(-.3);
				}
				else
				{
					Motors007.rightTalon.set(0);
					rightSide = true;
				}
				if(leftSide && rightSide)
				{
					currentState = 3;
				}
				break;
			case 3:
				clampObj.unClampThePiston();
				break;
		}
	}
		
	
	public void updateAllPistons()
	{
		clampObj.updatePistons();
		mainpiston.updatePistons();
		dropdown.updatePistons();
	}
	
	
	public void updateAllPistonsForSteeringWheel()
	{
		clampObj.updatePistons();
		mainpiston.updatePistons();
		dropdown.steeringWheelDropDown();
	}
	

	public void teleopPeriodic() 
	{
		runTeleOp();
	}
	
	
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
	
	
	public void driveTrainForSteeringWheel()
	{
		updatedArcadeDriveForSteeringWheelDrive();
	}
	
	
	public void testPeriodic() 
	{
		// Does N O T H I N G
	}
	
	
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
	
	
	public void newBlueRetrieval()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1.5);
				mainpiston.grabMainPiston();
				Timer.delay(1.5);
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
	
	
	public void newRedRetrieval()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1.5);
				mainpiston.grabMainPiston();
				Timer.delay(1.5);
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
	
	
	public void centerAuto()
	{
		switch(currentState)
		{
			case 1:
				mainpiston.dropMainPiston();
				Timer.delay(1.5);
				mainpiston.grabMainPiston();
				Timer.delay(1.5);
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
				Motors007.leftTalon.set(0);
				Motors007.rightTalon.set(0);
				Timer.delay(.2);
				mainpiston.grabMainPiston();
				Timer.delay(.3);
				enc.resetEncoders();
				currentState = 5;
				break;
			case 5:
				Motors007.leftTalon.set(0);
				Motors007.rightTalon.set(0);
				break;
		}
	}
	
	
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
	
	
	public void testEncoders()
	{
		System.out.println("Right encoder value: " + enc.returnrightEncValue());
		System.out.println("Left encoder value: " + enc.returnleftEncValue());
	}
	
	
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
