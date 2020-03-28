package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.API.Subscription;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubscriptionController implements Initializable {


    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField nomField;
    @FXML
    private JFXTextField prenomField;
    @FXML
    private JFXPasswordField passField;
    @FXML
    private JFXButton registerButton;

    @FXML
    private void loadConnect(ActionEvent event) {
        try {
            Parent connect = FXMLLoader.load(getClass().getResource("../home.fxml"));
            Scene conn = new Scene(connect);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(conn);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite !");
        }
    }

    @FXML
    public void loadSubscription(ActionEvent event){
        String email = emailField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String pass = passField.getText();
        Subscription sub = new Subscription(email, nom, prenom, pass);
        sub.run();
        if(sub.sub){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("../sceneHome.fxml"));
                Scene dashboard = new Scene(root);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(dashboard);
                appStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Impossible de charger le dashboard");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inscription impossible.");
            alert.setHeaderText("Champs incorrects");
            alert.setContentText(sub.res);

            alert.showAndWait();
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
