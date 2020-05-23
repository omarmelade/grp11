package sample.controller;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.API.Project;
import sample.API.Ressources;
import sample.model.ProjectTable;
import sample.model.RessourcesTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainAgendaController implements Initializable {

    @FXML
    public JFXTextField reunionName;
    @FXML
    public JFXComboBox<String> reunionGroup;
    @FXML
    public JFXComboBox<String> salleChoix;
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
    private AnchorPane agendapane;

    private AgendaController ac;

    public MainAgendaController() {
        ac = new AgendaController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initAgenda();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        reunionAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> addReunion());
    }


    private void initAgenda() throws IOException, SQLException {
        int boucle = 0;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/agenda.fxml"));
        loader.setController(ac);
        Node nodeitem = loader.load();
        agendapane.getChildren().add(nodeitem);
        debutHoraire._24HourViewProperty().setValue(true);
        finHoraire._24HourViewProperty().setValue(true);
        Project apiProj = new Project("get");
        apiProj.run();
        ProjectTable projectTable = apiProj.getPt();
        reunionGroup.setItems(FXCollections.observableList(projectTable.ListNProj()));
        Ressources apiRess = new Ressources("get");
        apiRess.run();
        RessourcesTable ressourcesTable = apiRess.getRt();
        salleChoix.setItems(FXCollections.observableList(ressourcesTable.ListNSalle()));
    }



    private void addReunion() {
        String nomGroupe = getReunionGroupe();
        String nomReunion = getReunionName();
        LocalDate dateReu = getReunionDate();
        LocalTime startH = getReunionStart();
        LocalTime endH = getReunionEnd();
        System.out.println(nomReunion + " pour le " + nomGroupe + " le " + dateReu.toString()
                + " à partir de : " + startH + " jusqu'à : " + endH + ".");
        ac.addToGpt(nomReunion, dateReu, startH, endH);
    }

    private LocalTime getReunionEnd() {
        return finHoraire.getValue();
    }

    private LocalTime getReunionStart() {
        return debutHoraire.getValue();
    }

    private LocalDate getReunionDate() {
        return reunionDate.getValue();
    }

    private String getReunionName() {
        return reunionName.getText();
    }

    private String getReunionGroupe() {
        return reunionGroup.getValue();
    }


}

