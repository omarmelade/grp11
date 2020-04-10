package sample.API;

import sample.Connexion;
import sample.model.ProjectModel;
import sample.model.ProjectTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Project implements Runnable {


    private Connection cx;
    public String demande;
    private ProjectTable pt;
    public boolean connected;

    public Project(String demande) {
        this.demande = demande;
        this.pt = new ProjectTable();
    }

    public void initConnection() throws SQLException {
        Statement stmt = cx.createStatement();
        String sql = "SELECT * FROM projet";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                int id_proj = rs.getInt("id_projet");
                int id_proprio = rs.getInt("id_proprio");
                String nom_proj = rs.getString("nom_projet");
                String desc = rs.getString("description");
                // on ajoute le projet a la table
                ProjectModel pm = new ProjectModel(id_proj, id_proprio, nom_proj, desc);
                this.pt.ajouteProj(pm);
                // la connexion a été effectué
                this.connected = true;
            }
        }catch (SQLException sq){
            this.connected = false;
            sq.printStackTrace();
            System.err.println("Impossible de recuperer les projets.");
        }
    }

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            initConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProjectTable getPt() { return pt; }
}
