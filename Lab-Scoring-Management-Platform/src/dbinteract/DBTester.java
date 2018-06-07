package dbinteract;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dbinteract.DBPullObject;
import dbinteract.LabSpecDBInteract;
 
public class DBTester 
{
	public static void main(String[] args)
	{
		try {
			/*
			StudentDBInteract interaction = new StudentDBInteract();
			System.out.println("Success");
			interaction.pushID(1999999);
			System.out.println("success");
			interaction.pushName(1999999, "darshan");
			System.out.println("Success");
			interaction.pushPeriod(1999999, 2);
			System.out.println("Success");
			interaction.pushScore(25, 1999999, "lab1_0");
			
			interaction.createLab("lab3_0");
			interaction.pushScore(45, 1888888, "lab2_0");
			interaction.pushScore(45, 1999999, "lab2_0");
			
			
			DBPullObject obj = interaction.pull(1888888);
			System.out.println(obj.getId());
			System.out.println(obj.getLab());
			System.out.println(obj.getPeriod());
			System.out.println(obj.getName());
			System.out.println(obj.getTimeStamp());
			interaction.pushScore(45, 1888888, "lab2_0");
			obj = interaction.pull(1888888);
			for(int i = 0; i < obj.getLabScores().size();i++)
			{
				System.out.println(obj.getLabScores().get(i));
			}
			
			*/
			LabSpecDBInteract interaction2 = new LabSpecDBInteract();
			
			interaction2.pushLab("lab1_0");
			interaction2.pushSpec("hello world","lab1_0");
			DBPullObject obj = interaction2.pull(interaction2.getAssociatedIdentifier("lab1_0"));
			System.out.println(obj.getLab());
			System.out.println(obj.getSpec());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
