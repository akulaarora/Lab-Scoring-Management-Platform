package servlets;

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
		PreparedStatement pS = getConnection().prepareStatement("INSERT INTO "+getTable()+"(LAB) VALUES(?)");
		pS.setString(1, lab);
		pS.executeUpdate();
	}
	

	public DBPullObject pull(int ID) throws SQLException 
	{
		return null; //---->temporary
	}
	
}
