package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AgendaController implements Initializable {

    private static Clock clock;

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

    private LocalDate ld;
    private LocalDate today;


    public AgendaController() {
        this.ld = LocalDate.now();
        this.today = ld;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWeekNum(this.ld);
        semaineMoins.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> weekMoins());
        semainePlus.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> weekPlus());
        moisMoins.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> monthMoins());
        moisPlus.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> monthPlus());
        todayBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> setToday());
    }

    private void setToday() {
        if (ld != today) {
            this.ld = today;
            setWeekNum(ld);
        }
    }

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


    private void setWeekNum(LocalDate ld) {
        this.ld = ld;
        nSemaine.setText("SEMAINE : " + getNumWeekYearOfDate(this.ld));
        anneeLabel.setText(ld.getYear() + "");
        LocalDate previousMonday = this.ld.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        setDaysNumbers(previousMonday, ld);
        setMonthName(previousMonday, ld);
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
            l.setText(date.getDayOfWeek().name() + " " + date.getDayOfMonth());
            date = date.plusDays(1);
            day = day.plus(1);
            previousMonday = previousMonday.plusDays(1);
            ld = ld.plusDays(1);
        }
    }


    public static int getNumWeekYearOfDate(LocalDate date) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = date.get(woy);

        return weekNumber;
    }


    public int getWeekNum(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yy");   // Define formatting pattern to match your input string.
        LocalDate date = LocalDate.parse(input, formatter);                     // Parse string into a `LocalDate` object.

        WeekFields wf = WeekFields.of(Locale.getDefault());                    // Use week fields appropriate to your locale. People in different places define a week and week-number differently, such as starting on a Monday or a Sunday, and so on.
        TemporalField weekNum = wf.weekOfWeekBasedYear();                       // Represent the idea of this locale’s definition of week number as a `TemporalField`.
        int week = Integer.parseInt(String.format("%02d", date.get(weekNum)));   // Using that locale’s definition of week number, determine the week-number for this particular `LocalDate` value.

        return week;
    }
}
