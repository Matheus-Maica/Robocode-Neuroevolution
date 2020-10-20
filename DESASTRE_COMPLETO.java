package DESASTRES;
import robocode.*;
import java.awt.Color;
import java.sql.Timestamp;

public class DESASTRE_COMPLETO extends AdvancedRobot {
	private RobotStatus robotStatus;
	private RobotInfo currentThreat = null;
	
	static int[] shape = {6, 7, 8}; // {INPUT_NODES, OUTPUT_NODES, HIDDEN_NODES}
	static double 
		scanned_robot = 0,
		hit_by_bullet = 0,
		bearing = 0,
		mutation_rate = 0.01;
	
	static int populationSize = 150;
	BattleFieldBots bots;
	
	static String[] actions = {"TurnBodyRight", "TurnBodyLeft", "TurnRadarRight", "TurnRadarLeft", "Ahead", "Back", "Shoot"};
	
	public void run() {	
		setColors(Color.white,Color.white,Color.white);
		setScanColor(Color.white);	
		setBulletColor(Color.white);
		setTurnRadarRight(Double.POSITIVE_INFINITY);
		
		GA_Source ga = new GA_Source();
		bots = new BattleFieldBots();
		
		NeuralNet[] individuals = ga.createPopulation(populationSize, shape);
		
		double[][] defaults = {
			{0, 1},
			{0, Math.sqrt(Math.pow(getBattleFieldWidth(), 2) + Math.pow(getBattleFieldHeight(), 2))},
			{0, 1},
			{-8, 8},
			{0, 360},
			{-180, 180}
		};
		
		while(true) {
			if(currentThreat != null) {
				double[] input = {scanned_robot, currentThreat.distance, hit_by_bullet, currentThreat.velocity, currentThreat.heading, bearing};
				double[] result = individuals[0].predict(input, defaults);
				String action = actions[maxValue(result)];
				
				switch(action) {
					case "TurnBodyRight":
						setTurnRight(60);
						break;
					case "TurnBodyLeft":
						setTurnLeft(60);
						break;
					case "TurnRadarRight":
						setTurnRadarRight(1);
						break;
					case "TurnRadarLeft":
						setTurnRadarLeft(1);
						break;
					case "Ahead":
						setAhead(10);
						break;
					case "Back":
						setBack(50);
						break;
					case "Shoot":
						setFire(2);
						break;
				}
			} else {
				setTurnRight(10);
			}
			
			execute();
			scanned_robot = 0;
			hit_by_bullet = 0;
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		RobotInfo[] bots_list = bots.getList();
		
		boolean found = false;
		if(bots_list != null) {
			for(int i = 0; i < bots_list.length; i++) {
				if(bots_list[i].name == e.getName()) {
					found=true;
					currentThreat = bots_list[i];
				}
			}
		}

		double Velocity = e.getVelocity();
		double Heading = e.getHeading();
		double Distance = e.getDistance();
		String Name = e.getName();
		double Energy = e.getEnergy();
        double angleToEnemy = e.getBearing();
        
        double angle = Math.toRadians((robotStatus.getHeading() + angleToEnemy % 360));

        double XPos = (robotStatus.getX() + Math.sin(angle) * e.getDistance());
        double YPos = (robotStatus.getY() + Math.cos(angle) * e.getDistance());
        Timestamp TimeStamp = new Timestamp(System.currentTimeMillis());
        
        bearing = e.getBearing();
        
		if(found) {
			bots.updateInfo(XPos, YPos, Energy, Heading, Velocity, TimeStamp, Distance, Name);			
		} else {
			RobotInfo robot = new RobotInfo(XPos, YPos, Energy, Heading, Velocity, TimeStamp, Distance, Name);
			bots.addRobot(robot);
			currentThreat = robot;
		}
	}
	
	public void onHitByBullet(HitByBulletEvent e) 
	{
		
		hit_by_bullet = 1;
	}
	
	public void onBulletHit(BulletHitEvent e)
	{
		
	}
	
	public void onBulletMissed(BulletMissedEvent e)
	{
		
	}
	
	public void onHitRobot(HitRobotEvent e)
	{
		
	}
	
	public void onRobotDeath(RobotDeathEvent e)
	{
		// RIP
		System.out.println("R.I.P");
	}
	
	public void onDeath(DeathEvent e)
	{
		end(false);
	}
	
	public void onWin(WinEvent e)
	{
		end(true);
	}
	
	public void end(boolean win)
	{
		
	}
	
	public int maxValue(double[] array)
	{
		int max = 0;

		for (int i = 1; i < array.length; i++) {
		    if (array[i] > max) {
		      max = i;
		    }
		}
		
		return max;
	}
	
	public void onStatus(StatusEvent e) 
	{
        this.robotStatus = e.getStatus();
    }
}
