<!-- 
This is the main page of the web server where the student enters their information to submit a lab.

Author: Akul Arora
Date: 5/21/2018
 -->


<!-- Default -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Lantsberger Scoring Engine</title>
</head>

<body>
	<form action="Submission" method="post">
	
		<input type="text" name="name" placeholder="type full name" />
		<br />
		
		<input type="text" name="id" placeholder="type school id" />
		<br />
		
		<select name="lab"> <!-- TODO: This needs to be replaced with list of labs from servlet. -->
			<option value="Lab1">Lab1</option>
			<option value="Lab2">Lab2</option>
			<option value="Lab3">Lab3</option>
		
		</select>
		<br />
		
		Files to Submit: <input type="file"  name="fileName" multiple="multiple"/>
		<br />
		
		<input type="submit" value="Submit Lab" />
		
	</form>

</body>

</html>