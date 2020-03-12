package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sample.API.Login;
import sample.Connexion;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    Label labelTitle;
    @FXML
    JFXTextField emailField;
    @FXML
    Label emailLabel;
    @FXML
    Label passLabel;
    @FXML
    JFXPasswordField passField;
    @FXML
    JFXButton logButton;
    @FXML
    JFXButton registerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            String email = emailField.getText();
            String pass = passField.getText();
            try {
                Login log = new Login(email, pass);
                if(log.initConn()){
                    System.out.println("Authentification reussie.");
                }else {
                    System.out.println("Authentification echouée.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Utilisateur inconnu");
            }
        });
    }
}
