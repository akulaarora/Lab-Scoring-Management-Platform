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

import chn.util.*;
import java.util.Scanner.*;
import java.io.File;
import java.lang.Object;
import java.io.*;
import org.apache.commons.lang3.StringUtils;//https://commons.apache.org/proper/commons-lang/download_lang.cgi
import org.apache.commons.io.FileUtils;//http://commons.apache.org/proper/commons-io/download_io.cgi

/**
 * Main scoring class for student lab submissions. Receives the student's lab files from the "LabSubmission" class
 * and compares them scoring various sections of the program based on the spec for said lab.
 *
 * @author Max, Darshan
 * @version 5/29
 */
public class Scorer
{
    /**
     * Acts as the main method and is the only outward facing method of class.
     * scoreStudent calls all other methods within the class.
     * Compilation of the student's labs is done by the Java provided library --> https://docs.oracle.com/javase/7/docs/api/javax/tools/JavaCompiler.html
     * Compilation section is forked from ---> https://github.com/0416354917/Algorithms/blob/master/src/util/InlineCompiler.java
     * Bash setup and commandline is forked from ---> https://stackoverflow.com/questions/26830617/java-running-bash-commands
     * Bash exectution is run by --> https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html
     * 
     * @param String labSpec
     * @param Sends array of labFiles
     * @return scoreData array holds the score output of the student's lab and returns the output to their student client
     */
    public static Score scoreStudent(String labSpec, String[] labFiles, String[] labNames)
    {
        Object[] scoreData;
        Object[] labData;
        Boolean testCompile = false;
        Boolean testExec;
        Double score;
        Integer numCom, numAck, numDoc, numRet;
        
        
        //COMPILE THE LABS
        for(int i = 0; i < labFiles.length; i++)
        {
            Boolean compOutput;
            compOutput = Invoker.compileLab(labNames[i], labFiles[i]);
            if(compOutput = true)
                testCompile = true;
        }
        
        //RUN THE MAIN FILE
        testExec = Invoker.runProgram("Driver");
        
        //Score the lab
        score = getScore(labSpec, testExec, testCompile);
        
        //Create user stats
        numCom = numComments();
        numAck = ackCounter();
        numDoc = numJavaDoc();
        numRet = numReturn();
        
        scoreData = new Object[] {score, testCompile, testExec};
        labData = new Object[] {numCom, numDoc, numAck, numRet};
        
        //Score object instantiated - use getters to access its components
        Score studentScore = new Score(scoreData, labData);
        return studentScore;
    }
    
    /**
     * Allows a user to score their lab based on compilation, execution, and their program output
     * 
     * @param String specName
     * @param boolean testExec
     * @param boolean testComp
     * 
     * @return double avgScore
     */
    public static double getScore(String specName, boolean testExec, boolean testComp)
    {
        FileInput correctFile = new FileInput("C:/Users/^Water_Bear/Desktop/temp/output.txt");
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
        String[] labSpec = {"cow went moo", "chick goes bock", "107"};//getAllOut(String specName)
        
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
    
    /**
     * Scans the user directory and returns all the .java files within it.
     * These files are then accessed and used to for Scorer
     * 
     * https://stackoverflow.com/questions/2102952/listing-files-in-a-directory-matching-a-pattern-in-java
     * 
     * @return File[] files
     */
    public static File[] scanDirectory()
    {
        File dir = new File("C:/Users/^Water_Bear/Desktop/temp");//CHANGE TO SERVER DIRECTORY
        File[] files = dir.listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(File dir, String name) 
            {
                return name.endsWith(".java");
            }
        });
        
        return files;
    }
    
    private String getAfterOut(String line)
    {
        String output;        
        int i = line.indexOf("OUT");
        
        output = line.substring(i + 4);
        return output;
    } 
    
    /**
     * Finds the specific lab spec and scans it for all output to be cross referenced in Score
     * 
     * @param String specName
     * 
     * @return output
     */
    public String[] getAllOut(String specName)
    {
        String specDirectory = "/Users/darshanparekh/Documents/test/" + specName + ".txt";//change to server directory
        String indLine;
        ArrayList<String> outList = new ArrayList<String>();
        
        FileInput inFile= new FileInput(specDirectory);
        while(inFile.hasMoreLines())
        {
            indLine = inFile.readLine();
            if(indLine.indexOf("OUT") >= 0)
            {
                indLine = getAfterOut(indLine);
                outList.add(indLine);
            }
        }
        
        String[] output = new String[outList.size()];
        for(int i = 0; i < outList.size(); i++)
        {
            output[i] = outList.get(i);
        }
        
        return output;
    }
    
    /**
     * Counts the number of comments in all labFiles the student has submit. NOT APPLIED TO THEIR SCORE!
     * This data is returned to the student only as a statistic.
     * 
     * @return int commentNum
     */
    public static int numComments()
    {
       int commentNum = 0;
       File[] files;
       files = scanDirectory();
       
       for(int i = 0; i < files.length; i++)
       {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               commentNum += StringUtils.countMatches(code, "//");
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
       }
       
       return commentNum;
    }
    
    /**
     * Counts the number of break statements in all labFiles the student has submit. NOT APPLIED TO THEIR SCORE!
     * This data is returned to the student only as a statistic
     * 
     * @return int breakNum
     */
    public static int ackCounter()
    {
        int breakNum = 0;
        
        File[] files;
        files = scanDirectory();
       
        for(int i = 0; i < files.length; i++)
        {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               breakNum += StringUtils.countMatches(code, "break");
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
        }
        
        return breakNum;
    }
    
    /**
     * Counts the number of javaDocs in all labFiles the student has submit. NOT APPLIED TO THEIR SCORE!
     * This data is returned to the student only as a statistic
     * 
     * @return int javaDocNum
     */
    public static int numJavaDoc()
    {
        int javaDocNum = 0;
        
        File[] files;
        files = scanDirectory();
       
        for(int i = 0; i < files.length; i++)
        {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               javaDocNum += StringUtils.countMatches(code, "/**");
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
        }
        
        return javaDocNum;
    }
    
    /**
     * Counts the number of returns in all labFiles the student has submit. NOT APPLIED TO THEIR SCORE!
     * This data is returned to the student only as a statistic
     * 
     * @return int instanceCheck
     */
    public static int numReturn()
    {
        int instanceCheck = 0;
        
        File[] files;
        files = scanDirectory();
       
        for(int i = 0; i < files.length; i++)
        {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               instanceCheck += StringUtils.countMatches(code, "return");
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
        }
        
        return instanceCheck;
    }
}