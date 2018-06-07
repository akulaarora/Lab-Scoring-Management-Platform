package scoringmanagement;

import java.util.*;

/**
* Companion class to Scorer. Places the values Scorer writes into an object and stores it for further manipulation if required. 
* studentScores array holds the score defined as (avg output correct/ avg output 
* A seperate array stores unscored aspects of the program with data only as feedback for the client.
* 
* Example format:
* studentScores array                 labData array
* ------------------------            ----------------------------------
* 20.0  | False   | True |            | 56     | 4      | 5       | 2   | 
* ------------------------            ----------------------------------
* Score--Compiled?-Runs?             #comments-#methods-#JavaDocs-#Break       
* 
*
* @author Darshan & Max
* @version 5/31
*/
public class Score
{
   private Object[] studentScores; 
   private Object[] labData;
   
   /**
    * Default constructor. Defines a small of unset data. Most definityl requires "setCell" method to configure to appropriate values.
    */
   public Score()
   {        
      Object[] studentScores = new Object[] {0.0, false, false};//defines scored data
      
    
      Object[] labData = new Object[]{
      0, 0, 0, 0};//defines general data
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
           studentScores[i] = scores[i];//Copy data into studentScores
       }
       
       for(int k=0; k < generalData.length; k++)
       {
           labData[k] = generalData[k];//Copy data into labData
       }
   }
   
   /**
    * Setter used for interacting and manipulating the data within the arrays
    */
   public void setCell(Object setInput, String selectedArray, int xCord)
   {
       try
       {
           if(selectedArray.equals("studentScores"))
           {
               studentScores[xCord] = setInput;
           }
           else
           {
               if(selectedArray.equals("labData"))
               {
                   labData[xCord] = setInput;
               }
           }
       }
       catch(Exception e)
       {
           System.out.print("Outside of array bounds, please specify a new set of valid cordinates");
       }
   }
   
   /**
    * Retreives the entire studentScores array which holds the score and factors that influenced the score
    * 
    * @return Object[] studentScores
    */
   public Object[] getStudentScores()
   {
       return studentScores;
   }
   
   /**
    * Retreives the entire labData array which holds additional information just for being displayed to the client
    */
   public Object[] getLabData()
   {
       return labData;
   }
   
   /**
    * returns a  test input's score
    * 
    * @return Double scoreList
    */
   public Double getScore()
   {
       Double scoreList;
       
       scoreList = (Double)studentScores[0];
       
       return scoreList;
   }
   
   /**
    * returns a  test input's compilationResults. Tests to see if the java file can even compile correctly
    * 
    * @return Boolean scoreList
    */
   public Boolean getCompileResults()
   {
       Boolean scoreList;
       
       scoreList = (Boolean)studentScores[1];
       
       return scoreList;
   }
   
   /**
    * returns a  test input's ExecutionResults. Tests to see if the java file can execute correctly
    * 
    * @return Boolean scoreList
    */
   public Boolean getExecutionResults()
   {
       Boolean scoreList;
       
       scoreList = (Boolean)studentScores[2];
       
       return scoreList;
   }
   
   /**
    * returns the total number of comments in all the programs combined.
    * 
    * @return Integer scoreList
    */
   public Integer getNumComments()
   {
       Integer scoreList;
       
       scoreList = (Integer)labData[0];

       return scoreList;
   }
   
    /**
    * returns the total number of methods in all the programs combined.
    * 
    * @return Integer scoreList
    */
   public Integer getDefinedMethods()
   {
       Integer scoreList;
       
       scoreList = (Integer)labData[1];

       return scoreList;
   }
   
   /**
    * returns the total number of JavaDocs in all the programs combined.
    * 
    * @return Integer scoreList
    */
   public Integer getNumJavaDocs()
   {
       Integer scoreList;
       
       scoreList = (Integer)labData[2];

       return scoreList;
   }
   
   /**
    * returns the total number of break in all the programs combined.
    * 
    * @return Integer scoreList
    */
   public Integer getAckNum()
   {
       Integer scoreList;
       
       scoreList = (Integer)labData[3];

       return scoreList;
   }
}