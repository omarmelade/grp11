package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Listener.DecoListener;
import sample.Listener.HomeListener;
import sample.model.PersonModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectScreenControler implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton accueil;
    @FXML
    private JFXButton deconnexion;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab listProject;
    @FXML
    private Tab userProject;
    @FXML
    private Tab userPartProject;
    @FXML
    private Tab createProject;



    private PersonModel pm;


    public ProjectScreenControler(PersonModel pm){
        this.pm = pm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getStylesheets().add(getClass().getResource("../css/Vbox.css").toExternalForm());

        deconnexion.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.root));
        accueil.addEventHandler(MouseEvent.MOUSE_RELEASED, new HomeListener(this.root, this.pm));


        listProject.setContent(new VBox(new Label("Aucun projet n'est encore disponible")));
        userProject.setContent(new VBox(new Label("Aucun projet n'est encore disponible")));
        userPartProject.setContent(new VBox(new Label("Aucun projet n'est encore disponible")));
        createProject.setContent(new VBox(new Label("La création projet n'est encore disponible")));
    }



}
