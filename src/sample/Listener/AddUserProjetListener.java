package sample.Listener;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import sample.API.AddUserProjet;
import sample.model.PersonModel;
import sample.model.ProjectModel;

public class AddUserProjetListener implements EventHandler {

    private ProjectModel projet;
    private PersonModel user;
    private JFXButton joinProj;

    public AddUserProjetListener(ProjectModel projet, PersonModel user,  JFXButton joinProj) {
        this.projet = projet;
        this.user = user;
        this.joinProj = joinProj;
    }

    @Override
    public void handle(Event event) {
        AddUserProjet aup = new AddUserProjet(this.projet, this.user, "");
        aup.run();
        if(aup.inserted){
            this.joinProj.setText("REJOINDRE LE PROJET");
            this.joinProj.setDisable(true);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Votre demande a bien été envoyé");
            a.showAndWait();
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Impossible de rejoindre ce projet.");
            a.setHeaderText("Vous faite peut-être déjà partie du projet.");
            a.showAndWait();
        }
    }
}
