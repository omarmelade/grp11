package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Listener.DecoListener;
import sample.Listener.HomeListener;
import sample.model.PersonModel;

import java.net.URL;
import java.util.ResourceBundle;

public class CompteController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    public JFXButton homebtn;
    @FXML
    public JFXButton deconnexionbtn;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    public Label nomInfoLabel;
    @FXML
    public Label prenomInfoLabel;
    @FXML
    public Label emailInfoLabel;

    private PersonModel pm;

    public CompteController(PersonModel pm) {
        this.pm = pm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomInfoLabel.setText(pm.getNom().toUpperCase());
        String prenom = this.pm.getPrenom().substring(0,1).toUpperCase() + this.pm.getPrenom().substring(1);
        prenomInfoLabel.setText(prenom);
        emailInfoLabel.setText(pm.getEmail() + "@ressources.org");

        deconnexionbtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.root));
        homebtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new HomeListener(this.root, this.pm));
    }
}
