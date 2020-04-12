package sample.API;

import sample.Connexion;
import sample.model.PersonModel;
import sample.model.ProjectModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddUserProjet implements Runnable {


    private Connection cx;

    private ProjectModel projet;
    private PersonModel user;

    public boolean inserted;

    public AddUserProjet(ProjectModel projet, PersonModel user) {
        this.projet = projet;
        this.user = user;
    }

    public void addUser() throws SQLException {
        if(!(this.user == null) || !(this.projet == null)) {
            Statement stmt = cx.createStatement();
            int id_proj = this.projet.getId_projet();
            int id_user = this.user.getId();

            if(!(id_proj < 1) && !(id_user < 1)){
                String sql;
                // si le propriétaire s'inscrit il est automatiquement validé
                if(this.projet.getId_proprio() == id_user){
                    sql = "INSERT INTO projet_membre (id_projet, id_membre, valide) VALUES ('"+ id_proj +"','"+ id_user +"','"+ 1 +"')";
                }else{
                    sql = "INSERT INTO projet_membre (id_projet, id_membre, valide) VALUES ('"+ id_proj +"','"+ id_user +"','"+ 0 +"')";
                }
                // on essaye d'executer la requete
                try {
                    int response = stmt.executeUpdate(sql);
                    // si la reponse est 1 tout s'est bien passé
                    this.inserted = response == 1;
                }catch (SQLException sq){
                    sq.printStackTrace();
                }
            // si les id sont inferieur a 1 l'insertion echoue
            }else {
                this.inserted = false;
            }
            stmt.close();
        }else {
            this.inserted = false;
        }
    }


    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            addUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
