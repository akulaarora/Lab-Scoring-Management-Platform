package scoringmanagement;

import java.util.*;

/**
* Companion class to Scorer. Places the values Scorer writes into an object and stores it for further manipulation if required. 
* studentScores array holds the score defined as (output correct/ output incorrect)*30+10(compiles)+10(executes)=possible total of 50 pnts.
* A seperate array stores unscored aspects of the program with data only as feedback for the client.
* 
* Example format:
* studentScores array                 labData array
* ------------------------            ----------------------------------
* 20  | False   | True |            | 56    | 5       | 2   | 5      |
* ------------------------            ----------------------------------
* Score--Compiled?-Runs?             #comments-#JavaDocs-#Break-#return       
* 
*
* @author Darshan & Max
* @version 5/31
*/
public class Score
{
   private Object[] studentScores;//Holds scored stats
   private Object[] labData;//Holds unscored stats
   
   /**
    * Default constructor. Defines a small of unset data. Use "setCell" method to configure to appropriate values.
    */
   public Score()
   {        
      Object[] studentScores = new Object[] {new Integer(0), new Boolean(false), new Boolean(false)};//defines scored data
      Object[] labData = new Object[]{new Integer(0), new Integer(0), new Integer(0), new Integer(0)};//defines general data
   }
   
   /**
    * Retrieves specific scoring data from the Scorer class and alongside some general data just for the client who submitted the lab.
    * copies the data into private variables of the current class.
    * 
    * @param Object[] scores
    * @param Object[] labData
    */
   public Score(Object[] scores, Object[] generalData)
   {
       studentScores = new Object[scores.length];//Define size of studentScores
       labData = new Object[generalData.length];//Define size of labData
       
       for(int i=0; i < scores.length; i++)
       {
           studentScores[i] = scores[i];//Copy data(scores) into studentScores
       }
       
       for(int k=0; k < generalData.length; k++)
       {
           labData[k] = generalData[k];//Copy data(generalData) into labData
       }
   }
   
   /**
    * Setter used for interacting and manipulating the data within the arrays.
    * 
    * @param Object setInput
    * @param String selectedArray
    * @param int xCord
    */
   public void setCell(Object setInput, String selectedArray, int xCord)
   {
       try
       {
           if(selectedArray.equals("studentScores"))
           {
               studentScores[xCord] = setInput;//Sets col value of an index in studentScores array
           }
           else
           {
               if(selectedArray.equals("labData"))
               {
                   labData[xCord] = setInput;//Sets col value of an index in labData array
               }
           }
       }
       catch(Exception e)
       {
           System.out.print("Outside of array bounds, please specify a new set of valid cordinates");
       }
   }
   
   /**
    * Retreives the entire studentScores array which holds the score and factors that influenced the score.
    * Format = (Scores, compiled?, executes?)
    * 
    * @return Object[] studentScores
    */
   public Object[] getStudentScores()
   {
       return studentScores;//return the object[] array
   }
   
   /**
    * Retreives the entire labData array which holds additional information just for being displayed to the client.
    * Format = (#comments, #javaDocs, #break, #return)
    * 
    * @return Object[] labData
    */
   public Object[] getLabData()
   {
       return labData;//return the object[] array
   }
   
   /**
    * Returns a user's score.
    * 
    * @return int score
    */
   public int getScoreValue()
   {
       Double score;
       Integer intScore;
       score = (Double)studentScores[0];//Typecasts the returned double as a Double object
       intScore = (int)score.doubleValue();//Takes the double and typecasts as an int and then returns as an Integer for the DB to be able to handle it
       return intScore.intValue();
   }
   
   /**
    * returns a user's compilationResults. Tests to see if the java file can even compile correctly.
    * 
    * @return Boolean testCompile
    */
   public Boolean getCompileResults()
   {
       Boolean testCompile;
       
       testCompile = (Boolean)studentScores[1];//Typecasts the returned boolean as a Boolean object
       
       return testCompile;
   }
   
   /**
    * returns a user's ExecutionResults. Tests to see if the java class file(s) can run without crash or exception.
    * 
    * @return Boolean testExec
    */
   public Boolean getExecutionResults()
   {
       Boolean testExec;
       
       testExec = (Boolean)studentScores[2];//Typecasts the returned boolean as a Boolean object
       
       return testExec;
   }
   
   /**
    * returns the total number of comments in all the programs combined.
    * 
    * @return Integer numComments
    */
   public Integer getNumComments()
   {
       Integer numComments;
       
       numComments = (Integer)labData[0];//Typecasts the returned int as an Integer object

       return numComments;
   }
   
   /**
    * returns the total number of JavaDocs in all the programs combined.
    * 
    * @return Integer numJavaDocs
    */
   public Integer getNumJavaDocs()
   {
       Integer numJavaDocs;
       
       numJavaDocs = (Integer)labData[1];//Typecasts the returned int as an Integer object

       return numJavaDocs;
   }
   
   /**
    * returns the total number of break statements in all the programs combined.
    * 
    * @return Integer numBreaks
    */
   public Integer getAckNum()
   {
       Integer numBreaks;
       
       numBreaks = (Integer)labData[2];//Typecasts the returned int as an Integer object

       return numBreaks;
   }
   
   /**
    * returns the total number of return statements in all the programs combined.
    * 
    * @return Integer numReturns
    */
   public Integer getRetNum()
   {
       Integer numReturns;
       
       numReturns = (Integer)labData[3];//Typecasts the returned int as an Integer object

       return numReturns;
   }
}