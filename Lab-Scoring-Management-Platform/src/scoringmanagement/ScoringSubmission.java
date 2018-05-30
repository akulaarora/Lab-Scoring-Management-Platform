package scoringmanagement;

/**
 * @author Akul Arora
 *
 */
public class LabSubmisssion 
{
    private String myName;
    private String myPeriod;
    private String myID;
    private String myLab;
    private String[] myFiles;
    private Score myScore;
    
    /**
     * Default constructor. Creates object of ScoringSubmission to modify class.
     * @param String name, String period, String student id, String lab, String files submitted name
     */
    public LabSubmission(String name, String period, String id, String lab, String[] files)
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
