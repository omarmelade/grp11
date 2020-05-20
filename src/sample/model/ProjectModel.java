package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class ProjectModel {

    private ProjectModel project;

    private SimpleStringProperty nom, description, email_proprio;
    private SimpleIntegerProperty id_projet, id_proprio;
    private Date dateFin;

    private PersonTable listMembres;


    public ProjectModel(int id_projet, int id_proprio, String nom, String description, String email_proprio,Date dateFin ){
        this.id_projet = new SimpleIntegerProperty(id_projet);
        this.id_proprio = new SimpleIntegerProperty(id_proprio);
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.email_proprio = new SimpleStringProperty(email_proprio);
        this.dateFin = new Date(String.valueOf(dateFin));
    }

    public ProjectModel(int id_projet, int id_proprio, String nom, String description, String email_proprio, PersonTable listMembres,Date dateFin  ){
        this.id_projet = new SimpleIntegerProperty(id_projet);
        this.id_proprio = new SimpleIntegerProperty(id_proprio);
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.email_proprio = new SimpleStringProperty(email_proprio);
        this.listMembres = listMembres;
        this.dateFin = new Date(String.valueOf(dateFin));
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

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    // retourne le projet en tant qu'object
    public ProjectModel getProjet(){
        return this.project;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "nom=" + nom +
                ", description=" + description +
                ", id_projet=" + id_projet +
                ", id_proprio=" + id_proprio +
                ", date de fin =" + dateFin +
                '}';
    }
}
