<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 
This is the web page where the student enters the information to submit a lab.
Author: Akul Arora
Date: 5/21/2018
 -->

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Lab Submission</title>
</head>

<body>
	<h1>Student Lab Submission</h1>

	<!--
	 Using post method for added security.
	 multipart="multipart/form-data" necessary for handling files
	-->
	<form action="LabSubmissionServlet" method="post"
		enctype="multipart/form-data">

		<input type="text" name="name" placeholder="type full name" required />
		<br />

		<!-- Min/max insures that potentially correct integer value is entered -->
		<input type="text" name="id" placeholder="type school id" min="1"
			max="9999999" required /> <br /> <select name="period" required>
			<option value="1">Period 1</option>
			<option value="2">Period 2</option>
			<option value="3">Period 3</option>
			<option value="4">Period 4</option>
			<option value="5">Period 5</option>
		</select> <br /> <select name="lab" required>
			<!-- Dynamically receives list of labs to be offered as options -->
			<%
			java.util.List<String> labs = (java.util.List)request.getAttribute("labs"); // Gets labs passed from servlet
			for (String lab : labs) // Offers them as options
			{
%>
			<option value="<%=lab%>"><%=lab %></option>
			<%
			}
%>
		<p> Please note that the java file containing main must be named "Driver.java" </p>
		</select> <br /> Files to Submit: <input type="file" name="files"
			multiple="multiple" required /> <br />

		<button>Submit Lab</button>

	</form>

</body>

</html>