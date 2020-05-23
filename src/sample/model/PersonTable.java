package sample.model;


import java.util.ArrayList;

public class PersonTable {

    private ArrayList<PersonModel> tablePersonModels;
    private int nbPerson;

    public PersonTable(){
        this.tablePersonModels = new ArrayList<>();
        this.nbPerson = 0;
    }

    public void add(PersonModel p) {
        this.tablePersonModels.add(p);
        this.nbPerson++;
    }

    public ArrayList<PersonModel> getTablePersonModels() {
        return tablePersonModels;
    }

    public boolean estDansListe(PersonModel pm){
        for (PersonModel p: tablePersonModels) {
            if(p.getId() == pm.getId()){
                return true;
            }
        }
        return false;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    @Override
    public String toString() {
        String print = "";
        for(PersonModel p : this.tablePersonModels){ print += p.toString() + ""; }
        return print;
    }
}
