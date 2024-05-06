/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import studentDetails.FXMLStudentDetailsController;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TableView<Student> tableStudent;
    @FXML
    private TableColumn<Student, Integer> columnId;
    @FXML
    private TableColumn<Student,String> columnName;
    @FXML
    private TableColumn<Student,String> columnPrenume;
    @FXML
    private TableColumn<Student,String> columnNota;
    @FXML
    private TableColumn<Student,String> columnMateria;
    @FXML
    private TableColumn<Student,String> columnEmail;
    @FXML
    private TableColumn<Student,String> columnProfesor;
    @FXML
    private TextField idLabel;
    @FXML
    private TextField nameLabel;
    @FXML
    private TextField prenumeLabel;
    @FXML
    private TextField notaLabel;
    @FXML
    private TextField materieLabel;
    @FXML
    private TextField emailLabel;
    @FXML
    private TextField profesorLabel;
    @FXML
    private TextField searchLabel;
    
    PreparedStatement pst;
    private final ObservableList<Student> dataList=FXCollections.observableArrayList();
    
     @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnClearText;
    
     @FXML
    private Button btnClose;
    @FXML
    private Button btnView;
    @FXML
    private Button btnExit;
 
    //functia de crud se realizeaza aici in functie de butonul apasat
    @FXML
    void crudStudent(ActionEvent event) {
        
    if(event.getSource()==btnAdd){
        insertStudent();
        }       
     else if(event.getSource()==btnUpdate){
        changeStudent();
        }
     else if(event.getSource()==btnDelete){
        deleteStudent();
        }
     else if(event.getSource()==btnClearText){
        clearText();
        }
    }
  
    //conectare baza de date
    public Connection getConnection()
    {
    Connection conn;
    try
    {
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb","root","P4r0l4m34mysql");
    return conn;
    }catch(Exception ex)
    {
    System.out.println("Error"+ex.getMessage());
    return null;
    }
    }
    
    //lista studentilor ce se afiseaza in tabel provenit din baza de date
    public ObservableList<Student> getStudentList()
    {
        ObservableList<Student> studentList=FXCollections.observableArrayList();
        Connection conn=getConnection();
        String query="select * from student";
        Statement st;
        ResultSet rs;
        
        try
        {
        st=conn.createStatement();
        rs=st.executeQuery(query);
        Student student;
        while(rs.next())
        {
        student=new Student(rs.getInt("id"),rs.getString("nume"),rs.getString("prenume"),rs.getString("nota"),rs.getString("materie"),rs.getString("email"),rs.getString("profesor"));
        studentList.add(student);
        }
        } catch(Exception ex)
         {
         ex.printStackTrace();
         }
        return studentList;
    }
    
    //afisarea caracteristici studentilor in bara de cautare
    public void showStudent()
    {
    ObservableList<Student> studentList=getStudentList();
    
    columnId.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
    columnName.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
    columnPrenume.setCellValueFactory(new PropertyValueFactory<Student,String>("prenume"));
    columnNota.setCellValueFactory(new PropertyValueFactory<Student,String>("nota"));
    columnMateria.setCellValueFactory(new PropertyValueFactory<Student,String>("materie"));
    columnEmail.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
    columnProfesor.setCellValueFactory(new PropertyValueFactory<Student,String>("profesor"));
    
    tableStudent.setItems(studentList);
    
    FilteredList<Student> searchData=new FilteredList<>(studentList,b->true);
   searchLabel.textProperty().addListener((observable,oldValue,newValue)->{
    searchData.setPredicate(student -> {    
    if(newValue.isEmpty()  || newValue==null)
    {   
    return true;
    }    
    String searchKey=newValue.toLowerCase();
    if(student.getId().toString().contains(searchKey))
    {
    return true;//it means found a match in id
    } 
    else if(student.getNume().toLowerCase().contains(searchKey))
    {
    return true;//it means found a match in name
    }
    else if(student.getPrenume().toLowerCase().contains(searchKey))
    {
    return true;//it means found a match in prenume
    }
    else if(student.getNota().toLowerCase().contains(searchKey))
    {
    return true;//it means found a match in nota
    }
    else if(student.getMaterie().toLowerCase().contains(searchKey))
    {
    return true;//it means found a match in materie
    }
    else if(student.getEmail().toLowerCase().contains(searchKey))
    {
    return true;//it means found a match in email
    }
    else if(student.getProfesor().toLowerCase().contains(searchKey))
    {
    return true;//it means found a match in profesor
    }
    return false;//no match found
    });    
    });
    SortedList<Student> sortedData= new SortedList<>(searchData);
    //bind sorted result to table
    sortedData.comparatorProperty().bind(tableStudent.comparatorProperty());
    tableStudent.setItems(sortedData);
    }
    
    //inserare student
    private void insertStudent()
    {
    String query="insert into student(id,nume,prenume,nota,materie,email,profesor) values("+idLabel.getText() + ",'" +
            nameLabel.getText() + "','" + prenumeLabel.getText() + "','" + notaLabel.getText() + "','" + materieLabel.getText() 
            + "','" + emailLabel.getText() + "','" + profesorLabel.getText() + "')";
    //String query="insert into studentdb.student(id,nume,prenume,nota,materie,email,profesor) values(?,?,?,?,?,?)";
    
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Adding Student");
    alert.setHeaderText("Student Added");
    alert.setContentText("Student was saved in record!");
    alert.showAndWait();
    
    executeQuery(query);
    
    showStudent();
    }
    
    //update student
    private void changeStudent()
    {
    String query="Update student set nume='" + nameLabel.getText() + "',prenume='" + prenumeLabel.getText() + "',nota='"
            + notaLabel.getText() + "',materie='" + materieLabel.getText() + "',email='" + emailLabel.getText() + "',profesor='"
             + profesorLabel.getText() + "'where id=" + idLabel.getText() + "";  
    
    executeQuery(query);
    
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Changing Student");
    alert.setHeaderText("Changed Student");
    alert.setContentText("Student was changed and saved");
    alert.showAndWait();
    
    showStudent();
    }
    
    //stergere student
    private void deleteStudent()
    {
     String query="Delete from student where id=" + idLabel.getText() + "";
     
     Alert alert=new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Deleting Student");
    alert.setHeaderText("Student deleted");
    alert.setContentText("Student was deleted from record");
    alert.showAndWait();
     
     executeQuery(query);
     showStudent();
    }
    
    //elimina text unde este scris
     private void clearText() {
        idLabel.setText("");
        nameLabel.setText("");
        prenumeLabel.setText("");
        notaLabel.setText("");
        materieLabel.setText("");
        emailLabel.setText("");
        profesorLabel.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showStudent();
    }    

    //executa codul mysql
    private void executeQuery(String query) {
      Connection conn=getConnection();
      Statement st;
      try
      {
      st=conn.createStatement();
      st.executeUpdate(query);
      }catch(Exception ex)
         {
         ex.printStackTrace();
         }
      
    }

    //cand este apasat pe tabel se afiseaza in text-area respectiv
    @FXML
    private void handleMouseAction(MouseEvent event) {
    Student student= tableStudent.getSelectionModel().getSelectedItem();
     idLabel.setText("" +student.getId());
     nameLabel.setText(student.getNume());
     prenumeLabel.setText(student.getPrenume());
     notaLabel.setText(student.getNota());
     materieLabel.setText(student.getMaterie());
     emailLabel.setText(student.getEmail());
     profesorLabel.setText(student.getProfesor());
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    //trecerea la login
    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
   root = FXMLLoader.load(getClass().getResource("/login/FXMLLogin.fxml"));
   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   scene = new Scene(root);
   stage.setScene(scene);
   stage.show();    
    }

    //trecerea la details
    @FXML
    private void goToDetails(ActionEvent event) throws IOException {
   FXMLLoader loader = new FXMLLoader();
   loader.setLocation(getClass().getResource("/studentDetails/FXMLStudentDetails.fxml"));
   Parent viewDetailTable=loader.load();
   
   Scene sceneDetailTable = new Scene(viewDetailTable);
   
   //acces the controller and call the method
   FXMLStudentDetailsController controller=loader.getController();
   controller.selectedData(tableStudent.getSelectionModel().getSelectedItem());
   
   Stage stageDetailTable = (Stage)((Node)event.getSource()).getScene().getWindow();
   
   
   stageDetailTable.setScene(sceneDetailTable);
   stageDetailTable.show();    
    }

    //iesirea din aplicatie
  public void exit()
  {
  System.exit(0);
  }  
}
