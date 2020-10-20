package DESASTRES;

import java.util.Arrays;
import java.sql.Timestamp;

public class BattleFieldBots {
	static RobotInfo[] robots = new RobotInfo[0];
	
	public void addRobot(RobotInfo robot)
	{	
		robots = Arrays.copyOf(robots, robots.length+1);
		robots[robots.length-1] = robot;
	}
	
	public void updateInfo(double XPos, double YPos, double Energy, double Heading, double Velocity, Timestamp TimeStamp, double Distance, String Name)
	{
		for(int i = 0; i < robots.length; i++) {
			if(robots[i].name == Name) {
				robots[i].xPos=XPos;
				robots[i].yPos=YPos;
				robots[i].energy=Energy;
				robots[i].heading=Heading;
				robots[i].velocity=Velocity;
				robots[i].timeStamp=TimeStamp;
				robots[i].distance=Distance;
			}
		}
	}
	
	public RobotInfo[] getList()
	{
		return robots;
	}
}
