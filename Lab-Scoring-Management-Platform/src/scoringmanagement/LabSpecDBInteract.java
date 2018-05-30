package scoringmanagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LabSpecDBInteract extends DBInteract
{

	public LabSpecDBInteract() throws SQLException 
	{
		super("SpecDB");
	}
	
	public void pushLab(String lab) throws SQLException
	{
		PreparedStatement pS = getConnection().prepareStatement("INSERT INTO "+getTable()+"(lab) VALUES(?)");
		pS.setString(1, lab);
		pS.executeUpdate();
	}
	
	public void pushSpec(String spec, String lab) throws SQLException
	{
		String sql = "UPDATE "+getTable()+" SET spec = ? WHERE lab = ?";
		PreparedStatement pS = getConnection().prepareStatement(sql);
		pS.setString(1,spec);
		pS.setString(2,lab);
		pS.executeUpdate();
	}

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
