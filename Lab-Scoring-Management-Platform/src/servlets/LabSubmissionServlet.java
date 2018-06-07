package servlets;

import scoringmanagement.LabSubmission; // For submitting labs

// For file uploads
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
//import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LabSubmissionServlet. Allows for student to submit labs for scoring.
 * @author Akul Arora
 * @version 06/01/2018.
 */

@WebServlet("/Student/LabSubmissionServlet")
public class LabSubmissionServlet extends SubmissionServlet 
{
	private final String ROOT_UPLOAD_DIR = "C:/Users/Akul/Desktop/Uploads"; // TODO change based upon server
	private String folderName; // Based on the id, period, lab
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * @param none
     */
    public LabSubmissionServlet() 
    {
    	// Object created by form action call. Nothing to be done here. 
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * Serves LabSubmission webpage with correct labs to choose from.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<String> labs= new ArrayList<String>(2);
		
		labs.add("Lab1");
		request.setAttribute("labs", labs);
		request.getRequestDispatcher("LabSubmission.jsp").forward(request, response);
	}
	
	/**
	 * Receives user input of lab submission information.
	 * Outputs scores of student back to web page.
	 * Also, stores to MySQL database and files submitted to file system. Files are stored to upload directory/foldername.
	 * Folder name is "<id>-<period>-<lab>" 
	List<String> options = new ArrayList<String>(2);
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Inputted values
    	ServletFileUpload input = null; // equivalent of request, but for multipart/form-data
    	Map<String,List<FileItem>> passedValues = null; // For handling all values passed (must parse multipart/form-data differently).
		String name = null, labName = null;
		int id = 0, period = 0;
		List<File> fileNames = null; // returned from fileUpload() method
		
		LabSubmission submission = null; // For submitting lab
		PrintWriter out = response.getWriter(); // Output
		
		// Temporary values for method to work
		boolean correctInput = true;
		
		// Get values passed
		try
		{
			input = new ServletFileUpload(new DiskFileItemFactory());
			passedValues = input.parseParameterMap(request);
		}
		catch (FileUploadException e) 
		{
			e.printStackTrace();
			out.write("Error - please try again later."); // Generic error output to user
		}
		
		// Get non-file values (convert to integer if needed)
		name = passedValues.get("name").get(0).getString(); // Chain - List of FileItems, first FileItem (only FileItem), String value passed
		labName = passedValues.get("lab").get(0).getString();
		try
		{
			id = Integer.parseInt(passedValues.get("id").get(0).getString());
			period = Integer.parseInt(passedValues.get("period").get(0).getString());
		}
		catch(NumberFormatException e) // Catches parsing errors. Should only be an issue with IDs (direct user input).
		{
			System.out.println(e);
			out.write("Please enter an integer value for the id.");
			correctInput = false;
		}
		catch(NullPointerException e) // Should never hit this, since required in html form input.
		{
			System.out.println(e);
			out.write("Please provide all required input." );
			correctInput = false;
		}
		
		
		// Begins execution of scoring and management of lab only if correct input was provided.
		if (correctInput == true)
		{
			// File upload
			folderName = id + "-" + period + "-" + labName;
			fileNames = fileUpload(passedValues.get("files"));
			
			// Submit lab for scoring and addition to management system. Will only continue if files were uploaded properly.
			//if (fileNames == null)
				// Instantiate submission object
				//submission = new LabSubmission(name, period, id, labName, fileNames);
		}
	}
    
    /**
	 * @see SubmissionServlet#fileUpload(List, String)
	 */
	public File getUploadDir()
	{
		return new File(ROOT_UPLOAD_DIR + "/" + folderName);
	}

/* For testing parameters passed. If not using form-data.
 * https://stackoverflow.com/questions/17281446/how-to-request-getparameternames-into-list-of-strings
List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
for (String x : parameterNames)
{
	System.out.println(x);
}

 * If using form-data
 * Courtesy of https://stackoverflow.com/questions/6385282/how-to-send-multipart-data-and-text-data-from-html-form-to-jsp-page
 List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
 for (FileItem item : items) {
    if (item.isFormField()) {
        // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
        String fieldname = item.getFieldName();
        String fieldvalue = item.getString();
        System.out.println(fieldname);
        System.out.println(fieldvalue);
        // ... (do your job here)
    } else {
        // Process form file field (input type="file").
        String fieldname = item.getFieldName();
        String filename = FilenameUtils.getName(item.getName());
        System.out.println(fieldname);
        System.out.println(filename);
        // ... (do your job here)
    }
}
*/
}
