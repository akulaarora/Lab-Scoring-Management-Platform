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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
