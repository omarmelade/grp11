package sample.model;

import java.util.ArrayList;

public class AgendaTable {

    private ArrayList<AgendaModel> arrayAgenda;
    private int nbRes;

    public AgendaTable(){
        this.arrayAgenda = new ArrayList<>();
        this.nbRes = 0;
    }

    public void ajouteAgenda (AgendaModel am){
        this.arrayAgenda.add(am);
        this.nbRes++;
    }

    public void deleteAgenda(AgendaModel am){
        this.arrayAgenda.remove(am);
        this.nbRes--;
    }

    public ArrayList<AgendaModel> getArrayAgenda() {
        return arrayAgenda;
    }

    public int getNbRes() {
        return nbRes;
    }

    @Override
    public String toString() {
        return "AgendaTable{" +
                "arrayAgenda=" + arrayAgenda +
                '}';
    }
}
