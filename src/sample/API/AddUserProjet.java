package sample.API;

import sample.Connexion;
import sample.model.PersonModel;
import sample.model.ProjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddUserProjet implements Runnable {


    private Connection cx;

    private ProjectModel projet;
    private PersonModel user;

    public boolean inserted;

    public boolean updated;
    private String demande;

    public AddUserProjet(ProjectModel projet, PersonModel user, String demande){
        this.projet = projet;
        this.user = user;
        this.demande = demande;
        this.inserted = false;
        this.updated = false;
    }

    private void addUser() throws SQLException {
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
            this.cx.close();
        }else {
            this.inserted = false;
        }
    }

    private void updateUserValid() throws SQLException{
        if(!(this.user == null) || !(this.projet == null)) {
            PreparedStatement stmt = cx.prepareStatement("UPDATE projet_membre SET valide = ? WHERE id_projet = ? AND id_membre = ?");
            stmt.setInt(1,1);
            stmt.setInt(2,this.projet.getId_projet());
            stmt.setInt(3,this.user.getId());
            int reponse = stmt.executeUpdate();
            // met updated en fonction de la reponse de la requete.
            updated = reponse == 1;
            stmt.close();
        }else{
            this.updated = false;
        }
        cx.close();
    }

    private void deleteUserValid() throws SQLException{
        if(!(this.user == null) || !(this.projet == null)) {
            PreparedStatement stmt = cx.prepareStatement("DELETE FROM projet_membre WHERE id_projet = ? AND id_membre = ?");
            stmt.setInt(1,this.projet.getId_projet());
            stmt.setInt(2,this.user.getId());
            int reponse = stmt.executeUpdate();
            // met updated en fonction de la reponse de la requete.
            this.updated = reponse == 1;
            stmt.close();
        }else {
            this.updated = false;
        }
        cx.close();
    }

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            if(this.demande.equals("update")){
                updateUserValid();
            }else if(this.demande.equals("delete")){
                deleteUserValid();
            }else {
                addUser();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
