<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Generate CSV</title>
</head>
<body>
	<h1>Generate CSV Files of Scores</h1>
	
	<form action="GenerateCSVServlet" method="post">
		<p>Leave blank what you would not like to filter by.</p>
		
		<input type="text" name="name" placeholder="type student name" />
		<br />
		<input type="text" name="id" placeholder="type school id" />
		<br />
		
		<select name="period">
			<option></option>
			<option value="1">Period 1</option>
			<option value="2">Period 2</option>
			<option value="3">Period 3</option>
			<option value="4">Period 4</option>
			<option value="5">Period 5</option>
		</select>
		<br />
		
		<button>Get Scores</button>
	</form>
</body>
</html>