package sample.model;



// prendre exemples sur les autres Classe de @model

/*
    doit être crée sous la forme de la base de donnée, pour acceuillir
    les Ressources sous forme @ArrayList<RessourcesModel>
*/


import java.util.ArrayList;


public class RessourcesTable {
    private ArrayList<RessourcesModel> tableRessources;
    private int nbSalle;

    public RessourcesTable (){
        this.tableRessources = new ArrayList<>();
        this.nbSalle = 0;
    }

    public void add(RessourcesModel r){
        this.tableRessources.add(r);
        this.nbSalle++;
    }

    public ArrayList<RessourcesModel> getTableRessources() {
        return tableRessources;
    }

    public int getNbSalle() {
        return nbSalle;
    }

    public boolean estDansListe(RessourcesModel rm){
        for (RessourcesModel r: tableRessources) {
            if(r.getID() == rm.getID()){
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String print = "";
        for(RessourcesModel rm : this.tableRessources){ print += rm.toString() + ""; }
        return print;
    }
}
