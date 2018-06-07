
/**
 * Handles interaction with lab specification database.
 * Obsolete as lab spec interactions are now completely handled in files.
 * Please Do not use this as no table is currently being setup use others instead
 * @Deprecated
 * @author Manseej Khatri
 * @version 06/01/2018
 *
 */



package dbinteract;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LabSpecDBInteract extends DBInteract
{
	/**
	 * Initiates an interaction with the lsmp database under the table:SpecDB
	 * @throws SQLException
	 */
	public LabSpecDBInteract() throws SQLException 
	{
		super("SpecDB");
	}
	
	/**
	 * Takes a lab value ie lab 1.0 and pushes it onto the table
	 * @param lab the name of the lab
	 * @throws SQLException
	 */
	public void pushLab(String lab) throws SQLException
	{
		PreparedStatement pS = getConnection().prepareStatement("INSERT INTO "+getTable()+"(lab) VALUES(?)");
		pS.setString(1, lab);
		pS.executeUpdate();
	}
	
	/**
	 * pushes a spec string onto the database.
	 * @param spec the requirements of each lab
	 * @param lab the lab name
	 * @throws SQLException
	 */
	public void pushSpec(String spec, String lab) throws SQLException
	{
		String sql = "UPDATE "+getTable()+" SET spec = ? WHERE lab = ?";
		PreparedStatement pS = getConnection().prepareStatement(sql);
		pS.setString(1,spec);
		pS.setString(2,lab);
		pS.executeUpdate();
	}

	/**
	 * Pulls a lab taken from a guaranteed identifier per corresponding lab
	 * @param identifier the identifier
	 * @return a DBPullObject for pulling information
	 * @throws SQLException 
	 */
	public DBPullObject pull(int identifier) throws SQLException 
	{
		DBPullObject returnObj = null;
		if(!checkIdentifierExists(identifier))
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			String sql = "SELECT * FROM "+getTable()+" WHERE identifier = ?";
			PreparedStatement pS = getConnection().prepareStatement(sql);
			pS.setInt(1,identifier );
			pS.executeQuery();
			ResultSet set = pS.getResultSet();
			set.next();
			returnObj = new DBPullObject(set.getString("lab"),set.getString("spec"));
		}
		return returnObj;
	}
	
	
	/**
	 * Takes the name of the lab to find the associated identifier
	 * @param labName the name of the lab
	 * @return the associated identifier
	 * @throws SQLException
	 */
	public int getAssociatedIdentifier(String labName) throws SQLException
	{
		String sql = "SELECT identifier FROM SpecDB where lab = ?";
		PreparedStatement pS = getConnection().prepareStatement(sql);
		pS.setString(1,labName);
		ResultSet set = pS.executeQuery();
		set.next();
		return set.getRow();
	}
	
	/* checks if the ID is valid then returns true or false */
	private boolean checkIdentifierExists(int ID) throws SQLException
	{
		ResultSet set = getStatement().executeQuery("Select * from "+getTable()+" where identifier = "+ID);
		if(!set.next())
		{
			set.previous();
			return false;
		}
		set.previous();
		return true;
	}
}
