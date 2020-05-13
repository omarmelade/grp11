package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAgendaController implements Initializable {

    @FXML
    public Label reunionName;
    @FXML
    public JFXComboBox<Label> reunionGroup;
    @FXML
    public JFXDatePicker reunionDate;
    @FXML
    public JFXTimePicker debutHoraire;
    @FXML
    private JFXTimePicker finHoraire;
    @FXML
    public JFXButton reunionAdd;
    @FXML
    public JFXButton reunionCancel;
    @FXML
    private AnchorPane root;
    @FXML
    private Pane agendapane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initAgenda();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAgenda() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/agenda.fxml"));
        loader.setController(new AgendaController());
        Node nodeitem = loader.load();
        agendapane.getChildren().add(nodeitem);
        debutHoraire._24HourViewProperty().setValue(true);
        finHoraire._24HourViewProperty().setValue(true);
        reunionGroup.getItems().add(new Label("GROUPE AEROPORT"));
        reunionGroup.getItems().add(new Label("GROUPE MAIRIE"));
    }
}

