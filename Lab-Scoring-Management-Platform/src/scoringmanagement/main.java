package scoringmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
 
class main 
{
	public static void main(String[] args)
	{
		try {
			
			ScoringDBInteract interaction = new ScoringDBInteract();
			/*
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
			
			*/
			/*
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
			
			LabSpecDBInteract interaction2 = new LabSpecDBInteract();
			
			interaction2.pushLab("lab1_0");
			interaction2.pushSpec("hello world","lab1_0");
			DBPullObject obj2 = interaction2.pull(interaction2.getAssociatedIdentifier("lab1_0"));
			System.out.println(obj2.getLab());
			System.out.println(obj2.getSpec());
			*/
			/*
			DBPullObject obj = interaction.getLabNames();
			for(int i = 0; i < obj.getFilter().size();i++)
			{
				System.out.print(obj.getFilter().get(i));
			}
			*/
			Map <String,String> hm = new HashMap<>();
			hm.put("id","1888888");
			hm.put("period", "2");
			interaction.generateCSV(hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
