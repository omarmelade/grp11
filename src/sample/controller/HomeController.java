package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.model.PersonModel;

import javax.swing.*;
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
        String name = this.pm.getPrenom().substring(0,1).toUpperCase() + this.pm.getPrenom().substring(1);
        Label msg = new Label("Hi, " + name);

        ImageView imageView = new ImageView(new Image("sample/ressources/2x/baseline_home_black_48dp.png"));
        JFXButton btn = new JFXButton("Home", imageView);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        ImageView accView = new ImageView(new Image("sample/ressources/2x/baseline_account_box_black_48dp.png"));
        JFXButton btnAcc = new JFXButton("Mon Compte", accView);
        btnAcc.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        ImageView settview  = new ImageView(new Image("sample/ressources/2x/baseline_settings_black_48dp.png"));
        JFXButton btnSett = new JFXButton("Settings", settview);
        btnSett.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        GridPane gp = new GridPane();
        gp.getStyleClass().add("grid-pane");
        gp.add(btn, 0,0);
        gp.add(btnAcc, 1,0);
        gp.add(btnSett, 2,0);

        VBox vb2 = new VBox();
        vb2.getChildren().add(msg);
        vb2.getChildren().add(gp);

        // creation des onglets
        Tab tab = new Tab("DashBoard", vb2);
        // ajout des onglets a TabPane (defini plus haut)
        tabPane.getTabs().add(tab);
    }




}
