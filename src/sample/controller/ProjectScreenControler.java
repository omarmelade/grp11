package sample.controller;

import com.jfoenix.controls.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import sample.API.Project;
import sample.Listener.DecoListener;
import sample.Listener.HomeListener;
import sample.Listener.OneProjectListener;
import sample.model.PersonModel;
import sample.model.ProjectModel;
import sample.model.ProjectTable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectScreenControler implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane projetlist;
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


    // formulaire creation projet
    @FXML
    private Tab createProject;
    @FXML
    private JFXButton creation;
    @FXML
    private JFXTextField nomNewProj;
    @FXML
    private JFXTextField descNewProj;
    @FXML
    private JFXTextField languageNewProj;

    private PersonModel pm;


    public ProjectScreenControler(PersonModel pm){
        this.pm = pm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getStylesheets().add(getClass().getResource("../css/Vbox.css").toExternalForm());

        deconnexion.addEventHandler(MouseEvent.MOUSE_RELEASED, new DecoListener(this.root));
        accueil.addEventHandler(MouseEvent.MOUSE_RELEASED, new HomeListener(this.root, this.pm));

        userPartProject.setContent(new VBox(new Label("La participation à un projet n'est pas encore disponible")));

        // update les projets lorsque l'on change de tab
        listProject.setOnSelectionChanged(new EventHandler<Event>() { @Override public void handle(Event t) { if(listProject.isSelected())
            { try { loadProjects(); } catch (SQLException | IOException e) { e.printStackTrace(); }}}});
        userProject.setOnSelectionChanged(new EventHandler<Event>() { @Override public void handle(Event t) { if(userProject.isSelected())
            { try { loadProjects(); } catch (SQLException | IOException e) { e.printStackTrace(); }}}});

        createProjectInit();

        try {
            loadProjects();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }



    }

    private void createProjectInit() {
        creation.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                creeprojet();
            }
        });
    }

    private void creeprojet() {
        Project apiProj = new Project(nomNewProj.getText(),descNewProj.getText(),pm.getId(), "put", this.pm);

        apiProj.run();
        Alert a;
        if(apiProj.inserted){
            a = new Alert(Alert.AlertType.INFORMATION, "Le projet `" + nomNewProj.getText().toUpperCase() + "` a bien été créé.");
            a.setHeaderText("Vous êtes maintenant chef de projet.");
        }else {
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Impossible de créer le projet `"+ nomNewProj.getText().toUpperCase() + "`.");
            a.setContentText("Remplissez correctement les champs OU Appelez le software developper.");
        }
        a.showAndWait();
    }


    void loadProjects() throws SQLException, IOException {

        Project apiProj = new Project("get");
        apiProj.run();
        ProjectTable projectTable = apiProj.getPt();

        GridPane gpAll = new GridPane();
        GridPane gpUser = new GridPane();

        int i = 0;
        int userI = 0;

        while (i + userI < projectTable.getNbProj()){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cardProj.fxml"));
            Node nodeItem = (Node) loader.load();
            JFXButton btnProj = new JFXButton();
            btnProj.setGraphic(nodeItem);
            btnProj.addEventHandler(MouseEvent.MOUSE_RELEASED, new OneProjectListener(this.pm, projectTable.getArrayProject().get(i+userI), root));


            Label nomProj = (Label) loader.getNamespace().get("nomProj");
            Label descriptionProj = (Label) loader.getNamespace().get("descriptionProj");
            Label nomProprio = (Label) loader.getNamespace().get("chefProj");

            ProjectModel projm = projectTable.getArrayProject().get(i+userI);


            nomProj.setText(projm.getNom().toUpperCase());
            descriptionProj.setText(projm.getDescription());
            nomProprio.setText(nomProprio.getText() + " " + projm.getEmail_proprio());

            if(projm.getId_proprio() == pm.getId()){
                JFXButton newBtn = btnProj;
                gpUser.add(newBtn, 0,userI);
                userI++;
            }else{
                gpAll.add(btnProj, 0,i);
                i++;
            }
        }
        ScrollPane spuser = new ScrollPane(gpAll);
        spuser.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spuser.setContent(gpUser);
        spuser.getStyleClass().add("scroll-pane");
        userProject.setContent(spuser);
        // si il n'y a pas de projet appartenant a l'user
        if(userI == 0){
            userProject.setContent(new VBox(new Label("Vous n'avez aucun projet.")));
        }else if(i == 0){
        // si l'user a tout les projets
            listProject.setContent(new Label("Tout les projets vous appartiennent."));
        }
        ScrollPane sp = new ScrollPane(gpAll);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.getStyleClass().add("scroll-pane");
        listProject.setContent(new ScrollPane(sp));
    }

}
