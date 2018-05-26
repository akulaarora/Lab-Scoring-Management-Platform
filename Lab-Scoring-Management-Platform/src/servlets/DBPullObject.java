package servlets;

import java.util.ArrayList;
public class DBPullObject 
{
	private ArrayList<Integer> labScore = new ArrayList<>(); 
	private int ID = -1;
	private String name = "";
	private String timestamp = "";
	private int period = -1;
	private String lab = "";
	private String spec = "";
	
	public DBPullObject(int ID, String name, String timestamp, int period, ArrayList<Integer> labs)
	{
		this.ID = ID;
		this.name = name;
		this.timestamp = timestamp;
		this.period = period;
		labScore = labs;
	}
	
	public DBPullObject(String lab,String spec)
	{
		this.lab = lab;
		this.spec = spec;
	}
	
	public int getId() {return ID;}
	public String getName() {return name;}
	public String getTimeStamp() {return timestamp;}
	public int getPeriod() {return period;}
	public ArrayList<Integer> getLabScores(){return labScore;}
	public String getLab() {return lab;}
	public String getSpec() {return spec;}
	
}
