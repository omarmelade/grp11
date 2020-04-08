package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controller.HomeController;
import sample.controller.MainController;

import java.util.Locale;
import java.util.ResourceBundle;

import static sun.util.resources.LocaleData.getBundle;


public class Main extends Application {

    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        // connexion a la base de données
        launch(args);
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
