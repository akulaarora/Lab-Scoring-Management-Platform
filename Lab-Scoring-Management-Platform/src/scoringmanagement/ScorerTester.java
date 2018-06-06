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

import chn.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.*;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;
import java.util.Scanner;

/**
 * Main scoring class for student lab submissions. Receives the student's lab files from the "LabSubmission" class
 * and compares them scoring various sections of the program based on the spec for said lab.
 *
 * @author Max, Darshan
 * @version 5/29
 */
public class Scorer
{
    public static void main(String[] args)//tester to be removed
    {
        String labFile = "public class Driver {\n public static void main(String[] args) {\n System.out.println(\"Hello world!\");\n }\n }\n";
        String[] labFiles = new String[1];
        labFiles[0] = labFile;
        scoreStudent("lab30", "194456", labFiles);
        
        //WE NEED TO REMOVE ALL NEWLINES FROM THE STRING + ADD ESCAPE CHARACTERS FOR ALL " IN THE CODE -- create parser
    }
    
    /**
     * Acts as the main method.
     * scoreStudent calls all other methods within the class.
     * 
     * Bash setup and commandline is forked from ---> https://stackoverflow.com/questions/26830617/java-running-bash-commands
     * Bash exectution is run by --> https://docs.oracle.com/javase/7/docs/api/java/lang/ProceslabStringuilder.html
     * 
     * @param String[] labFiles
     * @param String labName
     * @param String ID
     * 
     * @return scoreData array holds the score output of the student's lab and returns the output to their student client
     */
    public static void scoreStudent(String labName, String ID, String[] labFiles)
    {
        for(int k = 0; k < labFiles.length; k++)
        { 
            Invoker.compileLab(labName, ID, labFiles[k]);
        }
        
        Invoker.runProgram(labName, ID, "Driver.class");
        
        Object[][] scoreData;
        Object[] labData;
        
        scoreData = new Object[][] {
            { 0, 20.0, true, false, true},
            { 1, 30.0, false, true, false},
            { 2, 10.0, true, false, true},
        };
       
        labData = new Object[] { 0, 20.0, true, false, true};
        
        //calls private methods and returns calculated string as output
        //Score does not store data in the database
        //Student submission calls score which returns the String[] of information and then Student submission call the dbinteract and enters all the information
        //Score newScore = new Score(scoreData, labData);//only create the object once the variables have been created and set.
    }
    
    
    
    /**
    * Execute a bash command. We can handle complex bash commands including
     * multiple executions (; | && ||), quotes, expansions ($), escapes (\), e.g.:
     *     "cd /abc/def; mv ghi 'older ghi '$(whoami)"
     * @param command
     * @return true if bash got started, but your command may have failed.
     */
    public static boolean executeBashCommand(String command) {
        boolean success = false;
        System.out.println("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);
    
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
    
            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }
    
            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }
    
    /**
     * The execLab method takes in lab submission data (ID, LabName, and LabFiles)
     * A directory is setup such as below such that once execLab is run after compilation it creates a new folder holding the 
     * lab's output in a txt file
     * 
     * Sample Directory:
     * 1849999---> (newFolder) Lab30 --> labFiles (.class added after compilation)
     *        ^                      --> (newFolder) outputFile1
     *        ^--> (new Folder) lab31 --> etc
     *        
     * @param String ID
     * @param String labName
     * @param String mainFile
     * 
     * @return Object[] scoreNrunTest
     */
    /*
    public Object[][] execLab(String ID, String labName, String mainFile)
    {
        //--gets spec output data and compares it with the compiled file
        //--get the program to work dynamically with requiring user input
        final int arrayWidth = 5;
        Boolean runTest = new Boolean("false");
        //get lab spec which is formatted like
        /*
         * #number of inputs 3
         * //Specific set of inputs
         */
        /*
        Object[][] scoreNrunTest = new Object[3][arrayWidth];
        for(int i = 0; i < 3; i++)
        {
            scoreNrunTest[i][arrayWidth] = i;
        }
        */
        /*
         * ------------------------------------------  
         * | 0    | 20.0 | 65%    | True    | False |       
         * | 1    | 30.0 | 80%    | False   | True  |       METHOD DEFINES ALL THESE VALUES
         * | 2    | 10.0 | 10%    | True    | False |       
         * ------------------------------------------
         */
        
        /*
          * cd into correct directory
          * Create output folder
          * try to run main file and catch execp
          * read output in bash and cat to file in directory
          * return if the program ran
          
         
        return scoreNrunTest;
    }
    */

    
/*
    private static  StreamListener implements Runnable{
        private BufferedReader Reader;
        private boolean Run;

        public StreamListener(InputStream s){
            Reader = new BufferedReader(new InputStreamReader(s));
            Run = true;
        }
        
        private void start(){
        String command = "java C:/Users/^Water_Bear/Desktop/compiletest/HelloWorld";//Command to invoke the program

        ProcessBuilder pb = new ProcessBuilder(command);

        try{
            Process p = pb.start();

            InputStream stdout = p.getInputStream();
            InputStream stderr = p.getErrorStream();

            StreamListener stdoutReader = new StreamListener(stdout);
            StreamListener stderrReader = new StreamListener(stderr);

            Thread t_stdoutReader = new Thread(stdoutReader);
            Thread t_stderrReader = new Thread(stderrReader);

            t_stdoutReader.start();
            t_stderrReader.start();
        }catch(IOException n){
            System.err.println("I/O Exception: " + n.getLocalizedMessage());
        }
        }

        public void run(){
            String line;

            try{
                while(Run && (line = Reader.readLine()) != null){
                    //At this point, a line of the output from the external process has been grabbed. Process it however you want.
                    System.out.println("External Process: " + line);
                }
            }catch(IOException n){
                System.err.println("StreamListener I/O Exception!");
            }
        }
    }
    */
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