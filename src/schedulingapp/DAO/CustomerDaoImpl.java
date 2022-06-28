package schedulingapp.DAO;
import schedulingapp.utilities.EstablishConnection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedulingapp.model.Customer;

/**
 * Initializes the customer dao implementation class. Handles all interactions between
 * the customer data object and associated entities in the MySQL customers table.
 * @author Sam
 */
public class CustomerDaoImpl {
    
    
    /**
     * This method takes a integer customer id # and then returns a customer object if
     * the id# is found in the database.
     * @param custID integer corresponding to the customer id#.
     * @return returns a customer object if found otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    public static Customer getCustomer(int custID) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM customers, first_level_divisions, countries WHERE Customer_ID = '" + custID 
                + "' AND customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID = countries.Country_ID"; ;
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
            while(result.next()) {
                int customerId = result.getInt("Customer_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateBy = result.getString("Last_Updated_By");
                int divID = result.getInt("Division_ID");
                String divName = result.getString("Division");
                String country = result.getString("Country");
                
                String createDateStr = createDate.toString();
                String lastUpdateStr = lastUpdate.toString();
                
                LocalDateTime createDateTime = LocalDateTime.parse(createDateStr, dateFormat);
                LocalDateTime updateDateTime = LocalDateTime.parse(lastUpdateStr, dateFormat);
                
                Customer custResult = new Customer(customerId, customerName, address, postalCode, phone, createDateTime, createdBy, updateDateTime, lastUpdateBy, divID, divName, country);
                return custResult;
            }
            return null;
    }
    
    /**
     * This method takes a string customer name and then returns a customer object if
     * the name is found in the database.
     * @param custName string corresponding to the customer name in the database.
     * @return returns a customer object if found otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static Customer getCustomerByName(String custName) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM customers, first_level_divisions, countries WHERE Customer_Name = '" + custName 
                + "' AND customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID = countries.Country_ID"; ;
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
            while(result.next()) {
                int customerId = result.getInt("Customer_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateBy = result.getString("Last_Updated_By");
                int divID = result.getInt("Division_ID");
                String divName = result.getString("Division");
                String country = result.getString("Country");
                
                String createDateStr = createDate.toString();
                String lastUpdateStr = lastUpdate.toString();
                
                LocalDateTime createDateTime = LocalDateTime.parse(createDateStr, dateFormat);
                LocalDateTime updateDateTime = LocalDateTime.parse(lastUpdateStr, dateFormat);
                
                Customer custResult = new Customer(customerId, customerName, address, postalCode, phone, createDateTime, createdBy, updateDateTime, lastUpdateBy, divID, divName, country);
                return custResult;
            }
            return null;
    }
    
    /**
     * This method retrieves all customer object found in the MySQL database.
     * @return returns an observable array list with customers objects. 
     * @throws SQLException
     * @throws Exception 
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception {
        
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement= "SELECT * FROM customers, first_level_divisions, countries " + 
                "WHERE  customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID = countries.Country_ID ";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()) {
            int customerId = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdateBy = result.getString("Last_Updated_By");
            int divID = result.getInt("Division_ID");
            String divName = result.getString("Division");
            String country = result.getString("Country");
                
            String createDateStr = createDate.toString();
            String lastUpdateStr = lastUpdate.toString();
                
            LocalDateTime createDateTime = LocalDateTime.parse(createDateStr, dateFormat);
            LocalDateTime updateDateTime = LocalDateTime.parse(lastUpdateStr, dateFormat);
                
            Customer custResult = new Customer(customerId, customerName, address, postalCode, phone, createDateTime, createdBy, updateDateTime, lastUpdateBy, divID, divName, country);
            allCustomers.add(custResult);
            
        }
        return allCustomers;
    }
    
    
    
    /**
     * Creates a new customer entity in the database. Does not take a customer id#
     * because one is generated automatically with insertion in the MySQL customers table.
     * @param customerName string corresponding to customer name.
     * @param address string for the customer address.
     * @param postCode string with the postal code for customer.
     * @param phone string with the phone # of the customer.
     * @param createDate Timestamp for the customer entity creation date.
     * @param createBy string for the created by field.
     * @param lastUpdate Timestamp for the last update of the customer entity.
     * @param lastUpdateBy string for the last updated by.
     * @param divID integer for the division id.
     * @param divName integer for the name of the division.
     * @param country string for country name.
     * @return returns the customer id # if insert is successful otherwise 0. 
     */
    public static int createCustomer(String customerName, String address, String postCode, String phone, Timestamp createDate, String createBy, Timestamp lastUpdate, String lastUpdateBy, int divID, String divName, String country)  {
        try {
            String sqlstmt = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlstmt, Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, createDate);
            ps.setString(6, createBy);
            ps.setTimestamp(7, lastUpdate);
            ps.setString(8, lastUpdateBy);
            ps.setInt(9, divID);
            
            ps.execute();
            
            
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int custID = rs.getInt(1);
            return custID;
        }
        catch(SQLException err) {
            err.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Modifies a current customer entity in the database. Uses a customer id# integer
     * to look up and then update the correct row/entity in the MySQL customers table.
     * @param name string corresponding to customer name.
     * @param address string for the customer address.
     * @param postalcode string with the postal code for customer.
     * @param phone string with the phone # of the customer.
     * @param createBy string for the created by field.
     * @param lastUpdate Timestamp for the last update of the customer entity.
     * @param lastupdateby string for the last updated by.
     * @param divisionid integer for the division id. 
     */
    public static void modifyCustomer(int id, String name, String address, String postalcode, String phone, String createBy, Timestamp lastUpdate, String lastupdateby, int divisionid) {
        
        try {
            String sqlstatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = '" + id + "'";
            
            PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlstatement);
            
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalcode);
            ps.setString(4, phone);
            ps.setString(5, createBy);
            ps.setTimestamp(6, lastUpdate);
            ps.setString(7, lastupdateby);
            ps.setInt(8, divisionid);
            ps.execute();
        
        }
        catch(SQLException err) {
            err.printStackTrace();
        }
    }
    
    
    /** 
     * This method takes a customer id and if found in the database is deleted from the MySQL database.
     * @param custid integer corresponding to the customer id#.
     */
    
    public static void deleteCustomer(int custid) {
        
        try {
            
            String sqlstatement2 = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps2 = EstablishConnection.getConnect().prepareStatement(sqlstatement2);
            ps2.setInt(1, custid);
            ps2.execute();
            
        } 
        catch (SQLException err) {
            err.printStackTrace();
        }
    
    }
}
