package team3647subsystems;



import edu.wpi.first.wpilibj.Encoder;



public class Encoders 

{

	double rightEncValue;

	double leftEncValue;

	double averageEncoderValue;

	boolean encodersWithinRange;

	

	Encoder leftEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder rightEnc = new Encoder(4, 5, false, Encoder.EncodingType.k4X);

	

	

	public double returnleftEncValue()

	{

		leftEncValue = leftEnc.get();

		return -leftEncValue;

	}

	

	

	public double returnrightEncValue()

	{

		rightEncValue = rightEnc.get();

		return rightEncValue;

	}

	

	

	public double difference()

	{

		double diff;

		rightEncValue = returnrightEncValue();

		leftEncValue = returnleftEncValue();

		diff = (leftEncValue - rightEncValue);

		return diff;

	}

	

	

	public void resetEncoders()

	{

			rightEnc.reset();

			leftEnc.reset();

	}

	

	

	public void resetRight()

	{

		rightEnc.reset();

	}

	

	

	public void resetLeft()

	{

		leftEnc.reset();

	}

	

	

	public boolean withinRange(double rightEncValues, double leftEncValues)

	{

		if((Math.abs(rightEncValues - leftEncValues))< 6)

		{

			return true;

		}

		else

		{

			return false;

		}

	}

	

	

	public double getAverage(double rightEncValues, double leftEncValues)

	{

		double average;

		average = (leftEncValues + rightEncValues)/2;

		return average;

	}

	

	

	public boolean runOrNotForStraight(double distance)

	{

		rightEncValue = returnrightEncValue();

		leftEncValue = returnleftEncValue();

		averageEncoderValue = getAverage(rightEncValue, leftEncValue);

		encodersWithinRange = withinRange(rightEncValue, leftEncValue);

		

		if(averageEncoderValue < distance)

		{

			return true;

		}

		else

		{

			return false;

		}

	}

	

	public void slowDrive(double distance)

	{

		rightEncValue = returnrightEncValue();

		leftEncValue = returnleftEncValue();

		averageEncoderValue = getAverage(rightEncValue, leftEncValue);

		encodersWithinRange = withinRange(rightEncValue, leftEncValue);

		

		if(averageEncoderValue < distance)

		{

			if(encodersWithinRange == false)

			{

				if(leftEncValue > rightEncValue)

				{

					Motors007.leftTalon.set(.3);

					Motors007.rightTalon.set(-5);

					

				}

				else if(leftEncValue < rightEncValue)

				{

					Motors007.leftTalon.set(.5);

					Motors007.rightTalon.set(-.3);

				}

			}

			else

			{

				Motors007.leftTalon.set(4);

				Motors007.rightTalon.set(-.4);

			}

		}



		else

		{

			Motors007.leftTalon.set(0);

			Motors007.rightTalon.set(0);

		}

		

	}



	public void encoderDrive(double distance, double state)

	{

		if(state  == 1)

		{

			rightEncValue = returnrightEncValue();

			leftEncValue = returnleftEncValue();

			averageEncoderValue = getAverage(rightEncValue, leftEncValue);

			encodersWithinRange = withinRange(rightEncValue, leftEncValue);

			

			if(averageEncoderValue < distance)

			{

				if(encodersWithinRange == false)

				{

					if(leftEncValue > rightEncValue)

					{

						Motors007.leftTalon.set(.5);

						Motors007.rightTalon.set(-.8);

						

					}

					else if(leftEncValue < rightEncValue)

					{

						Motors007.leftTalon.set(.8);

						Motors007.rightTalon.set(-.5);

					}

				}

				else

				{

					Motors007.leftTalon.set(.7);

					Motors007.rightTalon.set(-.7);

				}

			}



			else

			{

				Motors007.leftTalon.set(0);

				Motors007.rightTalon.set(0);

			}

		}

		else

		{

			if(leftEncValue < distance || rightEncValue < distance)

			{

				if(leftEncValue < distance)

				{

					Motors007.leftTalon.set(0.2);

				}

				else

				{

					Motors007.leftTalon.set(0);

				}

				

				if(rightEncValue < distance)

				{

					Motors007.rightTalon.set(-0.2);

				}

				else

				{

					Motors007.rightTalon.set(0);

				}

			}

			else

			{

				Motors007.leftTalon.set(0);

				Motors007.rightTalon.set(0);

			}

		}

	}

	

	

}