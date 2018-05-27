package servlets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
class main 
{
	public static void main(String[] args)
	{
		try {
			StudentDBInteract interaction = new StudentDBInteract();
			System.out.println("Success");
			interaction.pushID(1888888);
			System.out.println("success");
			interaction.pushName(1888888, "max");
			System.out.println("Success");
			interaction.pushPeriod(1888888, 2);
			System.out.println("Success");
			interaction.pushScore(50, 1888888, "lab1_0");
			
			interaction.createLab("lab2_0");
			
			
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
