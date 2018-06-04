package scoringmanagement;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Akul Arora
 * @version
 */
public class LabSubmission 
{
    private String myName;
    private int myPeriod;
    private int myID;
    private String myLab;
    private List<File> myFiles;
    private Score myScore;
    
    /**
     * Default constructor. Creates object of ScoringSubmission to modify class.
     * @param String name, String period, String student id, String lab, String files submitted name
     */
    public LabSubmission(String name, int period, int id, String lab, List<File> files)
    {
    	myName = name;
    	myPeriod = period;
    	myID = id;
    	myLab = lab;
    	myFiles = files;
    	myScore = Scorer.score(myLab, myFiles);
    }
    
    public String toString()
    {
    	
    }
    
    
}
