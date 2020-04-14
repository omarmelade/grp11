package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.API.Subscription;
import sample.Listener.DecoListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class SubscriptionController implements Initializable {

    @FXML
    public Label connexion;
    @FXML
    public AnchorPane anchorBack;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField nomField;
    @FXML
    private JFXTextField prenomField;
    @FXML
    private JFXPasswordField passField;
    @FXML
    private JFXCheckBox adminBool;
    @FXML
    private JFXButton registerButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connexion.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.anchorBack));

        connexion.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                connexion.getStyleClass().add("hover-btn");
            }
        });

        connexion.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                connexion.getStyleClass().clear();
            }
        });

        registerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadSubscription();
            }
        });

        anchorBack.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) { if(event.getCode() == KeyCode.ENTER){ loadSubscription(); }}
        });



    }



    public void loadSubscription(){
        int admin = adminBool.isSelected() ? 1 : 0;
        String email = emailField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String pass = passField.getText();
        Subscription sub = new Subscription(email, nom, prenom, pass);
        sub.run();
        if(!email.equals("") && !pass.equals("")) {
            if (sub.sub) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/connectmembre.fxml"));

                    HomeController hm = new HomeController(sub.getNewUser());
                    loader.setController(hm);
                    Parent root = loader.load();
                    //Parent root = FXMLLoader.load(getClass().getResource("../view/connectmembre.fxml"));
                    Scene dashboard = new Scene(root);
                    Stage appStage = (Stage) anchorBack.getScene().getWindow();
                    appStage.setScene(dashboard);
                    appStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Impossible de charger le dashboard");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inscription impossible.");
                alert.setHeaderText(sub.res);
                alert.setContentText(sub.res);

                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inscription impossible.");
            alert.setHeaderText("Veuillez remplir TOUT les champs");
            alert.setContentText(sub.res);

            alert.showAndWait();
        }
    }


}
