package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.MainAgendaController;
import sample.model.PersonModel;

import java.io.IOException;

public class AgendaListener implements EventHandler {

     AnchorPane root;
     String uri;
    PersonModel pm;

    public AgendaListener(AnchorPane root, String uri, PersonModel pm) {
        this.root = root;
        this.uri = uri;
        this.pm = pm;
    }


    @Override
    public void handle(Event event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/mainAgenda.fxml"));
        Parent root = null;
        try {
            loader.setController(new MainAgendaController(pm));
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
