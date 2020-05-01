package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.CompteController;
import sample.controller.RessourcesController;
import sample.model.PersonModel;

import java.io.IOException;

public class RessourcesListener implements EventHandler {

    private AnchorPane root;
    private String uri;
    private PersonModel pm;

    public RessourcesListener(AnchorPane root, String uri, PersonModel pm) {
        this.root = root;
        this.uri = uri;
        this.pm = pm;
    }

    @Override
    public void handle(Event event) {
        try {
            loadScreen(this.root, this.uri, this.pm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScreen(AnchorPane root, String uri, PersonModel pm ) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(uri));
        loader.setController(new RessourcesController(pm));
        Parent def = loader.load();
        Scene scene = new Scene(def);
        Stage appStage = (Stage) root.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}
