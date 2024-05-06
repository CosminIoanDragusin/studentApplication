/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXMLLoginController implements Initializable {
    
    
    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink hyperLink;

    @FXML
    private PasswordField password;
    
    
    @FXML
    private TextField username;
    
    @FXML
    private Label labelProiect;

    @FXML
    private Button btnExit;
    @FXML
    private AnchorPane exitImage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //efecte de shadow pe labelul de culoare albastra inchisa
        username.setStyle("-fx-background-color:#fff;" + "-fx-border-width:#3px;");
        
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
    
    @FXML
    public void textField(MouseEvent event)
    {
     //efecte cand scrii date pe loginFXML
    if(event.getSource()==username){
        username.setStyle("-fx-background-color:#fff;" + "-fx-border-width:#3px;");
        password.setStyle("-fx-background-color:#eef3ff;" + "-fx-border-width:#1px;");
        }  
    else if(event.getSource()==password)
    {
    username.setStyle("-fx-background-color:#eef3ff;" + "-fx-border-width:#1px;");
    password.setStyle("-fx-background-color:#fff;" + "-fx-border-width:#3px;");
    }
    }
   
  //iesire din aplicatie
  @FXML
  public void exit()
  {
  System.exit(0);
  }  
  
  //database tools
  private Connection conection;
  private PreparedStatement pst;
  private ResultSet rst;
  
  //finctie de legare cu baza de date
  public Connection getConnectionForLogin()
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
  
  //functie de verificare daca imformatiile la login
    @FXML
  public void Login(ActionEvent event)
  {
  conection =getConnectionForLogin();
  try
  {
   String  sql ="select * from data where username=? and pass_word=?";
   pst=conection.prepareStatement(sql);
   pst.setString(1,username.getText());
   pst.setString(2,password.getText());
   rst=pst.executeQuery();
   
   if(rst.next())
   {
   btnLogin.getScene().getWindow().hide();
   
   Parent root = FXMLLoader.load(getClass().getResource("/studentapplication/FXMLDocument.fxml"));
   
   Scene scene = new Scene(root);
   Stage stage=new Stage();
   stage.setScene(scene);
   stage.setTitle("Login Succesfull");
   stage.show();   
   
    Alert alert=new Alert(Alert.AlertType.INFORMATION);   
    alert.setTitle("Succesfull");
    alert.setContentText("Succesfull login!");
    alert.showAndWait();
   } else
   {
    Alert alert=new Alert(Alert.AlertType.INFORMATION);   
    alert.setTitle("Error");
    alert.setHeaderText("Wrong username / password!");
    alert.setContentText("Please insert the corect password/username");
    alert.showAndWait();
   }
  } catch(Exception ex)
 {
 ex.printStackTrace();
 }

  }

  //trecerea la registre
    @FXML
    private void ToRegister(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("/registration/FXMLRegistration.fxml"));
     Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();    
    }
}
