package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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


    private PersonModel pm;


    public ProjectScreenControler(PersonModel pm){
        this.pm = pm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deconnexion.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.root));
        accueil.addEventHandler(MouseEvent.MOUSE_RELEASED, new HomeListener(this.root, this.pm));
    }


}
