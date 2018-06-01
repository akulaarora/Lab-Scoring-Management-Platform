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
        
        Object[][] scoreData;
        Object[] labData;
       
        
        
        /** BASH EXECUTION SECTION */
        
        //calls private methods and returns calculated string as output
        //Score does not store data in the database
        //Student submission calls score which returns the String[] of information and then Student submission call the dbinteract and enters all the information
        //Score newScore = new Score(scoreData, labData);//only create the object once the variables have been created and set.
    }
    
    /**
     * The compileLab method takes in lab submission data (ID, LabName, and LabFiles)
     * A directory is setup such as below that sandboxes each run within the user's unique directory.
     * Value returned determines if the program was able to compile the file(s) or not
     * 
     * Sample Directory:
     * 1849999---> (newFolder) Lab30 --> labFiles (.class added after compilation)
     *        ^--> (new Folder) lab31 --> etc
     *        
     * @param String labName
     * @param String ID
     * @param String[] labFiles
     */
    public boolean compileLab(String labName, String ID, String[] labFiles)
    {
        boolean testCompile = false;
        StringBuilder sb = new StringBuilder(64);//WHAT DOES 64 EVEN DO?
        //IMPLEMENT FOR LOOP TO APPEND EACH LINE AT EVERY NEWLINE
        //FOR SOME REASON JUST APPENDING THE ENTIRE LAB THROUGH A SINGLE METHOD CALL LIKE A MAD MAN IS A BAD IDEA. BREAKS I DUNNO WHY, DOCUMENTATION IS A PAIN IN THE ASS TO READ. COMMENTED OUT CODE WORKS THOUGH  
        
        sb.append("public class HelloWorld {\n");
        sb.append("    public static void main(String[] args) {\n");
        sb.append("        System.out.println(\"Hello world!\");\n");
        sb.append("    }\n");
        sb.append("}\n");    
        
        //sb.append(labFiles[0]);
       
        //CHANGE TO SERVER DIRECTORY 
        File helloWorldJava = new File("C:/Users/^Water_Bear/Desktop/compiletest/HelloWorld.java");
        if (helloWorldJava.getParentFile().exists() || helloWorldJava.getParentFile().mkdirs())
        {
            try
            {
                Writer writer = null;
                try
                {
                    writer = new FileWriter(helloWorldJava);
                    writer.write(sb.toString());
                    writer.flush();
                } 
                finally 
                {
                    try 
                    {
                        writer.close();
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("writer can't be closed");
                    }
                }

                /** Compilation Requirements *********************************************************************************************/
                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

                // This sets up the class path that the compiler will use
                List<String> optionList = new ArrayList<String>();
                optionList.add("-classpath");
                optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler1.jar");

                Iterable<? extends JavaFileObject> compilationUnit
                        = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
                JavaCompiler.CompilationTask task = compiler.getTask(
                    null, 
                    fileManager, 
                    diagnostics, 
                    optionList, 
                    null, 
                    compilationUnit);
                /********************************************************************************************* Compilation Requirements **/
                if (task.call()) 
                {
                    /** Load and execute *************************************************************************************************/
                    // Create a new custom class loader, pointing to the directory that contains the compiled
                    // classes, this should point to the top of the package structure!
                    @SuppressWarnings("resource")
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
                    // Load the class from the classloader by name....
                    Class<?> loadedClass = classLoader.loadClass("testcompile.HelloWorld");
                    // Create a new instance...
                    Object obj = loadedClass.newInstance();
                    /************************************************************************************************* Load and execute **/
                } 
                else 
                {
                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) 
                    {
                        System.out.format("Error on line %d in %s%n",
                            diagnostic.getLineNumber(),
                            diagnostic.getSource().toUri());
                    }
                }
                fileManager.close();
            } 
            catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exp) 
            {
                exp.printStackTrace();
            }
        }
        
        return testCompile;
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
        Object[][] scoreNrunTest = new Object[3][arrayWidth];
        for(int i = 0; i < 3; i++)
        {
            scoreNrunTest[i][arrayWidth] = i;
        }
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
          */
         
        return scoreNrunTest;
    }
    
    /**
     * Allows commands to executed through bash.
     * Once a program has been compiled and set into its appropriate directory the files can be interacted with this method.
     * Adapated from --> https://stackoverflow.com/questions/26830617/java-running-bash-commands
     * 
     * @param String command
     * @return String output
     */
    public String executeCommand(String command) 
    {
        StringBuffer output = new StringBuffer();
        Process p;
        try 
        {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = 
                        new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";           
            while ((line = reader.readLine())!= null) 
            {
                output.append(line + "\n");
            }

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        return output.toString();

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
