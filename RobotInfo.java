package DESASTRES;

import java.sql.Timestamp;

public class RobotInfo {
	double xPos, yPos, energy, heading, velocity, distance;
	String name;
	Timestamp timeStamp;
	
	public RobotInfo(double XPos, double YPos, double Energy, double Heading, double Velocity, Timestamp TimeStamp, double Distance, String Name)
	{
		xPos=XPos;
		yPos=YPos;
		energy=Energy;
		heading=Heading;
		velocity=Velocity;
		timeStamp=TimeStamp;
		name=Name;
		distance=Distance;
	}
}
