package servlets;

import dbinteract.ScoringDBInteract; // For getting list of labs and generating CSVs

import java.util.Map;
import java.util.HashMap;

// For reading and sending files
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenerateCSVServlet. Allows for teacher to retrieve CSV files of scores.
 * @author Akul Arora
 * @version 06/01/2018
 */

@WebServlet("/Teacher/GenerateCSVServlet")
public class GenerateCSVServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     * @param none
     */
    public GenerateCSVServlet()
    {
    	// Object created by form action call. Nothing to be done here. 
    }

    /**
	 * To not be used for these purposes. Unsafe.
	 * Sends error stating to use POST method.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
    	ScoringDBInteract dbInteract; // For retrieving labs
		List<String> labs = null; // labs sent
		
		// Instantiate dbInteract and retrieve labs
    	try 
    	{
			dbInteract = new ScoringDBInteract();
			labs = dbInteract.getLabNames();
		} 
    	catch (SQLException e) 
    	{
			response.getWriter().append("Error communicating with database. Please try again later.");
			e.printStackTrace();
		}
    	
    	// Send to jsp webpage
		request.setAttribute("labs", labs);
		request.getRequestDispatcher("GenerateCSV.jsp").forward(request, response);
	}

	/**
	 * Receives input of what CSV file to generate. Sends CSV file for downloading to teacher.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
    	// For retrieving data
		ScoringDBInteract dbInteract;
		Map<String, String> filters = new HashMap<String,String>();
		
		// For sending file to teacher
		FileInputStream fileInputStream = null;
		OutputStream responseOutputStream;
		File csvFile = null; // Filename
		
		// Method use
		Map<String, String[]> temp;
		int bytes;
		boolean getsFile = true;
		
		// Create list of filters to be passed
		temp = request.getParameterMap();
		for (String filter : temp.keySet()) // Converts to <String, String>. Only one value.
		{

			if (temp.get(filter) != null && temp.get(filter)[0] != "") // Ensures teacher entered value that is being filtered
			{
				filters.put(filter, temp.get(filter)[0]);
			}
		}
		
		try
		{
			dbInteract = new ScoringDBInteract(); // Instantiate interaction with database
			csvFile = dbInteract.generateCSV(filters); // Generate CSV
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response.getWriter().write("Error communicating with database. Please try again later.");
			getsFile = false;
		}
		
		// Will only continue if file was received
		if (getsFile)
		{
			// Modify response's attributes
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment; filename=" + csvFile.getName());
		
			// Send CSV file to teacher
			responseOutputStream = response.getOutputStream();
			fileInputStream = new FileInputStream(csvFile);
			while ((bytes = fileInputStream.read()) != -1) 
				responseOutputStream.write(bytes);
			
			fileInputStream.close();
			responseOutputStream.close();
		}
		

	}
}
