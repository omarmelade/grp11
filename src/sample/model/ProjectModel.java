package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProjectModel {

    private ProjectModel project;
    private SimpleStringProperty nom, description;
    private SimpleIntegerProperty id_projet, id_proprio;


    public ProjectModel(int id_projet, int id_proprio, String nom, String description ){
        this.id_projet = new SimpleIntegerProperty(id_projet);
        this.id_proprio = new SimpleIntegerProperty(id_proprio);
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
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

    // retourne le projet en tant qu'object
    public ProjectModel getProjet(){
        return this.project;
    }
}
