package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.OneProjectScreen;
import sample.model.PersonModel;
import sample.model.ProjectModel;

import java.io.IOException;

public class OneProjectListener implements EventHandler {

    PersonModel user;
    ProjectModel projet;

    public OneProjectListener(PersonModel pm, ProjectModel projectModel, AnchorPane root) {
        this.user = pm;
        this.projet = projectModel;
    }

    @Override
    public void handle(Event event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/projetclic.fxml"));
        Parent root = null;
        try {
            loader.setController(new OneProjectScreen(this.user , this.projet));
            root = loader.load();
            Scene secondScene = new Scene(root);
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle(projet.getNom().toUpperCase());
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(root.getScene().getWindow().getX() + 200);
            newWindow.setY(root.getScene().getWindow().getX() + 100);

            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
