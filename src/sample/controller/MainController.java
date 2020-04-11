package sample.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import sample.API.Login;
import sample.Main;
import sample.model.PersonModel;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {



    @FXML
    public Label inscription;
    @FXML
    public JFXButton logButton;
    @FXML
    public AnchorPane anchorBack;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXTextField emailField;
    @FXML
    JFXPasswordField passField;

    PersonModel pm;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inscription.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadSubscription();
            }
        });
        logButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadConnect();
            }
        });
        inscription.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                inscription.getStyleClass().add("hover-btn");
            }
        });
        inscription.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                inscription.getStyleClass().clear();
            }
        });
        anchorBack.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    loadConnect();
                }
            }
        });

    }


    @FXML
    private void loadConnect() {
        String email = emailField.getText();
        String pass = passField.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(!email.equals("") || !pass.equals("")) {

            Login login = new Login(email, pass);
            login.run();
            if (login.connected) {
                try {
                        // recuperation de l'utilisateur connnecté
                    this.pm = login.getPm();
                        // insctanciation du controller et passage de user en param
                    HomeController hm = new HomeController(this.pm);
                        // on charge la vue
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/connectmembre.fxml"));
                        // on charge le controller
                    loader.setController(hm);
                        // on charge le parent
                    Parent blah = loader.load();
                        // ancienne maneire de faire (sans charger de controller)
                        // ne permet pas d'avoir un constructeur
                        //Parent blah = FXMLLoader.load(getClass().getResource("../sceneHome.fxml"));
                    Scene scene = new Scene(blah);
                    Stage appStage = (Stage) anchorBack.getScene().getWindow();
                    appStage.setScene(scene);
                    appStage.show();
                } catch (IOException e) {
                    System.out.println("Une erreur s'est produite !" + e);
                }
            } else {

                alert.setTitle("Authentification impossible");
                alert.setHeaderText("Champs incorrects");
                alert.setContentText("Votre email ou votre mot de passe sont incorrects.");
                alert.showAndWait();

            }
        }else{
            alert.setTitle("ERREUR");
            alert.setHeaderText("LES CHAMPS SONT VIDES");
            alert.setContentText("Veuillez remplir les champs !");
            alert.showAndWait();
        }
    }


    private void loadSubscription() {
        Parent subscription = null;
        try {
            subscription = FXMLLoader.load(getClass().getResource("../homeSubscription.fxml"));
            Scene sub = new Scene(subscription);
            sub.getStylesheets().add(getClass().getResource("../css/Subscript.css").toExternalForm());
            Stage stage = (Stage) anchorBack.getScene().getWindow();
            stage.setScene(sub);
            stage.show();
        } catch (IOException e) {
            System.out.println("Impossible de changer de page !");
            e.printStackTrace();
        }
    }


    public PersonModel getConnectedPm() {
        return this.pm;
    }
}
