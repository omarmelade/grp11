package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.controlsfx.control.PopOver;
import sample.constantes.Constantes;
import sample.model.AgendaModel;
import sample.model.AgendaTable;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GridPaneTrackController implements Initializable, Observer {

    final int numCols = 4;
    int numRows = 9;

    @FXML
    private GridPane grid;
    @FXML
    private PopOver popOver;

    private AgendaTable data;
    private AgendaController agendaController;
    public DateTimeFormatter formatter;
    private int advancements;

    public GridPaneTrackController(AgendaController agendaController) {
        this.agendaController = agendaController;
        this.data = agendaController.getData();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.advancements = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
        grid.getStylesheets().add("/sample/css/pane.css");
    }

    public LocalDate dataToDate(AgendaModel m) {
        String date = m.getDateReu();
        return LocalDate.parse(date, formatter);
    }

    public LocalDateTime dataTotime(AgendaModel m) {
        String date = m.getDateReu();
        String time = m.getDebutReu().toString();
        // on enleve les secondes
        time = time.substring(0, 5);
        //System.out.println(time);
        return LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDateTime dataToEnd(AgendaModel m) {
        String date = m.getDateReu();
        String time = m.getFinReu().toString();
        // on enleve les secondes
        time = time.substring(0, 5);
        //System.out.println(time);
        return LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public boolean memeSemaine(String date1, String date2) {
        return agendaController.getWeekNum(date1) == agendaController.getWeekNum(date2);
    }

    public void initialize() {
        this.grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        for (int i = 0; i <= numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(22);
            colConstraints.setFillWidth(true);
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i <= numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.grid.getRowConstraints().add(rowConstraints);
        }
        this.advancements = 1;
        paneOnGrid();
    }

    private void paneOnGrid() {

        for (int i = 0; i <= numCols; i++) {
            for (int j = 0; j <= numRows; j++) {
                addPane(i, j);
            }
        }
    }


    private void addPane(int colIndex, int rowIndex) {

        ArrayList<LocalDateTime> arr = caseToDate(colIndex, rowIndex);
        LocalDateTime debut = arr.get(0);
        LocalDateTime fin = arr.get(1);
        Label lblName = new Label(debut.toLocalTime().toString());

        Label lblStreet = new Label(fin.toLocalTime().toString());
        Label date = new Label(debut.toLocalDate().toString());
        VBox vBox = new VBox(lblName, lblStreet, date);
        HBox hBox = new HBox(vBox);

        PopOver popOver = new PopOver(hBox);
        Pane pane = new Pane(new Label(""));

        for (int i = 0; i < this.data.getNbRes(); i++) {
            LocalDate debutReun = dataToDate(this.data.getArrayAgenda().get(i));
            LocalTime debutReunTime = dataTotime(this.data.getArrayAgenda().get(i)).toLocalTime();
            LocalTime finReun = dataToEnd(this.data.getArrayAgenda().get(i)).toLocalTime();
            // on verifie que la date est égal a la date de la reunion dans la base de données

            if (debut.toLocalDate().toString().equals(debutReun.toString())) {
                //  si l'heure est également coherente on ajoute
                if (debut.toLocalTime().toString().equals(debutReunTime.toString())) {

                    AgendaModel a = this.data.getArrayAgenda().get(i);
                    Label name = new Label("  " + a.getNomreu());
                    name.getStyleClass().add("label");
                    pane = new Pane(name);
                    pane.getStyleClass().add("reun");
                    if (a.getColor() != null) {
                        pane.setStyle("-fx-background-color: " + a.getColor() + ";");
                    }
                    //System.err.println( debut.toLocalDate() + " EGAL " + dataToDate(this.data.getArrayAgenda().get(i)));

                } else if ((finReun.isAfter(fin.toLocalTime()) && debutReunTime.isBefore(debut.toLocalTime()))
                        ||
                        finReun.toString().equals(fin.toLocalTime().toString())) {

                    AgendaModel a = this.data.getArrayAgenda().get(i);
                    Label name = new Label("  " + a.getNomreu());
                    Label hours = new Label("  " + a.getDebutReu().toLocalTime().toString() + " - " + a.getFinReu().toLocalTime().toString());
                    name.getStyleClass().add("label");
                    pane = new Pane(new VBox(name, hours));
                    pane.getStyleClass().add("reun");
                    //System.err.println( debut.toLocalDate() + " EGAL " + dataToDate(this.data.getArrayAgenda().get(i)));
                    if (a.getColor() != null) {
                        pane.setStyle("-fx-background-color: " + a.getColor() + ";");
                    }
                }
            }
            //System.out.println(i);
        }

        Pane finalPane = pane;
        pane.setOnMouseEntered(e -> {
            popOver.show(finalPane);
        });
        pane.setOnMouseExited(e -> {
            popOver.hide();
        });
        // catch les erreurs d'indexation
        try {
            this.grid.add(pane, colIndex, rowIndex);
        } catch (NullPointerException e) {
            //System.out.println(colIndex + "  " + rowIndex);
            //e.printStackTrace();
        }
    }

    public ArrayList<LocalDateTime> caseToDate(int colIndex, int rowIndex) {
        ArrayList<LocalDateTime> arr = new ArrayList<>();

        // DATE
        DayOfWeek day = agendaController.ld.getDayOfWeek();

        LocalDate jourdieu = agendaController.ld;
        LocalDate previousMonday = agendaController.ld.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        if (day != DayOfWeek.MONDAY) {
            jourdieu = previousMonday;
        }
        // on incremente le jour pour arrivé sur la bonne case
        for (int i = 0; i < colIndex; i++) {
            jourdieu = jourdieu.plusDays(1);
        }
        // HEURES
        String heure = Constantes.hours.get(rowIndex);
        int horairedebut = Integer.parseInt(heure);

        LocalDateTime heureReu = jourdieu.atTime(horairedebut, 0);
        LocalDateTime finReu = heureReu.plusHours(1);

        // renvoie le debut et la fin de la reunion
        arr.add(heureReu);
        arr.add(finReu);
        return arr;
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("M.A.J");
        //System.out.println(caseToDate(0, 0));
        if (advancements == 1) {
            this.grid.getChildren().retainAll(grid.getChildren().get(0));
        }
        paneOnGrid();
    }

}
