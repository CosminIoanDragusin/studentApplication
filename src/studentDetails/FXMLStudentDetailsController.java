/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentDetails;

import java.io.IOException;
import studentapplication.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXMLStudentDetailsController implements Initializable {

   Student selectedStudent;
    
    @FXML
    private Label labelNume;
    @FXML
    private Label labelPrenume;
    @FXML
    private Label labelNota;
    @FXML
    private Label labelMaterie;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelProfesor;
    @FXML
    private Button btnBack;

    //functia are rolul de a face transferul de la scena cu tabelul la scena cu detalii student
    public void selectedData(Student student)
    {
    selectedStudent=student;
    labelNume.setText(selectedStudent.getNume());
    labelPrenume.setText(selectedStudent.getPrenume());
    labelNota.setText(selectedStudent.getNota());
    labelMaterie.setText(selectedStudent.getMaterie());
    labelEmail.setText(selectedStudent.getEmail());
    labelProfesor.setText(selectedStudent.getProfesor());
    }
    
    //aici se face trecerea de la cena aplicatie la scena detalii student
    @FXML
    private void initData(ActionEvent event) throws IOException {
   Parent root = FXMLLoader.load(getClass().getResource("/studentapplication/FXMLDocument.fxml"));
   Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   Scene scene = new Scene(root);
   stage.setScene(scene);
   stage.show();      
    }

    //iesirea din aplicatie
    @FXML
  public void exit()
  {
  System.exit(0);
  }  
    /**
     * Initializes the controller class.
     */
 @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }       
}
