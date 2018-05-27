
/**
 * Student Database interaction or Student DBInteract serves the purpose
 * of acting as a direct intermediary between the Student servelet
 * and the the selected database. Since the lsmp will be stored
 * through a mysql server, the only connection necessary will be 
 * to mysql. DBInteract acts somewhat like a rest service through
 * pushing and pulling data to and from the mysql server however for
 * the sake of user convenience and organizational purposes, the push 
 * statements will be specific
 *
 * @author Manseej Khatri
 * @version 1.0
 */


package servlets;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentDBInteract extends DBInteract
{

	/**
	 * Class constructor for Database interaction
	 * Initializes a connection to the lsmp sql database
	 * @throws SQLException
	 */
	public StudentDBInteract() throws SQLException
	{
		super("labDB"); //creates a connection through the constructor
	}
	
	/**
	 * pushes a value into a database cell
	 * @param pushData the data to be pushed
	 * @param ID which row the location of the pushData will be inputed
	 * @param column which column the location of the pushData will be inputed
	 * @throws SQLException 
	 */
	public void pushScore(int pushData, int ID, String column) throws SQLException
	{
		/*pushes values onto selected table*/
		Date d1 = new Date();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //for the timestamp
		if(!checkIdExists(ID))
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			String sql = "UPDATE "+getTable()+" SET timestamp = ?, "+column+" = ? WHERE ID = ?";
			PreparedStatement pS = getConnection().prepareStatement(sql);
			pS.setString(1, format.format(d1));
			pS.setInt(2, pushData);
			pS.setInt(3, ID);
			pS.executeUpdate();
		}
	}
	
	/**
	 * Creates the id rows of the table
	 * This should be done first before the other queries are made
	 * To prevent an out of bounds error
	 * @param ID the identification of the user
	 * @throws SQLException
	 */
	public void pushID(int ID) throws SQLException
	{

		getStatement().executeUpdate("INSERT INTO "+getTable()+"(ID) VALUES("+ID+")");
	}
	
	/**
	 * Pushes a period onto the table of the user whose id is selected
	 * @param ID the identification of the user
	 * @param period the period in which the user is active in class
	 * @throws SQLException
	 */
	public void pushPeriod(int ID,int period) throws SQLException
	{
		if(!checkIdExists(ID))
			throw new IndexOutOfBoundsException();
		else
			getStatement().executeUpdate("UPDATE "+getTable()+" SET Period = "+period+" WHERE ID = "+ID);
	}
	
	/**
	 * Pushes a name of the user onto the table of the user whose id is selected
	 * @param ID the identification of the user 
	 * @param name the name of the user
	 * @throws SQLException 
	 */
	public void pushName(int ID, String name) throws SQLException
	{
		if(!checkIdExists(ID))
			throw new IndexOutOfBoundsException();
		else
		{
			PreparedStatement pS = getConnection().prepareStatement("UPDATE "+getTable()+" SET name = ? WHERE ID = ? ");
			pS.setString(1, name);
			pS.setInt(2, ID);
			pS.executeUpdate();
		}
	}
	/**
	 * Creates a pull object which represents all the data in a row
	 * @param id the identification of the user 
	 * @return row the DBPullObject in which the row is applicable
	 */
	public DBPullObject pull(int ID) throws SQLException
	{
		/*pulls values off the selected table*/
		DBPullObject row = null;
		
		if(!checkIdExists(ID))
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			ResultSet set = getStatement().executeQuery("Select * from "+getTable()+" WHERE ID = "+ID);
			set.next();
			ArrayList<Integer> list = new ArrayList<>(); //gives all lab scores
			String name = set.getString("name");
			int period = set.getInt("Period");
			String timestamp = set.getString("timestamp");
			ResultSetMetaData rsmd = set.getMetaData();
			
			/* all above creates an object of a row */
			
			for(int i = 5; i <= rsmd.getColumnCount();i++) //sets lab values
			{
				list.add(set.getInt(i));
			}
			row = new DBPullObject(ID,name,timestamp,period,list);
			
		}
		return row;
	}
	
	/**
	 * Creates a lab column
	 * @param columnName the name of the lab
	 * @throws SQLException
	 */
	public void createLab(String columnName) throws SQLException
	{
		String sql = "ALTER TABLE "+getTable()+" ADD "+columnName+" TEXT";
		PreparedStatement pS = getConnection().prepareStatement(sql);
		pS.executeUpdate();
	}
	
	/* checks if the ID is valid then returns true or false */
	private boolean checkIdExists(int ID) throws SQLException
	{
		ResultSet set = getStatement().executeQuery("Select * from "+getTable()+" where ID = "+ID);
		if(!set.next())
		{
			set.previous();
			return false;
		}
		set.previous();
		return true;
	}
	
}
