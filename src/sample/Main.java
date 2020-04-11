package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;



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

        // ecran de connexion
//        Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        loadScreen(primaryStage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        });
    }

    public void loadScreen(Stage primaryStage) throws IOException {
        StackPane sp = new StackPane();
        Parent main = FXMLLoader.load(getClass().getResource("home.fxml"));
        sp.getChildren().add(main);
        Scene sc = root.getScene();
        sc.getStylesheets().add(getClass().getResource("../css/Subscript.css").toExternalForm());
        primaryStage.setScene(sc);
        primaryStage.show();
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
