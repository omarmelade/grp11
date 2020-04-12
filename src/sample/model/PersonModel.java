package sample.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonModel {

    private IntegerProperty id;
    private StringProperty email;
    private StringProperty nom;
    private StringProperty prenom;


    public PersonModel(int id, String email, String nom, String prenom){
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
    }

    public PersonModel(int id, String email, String nom, String prenom, int valide){
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
    }

    public int getId() { return id.get(); }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty emailProperty() { return email; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }

    public String getEmail() { return email.get(); }

    public String getNom() { return nom.get(); }

    public String getPrenom() { return prenom.get(); }

    public void setEmail(String email) { this.email.set(email); }

    public void setNom(String nom) { this.nom.set(nom); }

    public void setPrenom(String prenom) { this.prenom.set(prenom); }

    @Override
    public String toString() {
        return "Person{" +
                " email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
