/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXMLRegistrationController implements Initializable {

    
     @FXML
    private Button btnExit;

    @FXML
    private Button btnRegister;


    @FXML
    private Hyperlink hyperYourAccount;

    @FXML
    private Label labelProiect;

    @FXML
    private TextField reg_username;
    @FXML
    private PasswordField reg_password;
    @FXML
    private TextField reg_email;

  //database tools  
  private Connection conection;
  private PreparedStatement pst;
  private ResultSet rst;
  
  
  //finctie de legare cu baza de date
  public Connection getConnectionForRegister()
    {
    try
    {
    Class.forName("com.mysql.cj.jdbc.Driver");
    conection=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb","root","P4r0l4m34mysql");
    return conection;
    }catch(Exception ex)
    {
    System.out.println("Error"+ex.getMessage());
    return null;
    }
    }
  
  //trecerea la login
    @FXML
  public void toLogin(ActionEvent event) throws IOException
  {
     Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLLogin.fxml"));
     Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
  }
 
  //inserarea in registre a imformatilor
    @FXML
  public void Register(ActionEvent event)
  {
  conection =getConnectionForRegister();
  try
  {
   String  sql ="insert into data(username,pass_word,email) values (?,?,?)";
   pst=conection.prepareStatement(sql);
   pst.setString(1,reg_username.getText());
   pst.setString(2,reg_password.getText());
   pst.setString(3,reg_email.getText());
   pst.execute();
   
   
   btnRegister.getScene().getWindow().hide();
   
   Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLLogin.fxml"));
   
   Scene scene = new Scene(root);
   Stage stage=new Stage();
   stage.setScene(scene);
   stage.setTitle("Register Succesfull");
   stage.show();   
   
    Alert alert=new Alert(Alert.AlertType.INFORMATION);   
    alert.setTitle("Succesfull");
    alert.setContentText("Succesfull login!");
    alert.showAndWait();

  } catch(Exception ex)
 {
 ex.printStackTrace();
 }

  }
    /**
     * Initializes the controller class.
     */
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //adaugare efecte de umbre si iluminare
        reg_username.setStyle("-fx-background-color:#fff;" + "-fx-border-width:#3px;");
        
        DropShadow original=new DropShadow(20 , Color.valueOf("#6a9ae7"));
        
        labelProiect.setEffect(original);
        
        labelProiect.setOnMouseEntered((MouseEvent event)->{
        
         DropShadow shadow=new DropShadow(50 , Color.valueOf("#6a9ae7"));
        labelProiect.setStyle("-fx-text-fill:#fff");
        labelProiect.setEffect(shadow);
 
    });
        
         labelProiect.setOnMouseExited((MouseEvent event)->{
        
         DropShadow shadow=new DropShadow(20 , Color.valueOf("#6a9ae7"));
        labelProiect.setStyle("-fx-text-fill:#6a9ae7");
        labelProiect.setEffect(shadow);
 
    });
    }    

    //functie de iesire din aplicatie
    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    
}
