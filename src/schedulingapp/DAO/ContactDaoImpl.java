/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingapp.DAO;

import schedulingapp.utilities.EstablishConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedulingapp.model.Contact;


/**
 * Implements the contact dao implementation class. This class handles all contact
 * object interactions with the MySQL contact table and associated entities. 
 * @author Sam
 */
public class ContactDaoImpl {
    
    public static Contact getContact(int Contact_ID) throws SQLException, Exception{
        String sqlStatement="SELECT * FROM contacts WHERE Contact_ID  = '" + Contact_ID + "'";
        
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        Contact contactResult;
        ResultSet result = ps.executeQuery();
        
           while(result.next()){
                int contactid = result.getInt("Contact_ID");
                String contactname = result.getString("Contact_Name");
                String email = result.getString("Email");
                
                contactResult = new Contact(contactid, contactname, email);
                return contactResult;
           }
        return null;
    }
    
    /**
     * Method takes a string name corresponding to a contact and retrieves all 
     * contact information for that contact. 
     * @param contName
     * @return returns a contact object if found in the database otherwise it returns
     * null.
     * @throws SQLException
     * @throws Exception 
     */
    public static Contact getContactByName(String contName) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM contacts WHERE Contact_Name = '" + contName + "'";
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        while(result.next()) {
            int contactID = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String email = result.getString("Email");
            
            Contact contactResult = new Contact(contactID, contactName, email);
            return contactResult;
        }
        
        return null;
    }
    
    /**
     * Method finds all contacts currently in the MySQL database contacts table.
     * @return an observablelist with contact objects. 
     * @throws SQLException
     * @throws Exception 
     */
    
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{
        
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();    
        
        String sqlStatement="SELECT * FROM contacts";         
        PreparedStatement ps = EstablishConnection.getConnect().prepareStatement(sqlStatement);
        ResultSet result = ps.executeQuery();
        
        while(result.next()){
            
            int contactid = result.getInt("Contact_ID");
            String contactname = result.getString("Contact_Name");
            String email = result.getString("Email");
                
            
            Contact contactResult = new Contact(contactid, contactname, email);
            allContacts.add(contactResult);
                
            }
            
        return allContacts;
    }    
}
