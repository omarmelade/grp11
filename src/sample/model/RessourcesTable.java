package sample.model;


// prendre exemples sur les autres Classe de @model

/*
    doit être crée sous la forme de la base de donnée, pour acceuillir
    les Ressources sous forme @ArrayList<RessourcesModel>
*/


import java.util.ArrayList;

public class RessourcesTable {

    private ArrayList<RessourcesModel> arrayRessources;
    private int nbRes;

    public RessourcesTable(){
        this.arrayRessources = new ArrayList<>();
        this.nbRes = 0;
    }

    public void ajouteRes(RessourcesModel rm){
        this.arrayRessources.add(rm);
        this.nbRes++;
    }

    public void deleteRes(RessourcesModel rm){
        this.arrayRessources.remove(rm);
        this.nbRes--;
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