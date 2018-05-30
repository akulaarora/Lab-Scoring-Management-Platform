import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
 * @author Max, Darshan, Akul, Manseej
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
     *
     * @param Sends array of labFiles
     * @return scoreData array holds the score output of the student's lab and returns the output to their student client
     */
    public static void scoreStudent(String[] labFiles)
    {
        String[] scoreData = new String[5];//scoreData is returned to the 
       
        StringBuilder sb = new StringBuilder(64);//WHAT DOES 64 EVEN DO?
        //IMPLEMENT FOR LOOP TO APPEND EACH LINE AT EVERY NEWLINE
        //FOR SOME REASON JUST APPENDING THE ENTIRE LAB THROUGH A SINGLE METHOD CALL LIKE A MAD MAN IS A BAD IDEA. BREAKS I DUNNO WHY, DOCUMENTATION IS A PAIN IN THE ASS TO READ. COMMENTED OUT CODE WORKS THOUGH  
        /*
        sb.append("public class HelloWorld {\n");
        sb.append("    public static void main(String[] args) {\n");
        sb.append("        System.out.println(\"Hello world!\");\n");
        sb.append("    }\n");
        sb.append("}\n");    
        */
        sb.append(labFiles[0]);
       
        //CHANGE TO SERVER DIRECTORY 
        File helloWorldJava = new File("C:\\Users\\^Water_Bear\\Desktop\\compiletest\\HelloWorld.java");
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
                finally //what even is a finally!??!
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
        //calls private methods and returns calculated string as output
        //Score does not store data in the database
        //Student submission calls score which returns the String[] of information and then Student submission call the dbinteract and enters all the information
        Score newScore = new Score(scoreData);
    }
    
    /*
     * List of methods to implement--All strings outputted are stored in scoreData
     * private static String labOutput(compiled labFile)
     * --gets spec output data and compares it with the compiled file
     * --get the program to work dynamically with requiring user input
     * private static String labComments(source labFile)
     * --Outputs percentage of comments to code written maybe?
     * --Check to make sure java docs exist for each method and the class
     * --counts the number of inline comments
     * private static String checkMethods(source labFile)
     * --Make sure the methods defined in the spec are synchronized with what is within the lab
     * private static String ackChecker(source labFile)
     * --check for break statements and count the number of returns for each method
     * private static String 
     * 
     * 
     */
}
