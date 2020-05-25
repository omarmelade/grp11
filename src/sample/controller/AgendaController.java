package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.API.Agenda;
import sample.API.Project;
import sample.API.Ressources;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

public class AgendaController extends Observable implements Initializable {

    private static Clock clock;
    private Agenda agendaAPI;

    @FXML
    private Label nSemaine;
    @FXML
    private JFXButton semaineMoins;
    @FXML
    private JFXButton semainePlus;
    @FXML
    private JFXButton moisPlus;
    @FXML
    private JFXButton moisMoins;
    @FXML
    private JFXButton todayBtn;
    @FXML
    private JFXComboBox<String> salleAffiche;
    @FXML
    private JFXButton validSalle;
    @FXML
    private JFXComboBox<String> projetAffiche;
    @FXML
    private JFXButton validProj;


    @FXML
    private Pane pane;

    @FXML
    private Label lundiLabel;
    @FXML
    private Label mardiLabel;
    @FXML
    private Label mercrediLabel;
    @FXML
    private Label jeudiLabel;
    @FXML
    private Label venLabel;
    @FXML
    private Label moisLabel;
    @FXML
    private Label anneeLabel;

    public LocalDate ld;
    public final LocalDate today;
    private Node grid;

    public GridPaneTrackController gptControl;
    private ProjectModel pm;
    private PersonModel personm;
    private String demande = "";
    private RessourcesTable rt;

    private ProjectTable projectTable;

    // pour afficher uniquement par salle
    private int id_salle;
    private int id_projet;


    public AgendaController(ProjectModel proj, String demande, int id_salle, int id_projet) {
        this.demande = demande;
        this.pm = proj;
        this.ld = LocalDate.now();
        this.today = ld;
        this.gptControl = new GridPaneTrackController(this);
        this.id_salle = id_salle;
        this.id_projet = id_projet;
        addObserver(gptControl);
    }

    public AgendaController(PersonModel personm, String demande, int id_salle) {
        this.demande = demande;
        this.personm = personm;
        this.ld = LocalDate.now();
        this.today = ld;
        this.gptControl = new GridPaneTrackController(this);
        this.id_salle = id_salle;
        addObserver(gptControl);
    }

