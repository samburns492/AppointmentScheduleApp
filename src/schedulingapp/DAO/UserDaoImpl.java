package schedulingapp.DAO;
import schedulingapp.utilities.EstablishConnection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedulingapp.model.User;


/** 
 * Initializes the user dao implementation class. This class handles all communication 
 * between the user object and the associated entities in the users MySQL table. 
 * @author Sam
 */
public class UserDaoImpl {
    
    /**
     * Finds and returns a user object if found in the database based on the username string.
     * @param usern string corresponding to user name.
     * @return returns a single user object or null if not found.
     * @throws SQLException
     * @throws Exception 
     */
    
     public static User getUser(String usern) throws SQLException, Exception {
        
        String sqlStatement="SELECT * FROM users WHERE User_Name  = '" + usern + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        User userResult;
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
           
           while(result.next()){
                int idNum = result.getInt("User_ID");
                String usern1 = result.getString("User_Name");
                String password = result.getString("Password");
                
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                
                String createDateStr = createDate.toString();
                String lastUpdateStr = lastUpdate.toString();
                
                LocalDateTime createDateCalendar = LocalDateTime.parse(createDateStr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(lastUpdateStr, dateFormat);
            
                userResult= new User(idNum, usern1, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return userResult;
           }
        return null;
    }
    
    /**
    * Finds and returns a user object if found in the database based on the user id integer.
    * @param usern integer corresponding to user id#.
    * @return returns a single user object or null if not found.
    * @throws SQLException
    * @throws Exception 
    */
     
    public static User getUserById(int userid) throws SQLException, Exception {
        
        String sqlStatement="SELECT * FROM users WHERE User_ID = '" + userid + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        User userResult;
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
            while(result.next()) {
                
                int idNum = result.getInt("User_ID");
                String usern1 = result.getString("User_Name");
                String password = result.getString("Password");
                
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdateby = result.getString("Last_Updated_By");
                
                String createDateStr = createDate.toString();
                String lastUpdateStr = lastUpdate.toString();
                
                LocalDateTime createDateCalendar = LocalDateTime.parse(createDateStr, dateFormat);
                LocalDateTime lastUpdateCalendar = LocalDateTime.parse(lastUpdateStr, dateFormat);
            
                userResult= new User(idNum, usern1, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return userResult;
                
            }
        return null;
    }
    
    /**
     * This method returns all user objects associated with all user entities in the database table 'users'.
     * @return returns an observablelist with user objects.
     * @throws SQLException
     * @throws Exception 
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers = FXCollections.observableArrayList();    
        String sqlStatement="SELECT * FROM users";          
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(localZoneId);
        
        while(result.next()){
            
            int idNum = result.getInt("User_ID");
            String usern1 = result.getString("User_Name");
            String password = result.getString("Password");
            Timestamp createDate  = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdateby = result.getString("Last_Updated_By");
            
            String createDateStr = createDate.toString();
            String lastUpdateStr = lastUpdate.toString();
                
            LocalDateTime createDateCalendar = LocalDateTime.parse(createDateStr, dateFormat);
            LocalDateTime lastUpdateCalendar = LocalDateTime.parse(lastUpdateStr, dateFormat);
       
            User userResult = new User(idNum, usern1, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allUsers.add(userResult);
                
            }
            
        return allUsers;
    }
}
