
/**
 * Creates an object to represent a row of information on a database
 * Should be referenced through the pull method in DBInteract type classes
 *
 * @author Manseej Khatri
 * @version 1.0
 */


package scoringmanagement;

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
	private ArrayList<String> filters = new ArrayList<>();
	/**
	 * Creates a database pull object for the lab db tables
	 * @param ID the identification of the user
	 * @param name of said user
	 * @param timestamp the time of submission
	 * @param period the period of the user
	 * @param labs what lab they are working on
	 */
	public DBPullObject(int ID, String name, String timestamp, int period, ArrayList<Integer> labs)
	{
		this.ID = ID;
		this.name = name;
		this.timestamp = timestamp;
		this.period = period;
		labScore = labs;
	}
	
	public DBPullObject(ArrayList<String> filters)
	{
		this.filters = filters;
	}
	
	/**
	 * Creates a pull object for the SpecDB
	 * @param lab the lab for which the spec is relevant
	 * @param spec the scoring guides
	 */
	public DBPullObject(String lab,String spec)
	{
		this.lab = lab;
		this.spec = spec;
	}
	
	/**
	 * gets the id
	 * @return the id
	 */
	public int getId() {return ID;}
	
	/**
	 * gets the name
	 * @return the name
	 */
	public String getName() {return name;}
	
	/**
	 * gets the timestamp
	 * @return the timestamp
	 */
	public String getTimeStamp() {return timestamp;}
	
	/**
	 * gets the period
	 * @return the period
	 */
	public int getPeriod() {return period;}
	
	/**
	 * returns the lab scores
	 * @return an array of labscores
	 */
	public ArrayList<Integer> getLabScores(){return labScore;}
	
	/**
	 * returns the lab
	 * @return the lab
	 */
	public String getLab() {return lab;}
	
	/**
	 * returns the scoring guidelines
	 * @return the scoring guidelines
	 */
	public String getSpec() {return spec;}
	
	public ArrayList<String> getFilter(){return filters;}
	
}
