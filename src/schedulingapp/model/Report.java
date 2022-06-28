package schedulingapp.model;

/**
 * This initializes the report class object. Combination of appointments and contacts table information.
 * @author Sam
 */
public class Report {
    
    private int contactID;
    private int month;
    private String type;
    private int count;
   
    /**
     * This method creates the report object.
     * @param contactID integer for contact id#
     * @param month int for month number (i.e. January = 1.)
     * @param type string for the appointment type
     * @param count integer for count which is not a MySQL field.
     */
    public Report(int contactID, int month, String type, int count) {
        
        this.contactID = contactID;
        this.month = month;
        this.type = type;
        this.count = count;
         
    }
    
    /**
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }
    
    /**
     * @param contactID the contactID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    
    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }
    
    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }
    
    /**
     * @return the type
     */
    
    public String getType() {
        return type;
    }
    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return the count
     */
    
    public int getCount() {
        return count;
    }
    
    /**
     * @param count the type to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
