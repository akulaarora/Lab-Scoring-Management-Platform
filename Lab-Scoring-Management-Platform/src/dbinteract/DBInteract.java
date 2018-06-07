
/**
 * The abstract class for database interaction with the lsmp database
 * Guarantees a connection and a return of row information
 *
 * @author Manseej Khatri
 * @version 1.0
 */


package dbinteract;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.opencsv.CSVWriter;

public abstract class DBInteract 
{
	private Connection SQLcon = null;
	private Statement myStatement;
	private String table = "";
	private File path;
	private static final String SQL_URL = "jdbc:mysql://localhost/lsmp";
	private static final String SQL_USER = "javacon";
	private static final String SQL_PASS = "password";

	/**
	 * Initialises a connection with the lsmp database and works with the table
	 * that is specified by the user. Direct subclasses have the table being used
	 * setup for them
	 * @param table the name of the table
	 * @throws SQLException 
	 */
	public DBInteract(String table) throws SQLException
	{
		this.table = table;
		try
		{
        	Class.forName("com.mysql.jdbc.Driver");
            SQLcon = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
			myStatement = SQLcon.createStatement();
		}
		catch(SQLException e)
		{
			System.out.print(e.getStackTrace());
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		path = new File("/csv/"+getTable()+".csv");
	}
	
	/**
	 * Generates a csv file representing the table and outputs it to a file called labDB.xml
	 * Credit goes to:
	 * @author Glen Smith
	 * @author Scott Conway
	 * @author Sean Sullivan
	 * @author Kyle Miller
	 * @author Tom Squires
	 * @author Andrew Rucker Jones
	 * @author Maciek Opala
	 * @author J.C. Romanda
	 * 
	 * more information about the class itself go to http://opencsv.sourceforge.net/index.html
	 * And please consider supporting them because they made my job so so so much easier
	 * 
	 * This method will generate a csv file based on a filter on data. For example
	 * If you wish to filter by period and specifically period 2 then you would use this filter
	 * @param filterType is the type of the filter
	 * @param filter is the specific filter
	 * @return a file object
	 * @throws SQLException
	 * @deprecated use {@link #generateCSV(Map<String,String> filters)} instead
	 */
	@Deprecated
	public File generateCSV(String filterType, String filter) throws SQLException
	{
		String sql = "Select * from "+getTable()+" where "+filterType+ " = "+filter;
		PreparedStatement pS = getConnection().prepareStatement(sql);
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(path.getPath()));
			writer.writeAll(pS.executeQuery(),true);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return FILE_PATH;
		return path;
	}
	/**
	 * See above for credit
	 * Generates a csv based solely on type. For example you can specify ID Labname and period
	 * and it will generate a table based on that
	 * @param filters on the type ie id, name etc
	 * @return a File object
	 * @throws SQLException
	 */
	public File generateCSV(Map<String,String> filters) throws SQLException
	{
		String sql = "Select * from " + getTable() + " where ( ";
		int counter = 0;
		for(String key : filters.keySet())
		{
			if(counter == filters.size()-1)
			{
				sql+=key+" = "+filters.get(key);
			}
			else
			{
				sql+=key + " = " + filters.get(key)+" AND ";
				counter++;
			}
		}
		sql+=")";
		PreparedStatement pS = getConnection().prepareStatement(sql);
		try
		{
			CSVWriter writer = new CSVWriter(new FileWriter(path.getPath()));
			writer.writeAll(pS.executeQuery(),true);
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * See above for credit
	 * This method will generate the table in its full glory with no filters
	 * @return a file object
	 * @throws SQLException
	 */
	public File generateCSV() throws SQLException
	{
		String sql = "Select * from "+getTable();
		PreparedStatement pS = getConnection().prepareStatement(sql);
		try
		{
			CSVWriter writer = new CSVWriter(new FileWriter(path.getPath()));
			writer.writeAll(pS.executeQuery(),true);
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return path;
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
