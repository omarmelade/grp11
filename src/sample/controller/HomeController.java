package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Listener.AccountListener;
import sample.Listener.DecoListener;
import sample.model.PersonModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public AnchorPane anchorBack;
    @FXML
    private Label nameHello;
    @FXML
    private JFXButton agenda;
    @FXML
    private JFXButton deconnexion;
    @FXML
    private JFXButton projetbtn;
    @FXML
    private JFXButton comptebtn;

    PersonModel pm;


    public HomeController(PersonModel pm){
        this.pm = pm;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // creation de l'espace d'affichage Personnalisé

        String name = this.pm.getPrenom().substring(0,1).toUpperCase() + this.pm.getPrenom().substring(1);

        nameHello.setText(nameHello.getText() + " " + name + ",");
        // creation du bouton deconnexion
        deconnexion.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.anchorBack));

        comptebtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new AccountListener(this.anchorBack, "../view/compte.fxml", getPm()));

        projetbtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try { loadProjectScreen(); } catch (IOException e) { e.printStackTrace(); System.out.println("ff"); }}}
        );



        agenda.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Label secondLabel = new Label("I'm a Label on new Window");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/projetclic.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                    Scene secondScene = new Scene(root, 1000, 800);
                    // New window (Stage)
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Second Stage");
                    newWindow.setScene(secondScene);

                    // Set position of second window, related to primary window.
                    newWindow.setX(getStage().getX() + 200);
                    newWindow.setY(getStage().getY() + 100);

                    newWindow.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }




    public void loadProjectScreen() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/projet.fxml"));
            loader.setController(new ProjectScreenControler(getPm()));
            Parent proj = loader.load();
            Scene sub = new Scene(proj);
            Stage stage = (Stage) anchorBack.getScene().getWindow();
            stage.setScene(sub);
            stage.show();
        } catch (IOException e) {
            System.out.println("Impossible de changer de page !");
            e.printStackTrace();
        }
    }


    public PersonModel getPm() {
        return pm;
    }

    public Stage getStage(){
        return  (Stage) anchorBack.getScene().getWindow();
    }
}
