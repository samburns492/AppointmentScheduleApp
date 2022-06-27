# AppointmentScheduleApp
Java based application that is an appointment calendar.

Purpose: The application is built to provide a means of scheduling appointments 
via a desktop application for employees of a global consulting organization.
The application allows users to look up relevant appointment and customer information
which is stored in the company MySQL database. The application also 
allows users to add, modify and delete for both appointments and customers in the database.
It also generates reports for each contact in the organization including the number
of appointments by type per month (first report), appointment schedule for apppointments
associated with the selected contact and finally the next upcoming appointment 
for the contact.

Author: Sam Burns 
Contact Info: samburns492@gmail.com	 
Application version: 1.0
Date: August 03, 2021

IDE version: Apache NetBeans IDE 11.3
JDK: Java(TM) SE Runtime Environment 11.0.10
JavaFX: JavaFX sdk 11.0.02

Directions to run the program:
The user needs to launch the application and then login using the correct
user name and password. The  With a successful login the user will see a popup
detailing whether the current user has any appointments that start within +/-
15 minutes of the user login. The user will then be taken to the main form
which has all the appointments and customer data populated in two table views.
Each table has a listener which will auto-populate the text boxes, combo boxes and
date pickers which correspond to either the appointment or customer data. 
The same data fields can be used to modify or create a new instance of either 
the appointment or customer data. It can also be used to select the instance to be deleted.
The appointment table view can be filtered by all appointments or appointments occurring 
within a month or a week of the current user's time. The main form also
has contact report button which when pressed launches a new pane. In the report 
pane the user can select a contact in the combo box and gets to three separate reports.

The first report gives the count of each appointment of a specified type by the month.
The second report gives all appointments which are associated with the selected 
contact. The third report is a text area which gives the time stamp of the next 
upcoming appoint for the selected contact. This is the additional report for part A3f.

MySQL Connector Driver Version: MySQL Connector Java 8.0.22 
