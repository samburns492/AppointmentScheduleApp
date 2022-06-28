package schedulingapp.DAO;

import schedulingapp.utilities.EstablishConnection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedulingapp.model.Appointment;

/**
 * Creates the appointment dao implementation class. Handles all appointment class
 * related MySQL interactions. 
 * @author Sam
 */
public class AppointmentDaoImpl {
    
    /**
     * Method takes a string and searches for an appointment by the title variable associated 
     * with the appointment class and which is a field in the MySQL database. 
     * @param title
     * @return returns a appointment object if it exits in the database otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static Appointment getAppointment(String title) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM appointments, customers, contacts WHERE Title = '" + title + "'AND appointments.Customer_ID = customers.Customer_ID AND appointments.Contact_ID = contacts.Contact_ID ";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        
            while(result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                String titleres = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp start = result.getTimestamp("Start");
                Timestamp end = result.getTimestamp("End");
                Timestamp createDate  = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                String custName = result.getString("Customer_Name");
                String contName = result.getString("Contact_Name");
                
                String startstr = start.toString();
                String endstr = end.toString();
                String createstr = createDate.toString();
                String updatestr = lastUpdate.toString();
                
                LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
                
                Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                return appointmentResult;
            }
            return null;
    } 
    
    /**
     * Method gets all appointments corresponding to a specific customer id number.
     * @param cusId integer corresponding to customer Id. 
     * @return returns an observablelist with appointment class objects.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Appointment> getAllAppointmentsByCusId(int cusId) throws SQLException, Exception {
        
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStatement= "SELECT * FROM appointments, customers, contacts WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.Contact_ID = contacts.Contact_ID AND appointments.Customer_ID = '" 
                + cusId + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()) {
            
                int appointmentID = result.getInt("Appointment_ID");
                String titleres = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp startdatet = result.getTimestamp("Start");
                Timestamp enddatet = result.getTimestamp("End");
                Timestamp createDate  = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                String custName = result.getString("Customer_Name");
                String contName = result.getString("Contact_Name");
                
                String startstr = startdatet.toString();
                String endstr = enddatet.toString();
                String createstr = createDate.toString();
                String updatestr = lastUpdate.toString();
                
                LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
             
                Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                allAppointments.add(appointmentResult); 
        }
        return allAppointments;
    }
    
    /**
     * Method gets all appointments corresponding to a specific customer id number.
     * @param userId integer corresponding to a user Id. 
     * @return returns an observablelist with appointment class objects.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Appointment> getAllAppointmentsByUserId(int userId) throws SQLException, Exception {
        
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStatement= "SELECT * FROM appointments, customers, contacts WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.Contact_ID = contacts.Contact_ID AND appointments.User_ID = '" 
                + userId + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()) {
            
                int appointmentID = result.getInt("Appointment_ID");
                String titleres = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp startdatet = result.getTimestamp("Start");
                Timestamp enddatet = result.getTimestamp("End");
                Timestamp createDate  = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                String custName = result.getString("Customer_Name");
                String contName = result.getString("Contact_Name");
                
                String startstr = startdatet.toString();
                String endstr = enddatet.toString();
                String createstr = createDate.toString();
                String updatestr = lastUpdate.toString();
                
                LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
             
                Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                allAppointments.add(appointmentResult); 
        }
        return allAppointments;
    }
    
    /**
     * Method gets all appointments found in the appointments MySQL table in the database.
     * @return returns an observablelist with appointment class objects.
     * @throws SQLException
     * @throws Exception 
     */
    
    
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
        
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStatement= "SELECT * FROM appointments, customers, contacts, users " + 
                "WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.Contact_ID = contacts.Contact_ID AND appointments.User_ID = users.User_ID";          
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()){
            
                int appointmentID = result.getInt("Appointment_ID");
                String titleres = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp startdatet = result.getTimestamp("Start");
                Timestamp enddatet = result.getTimestamp("End");
                Timestamp createDate  = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                String custName = result.getString("Customer_Name");
                String contName = result.getString("Contact_Name");
                
                String startstr = startdatet.toString();
                String endstr = enddatet.toString();
                String createstr = createDate.toString();
                String updatestr = lastUpdate.toString();
                
                LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
             
                Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                allAppointments.add(appointmentResult);  
         
            }
        
        return allAppointments;
    }
    
    /**
     * Method gets all appointments found in the appointments MySQL table in the database
     * which are plus 1 month from the current time of user. Users localdatetime now() function
     * and conversion to a timestamp
     * @return returns an observablelist with appointment class objects.
     * @throws SQLException
     * @throws Exception 
     */
    
   public static ObservableList<Appointment> getAllAppointmentsByMonth() throws SQLException, Exception{
        
        ObservableList<Appointment> allAppointmentsMonth = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        
        LocalDateTime withoutTz2 = now.plusMonths(1);
        
        Timestamp timestamp = Timestamp.valueOf(now);
        Timestamp timestamp2 = Timestamp.valueOf(withoutTz2);
        
        String sqlStatement= "SELECT * FROM appointments, customers, contacts, users " + 
                "WHERE appointments.Customer_ID = customers.Customer_ID "
                + "AND appointments.Contact_ID = contacts.Contact_ID "
                + "AND appointments.User_ID = users.User_ID";
                
                                                                      
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()){
                Timestamp startdatet = result.getTimestamp("Start");
                
                if (startdatet.before(timestamp2) && startdatet.after(timestamp)) {
                    
                    int appointmentID = result.getInt("Appointment_ID");
                    String titleres = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    Timestamp enddatet = result.getTimestamp("End");
                    Timestamp createDate  = result.getTimestamp("Create_Date");
                    String createdBy = result.getString("Created_By");
                    Timestamp lastUpdate = result.getTimestamp("Last_Update");
                    String lastUpdateby = result.getString("Last_Updated_By");
                    int customerID = result.getInt("Customer_ID");
                    int userID = result.getInt("User_ID");
                    int contactID = result.getInt("Contact_ID");
                    String custName = result.getString("Customer_Name");
                    String contName = result.getString("Contact_Name");
                    
                    String startstr = startdatet.toString();
                    String endstr = enddatet.toString();
                    String createstr = createDate.toString();
                    String updatestr = lastUpdate.toString();
                
                    LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                    LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                    LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                    LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
             
                    Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                    allAppointmentsMonth.add(appointmentResult);
                }
         
            }
        
        return allAppointmentsMonth;
    }
   
    /**
     * Method gets all appointments found in the appointments MySQL table in the database
     * which are plus 1 week from the current time of user. Users localdatetime now() function
     * and conversion to a timestamp
     * @return returns an observablelist with appointment class objects.
     * @throws SQLException
     * @throws Exception 
     */
    
   
    public static ObservableList<Appointment> getAllAppointmentsByWeek() throws SQLException, Exception{
        
        ObservableList<Appointment> allAppointmentsWeek = FXCollections.observableArrayList();
        
        LocalDateTime now = LocalDateTime.now();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        
        LocalDateTime withoutTz2 = now.plusWeeks(1);
        
        Timestamp timestamp = Timestamp.valueOf(now);
        Timestamp timestamp2 = Timestamp.valueOf(withoutTz2);
        
        System.out.println(now);
        
        String sqlStatement= "SELECT * FROM appointments, customers, contacts, users " + 
                "WHERE appointments.Customer_ID = customers.Customer_ID "
                + "AND appointments.Contact_ID = contacts.Contact_ID "
                + "AND appointments.User_ID = users.User_ID";
                
                                                                      
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()){
                Timestamp startdatet = result.getTimestamp("Start");
                
                if (startdatet.before(timestamp2) && startdatet.after(timestamp)) {
                    
                    int appointmentID = result.getInt("Appointment_ID");
                    String titleres = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    Timestamp enddatet = result.getTimestamp("End");
                    Timestamp createDate  = result.getTimestamp("Create_Date");
                    String createdBy = result.getString("Created_By");
                    Timestamp lastUpdate = result.getTimestamp("Last_Update");
                    String lastUpdateby = result.getString("Last_Updated_By");
                    int customerID = result.getInt("Customer_ID");
                    int userID = result.getInt("User_ID");
                    int contactID = result.getInt("Contact_ID");
                    String custName = result.getString("Customer_Name");
                    String contName = result.getString("Contact_Name");
                    
                    String startstr = startdatet.toString();
                    String endstr = enddatet.toString();
                    String createstr = createDate.toString();
                    String updatestr = lastUpdate.toString();
                
                    LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                    LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                    LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                    LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
                
                    Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                    allAppointmentsWeek.add(appointmentResult);
                }
         
            }
        
        return allAppointmentsWeek;
    }
    
    /**
     * Method gets all appointments corresponding to a specific contact id number.
     * @param contactid integer corresponding to a contact Id. 
     * @return returns an observablelist with appointment class objects.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Appointment> getAllAppointmentsByContact(int contactid) throws SQLException, Exception {
        
        ObservableList<Appointment> allAppointmentsContact = FXCollections.observableArrayList();
        
        String sqlStatement = "SELECT * FROM appointments, customers, contacts WHERE appointments.Contact_ID = '" + contactid + "'AND appointments.Customer_ID = customers.Customer_ID AND appointments.Contact_ID = contacts.Contact_ID ";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        
            while(result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                String titleres = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp start = result.getTimestamp("Start");
                Timestamp end = result.getTimestamp("End");
                Timestamp createDate  = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                String custName = result.getString("Customer_Name");
                String contName = result.getString("Contact_Name");
                
                String startstr = start.toString();
                String endstr = end.toString();
                String createstr = createDate.toString();
                String updatestr = lastUpdate.toString();
                
                LocalDateTime startDateCalendar = LocalDateTime.parse(startstr, dateFormat);
                LocalDateTime endDateCalendar = LocalDateTime.parse(endstr, dateFormat);
                LocalDateTime createDateCalendar = LocalDateTime.parse(createstr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(updatestr, dateFormat);
                
                Appointment appointmentResult = new Appointment(appointmentID, titleres, description, location, type, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, customerID, userID, contactID, custName, contName);
                allAppointmentsContact.add(appointmentResult);
            }
            return allAppointmentsContact;
    } 
    
    /**
     * Method creates a new appointment object and then inserts a new appointment entity
     * into the MySQL table. Doesn't take a appointment id number because one is automatically 
     * generated when inserted into the appointments table. 
     * @param title string for the title.
     * @param description string for the description.
     * @param location string for the location.
     * @param type string for the type.
     * @param startDate timestamp for start date.
     * @param endDate timestamp for end date
     * @param createBy string for created by.
     * @param lastUpdateBy timestamp for last update.
     * @param custID integer for customer id.
     * @param userID integer for user id.
     * @param contID integer for contact id.
     */
    
    public static void createAppointment(String title, String description, String location, String type, Timestamp startDate, Timestamp endDate, String createBy, String lastUpdateBy, int custID, int userID, int contID)  {
        try {
            String sqlstmt = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlstmt, Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startDate);
            ps.setTimestamp(6, endDate);
           
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime ldtZd = now.atZone(localZoneId);
            LocalDateTime withoutTz = ldtZd.toLocalDateTime();
            Timestamp timestamp = Timestamp.valueOf(withoutTz);
            
            ps.setTimestamp(7, timestamp);
            ps.setString(8, createBy);
            ps.setTimestamp(9, timestamp);
            ps.setString(10, lastUpdateBy);
            
            
            
            ps.setInt(11, custID);
            ps.setInt(12, userID);
            ps.setInt(13, contID);
            
            ps.execute();
            
            
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            
        }
        catch(SQLException err) {
            err.printStackTrace();
        }
    }
    
    
    /**
     * Method to modify a current appointment entity in the MySQL database. 
     * @param id integer for the appointment id to be modified.
     * @param title string for the title.
     * @param description string for the description.
     * @param location string for the location.
     * @param type string for the type.
     * @param startdate timestamp for start date.
     * @param enddate timestamp for end date.
     * @param custid integer for customer id.
     * @param userid integer for user id.
     * @param contid integer for contact id.
     */
    public static void modifyAppointment(int id, String title, String description, String location, String type, Timestamp startdate, Timestamp enddate, int custid, int userid, int contid) {
        try {
            String sqlstatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = '" + id + "'";
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlstatement, Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startdate);
            ps.setTimestamp(6, enddate);
           
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime ldtZd = now.atZone(localZoneId);
            LocalDateTime withoutTz = ldtZd.toLocalDateTime();
            Timestamp timestamp = Timestamp.valueOf(withoutTz);
            
            ps.setTimestamp(7, timestamp);
            ps.setInt(8, custid);
            ps.setInt(9, userid);
            
            ps.setInt(10, contid);
            ps.execute();
        
        }
        catch(SQLException err) {
            err.printStackTrace();
        }
    }
    
    /**
     * Method to delete an appointment entity in the MySQL database appointments 
     * table. 
     * @param id integer corresponding to a unique appointment. 
     */
    public static void deleteAppointment(int id) {
        try {
            
            String sqlstatement = "DELETE FROM appointments WHERE Appointment_ID ='" + id + "'";
            
            PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlstatement);
            
            ps.execute();
            
        }
        catch(SQLException err) {
            err.printStackTrace();
        }
    }
}
