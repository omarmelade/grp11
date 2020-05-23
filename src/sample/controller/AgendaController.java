package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.API.Agenda;
import sample.model.AgendaTable;

import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public final GridPaneTrackController gptControl;

    public AgendaController() {
        this.ld = LocalDate.now();
        this.today = ld;
        this.gptControl = new GridPaneTrackController(this);
        addObserver(gptControl);
    }


    public AgendaTable getData() {
        agendaAPI = new Agenda("get");
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
            initPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // affiche la grille du planning
    private void initPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/gridAgenda.fxml"));
        loader.setController(gptControl);
        Node n = loader.load();
        pane.getChildren().add(n);
    }

    // ajoute une reunion sur la grille
    public void addToGpt(String nomReunion, LocalDate dateReu, LocalTime startH, LocalTime endH) {

    }

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
