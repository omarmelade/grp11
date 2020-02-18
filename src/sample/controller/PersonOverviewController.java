package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Connexion;
import sample.Main;
import sample.model.PersonModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PersonOverviewController implements Initializable {
    // user add
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;

    // user print
    @FXML
    private TableView<PersonModel> personTable = new TableView<>();
    @FXML
    private TableColumn<PersonModel, String> nomColonne = new TableColumn<>();
    @FXML
    private TableColumn<PersonModel, String> prenomColonne = new TableColumn<>();
    @FXML
    private TableColumn<PersonModel, String> emailColonne = new TableColumn<>();

    // reference vers la classe Main

    ObservableList<PersonModel> oblist = FXCollections.observableArrayList();
    private Main main;

    public PersonOverviewController() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void loadDataFromDatabase(javafx.event.ActionEvent event){
        try {
            Connection conn = Connexion.getConnection();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM utilisateurs");

            while(rs.next()){
                System.out.println(rs.getString("id_user"));
                oblist.add(new PersonModel(rs.getString(2),rs.getString(3),rs.getString(4)));
            }

        }catch (SQLException ex){
            System.err.println("Error" + ex);
        }

        //emailColonne.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColonne.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        nomColonne.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColonne.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        //nomColonne.setCellValueFactory(new PropertyValueFactory<>("nom"));
        //prenomColonne.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        personTable.setItems(oblist);

    }


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
