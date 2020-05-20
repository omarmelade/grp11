package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.API.Project;
import sample.API.Ressources;
import sample.Listener.DecoListener;
import sample.Listener.HomeListener;
import sample.Listener.SupprRessourcesListener;
import sample.model.PersonModel;
import sample.model.RessourcesModel;
import sample.model.RessourcesTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class RessourcesController implements Initializable{

    @FXML
    public AnchorPane root;
    @FXML
    public JFXButton homebtn;
    @FXML
    public JFXButton deconnexionbtn;
    @FXML
    public JFXButton createRes;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab listRes;


    // formulaire
    @FXML
    private Tab formRes;
    @FXML
    private JFXTextField batName;
    @FXML
    private JFXTextField nomSalle;
    @FXML
    private JFXTextField nbPlace;
    @FXML
    private JFXTextField videoProj;
    @FXML
    private JFXTextField pc;


    private PersonModel pm;

    public RessourcesController(PersonModel pm) {
        this.pm = pm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabPane.getStylesheets().add(getClass().getResource("/sample/css/scroll.css").toExternalForm());
        try {
            loadRessources();
        } catch (IOException | NullPointerException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }

        listRes.setOnSelectionChanged(new EventHandler<Event>() { @Override public void handle(Event t) { if(listRes.isSelected())
        { try { loadRessources(); } catch (IOException e) { e.printStackTrace(); }}}});


        createRessourcesInit();

        deconnexionbtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.root));
        homebtn.addEventHandler(MouseEvent.MOUSE_RELEASED, new HomeListener(this.root, this.pm));
    }

    private void createRessourcesInit() {
        createRes.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                creeRessources();
            }
        });
    }

    private void creeRessources() {
        String salle = nomSalle.getText();
        String bat = batName.getText();
        int ordi = Integer.parseInt(pc.getText());
        int videoproj = Integer.parseInt(videoProj.getText());
        int nbplace = Integer.parseInt(nbPlace.getText());
        Ressources apiRes = new Ressources(salle, bat, ordi, videoproj, nbplace, "insert");

        apiRes.run();
        Alert a;
        if(apiRes.inserted){
            a = new Alert(Alert.AlertType.INFORMATION, "La Ressource `" + salle.toUpperCase() + "` a bien été créé.");
            a.setHeaderText("Une ressources a été crée.");
        }else {
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Impossible de créer la ressource `" + salle.toUpperCase() + "`.");
            a.setContentText("Remplissez correctement les champs OU Appelez le software developper.");
        }
        a.showAndWait();
    }


    private void loadRessources() throws IOException {

        Ressources apiRes = new Ressources("get");
        apiRes.run();
        RessourcesTable rt = apiRes.getRt();

        GridPane gpAll = new GridPane();

        int i = 0;
        while (i < rt.getNbRes()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/cardRessources.fxml"));
            Node nodeitem = loader.load();


            Label nomSalle = (Label) loader.getNamespace().get("nomSalle");
            Label nomBat = (Label) loader.getNamespace().get("nomBat");
            Label videoproj = (Label) loader.getNamespace().get("videoproj");
            Label nbplaces = (Label) loader.getNamespace().get("nbplaces");
            Label ordi = (Label) loader.getNamespace().get("ordi");
            Label numSalle = (Label) loader.getNamespace().get("numSalle");

            JFXButton suppr = (JFXButton) loader.getNamespace().get("suppr");

            RessourcesModel rm = rt.getArrayRessources().get(i);

            suppr.addEventHandler(MouseEvent.MOUSE_RELEASED, new SupprRessourcesListener(rm, rt, nodeitem));
            nomSalle.setText(rm.getNom_salle());
            nomBat.setText(nomBat.getText() + " " + rm.getBatiment());
            nbplaces.setText("" + rm.getNbplace());
            videoproj.setText("" + rm.getVideo_proj());
            ordi.setText("" + rm.getOrdi());
            // affiche l'id de la salle dans la table donc pas très interessant
            numSalle.setText("" + rm.getId_salle());

            gpAll.add(nodeitem, 0, i);

            i++;
        }
        ScrollPane spuser = new ScrollPane(gpAll);
        spuser.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spuser.setContent(gpAll);
        spuser.getStyleClass().add("scroll-pane");
        listRes.setContent(spuser);
    }


}
