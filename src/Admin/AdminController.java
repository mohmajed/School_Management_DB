package Admin;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView <StudentData> studenttable;
    @FXML
    private TableColumn<StudentData, String> idcolumn;
    @FXML
    private TableColumn<StudentData, String> firstnamecolumn;
    @FXML
    private TableColumn<StudentData, String> lastnamecolumn;
    @FXML
    private TableColumn<StudentData, String> emailcolumn;
    @FXML
    private TableColumn<StudentData, String> dobcolumn;

    private dbConnection dc;
    private ObservableList<StudentData> data;


    public void initialize (URL url, ResourceBundle rb){
        this.dc = new dbConnection();
    }

    @FXML
    private void loadStudentData (ActionEvent event){
        try{

            Connection connection = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = connection.createStatement().executeQuery("SELECT" +
                    "* FROM students");
            while (rs.next()){
                this.data.add(new StudentData(rs.getString(1),
                                            rs.getString(2),
                                            rs.getString(3),
                                            rs.getString(4),
                                            rs.getString(5)));
            }

        }catch (SQLException ex){
            System.err.println("Error" + ex);
        }
        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);

    }

    @FXML
    private void addStudent (ActionEvent event){
        String sql = "INSERT INTO students (id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setString(1,this.id.getText());
            pr.setString(2,this.firstname.getText());
            pr.setString(3,this.lastname.getText());
            pr.setString(4,this.email.getText());
            pr.setString(5,this.dob.getEditor().getText());

            pr.execute();
            connection.setAutoCommit(true);
            connection.close();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void cleanFields (ActionEvent event){
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);


    }


}
