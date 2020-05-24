package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProjectModel {

    private ProjectModel project;

    private SimpleStringProperty nom, description, email_proprio, dateCrea, dateFin;
    private SimpleIntegerProperty id_projet, id_proprio;
    private SimpleDoubleProperty note;


    private PersonTable listMembres;


    public ProjectModel(int id_projet, int id_proprio, double notes, String nom, String description, String email_proprio, String dateCrea, String dateFin) {
        this.id_projet = new SimpleIntegerProperty(id_projet);
        this.id_proprio = new SimpleIntegerProperty(id_proprio);
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.email_proprio = new SimpleStringProperty(email_proprio);
        this.note = new SimpleDoubleProperty(notes);
        this.dateCrea = new SimpleStringProperty(dateCrea);
        this.dateFin = new SimpleStringProperty(dateFin);
    }

    public ProjectModel(int id_projet, int id_proprio, String nom, String description, String email_proprio, PersonTable listMembres, double notes, String dateCrea, String dateFin) {
        this.id_projet = new SimpleIntegerProperty(id_projet);
        this.id_proprio = new SimpleIntegerProperty(id_proprio);
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.email_proprio = new SimpleStringProperty(email_proprio);
        this.listMembres = listMembres;
        this.note = new SimpleDoubleProperty(notes);
        this.dateCrea = new SimpleStringProperty(dateCrea);
        this.dateFin = new SimpleStringProperty(dateFin);
    }

    public double getNote() {
        return note.get();
    }

    ;

    public SimpleDoubleProperty noteProperty() {
        return note;
    }

    public PersonTable getListMembres() {
        return listMembres;
    }

    public String getEmail_proprio() {
        return email_proprio.get();
    }

    public SimpleStringProperty email_proprioProperty() {
        return email_proprio;
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public int getId_projet() {
        return id_projet.get();
    }

    public SimpleIntegerProperty id_projetProperty() {
        return id_projet;
    }

    public int getId_proprio() {
        return id_proprio.get();
    }

    public SimpleIntegerProperty id_proprioProperty() {
        return id_proprio;
    }

    public String getDateCrea() {
        return dateCrea.get();
    }

    public SimpleStringProperty dateCreaProperty() {
        return dateCrea;
    }

    public String getDateFin() {
        return dateFin.get();
    }

    public SimpleStringProperty dateFinProperty() {
        return dateFin;
    }

    // retourne le projet en tant qu'object
    public ProjectModel getProjet() {
        return this.project;
    }


}
