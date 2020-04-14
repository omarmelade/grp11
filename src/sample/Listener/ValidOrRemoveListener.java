package sample.Listener;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.API.AddUserProjet;
import sample.controller.OneProjectScreen;
import sample.model.PersonModel;
import sample.model.ProjectModel;
import sample.util.PlatformHelper;

public class ValidOrRemoveListener implements EventHandler {

    private final String req;
    private final PersonModel pm, userview;
    private final ProjectModel project;
    private final JFXButton btnVal, btnRem;
    private AnchorPane root;

    public ValidOrRemoveListener(PersonModel userview, PersonModel pm, ProjectModel project, String req, JFXButton btnVal, JFXButton btnRem, AnchorPane root) {
        this.userview = userview;
        this.pm = pm;
        this.project = project;
        this.req = req;
        this.btnVal = btnVal;
        this.btnRem = btnRem;
        this.root = root;
    }

    @Override
    public void handle(Event event) {
        AddUserProjet aup = new AddUserProjet(this.project, this.pm, this.req);
        aup.run();
        System.out.println("");
        if(aup.updated){
            Alert a = new Alert(Alert.AlertType.INFORMATION, "La fenêtre va automatiquement redemarrer");
            a.setHeaderText( "Ce membre a bien été affecté.");
            a.showAndWait();
//            this.btnVal.setVisible(false);
//            this.btnRem.setVisible(false);
            PlatformHelper.run(()->{
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
                OneProjectListener opl = new OneProjectListener(this.userview, this.project, new AnchorPane());
                opl.handle(new Event(EventType.ROOT));
            });
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR, "L'action sur ce membre n'as pas pu être effectué");
            a.showAndWait();
        }
    }
}
