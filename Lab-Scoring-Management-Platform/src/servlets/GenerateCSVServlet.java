package servlets;



import scoringmanagement.*; // For csvs // TODO Use specific classes
// For reading and sending files
import java.io.FileInputStream;
import java.io.OutputStream;

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

/**
 * Servlet implementation class GenerateCSVServlet
 */
@WebServlet("/Teacher/GenerateCSVServlet")
public class GenerateCSVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     * @param none
     */
    public GenerateCSVServlet() {
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
	 * Receives input of what CSV file to generate. Sends CSV file for downloading to teacher.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// For retrieving data
		ScoringDBInteract scoringData = new ScoringDBInteract();
		List<String> filters = new ArrayList<String>(3);
		Enumeration<String> names = request.getParameterNames();
		String filter;
		
		// For sending file to teacher
		FileInputStream fileInputStream = null;
		OutputStream responseOutputStream = response.getOutputStream();
		File csvFile = null; // Filename
		int bytes;
		
		// Create list of filters to be passed
		while (names.hasMoreElements())
		{
			filter = request.getParameter(names.nextElement());
			filters.add(filter);
		}
		
		csvFile = scoringData.generateCSV(filters); // Generate CSV
		
		// Send CSV file to teacher
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}
	}

}
