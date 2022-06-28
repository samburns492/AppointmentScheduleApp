/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingapp;


import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schedulingapp.utilities.EstablishConnection;


/**
 * Main application class of the program.
 * @author Sam
 */
public class SchedulingApp extends Application {

    /**
     * Main method for the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {
        
        EstablishConnection.startConnect();
        
        launch(args);
        
        EstablishConnection.tossConnect();

    }
    
    /**
     * Launches the login form fxml document and accompanying controller class. 
     * @param stage the fxml stage object. 
     */
    
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/loginform.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scheduling App");
        stage.show();
    }
}
