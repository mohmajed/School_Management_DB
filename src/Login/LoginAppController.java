package Login;

import Admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.StudentsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginAppController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbStatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox <option> comboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(this.loginModel.isDatabaseConnected()){
            this.dbStatus.setText("Connected");
        }else{
            this.dbStatus.setText("Not Connected");
        }
        this.comboBox.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void login (ActionEvent event){
        try{
            if(this.loginModel.isLogin(this.username.getText(), this.password.getText(),
                    ((option)this.comboBox.getValue()).toString())){
                //see which window the button took us from and close that stage
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                switch (((option)this.comboBox.getValue()).toString()){
                    case "Admin" :
                        adminLogin();
                        break;

                    case "Student" :
                        studentLogin();
                        break;

                }
            }else {
                this.loginStatus.setText("Wrong Credentials");
            }


        }catch (Exception localException){

        }
    }

    public void studentLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            //which file to load
            Pane root = (Pane) loader.load(getClass().getResource("/students/studentFXML.fxml").openStream());

            //attaches the controller to the fxml
            StudentsController studentsController = (StudentsController) loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void adminLogin(){
        try{
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminroot = (Pane)adminLoader.load(getClass().getResource("/Admin/Admin.fxml").openStream());
            AdminController adminController = (AdminController) adminLoader.getController();
            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
}
