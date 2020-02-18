package sample.model;


import java.util.ArrayList;

public class PersonTable {
    private ArrayList<PersonModel> tablePersonModels;

    public PersonTable(){
        this.tablePersonModels = new ArrayList<>();
    }

    public void add(PersonModel p) {
        this.tablePersonModels.add(p);
    }

    public ArrayList<PersonModel> getTablePersonModels() {
        return tablePersonModels;
    }

    @Override
    public String toString() {
        String print = "";
        for(PersonModel p : this.tablePersonModels){ print += p.toString() + ""; }
        return print;
    }
}
