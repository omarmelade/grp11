package sample.model;


// prendre exemples sur les autres Classe de @model

/*
    doit crée un model pour les Ressources en suivant les données
    de la BDD et de la classe @Ressources
    implemente des getteurs et setteurs pour recuperer les valeurs
*/


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RessourcesModel {

    private RessourcesModel rm;

    private SimpleIntegerProperty id_salle, ordi, video_proj, nbplace;
    private SimpleStringProperty nom_salle, batiment;

    public RessourcesModel(int id_salle, int ordi, int video_proj, int nbplace, String nom_salle, String batiment) {
        this.id_salle = new SimpleIntegerProperty(id_salle);
        this.ordi = new SimpleIntegerProperty(ordi);
        this.video_proj = new SimpleIntegerProperty(video_proj);
        this.nbplace = new SimpleIntegerProperty(nbplace);
        this.nom_salle = new SimpleStringProperty(nom_salle);
        this.batiment = new SimpleStringProperty(batiment);
    }

    public RessourcesModel(int ordi, int video_proj, int nbplace, String nom_salle, String batiment) {
        this.ordi = new SimpleIntegerProperty(ordi);
        this.video_proj = new SimpleIntegerProperty(video_proj);
        this.nbplace = new SimpleIntegerProperty(nbplace);
        this.nom_salle = new SimpleStringProperty(nom_salle);
        this.batiment = new SimpleStringProperty(batiment);
    }

    public int getNbplace() {
        return nbplace.get();
    }

    public SimpleIntegerProperty nbplaceProperty() {
        return nbplace;
    }

    public int getId_salle() {
        return id_salle.get();
    }

    public SimpleIntegerProperty id_salleProperty() {
        return id_salle;
    }

    public int getOrdi() {
        return ordi.get();
    }

    public SimpleIntegerProperty ordiProperty() {
        return ordi;
    }

    public int getVideo_proj() {
        return video_proj.get();
    }

    public SimpleIntegerProperty video_projProperty() {
        return video_proj;
    }

    public String getNom_salle() {
        return nom_salle.get();
    }

    public SimpleStringProperty nom_salleProperty() {
        return nom_salle;
    }

    public String getBatiment() {
        return batiment.get();
    }

    public SimpleStringProperty batimentProperty() {
        return batiment;
    }

    @Override
    public String toString() {
        return "RessourcesModel{" +
                " id_salle=" + id_salle +
                ", ordi=" + ordi +
                ", video_proj=" + video_proj +
                ", tableau=" + nbplace +
                ", nom_salle=" + nom_salle +
                ", batiment=" + batiment +
                '}';
    }
}
