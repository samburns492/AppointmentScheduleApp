package schedulingapp.model;
import java.time.LocalDateTime;

/**
 * This intializes the user class object. Corresponds to the user MySQL table entities.
 * @author Sam
 */
public class User {
    private int IdNumber;
    private String usern;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy; 

    /**
     * This method creates a user object
     * @param IdNumber integer for the user id#
     * @param usern string for the username
     * @param password string for the user password
     * @param createDate localdatetime obj for the creation date
     * @param createdBy string for created by
     * @param lastUpdate localdatetime obj for the last update
     * @param lastUpdateBy string for the last update by
     */
    public User(int IdNumber, String usern, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.IdNumber = IdNumber;
        this.usern = usern;
        this.password = password;
        //this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * @return the IdNumber
     */
    public int getIdNumber() {
        return IdNumber;
    }

    /**
     * @param IdNumber the userId to set
     */
    public void setIdNumber(int IdNumber) {
        this.IdNumber = IdNumber;
    }

    /**
     * @return the userName
     */
    public String getUsern() {
        return usern;
    }

    /**
     * @param userName the userName to set
     */
    public void setUsern(String usern) {
        this.usern = usern;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(LocalDateTime createDate) {
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
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
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
