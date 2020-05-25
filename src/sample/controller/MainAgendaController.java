package sample.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.API.Agenda;
import sample.API.Project;
import sample.API.Ressources;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        this.ac = new AgendaController(pm, "person", -1);
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

    public int getProjetKey() {
        try {
            return ProjectTable.getKey(
                    projectTable.ListNameId(), reunionGroup.getValue());
        } catch (NullPointerException ignored) {
        }
        return -1;
    }

    private void addReunion() {
        int projKey = getProjetKey();

        if (projKey != -1 || getSalleKey() != -1) {

            String nomGroupe = getReunionGroupe();
            String nomReunion = getReunionName();
            LocalDate dateReu = getReunionDate();
            LocalTime startH = getReunionStart();
            LocalTime endH = getReunionEnd();
            String hex = "#" + Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6);
            int id_salle = getSalleKey();


            boolean projetBool = verifProjetSup(projKey, id_salle, dateReu, startH, endH);

            if (projetBool) {
                Agenda a = new Agenda(projKey, nomReunion, nomGroupe, dateReu.toString(), startH, endH, hex, id_salle, "insert");
                a.run();

                setChanged();
                notifyObservers();
            } else {
                System.err.println("CONFLIT !!!");
            }
        }
    }

    private boolean verifProjetSup(int projKey, int id_salle, LocalDate date, LocalTime startH, LocalTime endH) {
        Agenda a = new Agenda("get", id_salle);
        a.run();
        AgendaTable at = a.getAt();

        Project apiProj = new Project("getNote", projKey);
        apiProj.run();
        ProjectModel pm = apiProj.getPm();
        double note = pm.getNote();

        for (AgendaModel am : at.getArrayAgenda()) {

            Project api = new Project("getNote", am.getId_projet());
            api.run();
            ProjectModel projTemp = api.getPm();

            // si la date est la même ET le debut de a est avant le debut de b
            if (am.getDateReu().equals(date.toString())
                    && (am.getDebutReu().toLocalTime().isBefore(startH) && am.getFinReu().toLocalTime().isAfter(startH))) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fin = LocalDate.parse(projTemp.getDateFin(), formatter);

                if (fin.isAfter(LocalDate.now().plusWeeks(2))) {

                    Alert dialog = new Alert(Alert.AlertType.WARNING);
                    dialog.setHeaderText("OPERATION IMPOSSIBLE");
                    dialog.setContentText("CE PROJET EST PROCHE DE SA DATE BUTOIRE");

                    if (am.getNote() >= note) {
                        dialog.setHeaderText("OPERATION IMPOSSIBLE");
                        dialog.setContentText(dialog.getContentText() + "\n" + "& UN PROJET PLUS IMPORTANT A DEJA PRIS CETTE SALLE");
                        dialog.showAndWait();
                    } else {

                    }

                    dialog.showAndWait();
                }

                return false;
            } else {

            }
        }
        return true;
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

