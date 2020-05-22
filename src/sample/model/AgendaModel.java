package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Time;

public class AgendaModel {

    private SimpleIntegerProperty id_reu, id_projet;
    private SimpleStringProperty nomreu, dateReu;
    private Time debutReu, finReu;


    public AgendaModel(int id_reu, int id_projet, String nomreu, String dateReu, Time debutReu, Time finReu) {
        this.id_reu = new SimpleIntegerProperty(id_reu);
        this.id_projet = new SimpleIntegerProperty(id_projet);
        this.nomreu = new SimpleStringProperty(nomreu);
        this.dateReu = new SimpleStringProperty(dateReu);
        this.debutReu = debutReu;
        this.finReu = finReu;
    }

    public int getId_reu() {
        return id_reu.get();
    }

    public SimpleIntegerProperty id_reuProperty() {
        return id_reu;
    }

    public int getId_projet() {
        return id_projet.get();
    }

    public SimpleIntegerProperty id_projetProperty() {
        return id_projet;
    }

    public String getNomreu() {
        return nomreu.get();
    }

    public SimpleStringProperty nomreuProperty() {
        return nomreu;
    }

    public String getDateReu() {
        return dateReu.get();
    }

    public SimpleStringProperty dateReuProperty() {
        return dateReu;
    }

    public Time getDebutReu() {
        return debutReu;
    }

    public Time getFinReu() {
        return finReu;
    }

    @Override
    public String toString() {
        return "AgendaModel{" +
                "id_reu=" + id_reu +
                ", id_projet=" + id_projet +
                ", nomreu=" + nomreu +
                ", dateReu=" + dateReu +
                ", debutReu=" + debutReu +
                ", finReu=" + finReu +
                '}';
    }
}
