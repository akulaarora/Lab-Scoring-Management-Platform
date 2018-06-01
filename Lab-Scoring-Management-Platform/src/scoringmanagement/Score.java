import java.util.*;

/**
* Companion class to Scorer. Places the values Scorer writes into an object and stores it for further manipulation if required. 
* Scorer output follows defined indexing of a 2D array. The inital rows start with the test input's index with data in further columns for storing the scored output.
* A seperate array stores unscored aspects of the program with data only as feedback for the client.
* 
* Example format:
* studentScores array                                    labData array
* -------------------------------------------            --------------------------------------------------------
* | 0     | 20.0 | True    | False   | True |            | 56     | 4      | 5       | 2    | True              |
* | 1     | 30.0 | False   | True    | False|            --------------------------------------------------------
* | 2     | 10.0 | True    | False   | True |            #comments-#methods-#JavaDocs-#Break-GlobalInstanceVars?
* -------------------------------------------            
* inputNum-Score--inputExst-Compiled?-Runs?
*
* @author Darshan & Max
* @version 5/31
*/
public class Score
{
   private Object[][] studentScores; 
   private Object[] labData;
   
   /**
    * ONLY USED FOR PROGRAM TROUBLESHOOTING - REMOVE WHEN MODULE FUNCTIONS CORRECTLY
    */
   public static void main(String[] args)
   {
       Object[][] testScores = new Object[][]{
      { 0, 20.0, true, false, true},
      { 1, 30.0, false, true, false},
      { 2, 10.0, true, false, true},
     };
     
     Object[] testData = new Object[]{
      56, 4, 5, 2, true};
     Score test = new Score(testScores, testData);
     /**
      * Test methods here>>>
      * test.setCell(true, 0, 1);
      */
   }
   
   /**
    * Default constructor. Defines a small of unset data. Most definityl requires "setCell" method to configure to appropriate values.
    */
   public Score()
   {        
      Object[][] studentScores = new Object[][]{
      {0, 0.0, false, false, false},
      {1, 0.0, false, false, false}
      };//defines scored data
    
      Object[] labData = new Object[]{
      0, 0, 0, 0, false};//defines general data
   }
   
   /**
    * Retrieves specific scoring data from the Scorer class and alongside some general data just for the client who submitted the lab.
    * copies the data into private variables of the current class.
    * 
    * @param Object[][] scores
    * @param Object[] labData
    */
   public Score(Object[][] scores, Object[] generalData)
   {
       studentScores = new Object[scores.length][scores[0].length];//Define size of studentScores
       labData = new Object[generalData.length];//Define size of labData
       
       for(int i=0; i < scores.length; i++)
       {
            for(int j=0; j < scores[0].length; j++)
            { 
                {
                    studentScores[i][j] = scores[i][j];//Copy data into studentScores
                }
            }
       }
       
       for(int k=0; k < generalData.length; k++)
       {
           labData[k] = generalData[k];//Copy data into labData
       }
   }
   
   /**
    * Setter used for interacting and manipulating the data within the arrays
    */
   public void setCell(Object setInput, String selectedArray, int xCord, int yCord)
   {
       try
       {
           if(selectedArray.equals("studentScores"))
           {
               studentScores[xCord][yCord] = setInput;
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
    * @return Object[][] studentScores
    */
   public Object[][] getStudentScores()
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
   * Returns the data found in a single test run
   * 
   * @param int inputNum
   * @return Object[] singleTestInput
   */
   public Object[] getTestInput(int inputNum)
   {
       Object[] singleTestInput = new Object[studentScores[inputNum].length];
       
       for(int i=0; i < singleTestInput.length; i++)
       {
           singleTestInput[i] = studentScores[inputNum][i];
       }
       
       return singleTestInput;
   }
   
   /**
    * returns a single test input's score or if a number less than 0 is specified the program returns the scores of all tested inputs
    * 
    * @param int inputNum
    * @return Double[] scoreList
    */
   public Double[] getScore(int inputNum)
   {
       Double[] scoreList = new Double[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Double)studentScores[k][1];
               }
       }
       else
            {
               scoreList[0] = (Double)studentScores[inputNum][1];
            }
       
       return scoreList;
   }
   
   /**
    * returns a value describing if the program accepts input
    * or if a number less than 0 is specified the program returns the scores of all tested inputs
    * 
    * @param int inputNum
    * @return Boolean[] scoreList
    */
   public Boolean[] getInputExists(int inputNum)
   {
       Boolean[] scoreList = new Boolean[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Boolean)studentScores[k][2];
               }
       }
       else
            {
               scoreList[0] = (Boolean)studentScores[inputNum][2];
            }
       
       return scoreList;
   }
   
   public Integer[] getNumComments(int inputNum)
   {
       Integer[] scoreList = new Integer[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Integer)studentScores[k][3];
               }
       }
       else
            {
               scoreList[0] = (Integer)studentScores[inputNum][3];
            }
       
       return scoreList;
   }
   
   public Boolean[] getCompileResults(int inputNum)
   {
       Boolean[] scoreList = new Boolean[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Boolean)studentScores[k][4];
               }
       }
       else
            {
               scoreList[0] = (Boolean)studentScores[inputNum][4];
            }
       
       return scoreList;
   }
   
   public Boolean[] getExecutionResults(int inputNum)
   {
       Boolean[] scoreList = new Boolean[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Boolean)studentScores[k][5];
               }
       }
       else
            {
               scoreList[0] = (Boolean)studentScores[inputNum][5];
            }
       
       return scoreList;
   }
   
   public Integer[] getDefinedMethods(int inputNum)
   {
       Integer[] scoreList = new Integer[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Integer)studentScores[k][6];
               }
       }
       else
            {
               scoreList[0] = (Integer)studentScores[inputNum][6];
            }
       
       return scoreList;
   }
   
   public Integer[] getNumBreaks(int inputNum)
   {
       Integer[] scoreList = new Integer[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Integer)studentScores[k][7];
               }
       }
       else
            {
               scoreList[0] = (Integer)studentScores[inputNum][7];
            }
       
       return scoreList;
   }
   
   public Boolean[] getGlobalVarCheck(int inputNum)
   {
       Boolean[] scoreList = new Boolean[studentScores.length];
       
       if(inputNum < 0)
       {
           for(int k=0; k < studentScores.length; k++)
               {
                   scoreList[k] = (Boolean)studentScores[k][8];
               }
       }
       else
            {
               scoreList[0] = (Boolean)studentScores[inputNum][8];
            }
       
       return scoreList;
   }
}
