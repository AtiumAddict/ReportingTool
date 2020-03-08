# Reporting Tool

Reporting Tool is a simple web application for employee report tracking. 


### Prerequisites - Installation - Testing

In order to run and test this application, you only need Java and a web browser (for testing through Swagger) or the Postman desktop app. The application uses an embedded h2 database.
When the application runs, it will be accessible through http://localhost:8090 and http://localhost:8090/swagger-ui.html for Swagger. 
It can be tested through Swagger or by importing the Reporting-Tool.postman_collection.json file, which can be found inside the root folder of the project

###API

The main entities are Report and Employee. 
There are 3 controllers: Employee Controller, Report Controller, Employee Report Controller:

## Employee

* createEmployee: POST: /employees: Create a new employee with the following body and receive a response with the created employee's details:
	{
	  "title": "string",
	  "firstName": "string",
	  "lastName": "string",
	  "username": "string"
	  "email": "string",
	  "gender": "string",
	  "comments": "string",
	}
* findEmployeeById: GET /employees/{employeeId}: Get the details of the employee with the given employeeId in the following format:
	{
	  "id": "number"
	  "title": "string",
	  "firstName": "string",
	  "lastName": "string",
	  "username": "string"
	  "email": "string",
	  "gender": "string",
	  "comments": "string",
	}
* findEmployeeByUsername: GET /employees: Get the details of the employee with a username as a "username" parameter in the following format:
	{
	  "id": "number"
	  "title": "string",
	  "firstName": "string",
	  "lastName": "string",
	  "username": "string"
	  "email": "string",
	  "gender": "string",
	  "comments": "string",
	}
* editEmployee: PUT /employees/{employeeId}: Edit the details of the employee with the given employeeId using the following body:
	{
	  "title": "string",
	  "firstName": "string",
	  "lastName": "string",
	  "gender": "string",
	  "comments": "string",
	}

## Report

* findReportsByCriteria: GET /reports: Get the a list of paginated report details, fiven the following parameters:
	Report search parameters:
	- username (part of an employee's username),
	- priority ("HIGH", "LOW")
	Pagination parameters:
	- page (starting page index),
	- size (the number of records per page),
	- sort (the field by which the pages will be sorted),
	- pageDirection (sorting direction - asc/desc),	
	
	The report details will have the following format:
	{
      "username": "string"
      "id": "number",
      "title": "string",
      "description": "string",
      "priority": "string",
      "createdOn": "timestamp",
      "editedOn": "timestamp",
    }
* createReport: POST: /reports: Create a new report with the following body and receive a response with the created report's details:
	{
      "employeeId": "number"
      "title": "string",
      "description": "string",
      "priority": "string",
    }
* findReportById: GET /reports/{reportId}: Get the details of the report with the given reportId in the following format:
	{
	  "username": "string"
	  "id": "number",
	  "title": "string",
	  "description": "string",
	  "priority": "string",
	  "createdOn": "2020-03-08T16:04:28.601Z",
	  "editedOn": "2020-03-08T16:04:28.601Z",
	}
* editReport: PUT /reports/{reportId}: Edit the details of the report with the given reportId using the following body:
	{
	  "title": "string"
	  "description": "string",
	  "priority": "string",
	}
* deleteReport: DELETE /reports/{reportId}: Delete the report with the given reportId.

## Employee Report

* createReport: POST: employees/{employeeId}/reports: Create a new report for the employee with the given employeeId with the following body and receive a response with the created report's details:
	{
      "employeeId": "number"
      "title": "string",
      "description": "string",
      "priority": "string",
    }
* findReportById: GET employees/{employeeId}/reports/{reportId}: Get the details of the report with the given reportId (which belongs to the employee with the given employeeId) in the following format:
	{
	  "username": "string"
	  "id": "number",
	  "title": "string",
	  "description": "string",
	  "priority": "string",
	  "createdOn": "2020-03-08T16:04:28.601Z",
	  "editedOn": "2020-03-08T16:04:28.601Z",
	}
* editReport: PUT employees/{employeeId}/reports/{reportId}: Edit the details of the report with the given reportId (which belongs to the employee with the given employeeId) using the following body:
	{
	  "title": "string"
	  "description": "string",
	  "priority": "string",
	}
* deleteReport: DELETE employees/{employeeId}/reports/{reportId}: Delete the report with the given reportId (which belongs to the employee with the given employeeId).

## Acknowledgments

The code and architecture of this project would have been a lot worse if I couldn't rely on an exemplary Spring web application designed by my ex-team leader, Chris Lytsikas.

