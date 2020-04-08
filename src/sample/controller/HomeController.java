package sample.controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.model.PersonModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    StackPane rootPane;
    @FXML
    JFXTabPane tabPane;

    PersonModel pm;


    public HomeController(PersonModel pm){
        this.pm = pm;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // creation de l'espace d'affichage du Tab
        VBox vb = new VBox(new Label("Hello" + this.pm.getPrenom()));
        VBox vb2 = new VBox(new Label("Salut"));
        // creation des onglets
        Tab tab1 = new Tab("DashBoard", vb);
        Tab tab2 = new Tab("Agenda", vb2);
        // ajout des onglets a TabPane (defini plus haut)
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
    }




}
