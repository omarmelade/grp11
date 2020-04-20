package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.API.Ressources;


public class Main extends Application{

    public Stage primaryStage;
    public StackPane root;

    public static void main(String[] args){
        // connexion a la base de données
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        root = new StackPane();

        // ecran vide
        Parent main = FXMLLoader.load(getClass().getResource("home.fxml"));
        root.getChildren().add(main);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
