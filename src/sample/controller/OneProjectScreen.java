package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.Rating;
import sample.API.Project;
import sample.Listener.AddUserProjetListener;
import sample.Listener.AgendaProjetListener;
import sample.Listener.ValidOrRemoveListener;
import sample.model.PersonModel;
import sample.model.PersonTable;
import sample.model.ProjectModel;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OneProjectScreen implements Initializable {

    @FXML
    private AnchorPane rootProjetView;
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
    private Tab planningTab;
    @FXML
    private JFXButton joinProj;
    @FXML
    private JFXButton planningBtn;
    @FXML
    AnchorPane agenda;


    public PersonModel user;
    public ProjectModel projet;

    public OneProjectScreen(PersonModel user, ProjectModel projet) {
        this.user = user;
        this.projet = projet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/cardProj.fxml"));
        projTitle.setText(projet.getNom().toUpperCase());
        infoGenerale.getStylesheets().add(getClass().getResource("/sample/css/Vbox.css").toExternalForm());
        projetBase.getStylesheets().add(getClass().getResource("/sample/css/Vbox.css").toExternalForm());
        Rating note = new Rating ((int) projet.getNote());
        note.setMax((int) projet.getNote());
        note.setRating((int) projet.getNote());
        note.setDisable(true);
        infoGenerale.getChildren().add(new VBox
                (new Label(projet.getDescription()), new Label("DEBUT : " + projet.getDateCrea()), new Label("FIN : " + projet.getDateFin()), new Label("PRIORITÉ : "), note));

        planningBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new AgendaProjetListener(agenda, projet));
        joinTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (joinTab.isSelected()) {
                    setJoining();
                }
            }
        });

        joinProj.addEventHandler(MouseEvent.MOUSE_RELEASED, new AddUserProjetListener(this.projet, this.user, this.joinProj));

        listMembres.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t)
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
                        if (pm.getValide() == 0) {
                            InputStream input = this.getClass().getResourceAsStream("/sample/ressources/2x/round_check_circle_black_48dp.png");
                            InputStream input2 = this.getClass().getResourceAsStream("/sample/ressources/2x/round_remove_circle_black_48dp.png");
                            ImageView check = new ImageView( new Image(input, 40, 40    , true, true));
                            ImageView remove = new ImageView(new Image(input2, 40, 40, true, true));
                            JFXButton validation = new JFXButton();
                            JFXButton removing = new JFXButton();
                            validation.setGraphic(check);
                            validation.setShape(new Circle(0.5));
                            validation.setRipplerFill(Color.web("#931621"));
                            removing.setGraphic(remove);
                            removing.setShape(new Circle(0.5));
                            removing.setRipplerFill(Color.web("#931621"));
                            validation.addEventHandler(MouseEvent.MOUSE_RELEASED, new ValidOrRemoveListener(this.user, pm, this.projet, "update", validation, removing, rootProjetView));
                            removing.addEventHandler(MouseEvent.MOUSE_RELEASED, new ValidOrRemoveListener(this.user, pm, this.projet, "delete", validation, removing, rootProjetView));
                            gp.add(validation, 4, i);
                            gp.add(removing, 5, i);
                        }
                        i++;
                    }
                }
            } else {
                for (PersonModel pm : membres.getTablePersonModels()) {
                    if (i == 2) {
                        gp.add(new Label("Chef de projet : \n"), 1, 1);
                        for (PersonModel pmb : membres.getTablePersonModels()) {
                            System.out.println(pmb);
                            if (pmb.getId() == this.projet.getId_proprio()) {
                                gp.add((new Label(pmb.getNom() + " " + pmb.getPrenom())), 2, i);
                            }
                        }
                        i++;
                    }

                    else if (i == 3) {
                        gp.add(new Label("Membres du projet : \n"), 1, 3);
                        i++;
                    }
                        if(pm.getId() != this.projet.getId_proprio()) {
                            gp.add((new Label(pm.getPrenom() + " " + pm.getNom())), 2, i);
                            gp.add((new Label((pm.getValide() == 1 ? " " : "En attente de validation"))), 4, i);
                            i++;
                        }
                }
            }
            listMembres.setContent(gp);
    }
}
