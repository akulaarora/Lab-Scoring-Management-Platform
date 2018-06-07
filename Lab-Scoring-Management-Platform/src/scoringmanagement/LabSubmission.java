package scoringmanagement;

import java.io.File;
import java.util.List;

/**
 * LabSubmission class is used to handle the lab submission.
 * Will take necessary input and generate the score for the lab as well.
 * @author Akul Arora
 * @version 05/30/2018
 */
public class LabSubmission 
{
    private String myName;
    private int myPeriod;
    private int myID;
    private String myLabName;
    private List<File> myFiles;
    private Score myScore;
    
    /**
     * Default constructor. Takes no input. Will set all values to empty or zero.
     * @param none
     */
    public LabSubmission()
    {
    	myName = "";
    	myPeriod = 0;
    	myID = 0;
    	myLabName = "";
    	myFiles = null;
    	myScore = null;
    }
    
    /**
     * Constructor that takes all input to handle lab submission.
     * @param String name, String period, String student id, String lab, String files submitted name
     */
    public LabSubmission(String name, int period, int id, String lab, List<File> files)
    {
    	myName = name;
    	myPeriod = period;
    	myID = id;
    	myLabName = lab;
    	myFiles = files;
    	myScore = Scorer.studentScore(myLabName, myFiles);
    }
    
    /**
     * Set name of student.
     * @param String name
     */
    public void setName(String name)
    {
    	myName = name;
    }
    
    /**
     * Set id of student.
     * @param int id
     */
    public void setId(int id)
    {
    	myID = id;
    }
    
    /**
     * Set class period student is in.
     * @param int period (1-5)
     */
    public void setPeriod(int period)
    {
    	myPeriod = period;
    }
    
    /**
     * Set lab name for submission.
     * @param String lab
     */
    public void setLabName(String labName)
    {
    	myLabName = labName;
    }
    
    /**
     * Set files submitted for lab submission.
     * Will also calculate score if a lab has been submitted as well.
     * @param List<File> Files submitted
     */
    public void setFiles(List<File> files)
    {
    	myFiles = files;
    	if (getLabName() != "")
    		myScore = Scorer.studentScore(getLabName(), getFiles());
    }
    
    /**
     * Get name of student.
     * @return String name
     */
    public String getName()
    {
    	return myName;
    }
    
    /**
     * Get id of student.
     * @return int id
     */
    public int getID()
    {
    	return myID;
    }
    
    /**
     * Get class period student is in.
     * @return int period (1-5)
     */
    public int getPeriod()
    {
    	return myPeriod;
    }
    
    /**
     * Get lab name for submission.
     * @return String lab
     */
    public String getLabName()
    {
    	return myLabName;
    }
    /**
     * Get files submitted for lab submission.
     * @return List<File> Files submitted
     */ 
    public List<File> getFiles()
    {
    	return myFiles;
    }
    
    /**
     * Converts lab submission into String.
     * @return String lab submission information
     */
    public String toString()
    {
    	String output = "";
    	
    	return output;
    }
    
    
}