package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Connexion;

import java.sql.SQLException;

public class UserAddController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;


    @FXML
    public void handleAddPerson() throws SQLException {
        Connexion conn = new Connexion();
        // recuperation des champs
        String email = getEmailField();
        String nom = getFirstnameField();
        String lastname = getLastnameField();
        if(!email.equals("") && !nom.equals("") && !lastname.equals("")) {
            // envoi de la requete sql
            conn.sentToDB(email, nom, lastname, "utilisateurs");
        }else{
            System.out.println("Veuillez remplir tout les champs !");
        }
        // remise a 0 des champs
        setEmailField("");
        setFirstnameField("");
        setLastnameField("");
    }

    // remplace la valeur des champs
    private void setEmailField(String email){ emailField.setText(email); }

    private void setFirstnameField(String fname){ firstnameField.setText(fname); }

    private void setLastnameField(String lname){ lastnameField.setText(lname); }

    // recuperation des valeurs des champs
    public String getEmailField() {
        return emailField.getText();
    }
    public String getFirstnameField() {
        return firstnameField.getText();
    }
    public String getLastnameField() {
        return lastnameField.getText();
    }
}
