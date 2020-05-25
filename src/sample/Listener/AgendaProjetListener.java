package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.AgendaController;
import sample.model.ProjectModel;

import java.io.IOException;

public class AgendaProjetListener implements EventHandler {

    AnchorPane root;
    ProjectModel project;

    public AgendaProjetListener(AnchorPane root, ProjectModel projet) {
        this.root = root;
        this.project = projet;
    }

    @Override
    public void handle(Event event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/agenda.fxml"));
        Parent root = null;
        try {
            loader.setController(new AgendaController(project, "proj", -1, -1));
            root = loader.load();
            Scene secondScene = new Scene(root);
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(root.getScene().getWindow().getX() + 40);
            newWindow.setY(root.getScene().getWindow().getY() + 20);
            newWindow.setResizable(false);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
