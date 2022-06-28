package schedulingapp.utilities;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * This initializes the query class. This class handles the passing of sql prepared statements between the
 * program and the MySQL database.
 * @author Sam
 */
public class Query {
    private static String query;
    private static PreparedStatement statement;
    private static ResultSet result;
    
    /**
     * This method returns the result set (i.e. data returned from MySQL.)
     * @return returns a ResultSet object called result.
     */
    public static ResultSet obtainRlt() {
        return result;
    }
    
    /**
     * This method takes the connection object and sql statement string and sets the prepared statement..
     * @param conn connection object 
     * @param sqlstmt string with the sql statement to be queried.
     * @throws SQLException 
     */
    
    public static void setPreparedStatement(Connection conn, String sqlstmt) throws SQLException
    {
        statement = conn.prepareStatement(sqlstmt);
        
    }
    
    /**
     * This method returns the prepared statement set above.
     * @return returns the prepared statement object.
     */
    public static PreparedStatement getPreparedStatement()
    {
        return statement;
    }
}
