package sample.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.API.Agenda;
import sample.API.Project;
import sample.API.Ressources;
import sample.model.PersonModel;
import sample.model.ProjectTable;
import sample.model.RessourcesTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainAgendaController extends Observable implements Initializable {

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
    private JFXColorPicker colorPicker;
    @FXML
    private AnchorPane agendapane;

    private AgendaController ac;
    private ProjectTable projectTable;
    private RessourcesTable rt;
    private PersonModel pm;

    public final GridPaneTrackController gptControl;

    public MainAgendaController(PersonModel pm) {
        this.pm = pm;
        ac = new AgendaController(pm, "person");
        this.gptControl = new GridPaneTrackController(ac);
        addObserver(gptControl);
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

        initComboBox();
    }

    private void initComboBox() throws SQLException {
        Project apiProj = new Project("get");
        apiProj.run();
        projectTable = apiProj.getPt();

        Ressources apiRes = new Ressources("get");
        apiRes.run();
        rt = apiRes.getRt();


//        reunionGroup.setItems(FXCollections.observableList(projectTable.ListNProj()));
        reunionGroup.setItems(FXCollections.observableArrayList(projectTable.ListNameId().values()));

        salleChoix.setItems(FXCollections.observableArrayList(rt.ListNameId().values()));


    }

    public int getSalleKey() {
        try {
            return ProjectTable.getKey(rt.ListNameId(), salleChoix.getValue());
        } catch (NullPointerException ignored) {
        }
        return -1;
    }

    public int getReunionKey() {
        try {
            return ProjectTable.getKey(
                    projectTable.ListNameId(), reunionGroup.getValue());
        } catch (NullPointerException ignored) {
        }
        return -1;
    }

    private void addReunion() {
        if (getReunionKey() != -1 || getSalleKey() != -1) {
            String nomGroupe = getReunionGroupe();
            String nomReunion = getReunionName();
            LocalDate dateReu = getReunionDate();
            LocalTime startH = getReunionStart();
            LocalTime endH = getReunionEnd();
            String hex = "#" + Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6);

//        System.out.println(nomReunion + " pour le " + nomGroupe + " le " + dateReu.toString()
//                + " à partir de : " + startH + " jusqu'à : " + endH + ".");
//        ac.addToGpt(nomReunion, dateReu, startH, endH);
            Agenda a = new Agenda(getReunionKey(), nomReunion, nomGroupe, dateReu.toString(), startH, endH, hex, "insert");
            a.run();
            notifyObservers();
        }
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

