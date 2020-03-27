package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.API.Login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    AnchorPane anchorBack;
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

    }

    public void logButton(){
        logButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            String email = emailField.getText();
            String pass = passField.getText();

            Login log = new Login(email, pass);
            log.run();
            if(log.connected){
                System.out.println("Authentification reussie.");

            }else{
                System.out.println("Authentification echouée.");
            }
        });
    }
}
