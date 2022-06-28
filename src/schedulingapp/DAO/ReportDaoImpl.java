package schedulingapp.DAO;
import schedulingapp.utilities.EstablishConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedulingapp.model.Report;

/**
 * Initializes the report dao implementation object which handles all communication 
 * between the report object and associated data in the MySQL "appointments" table.
 * Essentially this is a less complex object version of the appoinment object.
 * @author Sam
 */
public class ReportDaoImpl {
    
    /**
     * This method returns all report objects found in the MySQL table appointments.
     * @return returns an observable array list with report objects. 
     * @throws SQLException
     * @throws Exception 
     */
    public static ObservableList<Report> getAllReports() throws SQLException, Exception {
        
        ObservableList<Report> allReports = FXCollections.observableArrayList();
        String sqlStatement= "SELECT *, month(Start) AS start_month FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID";
        
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        while(result.next()) {
            
            int month = result.getInt("start_month");
            int contactId = result.getInt("Contact_ID");
            String type = result.getString("Type");
            int count = 0;
            
            Report reportResult = new Report(contactId, month, type, count);
            allReports.add(reportResult);
        }
        return allReports;
    } 
    
    /**
     * This method takes a month, contact id and string for type and returns integer count
     * of the number of appointments which have the same month, contact id and type. 
     * Query uses information from both the appointments and objects table.
     * @param month integer corresponding to a month 'January = 1'.
     * @param contact integer corresponding to contact ID.
     * @param typestr string for the appointment type.
     * @return returns a integer which is a count of the number of appointments
     * @throws SQLException
     * @throws Exception 
     */
    
    public static int getAllReportsByMonthContact(int month, int contact, String typestr) throws SQLException, Exception {
        
        ObservableList<Report> allReports = FXCollections.observableArrayList();
        String sqlStatement = "SELECT *, month(Start) AS start_month FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID AND appointments.Contact_ID = '" + contact + "' AND appointments.Type = '" + typestr + "'";
        
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        int counter = 0;
        
        while(result.next()) {
            
            int monthnum = result.getInt("start_month");
            
            if (monthnum == month) {
                counter++;
            }
        }
        
        return counter;
    }
    
}
