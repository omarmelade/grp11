package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class toolbarController implements Initializable {

    @FXML
    JFXButton homeButton;
    @FXML
    JFXButton accountButton;
    @FXML
    JFXButton agendaButton;
    @FXML
    JFXButton settingsButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonEventHandler();
    }

    public void buttonEventHandler(){
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED,(Event event) -> {
            System.out.println(homeButton.getText());
        });
        accountButton.addEventHandler(MouseEvent.MOUSE_CLICKED,(Event event) -> {
            System.out.println(accountButton.getText());
        });
        agendaButton.addEventHandler(MouseEvent.MOUSE_CLICKED,(Event event) -> {
            System.out.println(agendaButton.getText());
        });
        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED,(Event event) -> {
            System.out.println(settingsButton.getText());
        });
    }



}
