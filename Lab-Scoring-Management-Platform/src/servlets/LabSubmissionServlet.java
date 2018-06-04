package servlets;

import scoringmanagement.LabSubmission; // For submitting labs

// For file uploads
import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.*; // For Lists
import java.io.File; // For files

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
 */

@WebServlet("/Student/LabSubmissionServlet")
public class LabSubmissionServlet extends HttpServlet 
{
	private static final String UPLOAD_DIR = "C:/Users/Akul/Desktop/Uploads"; // TODO change based upon server 
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
	 * To not be used for these purposes. Unsafe.
	 * Sends error stating to use POST method.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Using a GET call poses risks."
				+ "Please use the POST method through the Lab Submission page provided.");
		/*
		 * Default
		 * response.getWriter().append("Served at: ").append(request.getContextPath());
		 */
	}
	
	/**
	 * Receives user input of lab submission information.
	 * Outputs scores of student back to web page.
	 * Also, stores to MySQL database and files submitted to file system. Files are stored to upload directory/foldername.
	 * Folder name is "<id>-<period>-<lab>" 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Inputted values
		LabSubmission submission = null;
		String name = null, labName = null;
		int id = 0, period = 0;
		List<File> fileNames = null;
		
		// Temporary values for method to work
		String folderName; // Based upon the id, period, lab
		boolean correctInput = true;
		
		// Output
		PrintWriter out = response.getWriter();
		
		// Get variable values (convert to integer if needed)
		name = request.getParameter("name");
		labName = request.getParameter("lab");
		try
		{
			id = Integer.parseInt(request.getParameter("id"));
			period = Integer.parseInt(request.getParameter("period"));
		}
		catch(NumberFormatException e) // Catches parsing errors. Should only be an issue with IDs (direct user input).
		{
			System.out.println(e);
			out.write("Please enter an integer value for the id.");
			correctInput = false;
		}
		catch(NullPointerException e) // Should never hit this, since required in html form.
		{
			System.out.println(e); // debugging
			out.write("Please provide all required input." );
			correctInput = false;
		}
		
		// Begins execution of scoring and management of lab only if correct input was provided.
		if (correctInput == true)
		{
			// File upload
			folderName = id + "-" + period + "-" + labName;
			fileNames = fileUpload(request, folderName);
			
			// Submit lab for scoring and addition to management system. Will only continue if files were uploaded properly.
			//if (fileNames == null)
				// Instantiate submission object
				//submission = new LabSubmission(name, period, id, labName, fileNames);
		}
	}
	
	// For handling file uploads. Will return list of filenames to handle
	private List<File> fileUpload(HttpServletRequest request, String folderName)
	{
		ServletFileUpload fileUpload = new ServletFileUpload(); // For receiving files uploaded
		String uploadFolder = UPLOAD_DIR + folderName; // Where to upload to
		List<File> fileNames = new ArrayList<>(10); // Return
		
		// For method use
		List<FileItem> filesList = null;
		File fileName; // Strings but for files
		boolean filesParsed = true; // Ensures execution only continues if files were parsed correctly
		
		
		// Parses files submitted
		try 
		{
			filesList = fileUpload.parseRequest(request);
		} 
		catch (FileUploadException e) 
		{
			e.printStackTrace();
			filesParsed = false;
		}
		
		// File uploading
		if (filesParsed == true) // Only executes file uploading to system if files were parsed correctly
		{
			for (FileItem file : filesList)
				if (!file.isFormField()) // Ensures valid input
				{
					fileName = new File(file.getName());
					fileNames.add(new File(UPLOAD_DIR + "/" + fileName));
					
					try 
					{
						file.write(fileNames.get(fileNames.size()-1)); // Creates file. Gets last File added (the one just added to the fileNames list).
					}
					catch (Exception e) // General exceptions with file creation/uploading
					{
						e.printStackTrace();
					}
				}
		}
		
		
		return fileNames;
	}

}
