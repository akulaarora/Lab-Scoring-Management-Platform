
/**
 * The abstract class for database interaction with the lsmp database
 * Guarantees a connection and a return of row information
 *
 * @author Manseej Khatri
 * @version 1.0
 */


package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBInteract 
{
	private Connection SQLcon = null;
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
	
	/**
	 * @return a statement object for sql queries
	 * 
	 */
	public Statement getStatement() {return myStatement;}
	
	/**
	 * gets a connection object to represent sql connection
	 * @return the sql connection object
	 */
	public Connection getConnection() {return SQLcon;}
	
	/**
	 * Gets the name of the table
	 * @return the table name
	 */
	public String getTable() {return table;}
	
	/**
	 * guarantees row information
	 * @param identifier the identifier for each row
	 * @return the DBPullObject
	 * @throws SQLException
	 */
	public abstract DBPullObject pull(int identifer) throws SQLException;
}
