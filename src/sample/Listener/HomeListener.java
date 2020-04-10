package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.HomeController;
import sample.model.PersonModel;

import java.io.IOException;

public class HomeListener implements EventHandler {

    private AnchorPane root;
    private PersonModel pm;

    public HomeListener(AnchorPane root, PersonModel pm) {
        this.root = root;
        this.pm = pm;
    }

    @Override
    public void handle(Event event) {
        try {
            loadHomeScreen(this.root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHomeScreen(AnchorPane root) throws IOException {
        // on charge la vue
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/connectmembre.fxml"));
        // on charge le controller
        loader.setController(new HomeController(this.pm));
        // on charge le parent
        Parent blah = loader.load();
        Scene scene = new Scene(blah);
        Stage appStage = (Stage) root.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}
