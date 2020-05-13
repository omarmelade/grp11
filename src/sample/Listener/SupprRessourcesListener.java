package sample.Listener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import sample.API.Ressources;
import sample.controller.RessourcesController;
import sample.model.RessourcesModel;
import sample.model.RessourcesTable;

public class SupprRessourcesListener implements EventHandler {

    private RessourcesModel rm;
    private RessourcesTable rt;
    private RessourcesController rc;
    private Ressources apiRessources;
    private Node nodeitem;

    public SupprRessourcesListener(RessourcesModel rm, RessourcesTable rt, Node nodeitem) {
        this.rm = rm;
        this.rt = rt;
        this.nodeitem = nodeitem;
    }

    @Override
    public void handle(Event event) {
        apiRessources = new Ressources(rm, "delete");
        apiRessources.run();
        if(apiRessources.inserted){
            rt.getArrayRessources().remove(rm);
            nodeitem.setVisible(false);
        }else{
            System.err.println("Impossible de supprimer cette ressources");
        }
    }
}
