package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.API.Project;
import sample.Listener.AddUserProjetListener;
import sample.model.PersonModel;
import sample.model.PersonTable;
import sample.model.ProjectModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OneProjectScreen implements Initializable {

    @FXML
    private JFXTabPane projetBase;
    @FXML
    private Label projTitle;
    @FXML
    private AnchorPane infoGenerale;
    @FXML
    private Tab listMembres;

    @FXML
    private Tab joinTab;
    @FXML
    private JFXButton joinProj;



    private PersonModel user;
    private ProjectModel projet;

    public OneProjectScreen(PersonModel user, ProjectModel projet) {
        this.user = user;
        this.projet = projet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projTitle.setText(projet.getNom().toUpperCase());
        infoGenerale.getStylesheets().add(getClass().getResource("../css/Vbox.css").toExternalForm());
        projetBase.getStylesheets().add(getClass().getResource("../css/Vbox.css").toExternalForm());
        infoGenerale.getChildren().add(new Label(projet.getDescription()));

        joinTab.setOnSelectionChanged(new EventHandler<Event>() { @Override public void handle(Event t) {
            if(joinTab.isSelected()) { setJoining(); }
        }});

        joinProj.addEventHandler(MouseEvent.MOUSE_RELEASED, new AddUserProjetListener(this.projet, this.user, this.joinProj));

        listMembres.setOnSelectionChanged(new EventHandler<Event>() { @Override public void handle(Event t)
            { if(listMembres.isSelected()) {
                try {
                    afficheMembre();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }}});

    }

    private void setJoining() {
        if(this.projet.getListMembres().estDansListe(this.user)){
            joinProj.setText("REJOINDRE LE PROJET");
            joinProj.setDisable(true);
        }
    }

    private void afficheMembre() throws SQLException {

            //System.out.println("je suis proprio !!!!!!");
            Project p = new Project("getUser", this.projet.getId_projet());
            p.run();
            PersonTable membres = p.getMembersProjet();
            GridPane gp = new GridPane();
            int i = 2;
            int boucle = 0;
            if(this.user.getId() == this.projet.getId_proprio()) {
                for (PersonModel pm : membres.getTablePersonModels()) {
                    if (i == 2) {
                        gp.add(new Label("Chef de projet : \n"), 1, 1);
                        gp.add((new Label("Vous même ! " )), 2, i);
                        i++;
                    }
                    if (i == 3) {
                        gp.add(new Label("Membres du projet : \n"), 1, 3);
                        i++;
                    }
                    if(pm.getId() != this.projet.getId_proprio()) {
                        gp.add((new Label(pm.getPrenom() +" "+ pm.getNom())), 2, i);
                        gp.add((new Label((pm.getValide() == 1 ? "Validé" : "non-Validé"))), 4, i);
                        i++;
                    }
                }
            }
            else{
                for (PersonModel pm : membres.getTablePersonModels()) {
                    if (i == 2) {
                        gp.add(new Label("Chef de projet : \n"), 1, 1);
                        for (PersonModel pmb : membres.getTablePersonModels()) {
                            System.out.println(pmb);
                            if(pmb.getId() == this.projet.getId_proprio()){
                                gp.add((new Label(pmb.getNom() + " " + pmb.getPrenom())), 2, i);
                            }
                        }
                        i++; }

                    else if (i == 3) {
                        gp.add(new Label("Membres du projet : \n"), 1, 3);
                        i++;
                    }
                        if(pm.getId() != this.projet.getId_proprio()) {
                            gp.add((new Label(pm.getPrenom() + " " + pm.getNom())), 2, i);
                            gp.add((new Label((pm.getValide() == 1 ? "" : "En attente de validation"))), 4, i);
                            i++;
                        }
                }

            }
            listMembres.setContent(gp);


    }
}
