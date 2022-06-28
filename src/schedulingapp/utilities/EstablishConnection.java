package schedulingapp.utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * This initializes the establish connection class. Which launches, grabs and then discards
 * the mysql database connection. All MySQL connection information is declared in strings.
 * @author Sam
 */
public class EstablishConnection {
    
    private static final String prtclDatabase = "jdbc";
    private static final String vendorNmDatabase = ":mysql:";
    private static final String urlDatabase = prtclDatabase + vendorNmDatabase + "//wgudb.ucertify.com/WJ072A4";
    private static final String userDatabase = "U072A4";
    private static final String pwDatabase = "53688935044";
    
    //the driver reference
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    static Connection conn = null;
    
    /**
     * This methods starts the MySQL database connection.
     * @return returns a connection object called "conn"
     */
    
    public static Connection startConnect()
    {
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(urlDatabase,userDatabase,pwDatabase);
            System.out.println("Connection Successful!");
        }
        catch(SQLException err) 
        {
            err.printStackTrace();
        }
        catch(ClassNotFoundException err) 
        {
            err.printStackTrace();
        }   
        
        return conn;      
    }
    
    /**
     * This returns the already open connection object.
     * @return returns connection object "conn"
     */
    
    public static Connection getConnect()
    { 
        return conn;
    }
    
    /**
     * This method closes the current MySQL database connection.
     */
    
    public static void tossConnect()
    {
        try{
            conn.close();
            System.out.println("Connection Closed!");
        }
        catch(SQLException err) 
        {
            err.printStackTrace();
        }
       
    }
            
    
}

