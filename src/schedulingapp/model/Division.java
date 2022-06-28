/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingapp.model;

/**
 * Initializes the division object class. Corresponds to first_level_divisions table in MySQL
 * @author Sam
 */
public class Division {
    
    private int divisionID;
    private String divisionname;
    private int countryID;
    
    /**
     * this method creates the division object
     * @param divisionID integer for division id#
     * @param divisionname string for first level division name
     * @param countryID integer for country id#
     */
    public Division(int divisionID, String divisionname,int countryID) {
        this.divisionID = divisionID;
        this.divisionname = divisionname;
        this.countryID = countryID;
    }
    
    /**
     * @return divisionID
     */
    
    public int getDivisionID() {
        return divisionID;
    }
    
    /**
     * @param divisionID the divisionID to set
     */
    
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    
    /**
     * @return divisionname
     */  
    
    public String getDivisionName() {
        return divisionname;
    }
    
    /**
     * @param divisionname the divisionname to set
     */ 
    
    public void setDivisionName()  {
        this.divisionname = divisionname;
    }
    
    /**
     * @return countryID
     */
    
    public int getCountryID() {
        return countryID;
    }
    
    /**
     * @param countryID the countryID to set
     */ 
    
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
            
}
