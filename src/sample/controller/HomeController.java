package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Listener.AccountListener;
import sample.Listener.DecoListener;
import sample.Listener.RessourcesListener;
import sample.model.PersonModel;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public AnchorPane anchorBack;
    @FXML
    private Label nameHello;
    @FXML
    private JFXButton agenda;
    @FXML
    private JFXButton deconnexion;
    @FXML
    private JFXButton projetbtn;
    @FXML
    private JFXButton comptebtn;
    @FXML
    private JFXButton messagerie;

    PersonModel pm;


    public HomeController(PersonModel pm){
        this.pm = pm;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // creation de l'espace d'affichage Personnalisé

        String name = this.pm.getPrenom().substring(0,1).toUpperCase() + this.pm.getPrenom().substring(1);

        nameHello.setText(nameHello.getText() + " " + name + ",");
        // creation du bouton deconnexion
        deconnexion.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.anchorBack));

        comptebtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new AccountListener(this.anchorBack, "/sample/view/compte.fxml", getPm()));

        projetbtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try { loadProjectScreen(); } catch (IOException e) { e.printStackTrace(); System.out.println("ff"); }}}
        );

        agenda.setDisable(true);
        messagerie.setVisible(false);
        if(this.pm.estAdmin()){
            messagerie.setVisible(true);
            messagerie.addEventHandler(MouseEvent.MOUSE_RELEASED, new RessourcesListener(this.anchorBack, "/sample/view/gestionressources.fxml", getPm()));
        }

    }




    public void loadProjectScreen() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/projet.fxml"));
            loader.setController(new ProjectScreenControler(getPm()));
            Parent proj = loader.load();
            Scene sub = new Scene(proj);
            Stage stage = (Stage) anchorBack.getScene().getWindow();
            stage.setScene(sub);
            stage.show();
        } catch (IOException e) {
            System.out.println("Impossible de changer de page !");
            e.printStackTrace();
        }
    }


    public PersonModel getPm() {
        return pm;
    }

    public Stage getStage(){
        return  (Stage) anchorBack.getScene().getWindow();
    }
}
