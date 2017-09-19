# Robot.java #

```
import edu.wpi.first.wpilibj.IterativeRobot;
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
```
These are the import statements in Robot.java. Import statements allow us to refer to classes located in other packages without referring to the full package name at all times you need to use something from the package.


```
double autoSelected;
double leftEncoderValues;
double rightEncoderValues;
boolean leftSide, rightSide;
int currentState;
	
double straightDistBR = 1940;
double straightDistRR = 1840;
double bigTurn = 1600;
double smallTurn = 930;
double distanceforGearFromMiddle = 1600;
double backUpDisatnceFromMiddle = 2300;
double goForwToCross = 5000;
```
These are variables we use for the autonomous period. ```autoSelected``` is a variable that is used in the ```autonomousPeriodic()``` to determine what autonomous function we will be running. The ```leftEncoderValues``` and ```rightEncoderValues``` are variables used to store the encoder values of each side of the robot. Booleans ```leftSide``` and ```rightSide``` are used for satisfying conditions for encoder values of each motor. The variable ```currentState``` is used to determine which step of the autonomous function we are running in the switch case. Variables such as ```straightDistBR```, ```straightDistRR```, ```bigTurn```, ```smallTurn```, ```distanceforGearFromMiddle```, ```backUpDisatnceFromMiddle```, ```goForwToCross``` are used in our autonmous functions.
```
boolean steeringWheelDrive = false;
int prevState = 0;
String switchWiggle = "left";
```
These are variables we use for the tele-operated period. The use of the ```steeringWheelDrive``` variable can be found in the ```runTeleOp()``` function, the use of the ```prevState``` variable can be found in both the ```updatedArcadeDrive()``` and ```updatedArcadeDriveForSteeringWheelDrive()``` function, and the use of the ```switchWiggle``` variable can be found in the ```teleOpWiggle()``` function.
