package schedulingapp.model;
import java.sql.Timestamp;

/**
 * Initializes the country class object.
 * @author Sam
 */
public class Country {
    
    private int countryID;
    private String countryname; 
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /** 
     * Creates a country object.
     * @param countryID integer for country id#
     * @param countryname string for country name
     * @param createDate timestamp for create date
     * @param createdBy string for created by
     * @param lastUpdate timestamp for last update
     * @param lastUpdateBy string for last update by
     */
    public Country(int countryID, String countryname, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        
        this.countryID = countryID;
        this.countryname = countryname;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        
    }
    
    /**
     * @return the IdNumber
     */
    public int getIdNumber() {
        return countryID;
    }
    
    /**
     * @param countryID the userId to set
     */
    public void setIdNumber(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return the countryname
     */
    public String getCountryname() {
        return countryname;
    }

    /**
     * @param countryname the countryname to set
     */
    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    
    /**
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastUpdate
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @param lastUpdateBy the lastUpdateBy to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
