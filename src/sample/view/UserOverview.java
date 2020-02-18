package sample.view;

import sample.Connexion;
import sample.model.PersonTable;

import java.sql.SQLException;

public class UserOverview {

    private PersonTable pt;

    public UserOverview(){
        this.pt = userData();
    }

    private PersonTable userData(){
        try {
            Connexion conn = new Connexion();
            pt = conn.getData(5, "utilisateurs");

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return pt;
    }

    public PersonTable getPt() {
        return pt;
    }
}
