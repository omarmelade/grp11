package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import sample.API.Project;
import sample.Listener.DecoListener;
import sample.Listener.HomeListener;
import sample.model.PersonModel;
import sample.model.ProjectModel;
import sample.model.ProjectTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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


        userProject.setContent(new VBox(new Label("Aucun projet n'est encore disponible")));
        userPartProject.setContent(new VBox(new Label("Aucun projet n'est encore disponible")));
        createProject.setContent(new VBox(new Label("La création projet n'est encore disponible")));

        try {
            loadProjects();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    void loadProjects() throws SQLException, IOException {

        Project apiProj = new Project();
        apiProj.run();
        ProjectTable projectTable = apiProj.getPt();

        GridPane gp = new GridPane();

        int i = 0;
        while (i<projectTable.getNbProj()){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/cardProj.fxml"));
            Node nodeItem = (Node) loader.load();
            Label nomProj = (Label) loader.getNamespace().get("nomProj");
            Label descriptionProj = (Label) loader.getNamespace().get("descriptionProj");
            Label nomProprio = (Label) loader.getNamespace().get("chefProj");

            ProjectModel projm = projectTable.getArrayProject().get(i);

            nomProj.setText(projm.getNom());
            descriptionProj.setText(projm.getDescription());
            nomProprio.setText(nomProprio.getText() + " " + projm.getEmail_proprio());

            gp.add(nodeItem, 0,i);


            i++;
        }


        projetlist.getChildren().add(gp);
    }

}
