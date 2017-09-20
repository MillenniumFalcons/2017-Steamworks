# 2017-Steamworks

This is the code for the 2017 Steamworks Robot. 

Steamworks is a steampunk themed competition based on delivering gears to airships and shooting fuel into a boiler to gain steam pressure. During the autonomous period, points are awarded based on a successful gear delivery, crossing the line, and shooting fuel into the boiler. During the teleoperated period, teams continue to deliver gears to the airship to turn rotors and shoot fuel into the low and high goal to build steam pressure. During the last 30 seconds of the match, ropes are deployed in the airship for the robots to climb. This awards a team with the most points.

Our 2017 robot, General Gearvous, focused on cycling gears and climbing. Our unique spatula and clamp contraption allowed us to pick up gears from both the ground and the loading station. Its powerful drivetrain combined with a closed loop PID using encoders allowed us to maneuver across the field incredibly quickly and incredibly gracefully. Our dual 775 pro climber climbed the rope incredibly fast.

## Robot.java ##
* [Robot.java](https://github.com/MillenniumFalcons/2017-Steamworks/tree/master/Robot_Code/src/org/usfirst/frc/team3647/robot) is the main file of the project. IT contains all the code for both the Tele-Operated Period and the autonomous period.

## Team 3647 Pistons ##
* [The pistons package](https://github.com/MillenniumFalcons/2017-Steamworks/tree/master/Robot_Code/src/team3647pistons) contains all the code for controlling the pistons of the robot. In our 2017 Robot, we use pistons to control our gear mechanism and dropdown omni-wheels.

## Team 3647 Subsystems ##
* [The subsystems package](https://github.com/MillenniumFalcons/2017-Steamworks/tree/master/Robot_Code/src/team3647subsystems) contains all the code for the DigitalPins, Encoder, Climber, Joystick Contoller and Motors.