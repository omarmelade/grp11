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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GridPaneTrackController implements Initializable, Observer {

    final int numCols = 4;
    int numRows = 5;

    @FXML
    private GridPane grid;
    @FXML
    private PopOver popOver;

    private AgendaTable data;
    private AgendaController agendaController;
    public DateTimeFormatter formatter;

    public GridPaneTrackController(AgendaController agendaController) {
        this.agendaController = agendaController;
        this.data = agendaController.getData();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    public LocalDate dataToDate(AgendaModel data) {
        String date = data.getDateReu();
        return LocalDate.parse(date, formatter);
    }

    public boolean memeSemaine(String date1, String date2) {
        return agendaController.getWeekNum(date1) == agendaController.getWeekNum(date2);
    }

    public void initialize() {

        for (int i = 0; i <= numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i <= numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.grid.getRowConstraints().add(rowConstraints);
        }

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

        Pane pane = new Pane(new Label("Clique."));

        pane.setOnMouseEntered(e -> {
            popOver.show(pane);
        });
        pane.setOnMouseExited(e -> {
            popOver.hide();
        });
        // catch les erreurs d'indexation
        try {
            this.grid.add(pane, colIndex, rowIndex);
        } catch (NullPointerException e) {
            System.out.println(colIndex + "  " + rowIndex);
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
        LocalDateTime finReu = heureReu.plusHours(2);

        // renvoie le debut et la fin de la reunion
        arr.add(heureReu);
        arr.add(finReu);
        return arr;
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println(caseToDate(0, 0));
        paneOnGrid();
    }


}
