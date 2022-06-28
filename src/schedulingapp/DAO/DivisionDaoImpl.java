package schedulingapp.DAO;
import schedulingapp.utilities.EstablishConnection;
import schedulingapp.model.Division;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Initializes the division doa implementation class. Handles all interaction between the
 * division object and the corresponding entities in the MySQL database first_level_divisions
 * table.
 * @author Sam
 */
public class DivisionDaoImpl {
    
    /**
     * This method gets the associated division information based on input of a string of the 
     * division name
     * @param div string corresponding to division name.
     * @return returns a division object if found otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static Division getDivision(String div) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division ='" + div + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
            while(result.next()) {
                int divisionid = result.getInt("Division_ID");
                String divisionname = result.getString("Division");
                int countryid = result.getInt("COUNTRY_ID");
                
                Division divResult = new Division(divisionid, divisionname, countryid);
                return divResult;
            }
        return null;    
    }
      
    /**
     * This method gets the associated division information based on input of a integer 
     * corresponding to the division id.
     * @param div integer corresponding to division int. 
     * @return returns a division object if found otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static Division getDivisionById(int div) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_ID ='" + div + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
            while(result.next()) {
                int divisionid = result.getInt("Division_ID");
                String divisionname = result.getString("Division");
                int countryid = result.getInt("COUNTRY_ID");
                
                Division divResult = new Division(divisionid, divisionname, countryid);
                return divResult;
            }
        return null;    
    }
    
    /**
     * This method returns all divisions objects found in the MySQL database "divisions" table.
     * @return returns an observablelist  with all the found division objects.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Division> getAllDivisions() throws SQLException, Exception {
        
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        String sqlStatement= "SELECT * FROM first_level_divisions";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        while(result.next()) {
            
            int divid = result.getInt("Division_ID");
            String divname = result.getString("Division");
            int countryid = result.getInt("COUNTRY_ID");
            
            Division divResult = new Division(divid, divname, countryid);
            allDivisions.add(divResult);
        }
        return allDivisions;
    }
    
    /**
     * This method gets the associated division information based on input of a country id#
     * corresponding to the country id of the division object .
     * @param countryid integer corresponding to country id int. 
     * @return returns a division object if found otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Division> getAllDivisionsByCountry(int countryid) throws SQLException, Exception {
        
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID ='" + countryid + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
            while(result.next()) {
                int divisionid = result.getInt("Division_ID");
                String divisionname = result.getString("Division");
                int countid = result.getInt("COUNTRY_ID");
                
                Division divResult = new Division(divisionid, divisionname, countid);
                allDivisions.add(divResult);
            }
        return allDivisions;            
    }     
}
