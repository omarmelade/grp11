package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.MainAgendaController;

import java.io.IOException;

public class AgendaListener implements EventHandler {

    private AnchorPane root;
    private String uri;

    public AgendaListener(AnchorPane root, String uri) {
        this.root = root;
        this.uri = uri;
    }

    @Override
    public void handle(Event event) {
        try {
            loadScreen(this.root, this.uri);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
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
