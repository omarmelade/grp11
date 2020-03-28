package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.API.Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXTextField emailField;
    @FXML
    JFXPasswordField passField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initDrawer();
    }

/*    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("../view/toolbarController.fxml"));
            drawer.setSidePane(toolbar);
            drawer.close();
        } catch (IOException ignored) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ignored);
        }

        HamburgerBackArrowBasicTransition task = new HamburgerBackArrowBasicTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
    }*/

    @FXML
    private void loadConnect(ActionEvent event) {
        String email = emailField.getText();
        String pass = passField.getText();
        Login login = new Login(email, pass);
        login.run();
        if(login.connected) {
            try {
                Parent blah = FXMLLoader.load(getClass().getResource("../sceneHome.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            } catch (IOException e) {
                System.out.println("Une erreur s'est produite !");
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
}
