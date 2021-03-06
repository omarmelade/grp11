package sample.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RessourcesTable {

    private ArrayList<RessourcesModel> arrayRessources;
    private int nbRes;

    public RessourcesTable() {
        this.arrayRessources = new ArrayList<>();
        this.nbRes = 0;
    }

    public void ajouteRes(RessourcesModel rm) {
        this.arrayRessources.add(rm);
        this.nbRes++;
    }

    public Map<Integer, String> ListNameId() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (RessourcesModel r : arrayRessources) {
            map.put(r.getId_salle(), r.getNom_salle());
        }
        return map;
    }

    public void deleteRes(RessourcesModel rm) {
        this.arrayRessources.remove(rm);
        this.nbRes--;
    }

    public ArrayList<String> ListNSalle() throws SQLException {
        ArrayList<String> salles = new ArrayList<String>();
        for (RessourcesModel r : arrayRessources) {
            salles.add(r.getNom_salle());

        }
        return(salles);

    }

    public ArrayList<RessourcesModel> getArrayRessources() {
        return arrayRessources;
    }

    public int getNbRes() {
        return nbRes;
    }

    @Override
    public String toString() {
        return "RessourcesTable{" +
                "arrayRessources=" + arrayRessources +
                '}';
    }
}
