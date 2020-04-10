package sample.Listener;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class DecoListener implements EventHandler {

    private AnchorPane root;

    public DecoListener( AnchorPane root){
        this.root = root;
    }


    @Override
    public void handle(Event event) {
        try {
            loadConnectScreen(this.root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConnectScreen(AnchorPane root) throws IOException {
        Parent connect = null;
        try {
            connect = FXMLLoader.load(getClass().getResource("../home.fxml"));
            Scene sub = new Scene(connect);
            sub.getStylesheets().add(getClass().getResource("../css/Subscript.css").toExternalForm());
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(sub);
            stage.show();
        } catch (IOException e) {
            System.out.println("Impossible de changer de page !");
            e.printStackTrace();
        }
    }
}
