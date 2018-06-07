package scoringmanagement;

import servlets.SpecSubmissionServlet;

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
    private static final String OUTPUT_FILE = "C:/Users/Akul/Desktop/temp/output.txt";
    private static final File TEMP_DIR = new File("C:/Users/Akul/Desktop/temp");
    
    
    
    /**
     * Outwards facing method for receiving data and converting file contents to Strings.
     * Works with score() method.
     * @param labName
     * @param files
     * @return Score
     */
    public static Score scoreLab(String labName, List<File> files)
    { 	
    	// Scoring
    	Score score;
    	
    	// File Contents
    	String[] fileContents = new String[files.size()];
    	String[] fileNames = new String[files.size()];
    	
    	// Create arrays for use. File names and contents.
    	for (int i = 0; i < files.size(); i++)
    	{
    		fileNames[i] = files.get(i).getName();
    		fileContents[i] = fileToString(files.get(i));
    	}
    	
    	score = score(labName, fileContents, fileNames);
    	
    	return score;
    }
    
    private static String fileToString(File in) 
    {
    	FileInput input = new FileInput(in.getPath());
    	String temp = "";
    	while (input.hasMoreLines()) {
    		temp += input.readLine() + " \n";//converts File to a String
    	}
    	return temp;
    }
	
	
    /**
     * Acts as the main method for scoring.
     * scoreStudent calls all other methods within the class.
     * Compilation of the student's labs is done by the Java provided library --> https://docs.oracle.com/javase/7/docs/api/javax/tools/JavaCompiler.html
     * Compilation section is forked from ---> https://github.com/0416354917/Algorithms/blob/master/src/util/InlineCompiler.java
     * Bash setup and command line is forked from ---> https://stackoverflow.com/questions/26830617/java-running-bash-commands
     * Bash execution is run by --> https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html
     * 
     * @param String labSpec
     * @param Sends array of labFiles
     * @return scoreData array holds the score output of the student's lab and returns the output to their student client
     */
    public static Score score(String labSpec, String[] labFiles, String[] labNames)
    {
        //Declarations for values to be put into the Score class
        Object[] scoreData;
        Object[] labData;
        Boolean testCompile = false;
        Boolean testExec;
        Double score;
        Integer numCom, numAck, numDoc, numRet;
        
        // Create java files
        for(int i = 0; i < labFiles.length; i++)
        {
            System.out.println(labNames[i]);
            System.out.println(labFiles[i]);
            Invoker.createSource(labNames[i], labFiles[i]);
        }
        
        //COMPILE THE java files
        for(int i = 0; i < labFiles.length; i++)
        {
            Boolean compOutput;
            compOutput = Invoker.compileClass(labNames[i], labFiles[i]);
            if(compOutput = true)
                testCompile = true;
        }
        
        //RUN THE MAIN FILE
        testExec = Invoker.runProgram("Driver");
        
        //Score the lab
        score = calcScore(labSpec, testExec, testCompile);
        
        //Create user stats
        numCom = numComments();
        numAck = ackCounter();
        numDoc = numJavaDoc();
        numRet = numReturn();
        
        //create Object[] arrays
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
    public static double calcScore(String specName, boolean testExec, boolean testComp)
    {
        FileInput outputFile = new FileInput(OUTPUT_FILE);//File where console output is redirected to
        FileInput countFile;//Counts the number of lines in the output file
        
        double avgScore = 0.0;//Total student's score
        int numLines = 0;//Number lines in the output file
        int totalOutputScore = 30;//The maximum score for getting full credit in the run output
        
        if(testExec == true)
        {
            avgScore+=10;//if the program fully executed without exception add 10 points
        }
        if(testComp == true)
        {
            avgScore+=10;//if the program compiled correctly add 10 points
        }
        
        Boolean[] checkOut;//True if the correct output was found and false if the output was not found
        String[] labSpec = getAllOut(specName);//Holds every OUT value in a String[]
        
        // Count # of lines in output files
        try
        {
            countFile = new FileInput(OUTPUT_FILE);

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
        
        String[] labFile = new String[numLines];//output.txt is copied into a String[] array
        checkOut = new Boolean[numLines];//True if the correct output was found and false if the output was not found
        int x = 0;
        
        while(outputFile.hasMoreLines())
        {
            labFile[x] = outputFile.readLine().toLowerCase(); //output.txt is copied into a String[] array
            x++;
        }
        
        for(int i = 0; i < labFile.length; i++)//loop for each line in the output.txt
        {
            for(int j = 0; j < labSpec.length; j++)//loop for each OUT value in the spec
            {
                if(labSpec[j].equals(labFile[i]))
                {
                    checkOut[i] = true;//If the 1 line in output.txt was found in any of the spec lines then the output value is true
                }
            }
            if(checkOut[i] == null)
                checkOut[i] = false;//if the output.txt value was not found in the spec then false
        }
        
        int checksCorrect = 0;
        for(int k = 0; k < checkOut.length; k++)
        {
            if(checkOut[k] == true)
                checksCorrect++;//tallies up the total number of output values correct
        }
        
        avgScore += ((((double)checksCorrect)/(checkOut.length))*totalOutputScore);//calculates fraction and multiples by total score

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
        File dir = TEMP_DIR; // TODO Change to server directory
        File[] files = dir.listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(File dir, String name) //Scans the working(temp) directory
            {
                return name.endsWith(".java");//Every file that ends with .java is returned
            }
        });
        
        return files;
    }
    
    private static String getAfterOut(String line)
    {
        String output;        
        int i = line.indexOf("OUT");//search for the OUT in the String
        
        output = line.substring(i + 4);//Takes the line after the OUT String and reads until new line
        return output;
    } 
    
    /**
     * Finds the specific lab spec and scans it for all output to be cross referenced in Score
     * 
     * @param String specFileName
     * 
     * @return String[] output
     */
    public static String[] getAllOut(String specFileName)
    {
    	// Get the filepath for the lab specs
	SpecSubmissionServlet specSubmission = new SpecSubmissionServlet();
	String labSpecFolderDirectory = specSubmission.getUploadDir().getPath(); 
	File labSpecFile = new File(labSpecFolderDirectory + "/" + specFileName + ".txt");
    	
	//Creates ArrayList to store out Strings on
        String indLine;
        ArrayList<String> outList = new ArrayList<String>();
        
        //Set stream to deal with file input
        FileInput inFile= new FileInput(labSpecFile.getPath());
        while(inFile.hasMoreLines())//Traverse every line of the file
        {
            indLine = inFile.readLine();
            if(indLine.indexOf("OUT") >= 0)
            {
                indLine = getAfterOut(indLine);//Call helper method to assist in adding the string to the arrayList - only triggered if OUT exists on said line
                outList.add(indLine);
            }
        }
        
        //Take the data out of the ArrayList and return to String[] array
        String[] output = new String[outList.size()];
        for(int i = 0; i < outList.size(); i++)
        {
            output[i] = outList.get(i);
        }
        
        return output;//String[] array holding each string of OUT data
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
       files = scanDirectory();//Scans every .java file in the directory and stores the array
       
       for(int i = 0; i < files.length; i++)
       {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               commentNum += StringUtils.countMatches(code, "//");//counts all occurances of the sequence "//" in a file
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
       }
       
       return commentNum;//total number of comments
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
        files = scanDirectory();//Scans every .java file in the directory and stores the array
       
        for(int i = 0; i < files.length; i++)
        {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               breakNum += StringUtils.countMatches(code, "break");//counts all occurances of the sequence "break" in a file 
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
        }
        
        return breakNum;//total number of break statements
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
        files = scanDirectory();//Scans every .java file in the directory and stores the array
       
        for(int i = 0; i < files.length; i++)
        {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               javaDocNum += StringUtils.countMatches(code, "/**");//counts all occurances of the sequence "/**" in a file 
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
        }
        
        return javaDocNum;//total number of javaDoc sections
    }
    
    /**
     * Counts the number of returns in all labFiles the student has submit. NOT APPLIED TO THEIR SCORE!
     * This data is returned to the student only as a statistic
     * 
     * @return int instanceCheck
     */
    public static int numReturn()
    {
        int numRet = 0;
        
        File[] files;
        files = scanDirectory();//Scans every .java file in the directory and stores the array
       
        for(int i = 0; i < files.length; i++)
        {
           try
           {
               String code = FileUtils.readFileToString(files[i]);
               
               numRet += StringUtils.countMatches(code, "return");//counts all occurances of the word "return" in a file 
           }
           catch(Exception e)
           {
               System.out.println("Could not convert File object to String object");
           }
        }
        
        return numRet;//total number of return statements
    }
}
