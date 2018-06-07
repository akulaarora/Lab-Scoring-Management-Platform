import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import java.util.*;
import chn.util.*;
import java.util.Scanner.*;

/**
 * Main scoring class for student lab submissions. Receives the student's lab files from the "LabSubmission" class
 * and compares them scoring various sections of the program based on the spec for said lab.
 *
 * @author Max, Darshan
 * @version 5/29
 */
public class Scorer
{
    public static void main(String[] args)
    {
        String testLab = "package testcompile; public class HelloWorld implements inlinecompiler.InlineCompiler.DoStuff{ public static void main(String[] args) {System.out.println(\"Hello world\");}}";
        String[] labFiles = new String[1];
        labFiles[0] = testLab;
        scoreStudent(labFiles);
        
        //https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes?noredirect=1&lq=1
        
        //WE NEED TO REMOVE ALL NEWLINES FROM THE STRING + ADD ESCAPE CHARACTERS FOR ALL " IN THE CODE -- create parser
        //maybe keep the newlines? I think the program breaks without them
    }
    
    /**
     * Acts as the main method and is the only outward facing method of class.
     * scoreStudent calls all other methods within the class.
     * Compilation of the student's labs is done by the Java provided library --> https://docs.oracle.com/javase/7/docs/api/javax/tools/JavaCompiler.html
     * Compilation section is forked from ---> https://github.com/0416354917/Algorithms/blob/master/src/util/InlineCompiler.java
     * Bash setup and commandline is forked from ---> https://stackoverflow.com/questions/26830617/java-running-bash-commands
     * Bash exectution is run by --> https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html
     * 
     * @param Sends array of labFiles
     * @return scoreData array holds the score output of the student's lab and returns the output to their student client
     */
    public static void scoreStudent(String[] labFiles)
    {
        //Read the lab spec and set the number of inputs required to the size of the object
        //int inputNum = ???;
        
        Object[] scoreData;
        Object[] labData;
       
        System.out.println(getScore("lab30_1", "1849935", true, false));
        
        /** BASH EXECUTION SECTION */
        
        //calls private methods and returns calculated string as output
        //Score does not store data in the database
        //Student submission calls score which returns the String[] of information and then Student submission call the dbinteract and enters all the information
        //Score newScore = new Score(scoreData, labData);//only create the object once the variables have been created and set.
    }
    
    public static double getScore(String labName, String ID, boolean testExec, boolean testComp)
    {
        FileInput correctFile = new FileInput("C:/Users/^Water_Bear/Desktop/1849935/lab30_1/output.txt");;
        FileInput wrongFile;
        FileInput countFile;
        
        double avgScore = 0.0;
        int numLines = 0;
        int totalOutputScore = 30;
        
        if(testExec == true)
        {
            avgScore+=10;
        }
        if(testComp == true)
        {
            avgScore+=10;
        }
        
        
        Boolean[] checkOut;
        String[] labSpec = {"cow went moo", "chick goes bock", "107"};;
        
        try
        {
            countFile = new FileInput("C:/Users/^Water_Bear/Desktop/1849935/lab30_1/output.txt");

            while(countFile.hasMoreLines())
            {    
                numLines++;
                countFile.readLine();
            }   
            
            countFile.close();
            
            
        }
        catch(Exception e)
        {
            System.out.println("Make sure to first compile and execute the program before calling this method!!");
        }
        
        String[] labFile = new String[numLines];
        checkOut = new Boolean[numLines];
        int x = 0;
        while(correctFile.hasMoreLines())
        {
            labFile[x] = correctFile.readLine().toLowerCase(); 
            x++;
        }
        
        for(int i = 0; i < labFile.length; i++)
        {
            for(int j = 0; j < labSpec.length; j++)
            {
                if(labSpec[j].equals(labFile[i]))
                {
                    checkOut[i] = true;
                }
            }
            if(checkOut[i] == null)
                checkOut[i] = false;
        }
        
        int checksCorrect = 0;
        for(int k = 0; k < checkOut.length; k++)
        {
            if(checkOut[k] == true)
                checksCorrect++;   
        }
        
        avgScore += ((((double)checksCorrect)/(checkOut.length))*totalOutputScore);

        return avgScore;
    }
    
    public int numComments(String ID, String labName)
    {
       int commentNum = 0;
        /*
        --Outputs percentage of comments to code written maybe?
       --Check to make sure java docs exist for each method and the class
       --counts the number of inline comments
       */
       return commentNum;
    }
    
    public int numMethods(String ID, String labName)
    {
        int methodNum = 0;
        //Check to see how many methods are defined 
        return methodNum;
    }
    
    public int ackCounter(String ID, String labName)
    {
        int breakNum = 0;
        //check for break statements and count the total
        return breakNum;
    }
    
    public int numJavaDoc(String ID, String labName)
    {
        int javaDocNum = 0;
        //check for the number of java docs in the program 
        //could be used in conjunction with the numMethods method and compared + private methods screw things up but that does not really matter
        return javaDocNum;
    }
    
    public boolean globalInstanceCheck(String ID, String labName)
    {
        boolean instanceCheck = false;
        //This method checks to see if global variables exists "ACK" bad mojo //emi rigt
        return instanceCheck;
    }
}