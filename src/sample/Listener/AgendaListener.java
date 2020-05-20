package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.API.Agenda;
import sample.controller.AgendaController;
import sample.controller.MainAgendaController;
import sample.controller.OneProjectScreen;
import sample.model.PersonModel;
import sample.model.ProjectModel;

import java.io.IOException;

public class AgendaListener implements EventHandler {

     AnchorPane root;
     String uri;

    public AgendaListener(AnchorPane root, String uri) {
        this.root = root;
        this.uri = uri;
    }

    @Override
    public void handle(Event event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/agenda.fxml"));
        Parent root = null;
        try {
            loader.setController(new AgendaController());
            root = loader.load();
            Scene secondScene = new Scene(root);
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(root.getScene().getWindow().getX() + 200);
            newWindow.setY(root.getScene().getWindow().getX() + 100);
            newWindow.setResizable(false);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadScreen(AnchorPane root, String uri) throws IOException {
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(uri));
        loader.setController(new MainAgendaController());
        Parent def = loader.load();
        Scene s = new Scene(def);
        Stage appStage = (Stage) root.getScene().getWindow();
        appStage.setScene(s);
        appStage.show();
    }
}
