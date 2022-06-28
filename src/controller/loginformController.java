package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedulingapp.DAO.UserDaoImpl;
import schedulingapp.model.User;

/**
 * Creates a controller class for the login form of the Scheduling application. This controller
 * handles the user login to the system.
 * @author Sam
 */
public class loginformController implements Initializable {
    
    @FXML
    private Label username_label;
    @FXML
    private Label login_label;
    @FXML
    private Label timezone_label;
    @FXML
    private Label password_label;
    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    @FXML
    private Button login_button;
    
    public TextField zoneField;
    
    
      
    /**
     * This method initializes the controller class. It also evaluates the computer's 
     * language settings and determines if the login form should display in French or english
     * depending on the user computer language settings. 
     * @param url the url
     * @param rb resource bundle
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            String stringLocal = localZoneId.toString();
            Locale locale = Locale.getDefault();
            
            
            username_label.setText(stringLocal);
              
            String localestr = locale.getDisplayLanguage();
            
            String frstr= new String("fr");
            
            if  (frstr.equals(localestr.substring(0,2))) {
                login_label.setText("Nom d'utilisateur");
                password_label.setText("Le mot de passe");
                timezone_label.setText("Fuseau horaire");
                login_button.setText("Connexion");
            } else {
                login_label.setText("User name");
                password_label.setText("Password");
                timezone_label.setText("Timezone");
            }
            
    } 
    
    /**
     * This method is the actual login process for the application. It uses a user DAO 
     * to check all users in the MySQL database and allows the main form to launch only if the
     * username and password are correct. In addition the method evaluates the local display language and
     * displays any messages in either French or English. This is also where the "login_activity.txt" file 
     * is written with each login attempt (appends new line with username and a timestamp to the file.) 
     * This method also send calls the main form userSetter method which launches the appointment within 15 minutes
     * dialogue which is displayed prior to the main form being shown to the user. 
     * @param actionEvent activates when the user presses the "login" button on the screen.
     * @throws IOException 
     */
    
    public void toLogin(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        boolean x = false;
        Locale locale = Locale.getDefault();
        String localestr = locale.getDisplayLanguage();
        String frstr= new String("fr");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        
        
        String filename = "src/files/login_activity.txt", item;
        File file = new File(filename);
        String username2 = username_field.getText();
        String pw = password_field.getText();
        String nowldt = now.format(formatter);
        
        
        
        
        if(!file.exists()) {
            System.out.println("login_acitivity file not found!");
            System.exit(0);
        }
        
        PrintWriter outputFile = new PrintWriter(new FileWriter(file, true));
        
        
        String errormsg = "Error";
        String errormsg2 = "Please enter a user name and password";
        String errormsg3 = "Login failed";
        
        if  (frstr.equals(localestr.substring(0,2))) {
                errormsg = "Erreur";
                errormsg2 = "Veuillez saisir un nom d'utilisateur et un mot de passe";
                errormsg3 = "échec de la connexion";
        } 
        
        
        try {
            ObservableList<User> userlist = UserDaoImpl.getAllUsers();
                   
            for(User U : userlist)
            {
               
                if ((U.getUsern().equals(username_field.getText())) & (U.getPassword().equals(password_field.getText()))) { 
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainform.fxml"));
                    Parent root = loader.load();
                    
                    mainformController cntrller = loader.getController();
                    cntrller.setterUser(U);
                    
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Scheduling App");
                    stage.setScene(scene);
                    stage.show();
                    x = true;
                    
                    
                    outputFile.println("User " + username2 + " successfully logged in at " + nowldt + " Timezone: " + localZoneId);
                    outputFile.close();
                }
            }        
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
        if (username_field.getText().isBlank() || password_field.getText().isBlank()) {
            alert.setHeaderText(errormsg);
            alert.setContentText(errormsg2);
            alert.showAndWait();
            
            outputFile.println("User " + username2 + " gave invalid log-in at " + nowldt + " Timezone: " + localZoneId);
            outputFile.close();
            return;
        } else if (x == false) {
            alert.setHeaderText(errormsg);
            alert.setContentText(errormsg3);
            alert.showAndWait();
            
            
            outputFile.println("User " + username2 + " gave invalid log-in at " + nowldt + " Timezone: " + localZoneId);
            outputFile.close();
            return;
        } 
        
        outputFile.println("User " + username2 + " gave invalid log-in at " + nowldt + " Timezone: " + localZoneId);
        outputFile.close();
    }
}
