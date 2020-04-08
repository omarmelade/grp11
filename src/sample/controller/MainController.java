package sample.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import sample.API.Login;
import sample.Main;
import sample.model.PersonModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class MainController implements Initializable {


    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXTextField emailField;
    @FXML
    JFXPasswordField passField;

    PersonModel pm;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    private void loadConnect(ActionEvent event) {
        String email = emailField.getText();
        String pass = passField.getText();
        Login login = new Login(email, pass);
        login.run();
        if(login.connected) {
            try {
                // recuperation de l'utilisateur connnecté
                this.pm = login.getPm();
                // insctanciation du controller et passage de user en param
                HomeController hm = new HomeController(this.pm);
                // on charge la vue
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneHome.fxml"));
                // on charge le controller
                loader.setController(hm);
                // on charge le parent
                Parent blah = loader.load();
                    // ancienne maneire de faire (sans charger de controller)
                    // ne permet pas d'avoir un constructeur
                //Parent blah = FXMLLoader.load(getClass().getResource("../sceneHome.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            } catch (IOException e) {
                System.out.println("Une erreur s'est produite !" + e);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentification impossible");
            alert.setHeaderText("Champs incorrects");
            alert.setContentText("Votre email ou votre mot de passe sont incorrects.");

            alert.showAndWait();
        }
    }

    @FXML
    private void loadSubscription(ActionEvent event) {
        try {
            Parent subscription = FXMLLoader.load(getClass().getResource("../homeSubscription.fxml"));
            Scene sub = new Scene(subscription);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(sub);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite !");
        }
    }


    public PersonModel getConnectedPm() {
        return this.pm;
    }
}
