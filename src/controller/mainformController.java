package controller;
import java.io.IOException;
import static java.lang.Math.abs;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import schedulingapp.DAO.AppointmentDaoImpl;
import static schedulingapp.DAO.AppointmentDaoImpl.createAppointment;
import static schedulingapp.DAO.AppointmentDaoImpl.deleteAppointment;
import static schedulingapp.DAO.AppointmentDaoImpl.modifyAppointment;
import schedulingapp.DAO.ContactDaoImpl;
import static schedulingapp.DAO.ContactDaoImpl.getContactByName;
import schedulingapp.DAO.CountryDaoImpl;
import static schedulingapp.DAO.CountryDaoImpl.getCountry;
import static schedulingapp.DAO.CustomerDaoImpl.createCustomer;
import schedulingapp.DAO.CustomerDaoImpl;
import schedulingapp.DAO.UserDaoImpl;
import static schedulingapp.DAO.CustomerDaoImpl.getCustomer;
import static schedulingapp.DAO.CustomerDaoImpl.getCustomerByName;
import static schedulingapp.DAO.CustomerDaoImpl.modifyCustomer;
import static schedulingapp.DAO.AppointmentDaoImpl.getAllAppointmentsByCusId;
import static schedulingapp.DAO.CustomerDaoImpl.deleteCustomer;
import schedulingapp.DAO.DivisionDaoImpl;
import static schedulingapp.DAO.DivisionDaoImpl.getDivision;
import static schedulingapp.DAO.UserDaoImpl.getUser;
import static schedulingapp.DAO.UserDaoImpl.getUserById;

import schedulingapp.model.Appointment;
import schedulingapp.model.Contact;
import schedulingapp.model.Country;
import schedulingapp.model.Customer;
import schedulingapp.model.Division;
import schedulingapp.model.User;

/**
 * Creates a controller class for the main form of the Scheduling application. This controller
 * handles most of the data that is displayed, updated and deleted in the program.
 * @author Sam
 */
public class mainformController implements Initializable {
   
    public TableView<Appointment> appointTable;
    public TableView<Customer> customerTable;
    public TableColumn apptTableId;
    public TableColumn apptTableTitle;
    public TableColumn apptTableDesc;
    public TableColumn apptTableLoc;
    public TableColumn apptTableCon;
    public TableColumn apptTableType;
    public TableColumn <Appointment, LocalDateTime> apptTableStDt;
    public TableColumn <Appointment, LocalDateTime> apptTableEdDt;
    public TableColumn apptTableCusId;
    
    
    public ObservableList<Appointment> appoints = FXCollections.observableArrayList();
    public ObservableList<Customer> custs = FXCollections.observableArrayList();
    public ObservableList<Appointment> appointsmonth = FXCollections.observableArrayList();
    public ObservableList<Appointment> appointsweek = FXCollections.observableArrayList();
    public ObservableList<Contact> contacts = FXCollections.observableArrayList();
    
    @FXML private RadioButton allToggle;
    @FXML private RadioButton monthToggle;
    @FXML private RadioButton weekToggle;
    
    //edit appoinmment fx:id's
    @FXML private TextField editID;
    @FXML private TextField editTitle;
    @FXML private TextField editDescript;
    @FXML private TextField editLocation;
    @FXML private ChoiceBox contSelect;
    @FXML private ChoiceBox custSelect;
    @FXML private ChoiceBox userSelect;
    @FXML private ComboBox<LocalTime> selectStart;
    @FXML private ComboBox<LocalTime> selectEnd;
    @FXML private DatePicker startDatePicker1;
    @FXML private DatePicker endDatePicker1;
    @FXML private TextField typeField;
    
    @FXML private TableColumn custidCol;
    @FXML private TableColumn custNameCol;
    @FXML private TableColumn addressCol;
    @FXML private TableColumn postCodeCol;
    @FXML private TableColumn phoneCol;
    @FXML private TableColumn <Customer, LocalDateTime> createDateCol;
    @FXML private TableColumn createByCol;
    @FXML private TableColumn <Customer, LocalDateTime> clastUpdateCol;
    @FXML private TableColumn lastUpdateByCol;
    @FXML private TableColumn custDivIDCol;
    @FXML private TableColumn custDivCol;
    @FXML private TableColumn countryCol;
    
    //customer field fx:id's
    @FXML private TextField custId2;
    @FXML private TextField custName;
    @FXML private TextField custAddress;
    @FXML private TextField custPC;
    @FXML private TextField custPhone;
    @FXML private TextField custCB;
    @FXML private TextField custLUB;
    @FXML private DatePicker custCD;
    @FXML private DatePicker custLU;
    @FXML private ChoiceBox custDivN;
    @FXML private ChoiceBox custCountry;
           
    
    private User usernamelogin = null;
    private Division currentdivision = null;
    
    
    /** Method initializes the main form and sets the data for all elements in the FXML file. This includes using 
     *  cell value factory for all table columns which are set to either the Appointment or Customers class types. 
     *  The method also adds cell factories using four different lambda expressions (one per TableColumn) to set 
     *  the localdatetime variables to strings for display in the TableView GUI. The method also sets four listeners
     *  to both the Appointment table and Customer table so that when selected it will repopulate the from with the 
     *  selected appointment or customer information. This carried out using lamdba expressions. In addition a lister
     *  is placed on the customer select combobox (appointment table) to populate the customer table and details to the
     *  selected customer. A similar listener is placed on the customer country combobox to restrict division names when 
     *  a specific region is selected (i.e. US will only have divisions from the US.) The four listeners are implemented 
     * using lambda expressions. The benefit of using this expressions is that it allows me to reduce the number of classes
     * that needed be coded in the application and allows me to code each listener response within the controller class.
     * 
     * The use of each lambda is described here 
     * 1. Cell Factory for apptTableStDt - this lambda applies a cell factory to all appointemnt tableview cells in 
     * the Start Date column. Allows each to be converted to a displayable string vs. a localdatetime obj. This reduces the 
     * need to make a class for the cell factory.
     * 2. Cell Factory for apptTableEdDt - this lambda applies a cell factory to all appointment tableview cells in 
     * the End Date column. Allows each to be converted to a displayable string vs. a localdatetime obj. This means the 
     * cell factory doesn't have to be called for each cell in the column and just the column.
     * 3. Cell Factory for creatDateCol - this lambda applies a cell factory to all customer tableview cells in 
     * the Create Date column. Allows each to be converted to a displayable string vs. a localdatetime obj. This means an 
     * outside cell factory class doesn't need to be created in the program to set the cell factory.
     * 4. Cell Factory for clastUpdateCol - this lambda applies a cell factory to all tableview cells in 
     * the Last Update column. Allows each to be converted to a displayable string vs. a localdatetime obj. Reduces the need
     * to call a separate class to run the cell factory.
     * 5. Adds a listener to the appointTable so that any selected row in the appointment table will fill the 
     * other appointment information boxes.
     * 6. Adds a listener to the customerTable so that any selected row in the customer table will fill the 
     * other customer information boxes. Improve efficiency because there is no need to relaunch the fxml scene.
     * 7. Adds a listener to the customer select combobox so that any selected customer will fill the 
     * other customer information boxes.  Improve efficiency because there is no need to write another class to build the listener code
     * 8. Adds a listener to the country select combobox so that any selected country will limit the first_level division selections in 
     * the division select combobox. Improves efficiency because the code doesn't have to be declared as an anonymous method.
     * @param url URL object
     * @param rb resource bundle object
     */

