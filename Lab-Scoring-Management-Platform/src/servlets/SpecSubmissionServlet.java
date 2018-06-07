package servlets;

import dbinteract.ScoringDBInteract; // For submitting labs to database
import scoringmanagement.LabSubmission; // For submitting labs

// For file uploads
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.*;

import java.util.List;
import java.util.Map;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SpecSubmissionServlet. Allows for teacher to submit specs for scoring labs.
 * @author Akul Arora
 * @version 06/05/2018.
 */

@WebServlet("/Teacher/SpecSubmissionServlet")
public class SpecSubmissionServlet extends SubmissionServlet 
{
	private final String UPLOAD_DIR = "C:/Users/Akul/Desktop/LabSpecs"; // TODO change based upon server
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * @param none
     */
    public SpecSubmissionServlet() 
    {
    	// Object created by form action call. Nothing to be done here. 
    }
    
    
	/**
	 * Receives user input of lab spec. Stores to server for later use.
	 * The filename is what is stored as the lab name in the database.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
    	ServletFileUpload input = null; // equivalent of request, but for multipart/form-data
    	ScoringDBInteract dbInteract = null;
		FileItem specFile = null;
		PrintWriter out = response.getWriter(); // Output
		
		boolean isWorking = true;
		
		// Get values passed
		try
		{
			input = new ServletFileUpload(new DiskFileItemFactory()); // Instantiate input
			// Gets map of parameters. Gets file parameter (list of all parameters with name file).
			// Gets first and only file value.
			specFile = input.parseParameterMap(request).get("file").get(0);
			// Upload file
			fileUpload(specFile);
		}
		catch (FileUploadException e) 
		{
			isWorking = false;
			e.printStackTrace();
			out.write("Error uploading file. Please try again later."); // Generic error output to user
		}
		
		// Add to database name of lab
		try
		{
			if (isWorking) // Will only run if so far has been working. Otherwise, will not continue.
			{
				dbInteract = new ScoringDBInteract();
				dbInteract.createLab(getLabName(specFile.getName()));
			}
		}
		catch (SQLException e)
		{
			isWorking = false;
			out.write("Error writing to database. Please try again later.");
		}
		
		
		if (isWorking)
			out.write("Lab has been submitted!");
	}
    
    // Gets labname from path
    private String getLabName(String path)
    {
    	System.out.println(path);
    	String output = "";
    	
    	output = path.substring(0, path.indexOf(".txt")); // Remove .txt extension
    	// Get from last backslash to end
    	while (output.indexOf("\\") != -1)
    		output = output.substring(output.indexOf("\\")+1, output.length());

    	return output;
    }
    
    /**
	 * @see SubmissionServlet#fileUpload(List, String)
	 */
	public File getUploadDir()
	{
		return new File(UPLOAD_DIR);
	}
}
