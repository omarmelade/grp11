package sample.model;


// prendre exemples sur les autres Classe de @model

/*
    doit crée un model pour les Ressources en suivant les données
    de la BDD et de la classe @Ressources
    implemente des getteurs et setteurs pour recuperer les valeurs
*/


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RessourcesModel {
    private IntegerProperty ID;
    private StringProperty nomSalle;
    private StringProperty batiment;
    private IntegerProperty ordi ;
    private IntegerProperty video_proj;
    private IntegerProperty tabl;

    public RessourcesModel(int ID, String nomSalle, String batiment, int ordi, int video_proj, int tabl) {
        this.ID = new SimpleIntegerProperty(ID);
        this.nomSalle = new SimpleStringProperty(nomSalle);
        this.batiment = new SimpleStringProperty(batiment);
        this.ordi = new SimpleIntegerProperty(ordi);
        this.video_proj = new SimpleIntegerProperty(video_proj);
        this.tabl = new SimpleIntegerProperty(tabl);
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public int getID() {
        return ID.get();
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle.set(nomSalle);
    }

    public String getNomSalle() {
        return nomSalle.get();
    }

    public void setBatiment(String batiment) {
        this.batiment.set(batiment);
    }

    public String getBatiment() {
        return batiment.get();
    }

    public void setOrdi(int ordi) {
        this.ordi.set(ordi);
    }

    public int getOrdi() {
        return ordi.get();
    }

    public void setTabl(int tabl) {
        this.tabl.set(tabl);
    }

    public int getTabl() {
        return tabl.get();
    }

    public void setVideo_proj(int video_proj) {
        this.video_proj.set(video_proj);
    }

    public int getVideo_proj() {
        return video_proj.get();
    }

    public String toString() {
        return "Salle{" +
                "numero de la salle ='" + ID + '\'' +
                "nom de la salle ='" + nomSalle + '\'' +
                "du batiment ='" + batiment + '\'' +
                "ordinateur ='" + ordi + '\'' +
                ", vidéo-projecteur='" + video_proj + '\'' +
                ", tableau ='" + tabl  +
                '}';
    }
}
