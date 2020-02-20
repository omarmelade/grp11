package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
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
import java.sql.Statement;
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
    private TableView<PersonModel> personTable;
    @FXML
    private TableColumn<PersonModel, String> col_nom;
    @FXML
    private TableColumn<PersonModel, String> col_prenom;
    @FXML
    private TableColumn<PersonModel, String> col_email;

    // reference vers la classe Main

    private Main main;

    public PersonOverviewController() {
    }

    // appelée automatiquement après le chargement du FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataFromDatabase();
    }

    @FXML
    private void loadDataFromDatabase(){
        ObservableList<PersonModel> oblist = FXCollections.observableArrayList();

        try {
            Connection conn = Connexion.getConnection();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM utilisateurs");

            while(rs.next()){
                oblist.add(new PersonModel(rs.getInt("id_user"), rs.getString("email"),rs.getString("nom"),rs.getString("prenom")));
            }

        }catch (SQLException ex){
            System.err.println("Error" + ex);
        }

        //emailColonne.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        //nomColonne.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        //prenomColonne.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());


        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

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
        loadDataFromDatabase();
    }

    @FXML
    public void handleDeletePerson() throws SQLException {
        Statement stmt = null;
        try{
            Connection conn = Connexion.getConnection();

            PersonModel pm = personTable.getSelectionModel().getSelectedItem();
            System.out.println(pm.getId());
            if(pm != null) {
                stmt = conn.createStatement();
                String sql = "DELETE FROM utilisateurs WHERE id_user=" + pm.getId();
                stmt.executeUpdate(sql);

                System.out.println("L'utilisateur a bien été supprimé");
            }else{
                System.out.println("Veuillez selectionner un utilisateurs");
            }
            conn.close();
            stmt.close();
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Un probleme est survenu.");
        }

        // remise a 0 des champs
        setEmailField("");
        setFirstnameField("");
        setLastnameField("");

        loadDataFromDatabase();
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
