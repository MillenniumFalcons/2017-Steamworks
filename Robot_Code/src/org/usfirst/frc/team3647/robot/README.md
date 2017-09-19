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
int prevState = 0;
	
double straightdist = 1900;
double straightDistBR = 1940;
double straightDistRR = 1840;
double bigTurn = 1600;
double smallTurn = 930;
double distanceforGearFromMiddle = 1600;
double backUpDisatnceFromMiddle = 2300;
double goForwToCross = 5000;
```
