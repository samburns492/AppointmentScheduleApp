package schedulingapp.DAO;
import schedulingapp.utilities.EstablishConnection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import schedulingapp.model.Country;

/**
 * Initializes the country dao implementation class which handles all interactions
 * between the country object and the associated entities in the MySQL country table.
 * @author Sam
 */
public class CountryDaoImpl {
    
    
    /**
     * This method takes a country name string and returns a single country object
     * and associated data from the MySQL table.
     * @param countryname string of the country.
     * @return a single country object if found otherwise null.
     * @throws SQLException
     * @throws Exception 
     */
    
    public static Country getCountry(String countryname) throws SQLException, Exception {
        
        String sqlStatement = "SELECT * FROM countries WHERE Country ='" + countryname + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
            while(result.next()) {
                int countryid = result.getInt("Country_ID");
                String country = result.getString("Country");
                Timestamp creatdate = result.getTimestamp("Create_Date");
                String createby = result.getString("Created_By");
                Timestamp lastupdate = result.getTimestamp("Last_Update");
                String lastupdateby = result.getString("Last_Updated_By");
                
                Country countryResult = new Country(countryid, country, creatdate, createby, lastupdate, lastupdateby);
                return countryResult;
            }      
        return null;        
    }
    
    /**
     * Method returns all the country objects found in the MySQL database table 'countries;
     * @return returns an observablelist witha country objects
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception {
        
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        
        String sqlStatement="SELECT * FROM countries";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        while(result.next()){
            
            int countryid = result.getInt("Country_ID");
            String country = result.getString("Country");
            Timestamp createdate = result.getTimestamp("Create_Date");
            String creatby = result.getString("Created_By");
            Timestamp lastupdate = result.getTimestamp("Last_Update");
            String lastupdateby = result.getString("Last_Updated_By");
            
            Country countryresult = new Country(countryid, country, createdate, creatby, lastupdate, lastupdateby);
            allCountries.add(countryresult);
            
        }
        return allCountries;
    }
}