    public AgendaTable getData() {
        if (this.demande.equals("proj")) {
            agendaAPI = new Agenda("proj", pm);
        }
//        else if(this.demande.equals("projetSelect")){
//            agendaAPI = new Agenda("proj", pm);
//            agendaAPI.id_projetpm = this.id_projet;
//        }
        else if (this.demande.equals("salle")) {
            agendaAPI = new Agenda("salle", this.id_salle);
        } else if (this.personm.estAdmin()) {
            agendaAPI = new Agenda("get");
        } else {
            agendaAPI = new Agenda("person", personm);
        }
        agendaAPI.run();
        return agendaAPI.getAt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
        setWeekNum(this.ld);
        semaineMoins.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> weekMoins());
        semainePlus.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> weekPlus());
        moisMoins.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> monthMoins());
        moisPlus.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> monthPlus());
        todayBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> setToday());

        try {
            initCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            initPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCombo() throws SQLException {

        Project apiProj = new Project("get");
        apiProj.run();
        projectTable = apiProj.getPt();

        Ressources apiRes = new Ressources("get");
        apiRes.run();
        rt = apiRes.getRt();

        salleAffiche.setItems(FXCollections.observableArrayList(rt.ListNameId().values()));
        salleAffiche.setStyle("-fx-fill: #fff;");
        validSalle.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getSalleKey() != id_salle) {
                    getSalleKey();
                    try {
                        pane.getChildren().remove(grid);
                        System.out.println(id_salle);
                        initPane();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        projetAffiche.setVisible(false);
        validProj.setVisible(false);

//        projetAffiche.setItems(FXCollections.observableArrayList(projectTable.ListNameId().values()));
//
//        validProj.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (getProjetKey() != id_projet) {
//                    try {
//                        pane.getChildren().remove(grid);
//                        initPane();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

//    public int getProjetKey() {
//        try {
//            this.demande = "projetSelect";
//            this.id_projet = ProjectTable.getKey(projectTable.ListNameId(), projetAffiche.getValue());
//            setChanged();
//            notifyObservers();
//            return id_projet;
//        } catch (NullPointerException ignored) { }
//        return -1;
//    }

    public int getSalleKey() {
        try {
            this.demande = "salle";
            this.id_salle = ProjectTable.getKey(rt.ListNameId(), salleAffiche.getValue());
            setChanged();
            notifyObservers();
            return id_salle;
        } catch (NullPointerException ignored) {
        }
        return -1;
    }

    // affiche la grille du planning
    private void initPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/gridAgenda.fxml"));
        loader.setController(this.gptControl);
        grid = loader.load();
        pane.getChildren().add(grid);
    }


    // ajoute une reunion sur la grille
//    public void addToGpt(String nomReunion, LocalDate dateReu, LocalTime startH, LocalTime endH) {
//
//    }

    // met le jour a "aujourd'hui"
    private void setToday() {
        if (ld != today) {
            this.ld = today;
            setWeekNum(ld);
        }
    }

    // operation sur la date courante
    private void monthMoins() {
        LocalDate monthLess = this.ld.minusMonths(1);
        setWeekNum(monthLess);
    }

    private void monthPlus() {
        LocalDate monthPlus = this.ld.plusMonths(1);
        setWeekNum(monthPlus);
    }

    private void weekMoins() {
        LocalDate weekless = this.ld.minusWeeks(1);
        setWeekNum(weekless);
    }

    private void weekPlus() {
        LocalDate weekmore = this.ld.plusWeeks(1);
        setWeekNum(weekmore);
    }
    // fin d'operation sur la date

    private void setWeekNum(LocalDate ld) {
        this.ld = ld;
        nSemaine.setText("SEMAINE : " + getNumWeekYearOfDate(this.ld));
        anneeLabel.setText(ld.getYear() + "");
        LocalDate previousMonday = this.ld.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        setDaysNumbers(previousMonday, ld);
        setMonthName(previousMonday, ld);

        // appel l'observer sur les nouvelles données
        setChanged();
        notifyObservers();
    }

    private void setMonthName(LocalDate previousMonday, LocalDate ld) {
        String date;
        if (ld.getDayOfWeek() == DayOfWeek.MONDAY) {
            date = this.ld.format(DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH));
        } else {
            date = previousMonday.format(DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH));
        }
        moisLabel.setText(date.toUpperCase());
    }

    private void setDaysNumbers(LocalDate previousMonday, LocalDate ld) {
        DayOfWeek day = ld.getDayOfWeek();
        List<Label> labels = Arrays.asList(lundiLabel, mardiLabel, mercrediLabel, jeudiLabel, venLabel);

        LocalDate date = ld;
        if (day != DayOfWeek.MONDAY) {
            date = previousMonday;
        }
        for (Label l : labels) {
            // style de base des jours
            l.setStyle("-fx-text-fill: #000; -fx-font-size: 20;");
            // le jour present est affiché en blanc
            if (this.today.toString().equals(date.toString())) {
                l.setStyle("-fx-text-fill: #fff; -fx-font-size: 20;");
            }
            l.setText(date.getDayOfWeek().name() + " " + date.getDayOfMonth());
            date = date.plusDays(1);
            day = day.plus(1);
            previousMonday = previousMonday.plusDays(1);
            ld = ld.plusDays(1);
        }
    }

    public boolean weekNumEquals(LocalDate l, LocalDate m) {
        return getNumWeekYearOfDate(l) == getNumWeekYearOfDate(m);
    }

    // recupere le numero de la semaine affichée
    public static int getNumWeekYearOfDate(LocalDate date) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

        return date.get(woy);
    }

    // recupere le numero de la semaine par rapport a une chaine de caractère
    public int getWeekNum(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");   // Define formatting pattern to match your input string.
        LocalDate date = LocalDate.parse(input, formatter);                     // Parse string into a `LocalDate` object.

        WeekFields wf = WeekFields.of(Locale.getDefault());                    // Use week fields appropriate to your locale. People in different places define a week and week-number differently, such as starting on a Monday or a Sunday, and so on.
        TemporalField weekNum = wf.weekOfWeekBasedYear();                       // Represent the idea of this locale’s definition of week number as a `TemporalField`.

        return Integer.parseInt(String.format("%02d", date.get(weekNum)));
    }

    public int getMois() {
        return this.ld.getMonthValue();
    }

    public int getAnnee() {
        return this.ld.getYear();
    }
}
