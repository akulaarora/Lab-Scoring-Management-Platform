package servlets;

// For file uploads
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.*;

import java.util.ArrayList;
import java.util.List;
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
 * Servlet implementation class SubmissionServlet. Allows for submission of data involving files.
 * @author Akul Arora
 * @version 06/01/2018.
 */

public abstract class SubmissionServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * @param none
     */
    public SubmissionServlet() 
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
		response.getWriter().append("Using a GET call for submitting certain data poses risks."
				+ "Please use the POST method provided.");
		/*
		 * Default
		 * response.getWriter().append("Served at: ").append(request.getContextPath());
		 */
	}
	
	protected File fileUpload(FileItem passedFile)
	{
		File uploadDir = getUploadDir();
		File filepath; // Return
		
		// For method use
		String filename;
		
		
		// Creates necessary folders
		uploadDir.mkdirs(); // Makes necessary directories/folders
		
		// Get file path and filename
		// FileNameUtils converts from full path to just filename
		// See more here: http://commons.apache.org/proper/commons-fileupload/faq.html#whole-path-from-IE
        filename = FilenameUtils.getName(passedFile.getName()); // Get filename
        System.out.println(filename);
        
        filepath = new File(uploadDir + "/" + filename); // Get new file path
        
        // Upload file
        try
        {
			passedFile.write(filepath); // Creates file
		}
        catch (Exception e) // Any issue with file uploading 
        {
			e.printStackTrace();
		}
        
        return filepath;
	}
    
	// For handling file uploads. 
    // Takes input of List of files sent.
    // Will return list of filenames (full directory) to handle in scoring.
	protected List<File> fileUpload(List<FileItem> passedFiles)
	{
		File uploadDir = getUploadDir();
		List<File> filespaths = new ArrayList<File>(3); // Return
		
		// For method use
		String filename;
		File filepath;
		
		// Creates necessary folders
		uploadDir.mkdirs(); // Makes necessary directories/folders
		
		// Traverses list of files
		for (FileItem file : passedFiles) 
		{
			// Get file path and filename
			// FileNameUtils converts from full path to just filename
			// See more here: http://commons.apache.org/proper/commons-fileupload/faq.html#whole-path-from-IE
	        filename = FilenameUtils.getName(file.getName()); // Get filename
	        System.out.println(filename);
	        
	        filepath = new File(uploadDir + "/" + filename); // Get new file path
	        filespaths.add(filepath); // Add filepath to list of file paths
	        
	        // Write file
	        try
	        {
				file.write(filepath); // Creates file
			}
	        catch (Exception e) // Any issue with file uploading 
	        {
				e.printStackTrace();
			}
		}
		
		return filespaths;
	}
	
	
	/**
	 * Gets File path to the directory where the files were uploaded.
	 * @return File file path
	 */
	public abstract File getUploadDir();

}
