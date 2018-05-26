package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBInteract 
{
	protected Connection SQLcon = null;
	private Statement myStatement;
	private String table = "";
	/**
	 * Class constructor for Database interaction
	 * Initializes a connection to the lsmp sql database
	 * @throws SQLException
	 */
	public DBInteract(String table) throws SQLException
	{
		this.table = table;
		try
		{
        	Class.forName("com.mysql.jdbc.Driver");
            SQLcon = DriverManager.getConnection("jdbc:mysql://localhost/lsmp", "javacon", "password");
			myStatement = SQLcon.createStatement();
		}
		catch(SQLException e)
		{
			System.out.print(e.getStackTrace());
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Statement getStatement() {return myStatement;}
	public Connection getConnection() {return SQLcon;}
	public String getTable() {return table;}
	
	public abstract DBPullObject pull(int ID) throws SQLException;
}