    public void initialize(URL url, ResourceBundle rb) {
        
        apptTableId.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("appointmentID"));
        apptTableTitle.setCellValueFactory(new PropertyValueFactory<Appointment,String>("title"));
        apptTableDesc.setCellValueFactory(new PropertyValueFactory<Appointment,String>("description"));
        apptTableLoc.setCellValueFactory(new PropertyValueFactory<Appointment,String>("location"));
        apptTableCon.setCellValueFactory(new PropertyValueFactory<Appointment,String>("contactName"));
        apptTableType.setCellValueFactory(new PropertyValueFactory<Appointment,String>("type"));
        apptTableCusId.setCellValueFactory(new PropertyValueFactory<Appointment,String>("customerName"));
        apptTableStDt.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        apptTableEdDt.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
        
        custidCol.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("custID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));
        postCodeCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<Customer,LocalDateTime>("createDate"));
        createByCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("createdBy"));
        clastUpdateCol.setCellValueFactory(new PropertyValueFactory<Customer,LocalDateTime>("lastUpdate"));
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastUpdateBy"));
        custDivIDCol.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("divisionId"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("countryName"));
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        Division currentdivision = null;
        
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,0);
        
        while(start.isBefore(end.plusSeconds(1))) {
            selectStart.getItems().add(start);
            selectEnd.getItems().add(start);
            start = start.plusMinutes(10);
        }
        
                
        // lambda expression #1 to add cell factory to format appointment localdatetime into string
        apptTableStDt.setCellFactory(col -> new TableCell<Appointment, LocalDateTime>()
            {
                public void updateItem(LocalDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    
                    if (date == null) {
                       setText(null);
                    } else {
                        setText(dateFormat.format(date));
                    }
                    
                }
        });
        
        // lambda expression #2 to add cell factory to format appointment localdatetime into string
        apptTableEdDt.setCellFactory(col -> new TableCell<Appointment, LocalDateTime>()
            {
                public void updateItem(LocalDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    
                    if (date == null) {
                       setText(null);
                    } else {
                        setText(dateFormat.format(date));
                    }
                    
                }
        });
        
        // lambda expression #3 to add cell factory to format customer localdatetime into string
        createDateCol.setCellFactory(col -> new TableCell<Customer, LocalDateTime>()
            {
                public void updateItem(LocalDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    
                    if (date == null) {
                       setText(null);
                    } else {
                        setText(dateFormat.format(date));
                    }
                    
                }
        });
        
        // lambda expression #4 to add cell factory to format customer localdatetime into string
        clastUpdateCol.setCellFactory(col -> new TableCell<Customer, LocalDateTime>()
            {
                public void updateItem(LocalDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    
                    if (date == null) {
                       setText(null);
                    } else {
                        setText(dateFormat.format(date));
                    }
                    
                }
        });
        
        try {
           
            appoints.addAll(AppointmentDaoImpl.getAllAppointments());
            
            ObservableList<Customer> custlist = CustomerDaoImpl.getAllCustomers();
            ObservableList<Contact> contlist = ContactDaoImpl.getAllContacts();
            ObservableList<User> userlist = UserDaoImpl.getAllUsers();
            ObservableList<Country> countrylist = CountryDaoImpl.getAllCountries();
            ObservableList<Division> divisionlist = DivisionDaoImpl.getAllDivisions();
            
            ObservableList<String> stringDiv = FXCollections.observableArrayList();
            ObservableList<String> stringCust = FXCollections.observableArrayList();
            ObservableList<String> stringContact = FXCollections.observableArrayList();
            ObservableList<String> stringCountry = FXCollections.observableArrayList();
            ObservableList<String> stringUser = FXCollections.observableArrayList();
            
            for (Customer A : custlist)
            {
                stringCust.add(A.getCustomerName());
            }
            
            for (Division D: divisionlist)
            {
                stringDiv.add(D.getDivisionName());
            }
            
            for (Country B: countrylist)
            {
                stringCountry.add(B.getCountryname());
            }
            
            for (Contact C : contlist)
            {
                stringContact.add(C.getContactName());
            } 
            
            for (User U : userlist) 
            {
                stringUser.add(U.getUsern());
            }

            customerTable.setItems(custlist);
            custSelect.setItems(stringCust);
            contSelect.setItems(stringContact);
            custDivN.setItems(stringDiv);
            custCountry.setItems(stringCountry);
            userSelect.setItems(stringUser);
            
        } catch (Exception err) {
           err.printStackTrace();
       } 
        
        appointTable.setItems(appoints);
        
        final ToggleGroup group = new ToggleGroup();
        allToggle.setToggleGroup(group);
        monthToggle.setToggleGroup(group);
        weekToggle.setToggleGroup(group);
        allToggle.setSelected(true);
        
        // lambda expression #5 to add listener on appoitment tableview
        appointTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, tempAppoint) ->
        {
            if (tempAppoint != null) {
                try {
                    
                    String cusname = tempAppoint.getCustomerName();
                    String conname = tempAppoint.getContactName();
                    int userid = tempAppoint.getUserID();
                    User user = getUserById(userid);
                    String username = user.getUsern();
                    
                    userSelect.setValue(username);
                    custSelect.setValue(cusname);
                    contSelect.setValue(conname);
                    
                   
                    } catch (Exception err) {
                        err.printStackTrace();
                    } 
                
                editID.setText(String.valueOf(tempAppoint.getAppointmentID()));
                editTitle.setText(tempAppoint.getTitle());
                editDescript.setText(tempAppoint.getDescription());
                editLocation.setText(tempAppoint.getLocation());
                typeField.setText(tempAppoint.getType());
                startDatePicker1.setValue(tempAppoint.getStart().toLocalDate());
                endDatePicker1.setValue(tempAppoint.getEnd().toLocalDate());
                selectStart.setValue(tempAppoint.getStart().toLocalTime());
                selectEnd.setValue(tempAppoint.getEnd().toLocalTime());
            }
        });
        
        // lambda expression #6 to add listener on customer tableview
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, tempCust) ->
        {
            if (tempCust != null) {
                try {
                    
                    int cusint = tempCust.getCustID();
                    Customer c = getCustomer(cusint);
                    
                    String cusname = c.getCustomerName();
                    String cusadd = c.getAddress();
                    String cuspc = c.getPostalCode();
                    String cusp = c.getPhone();
                    LocalDateTime cuscd = c.getCreateDate();
                    String createb = c.getCreatedBy();
                    LocalDateTime cuslu = c.getLastUpdate();
                    String cuslub = c.getLastUpdateBy();
                    String contname = c.getCountryName();
                    String divname = c.getDivisionName();
                        
                    custSelect.setValue(cusname);    
                    custDivN.setValue(divname);
                    custCountry.setValue(contname);
                    custId2.setText(String.valueOf(cusint));
                    custName.setText(cusname);
                    custAddress.setText(cusadd);
                    custPC.setText(cuspc);
                    custPhone.setText(cusp);
                    custCB.setText(createb);
                    custLUB.setText(cuslub);
                    custCD.setValue(cuscd.toLocalDate());
                    custLU.setValue(cuslu.toLocalDate());
                   
                } catch (Exception err) {
                    err.printStackTrace();
                } 
                
            }
        });
        
        // lambda expression #7 to add listener on customer combobox
        custSelect.valueProperty().addListener((observable, oldlist, newlist) -> {
            
                
                boolean isComboEmpty = custSelect.getSelectionModel().isEmpty();
                
                if(isComboEmpty != true) {
                    try{
                        
                        Customer c = getCustomerByName((String) custSelect.getValue());
                        
                        Integer cusid = c.getCustID();
                        String cusname = c.getCustomerName();
                        String cusadd = c.getAddress();
                        String cuspc = c.getPostalCode();
                        String cusp = c.getPhone();
                        LocalDateTime cuscd = c.getCreateDate();
                        String createb = c.getCreatedBy();
                        LocalDateTime cuslu = c.getLastUpdate();
                        String cuslub = c.getLastUpdateBy();
                        String contname = c.getCountryName();
                        String divname = c.getDivisionName();
                        
                        
                        custDivN.setValue(divname);
                        custCountry.setValue(contname);
                        custId2.setText(String.valueOf(cusid));
                        custName.setText(cusname);
                        custAddress.setText(cusadd);
                        custPC.setText(cuspc);
                        custPhone.setText(cusp);
                        custCB.setText(createb);
                        custLUB.setText(cuslub);
                        custCD.setValue(cuscd.toLocalDate());
                        custLU.setValue(cuslu.toLocalDate());
                        
                        
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                    
                    
                    
                }
        });
        
        // lambda expression #8 to add listener on customer coutnry combobox
        custCountry.valueProperty().addListener((observable, olist, nlist) -> {
            
            boolean isempty = custCountry.getSelectionModel().isEmpty();
            
            
            if(isempty != true) {
                
                try {
                    
                    
                    Country tempcountry = getCountry((String) custCountry.getValue());
                    
                    int x = tempcountry.getIdNumber();
                    
                    
                    ObservableList<Division> divlistshort = DivisionDaoImpl.getAllDivisionsByCountry(x);
                    ObservableList<String> stringDivision = FXCollections.observableArrayList();
                    
                    if (custSelect.getValue() != null) {
                        for (Division D: divlistshort)
                        {
                            stringDivision.add(D.getDivisionName());
                        
                        }
                    
                        String tempcusstr = custSelect.getValue().toString();
                        Customer tempcus = getCustomerByName(tempcusstr);
                        String tempdiv = tempcus.getDivisionName();
                        custDivN.setItems(stringDivision);
                        custDivN.setValue(tempdiv);
                        
                    } else {    
                        for (Division L: divlistshort)
                        {
                            stringDivision.add(L.getDivisionName());
                        }
                        
                        custDivN.setItems(stringDivision);
                    }
                      
                } catch (Exception err) {
                        err.printStackTrace();
                }
            }
        });
        
        
        
    }
    
    /**
     * This method takes a User object passed by the loginformcontroller class and checks for appoinments that
     * are within 15 minutes of the when the user successfully logs into the application. The pop-up appears 
     * before the main form is seen by the user. 
     * @param u of class type User
     */
   
    public void setterUser(User u) {
        
        this.usernamelogin = u;
        
        LocalDateTime currentTime = LocalDateTime.now();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        ObservableList<LocalDateTime> startLst = FXCollections.observableArrayList();
        ObservableList<LocalDateTime> endLst = FXCollections.observableArrayList();
        ObservableList<Integer> appointID = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int index = 0;
        
        if(usernamelogin != null) {
            
            try {
                
            int userid = usernamelogin.getIdNumber();
            
            
            
            ObservableList<Appointment> appointschecker = AppointmentDaoImpl.getAllAppointmentsByUserId(userid);
            
                for (Appointment A: appointschecker) 
                    {
                        startLst.add(A.getStart());
                        endLst.add(A.getEnd());
                        appointID.add(A.getAppointmentID());
                        
                    } 
                
            }catch (Exception ex) {
                Logger.getLogger(mainformController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (LocalDateTime S: startLst) {
                
                long timeDifference = ChronoUnit.MINUTES.between(currentTime, S);
                index++;
                
                if (timeDifference > 0 & timeDifference <= 15) {
                    
                    int indx = startLst.indexOf(S);
                    
                    alert.setHeaderText("Alert");
                    alert.setContentText("You have an appointment in " + timeDifference + " minutes! ID#: " + appointID.get(indx) + " at " + S.format(formatter));
                    alert.showAndWait();
                    return;
                } else if (timeDifference >= -15 & timeDifference < 0) {
                    alert.setHeaderText("Alert");
                    alert.setContentText("You have an appointment that started " + abs(timeDifference) + " minutes ago!");
                    alert.showAndWait();
                    return;
                } 
            }
        }
            alert.setHeaderText("Alert");
            alert.setContentText("There are no upcoming appointments");
            alert.showAndWait();
    } 
    
    /**
     * This method is used to set the appointments TableView to display all appointments
     * currently found in the database table appointments. 
     * @param actionEvent activated when the user clicks the "all" radio button above appointment table.
     */
     
    public void radioAll(ActionEvent actionEvent) {
        
        appointTable.getSelectionModel().clearSelection();
        appointTable.getItems().clear();
        appoints.clear();
        editID.clear();
        editTitle.clear();
        typeField.clear();
        editDescript.clear();
        editLocation.clear();
        startDatePicker1.setValue(null);
        endDatePicker1.setValue(null);
        
        selectStart.setValue(null);
        selectEnd.setValue(null);
        
        custId2.clear();
        custName.clear();
        custAddress.clear();
        custPC.clear();
        custPhone.clear();
        custCB.clear();
        custLUB.clear();
        custCD.setValue(null);
        custLU.setValue(null);
        
        
        
        
        custSelect.getSelectionModel().clearSelection();
        
        try {
           
            appoints.addAll(AppointmentDaoImpl.getAllAppointments());
            ObservableList<Customer> custlist = CustomerDaoImpl.getAllCustomers();
            ObservableList<Contact> contlist = ContactDaoImpl.getAllContacts();
            ObservableList<String> stringCustAll = FXCollections.observableArrayList();
            ObservableList<String> stringContactAll = FXCollections.observableArrayList();
            ObservableList<String> stringDivision = FXCollections.observableArrayList();
            ObservableList<String> stringCountry = FXCollections.observableArrayList();
            
            for (Customer X : custlist)
            {
                stringCustAll.add(X.getCustomerName());
                stringDivision.add(X.getDivisionName());
                stringCountry.add(X.getCountryName());
            }
            
            for (Contact C : contlist)
            {
                stringContactAll.add(C.getContactName());
            } 
            
            
            custSelect.setItems(stringCustAll);
            contSelect.setItems(stringContactAll);
            custDivN.setItems(stringDivision);
            
            
        } catch (Exception err) {
           err.printStackTrace();
       } 
        
        appointTable.setItems(appoints);
        
    }
    
    /**
     * This method is used to set the appointments TableView to display appointments
     *  found in the database table appointments that will occur within one month of the current user local time. 
     * @param actionEvent activated when the user clicks the "month" radio button above appointment table.
     */
    
    public void radioMonth(ActionEvent actionEvent) {
        
        appointTable.getSelectionModel().clearSelection();
        appointTable.getItems().clear();
        appointsmonth.clear();
        editID.clear();
        editTitle.clear();
        editDescript.clear();
        editLocation.clear();
        typeField.clear();
        startDatePicker1.setValue(null);
        endDatePicker1.setValue(null);
        selectStart.setValue(null);
        selectEnd.setValue(null);
        custId2.clear();
        custName.clear();
        custAddress.clear();
        custPC.clear();
        custPhone.clear();
        custCB.clear();
        custLUB.clear();
        custCD.setValue(null);
        custLU.setValue(null);
        
        
        custSelect.getSelectionModel().clearSelection();
        
        try {
           
            appointsmonth.addAll(AppointmentDaoImpl.getAllAppointmentsByMonth());
            ObservableList<Customer> custlist = CustomerDaoImpl.getAllCustomers();
            ObservableList<Contact> contlist = ContactDaoImpl.getAllContacts();
            ObservableList<String> stringCustM = FXCollections.observableArrayList();
            ObservableList<String> stringContactM = FXCollections.observableArrayList();
            ObservableList<String> stringDivision = FXCollections.observableArrayList();
            ObservableList<String> stringCountry = FXCollections.observableArrayList();
            
            for (Customer A : custlist)
            {
                stringCustM.add(A.getCustomerName());
                stringDivision.add(A.getDivisionName());
                stringCountry.add(A.getCountryName());
            }
            
            for (Contact C : contlist)
            {
                stringContactM.add(C.getContactName());
            } 
            
            
            if (custSelect != null || contSelect != null) {
                custSelect.setItems(stringCustM);
                contSelect.setItems(stringContactM);
                
            }
        } catch (Exception err) {
           err.printStackTrace();
       } 
        
        appointTable.setItems(appointsmonth);
       
    }
    
    /**
     * This method is used to set the appointments TableView to display appointments
     *  found in the database table appointments that will occur within one week of the current user local time. 
     * @param actionEvent activated when the user clicks the "week" radio button above appointment table.
     */
    
    public void radioWeek(ActionEvent actionEvent) {
        
        appointTable.getSelectionModel().clearSelection();
        appointTable.getItems().clear();
        appointsweek.clear();
        editID.clear();
        editTitle.clear();
        editDescript.clear();
        editLocation.clear();
        selectStart.setValue(null);
        selectEnd.setValue(null);
        typeField.clear();
        startDatePicker1.setValue(null);
        endDatePicker1.setValue(null);
        custId2.clear();
        custName.clear();
        custAddress.clear();
        custPC.clear();
        custPhone.clear();
        custCB.clear();
        custLUB.clear();
        custCD.setValue(null);
        custLU.setValue(null);
        
        
        custSelect.getSelectionModel().clearSelection();
        
        
        try {
           
            appointsweek.addAll(AppointmentDaoImpl.getAllAppointmentsByWeek());
            ObservableList<Customer> custlist = CustomerDaoImpl.getAllCustomers();
            ObservableList<Contact> contlist = ContactDaoImpl.getAllContacts();
            ObservableList<String> stringCustW = FXCollections.observableArrayList();
            ObservableList<String> stringContactW = FXCollections.observableArrayList();
            ObservableList<String> stringDivision = FXCollections.observableArrayList();
            ObservableList<String> stringCountry = FXCollections.observableArrayList();
            
            for (Customer A : custlist)
            {
                stringCustW.add(A.getCustomerName()); 
                stringDivision.add(A.getDivisionName());
                stringCountry.add(A.getCountryName());
            }
            
            for (Contact C : contlist)
            {
                stringContactW.add(C.getContactName());
            } 
            
            if (custSelect != null || contSelect != null) {
                custSelect.setItems(stringCustW);
                contSelect.setItems(stringContactW);
                
            }
            
        } catch (Exception err) {
           err.printStackTrace();
       } 
        
        appointTable.setItems(appointsweek);
        
        // TESTER 
        System.getProperty("user.timezone");
        //System.out.println("JVM default: " + localZoneID);
        
        // END TESTER
    }
    
    /**
     * This method is used to add a new customer class type object into the SQL table customers. 
     * The method converts converts local date time objects to a timestamp for insertion into the database.
     * MySQL automatically converts timestamps to UTC so no explicit conversion is completed here. 
     * @param actionEvent activated when the user clicks the "new customer" button below the customer table.
     */
        
    public void toNewCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        if (custName.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer name field is empty!");
            alert.showAndWait();
            return;
        } else if (custAddress.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer address field is empty!");
            alert.showAndWait();
            return;    
        } else if (custPC.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer postal code field is empty!");
            alert.showAndWait();
            return;  
        } else if (custPhone.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer phone field is empty!");
            alert.showAndWait();
            return;   
        } else if (custCB.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer created by field is empty!");
            alert.showAndWait();
            return;  
        } else if (custLUB.getText().toString().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer last updated by field is empty!");
            alert.showAndWait();
            return;   
        } else if (custCD.getValue() == null) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer create date field is empty!");
            alert.showAndWait();
            return;
        } else if (custDivN.getValue() == null) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer division name field is empty!");
            alert.showAndWait();
            return;
        } else if (custCountry.getValue() == null) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer country field is empty!");
            alert.showAndWait();
            return;
        }
        
        
        String custN = custName.getText();
        String custA = custAddress.getText();
        String postC = custPC.getText();
        String phone = custPhone.getText();
        String createB = custCB.getText();
        String lastUB = custLUB.getText();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        
        LocalDate custCd = custCD.getValue();
        LocalTime loctime = LocalTime.now();
        LocalDateTime x = LocalDateTime.of(custCd, loctime);
        ZonedDateTime ldtZd = x.atZone(localZoneId);
        
        LocalDateTime withoutTz = ldtZd.toLocalDateTime();
        Timestamp custcdts = Timestamp.valueOf(withoutTz);
        
        LocalDate custLu = custLU.getValue();
        LocalTime loctime2 = LocalTime.now();
        LocalDateTime y = LocalDateTime.of(custLu, loctime2);
        ZonedDateTime ldtZd2 = y.atZone(localZoneId);
        
        LocalDateTime withoutTz2 = ldtZd2.toLocalDateTime();
        Timestamp custluts = Timestamp.valueOf(withoutTz2);
        
        
        String custdivn = custDivN.getValue().toString();
        String countryn = custCountry.getValue().toString();
        
        try {
            
            Division custD = getDivision(custdivn);
            int custDint = custD.getDivisionID();
            
            alert.setHeaderText("New Customer");
            alert.setContentText("Confirm new customer for " + custN + "?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
            
                createCustomer(custN, custA, postC, phone, custcdts, createB, custluts, lastUB, custDint, custdivn, countryn);
                
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainform.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling App");
                stage.setScene(scene);
                stage.show();
            }    
          
        } catch (Exception err) {
            err.printStackTrace();
        }  
    }
    
    /**
     * This method is used to add a new appointment class type object into the SQL table customers. 
     * The method converts converts local date time objects to a timestamp for insertion into the database.
     * MySQL automatically converts timestamps to UTC so no explicit conversion is completed here. The method
     * also checks the requested appointment time against 8AM - 10PM EST time which is company office hours. 
     * @param actionEvent activated when the user clicks the "new appointments" button below the appointments table.
     */
    
    public void toNewAppt(ActionEvent actionEvent) throws IOException, SQLException {
      
       Alert alert = new Alert(AlertType.CONFIRMATION); 
       
       if (editTitle.getText().isBlank()) { 
            alert.setHeaderText("Error");
            alert.setContentText("Appointment title field is empty!");
            alert.showAndWait();
            return;
       } else if (startDatePicker1.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment start date field is empty!");
            alert.showAndWait();
            return;
       } else if (editDescript.getText().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment description field is empty!");
            alert.showAndWait();
            return;
       } else if (editLocation.getText().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment location field is empty!");
            alert.showAndWait();
            return;
       } else if (typeField.getText().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment type field is empty!");
            alert.showAndWait();
            return;
       } else if (contSelect.getValue().toString().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment contact field is empty!");
            alert.showAndWait();
            return;
       } else if (custSelect.toString().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment customer field is empty!");
            alert.showAndWait();
            return;
       } else if (selectStart.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment start time field is empty!");
            alert.showAndWait();
            return;
       } else if (endDatePicker1.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appoinment end date field is empty!");
            alert.showAndWait();
            return;
       } else if (selectEnd.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment end time field is empty!");
            alert.showAndWait();
            return;
       }       
        
       String title = editTitle.getText();
       String description = editDescript.getText();
       String location = editLocation.getText();
       String type = typeField.getText();
       String contact = contSelect.getValue().toString();
       String cs = custSelect.getValue().toString();
       
       ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       
       LocalDate sd = startDatePicker1.getValue();
       LocalTime st = selectStart.getValue();
       LocalDateTime sdpldt = LocalDateTime.of(sd, st);
       ZonedDateTime ldtZd = sdpldt.atZone(localZoneId);
       LocalDateTime withoutTz = ldtZd.toLocalDateTime();
       Timestamp startdate = Timestamp.valueOf(withoutTz);
       
       LocalDate edp = endDatePicker1.getValue();
       LocalTime et = selectEnd.getValue();
       LocalDateTime edpldt = LocalDateTime.of(edp, et);
       ZonedDateTime ldtZd2 = edpldt.atZone(localZoneId);
       LocalDateTime withoutTz2 = ldtZd2.toLocalDateTime();
       Timestamp enddate = Timestamp.valueOf(withoutTz2);
       
       ZonedDateTime zdt = sdpldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
       System.out.println(zdt);
       
       String createby = "application";  
       String lastupdateby = "application";  
       
       LocalTime startTimeOffice = LocalTime.of(8, 0);
       LocalTime endTimeOffice = LocalTime.of(22, 0);
       ZonedDateTime strTimeZDT = ZonedDateTime.of(sd, startTimeOffice, ZoneId.of("America/New_York"));
       ZonedDateTime endTimeZDT = ZonedDateTime.of(edp, endTimeOffice, ZoneId.of("America/New_York"));
       ZonedDateTime strTimeLoc = strTimeZDT.withZoneSameInstant(localZoneId);
       ZonedDateTime endTimeLoc = endTimeZDT.withZoneSameInstant(localZoneId);
       LocalDateTime strTimeLDT = strTimeLoc.toLocalDateTime();
       LocalDateTime endTimeLDT = endTimeLoc.toLocalDateTime();
       
       Map<Long, String> ampmStr = Map.of(0L, " AM", 1L, " PM");
       DateTimeFormatter timeFormat = new DateTimeFormatterBuilder()
               .appendPattern("hh:mm")
               .appendText(ChronoField.AMPM_OF_DAY, ampmStr)
               .toFormatter();
       
       String startLT = strTimeLDT.format(timeFormat);
       String endLT = endTimeLDT.format(timeFormat);
       
       if (withoutTz.isBefore(strTimeLDT) || withoutTz2.isAfter(endTimeLDT)) {
            alert.setHeaderText("Error");
            alert.setContentText("Apointment time is not between " + startLT + " and " + endLT + " (office hours)!");
            alert.showAndWait();
            return;
       } else if (withoutTz.isAfter(withoutTz2)) {
            alert.setHeaderText("Error");
            alert.setContentText("Start time is after end time!");
            alert.showAndWait();
            return;
       } else if (withoutTz.isEqual(withoutTz2)) {
            alert.setHeaderText("Error");
            alert.setContentText("Start and end time are the same!");
            alert.showAndWait();
            return;
       }
       
       
       try {
           
            User usertemp = getUser(userSelect.getValue().toString());  
            int custid = getCustomerByName(cs).getCustID();
            int userid = usertemp.getIdNumber();
            int contid = getContactByName(contact).getContactID();
            
            ObservableList<Appointment> cusaptlist = getAllAppointmentsByCusId(custid);
            
            for (Appointment A: cusaptlist) 
            {
                String starttimestr = A.getStart().toString();
                LocalDateTime starttime = LocalDateTime.parse(starttimestr);
                
                String endtimestr = A.getEnd().toString();
                LocalDateTime endtime = LocalDateTime.parse(endtimestr);
                
                if(sdpldt.isAfter(starttime) & sdpldt.isBefore(endtime))  {
                    
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                    
                } else if (sdpldt.isEqual(starttime) || sdpldt.isEqual(endtime)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                }   else if (edpldt.isAfter(starttime) & edpldt.isBefore(endtime)) {
                    
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (edpldt.isEqual(starttime) || edpldt.isEqual(endtime)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (starttime.isAfter(sdpldt) & starttime.isBefore(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (endtime.isAfter(sdpldt) & endtime.isBefore(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (starttime.isAfter(sdpldt) & endtime.isBefore(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (starttime.isBefore(sdpldt) & endtime.isAfter(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                }
            }
            
       
            alert.setHeaderText("New Appointment");
            alert.setContentText("Confirm new appointment for " + cs + "?");
            Optional<ButtonType> result = alert.showAndWait();
            
       if (result.get() == ButtonType.OK) {
           createAppointment(title, description, location, type, startdate, enddate, createby, lastupdateby, custid, userid, contid);
           
           Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainform.fxml"));
           Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setTitle("Scheduling App");
           stage.setScene(scene);
           stage.show();
       }
       
       } catch (Exception err) {
           err.printStackTrace();
       }  
    }
    
    /**
     * This method is used to modify appointment class type object reflected in the SQL table appointments. 
     * The method converts converts local date time objects to a timestamp for insertion into the database.
     * MySQL automatically converts timestamps to UTC so no explicit conversion is completed here. 
     * also checks the requested appointment time against 8AM - 10PM EST time which is company office hours. 
     * @param actionEvent activated when the user clicks the "modify appointment" button below the appointment table.
     */
    
    public void toModifyAppt(ActionEvent actionEvent) throws IOException, SQLException {
        
       Alert alert = new Alert(AlertType.CONFIRMATION);
       
       if (editTitle.getText().isBlank()) { 
            alert.setHeaderText("Error");
            alert.setContentText("Appointment title field is empty!");
            alert.showAndWait();
            return;
       } else if (startDatePicker1.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment start date field is empty!");
            alert.showAndWait();
            return;
       } else if (editDescript.getText().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment description field is empty!");
            alert.showAndWait();
            return;
       } else if (editLocation.getText().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment location field is empty!");
            alert.showAndWait();
            return;
       } else if (typeField.getText().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment type field is empty!");
            alert.showAndWait();
            return;
       } else if (contSelect.getValue().toString().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment contact field is empty!");
            alert.showAndWait();
            return;
       } else if (custSelect.toString().isBlank()) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment customer field is empty!");
            alert.showAndWait();
            return;
       } else if (selectStart.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment start time field is empty!");
            alert.showAndWait();
            return;
       } else if (endDatePicker1.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appoinment end date field is empty!");
            alert.showAndWait();
            return;
       } else if (selectEnd.getValue() == null) {
           alert.setHeaderText("Error");
            alert.setContentText("Appointment end time field is empty!");
            alert.showAndWait();
            return;
       } 
       
       Integer id =  Integer.parseInt(editID.getText());
       String title = editTitle.getText();
       String description = editDescript.getText();
       String location = editLocation.getText();
       String type = typeField.getText();
       String contact = contSelect.getValue().toString();
       String cs = custSelect.getValue().toString();
       ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
         
       
       LocalDate sd = startDatePicker1.getValue();
       LocalTime st = selectStart.getValue();
       LocalDateTime sdpldt = LocalDateTime.of(sd, st);
       ZonedDateTime ldtZd = sdpldt.atZone(localZoneId);
       LocalDateTime withoutTz = ldtZd.toLocalDateTime();
       Timestamp startdate = Timestamp.valueOf(withoutTz);
       
       
       LocalDate edp = endDatePicker1.getValue();
       LocalTime et = selectEnd.getValue();
       LocalDateTime edpldt = LocalDateTime.of(edp, et);
       ZonedDateTime ldtZd2 = edpldt.atZone(localZoneId);
       LocalDateTime withoutTz2 = ldtZd2.toLocalDateTime();
       Timestamp enddate = Timestamp.valueOf(withoutTz2);
       
       LocalTime startTimeOffice = LocalTime.of(8, 0);
       LocalTime endTimeOffice = LocalTime.of(22, 0);
       ZonedDateTime strTimeZDT = ZonedDateTime.of(sd, startTimeOffice, ZoneId.of("America/New_York"));
       ZonedDateTime endTimeZDT = ZonedDateTime.of(edp, endTimeOffice, ZoneId.of("America/New_York"));
       ZonedDateTime strTimeLoc = strTimeZDT.withZoneSameInstant(localZoneId);
       ZonedDateTime endTimeLoc = endTimeZDT.withZoneSameInstant(localZoneId);
       LocalDateTime strTimeLDT = strTimeLoc.toLocalDateTime();
       LocalDateTime endTimeLDT = endTimeLoc.toLocalDateTime();
       
       Map<Long, String> ampmStr = Map.of(0L, " AM", 1L, " PM");
       DateTimeFormatter timeFormat = new DateTimeFormatterBuilder()
               .appendPattern("hh:mm")
               .appendText(ChronoField.AMPM_OF_DAY, ampmStr)
               .toFormatter();
       
       String startLT = strTimeLDT.format(timeFormat);
       String endLT = endTimeLDT.format(timeFormat);
       
       if (withoutTz.isBefore(strTimeLDT) || withoutTz2.isAfter(endTimeLDT)) {
            alert.setHeaderText("Error");
            alert.setContentText("Apointment time is not between " + startLT + " and " + endLT + " (office hours)!");
            alert.showAndWait();
            return;
       } else if (withoutTz.isAfter(withoutTz2)) {
            alert.setHeaderText("Error");
            alert.setContentText("Start time is after end time!");
            alert.showAndWait();
            return;
       } else if (withoutTz.isEqual(withoutTz2)) {
            alert.setHeaderText("Error");
            alert.setContentText("Start and end time are the same!");
            alert.showAndWait();
            return;
       }
       
       try {
           
            User usertemp = getUser(userSelect.getValue().toString());
            int custid = getCustomerByName(cs).getCustID();
            int userid = usertemp.getIdNumber();
            int contid = getContactByName(contact).getContactID();
            Customer c = getCustomer(custid);
            String createby = c.getCreatedBy();
            
            
            
            ObservableList<Appointment> cusaptlist = getAllAppointmentsByCusId(custid);
            
            for (Appointment A: cusaptlist) 
            {
                String starttimestr = A.getStart().toString();
                LocalDateTime starttime = LocalDateTime.parse(starttimestr);
                
                String endtimestr = A.getEnd().toString();
                LocalDateTime endtime = LocalDateTime.parse(endtimestr);
                
                int tempid = A.getAppointmentID();
               
                
                if(withoutTz.isAfter(starttime) & withoutTz.isBefore(endtime) & tempid != id)  {
                    alert.setHeaderText("Error");
                    alert.setContentText("Start time overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                    
                } else if (tempid != id & (withoutTz.isEqual(starttime) || withoutTz.isEqual(endtime))) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Start time overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                }   else if (withoutTz2.isAfter(starttime) & withoutTz2.isBefore(endtime) & tempid != id) {
                    
                    alert.setHeaderText("Error");
                    alert.setContentText("End time overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (tempid != id & (withoutTz2.isEqual(starttime) || withoutTz2.isEqual(endtime))) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Start time overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (tempid != id & starttime.isAfter(sdpldt) & starttime.isBefore(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (tempid != id & endtime.isAfter(sdpldt) & endtime.isBefore(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (tempid != id & starttime.isAfter(sdpldt) & endtime.isBefore(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                } else if (tempid != id & starttime.isBefore(sdpldt) & endtime.isAfter(edpldt)) {
                    alert.setHeaderText("Error");
                    alert.setContentText("Overlaps with appointment # " + A.getAppointmentID() + "!");
                    alert.showAndWait();
                    return;
                }
            }
            
        alert.setHeaderText("Modify Appointment?");
        alert.setContentText("Confirm modification of appointment for " + cs + "? Appointment ID# " + id);
        Optional<ButtonType> result = alert.showAndWait();
                    
       if (result.get() == ButtonType.OK) {
           
           modifyAppointment(id, title, description, location, type, startdate, enddate, custid, userid, contid);
           
           Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainform.fxml"));
           Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setTitle("Scheduling App");
           stage.setScene(scene);
           stage.show();
       }
       
       } catch (Exception err) {
           err.printStackTrace();
       }
    }
    
    /**
     * This method is used to modify an appointment class type object reflected in the SQL table customers. 
     * The method converts converts local date time objects to a timestamp for insertion into the database.
     * MySQL automatically converts timestamps to UTC so no explicit conversion is completed here. 
     * @param actionEvent activated when the user clicks the "modify customer" button below the customer table.
     */
    
    public void toModifyCust(ActionEvent actionEvent) throws IOException, SQLException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
         if (custName.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer name field is empty!");
            alert.showAndWait();
            return;
        } else if (custAddress.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer address field is empty!");
            alert.showAndWait();
            return;    
        } else if (custPC.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer postal code field is empty!");
            alert.showAndWait();
            return;  
        } else if (custPhone.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer phone field is empty!");
            alert.showAndWait();
            return;   
        } else if (custCB.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer created by field is empty!");
            alert.showAndWait();
            return;  
        } else if (custLUB.getText().toString().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer last updated by field is empty!");
            alert.showAndWait();
            return;   
        } else if (custCD.getValue() == null) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer create date field is empty!");
            alert.showAndWait();
            return;
        } else if (custDivN.getValue() == null) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer division name field is empty!");
            alert.showAndWait();
            return;
        } else if (custCountry.getValue() == null) {
            alert.setHeaderText("Error");
            alert.setContentText("Customer country field is empty!");
            alert.showAndWait();
            return;
        }
        
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        int id = Integer.parseInt(custId2.getText());
        String custN = custName.getText();
        String custA = custAddress.getText();
        String postC = custPC.getText();
        String phone = custPhone.getText();
        String createB = custCB.getText();
        String lastUB = custLUB.getText();
        
        LocalDate custCd = custCD.getValue();
        
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime ldtZd = now.atZone(localZoneId);
        LocalDateTime withoutTz = ldtZd.toLocalDateTime();
        
        
        Timestamp timestamp = Timestamp.valueOf(withoutTz);
        
        
        String custdivn = custDivN.getValue().toString();
       
       
        
        try {
            
            Division custD = getDivision(custdivn);
            int custDint = custD.getDivisionID();
            
            
            
            alert.setHeaderText("Modify Customer?");
            alert.setContentText("Confirm modification of customer " + custN + "? Customer ID# " + id);
            Optional<ButtonType> result = alert.showAndWait();
            
            
            if (result.get() == ButtonType.OK) {
                modifyCustomer(id, custN, custA, postC, phone, createB, timestamp, lastUB, custDint);
                
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainform.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling App");
                stage.setScene(scene);
                stage.show();
            }    
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    
    /**
     * This method is used to delete an appointment object from the SQL table appointments. 
     * The method uses the appointment Id to lookup and then delete the appointment date from the table.  
     * @param actionEvent activated when the user clicks the "delete appointment" button below the appointment table.
     */
    
    public void toDeleteAppoint(ActionEvent actionEvent) throws IOException, SQLException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        Integer id =  Integer.parseInt(editID.getText());
        
        try {
            String title = editTitle.getText();
            
            alert.setHeaderText("Delete Appointment?");
            alert.setContentText("Confirm delete of appointment " + title + "? Appointment ID# " + id);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                deleteAppointment(id);
                
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainform.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling App");
                stage.setScene(scene);
                stage.show();
            } 
            
        } catch (Exception err) {
            err.printStackTrace();
        }
        
    }
    
    /**
     * This method is used to delete an customer object from the SQL table customers. It also deletes
     * all appointments associated with that customer. The method uses the customer Id to lookup and then delete the 
     * appointments from the appointment table and then the customer row in the customer table.
     * @param actionEvent activated when the user clicks the "delete customer" button below the customer table.
     */
    
    public void toDeleteCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        ObservableList<Integer> intlist = FXCollections.observableArrayList();
        Integer id =  Integer.parseInt(custId2.getText());
        
        try {
            ObservableList<Appointment> x = getAllAppointmentsByCusId(id);
           
            for (Appointment A: x) {
                int temp = A.getAppointmentID();
                deleteAppointment(temp);
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(mainformController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            String title = custName.getText();
            
            alert.setHeaderText("Delete Customer?");
            alert.setContentText("Confirm delete of customer " + title + "? Customer ID# " + id);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                deleteCustomer(id);
                
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainform.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling App");
                stage.setScene(scene);
                stage.show();
            } 
            
        } catch (Exception err) {
            err.printStackTrace();
        }
        
        
    }
    
    /**
     * This method is used to launch the report pane and its corresponding fxml file + controller. 
     * @param actionEvent activated when the user clicks the "contact report" button below the customer table.
     */
    
    public void toReportPane(ActionEvent actionEvent) throws IOException, SQLException {
           Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/reportform.fxml"));
           Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setTitle("Scheduling App");
           stage.setScene(scene);
           stage.show();
    }
}

