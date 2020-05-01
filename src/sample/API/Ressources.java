package sample.API;

import sample.Connexion;
import sample.model.RessourcesModel;
import sample.model.RessourcesTable;

import java.sql.*;

public class Ressources implements Runnable {

    /////////////////// ATTRIBUTS ///////////////////

    // attributs de connexion
    private Connection cx;
    private String demande;

    private RessourcesTable rt;
    private RessourcesModel supprrm;

    // attributs d'objet ressources
    private String nom_salle, batiment;
    private int ordi, video_proj, nbplace;

    // attributs de succes de l'insertion
    public boolean inserted;

    public Ressources(RessourcesModel supprrm, String demande) {
        this.supprrm = supprrm;
        this.demande = demande;
    }

    public Ressources(String nom_salle, String batiment, int ordi, int video_proj, int nbplace, String demande) {
        this.nom_salle = nom_salle;
        this.batiment = batiment;
        this.ordi = ordi;
        this.video_proj = video_proj;
        this.nbplace = nbplace;
        this.demande = demande;
    }

    public Ressources(String demande) {
        this.rt = new RessourcesTable();
        this.demande = demande;
    }

    private void addMainRessources() throws SQLException{
        this.inserted = false;
        if(!nom_salle.equals("") && !batiment.equals("")){
            PreparedStatement stmt = cx.prepareStatement("INSERT INTO salle (nom_salle, ordi, video_proj, tabl, batiment) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, nom_salle);
            stmt.setInt(2, ordi);
            stmt.setInt(3, video_proj);
            stmt.setInt(4, nbplace);
            stmt.setString(5, batiment);

            int reponse = stmt.executeUpdate();
            System.out.println(reponse + " est la reponse a l'insertion de ressources");
            if(reponse == 1){
                this.inserted = true;
            }
            stmt.close();
            cx.close();
        }
    }

    private void supprRessources() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("DELETE FROM salle WHERE id_salle = ? ");
        if(this.supprrm != null){
            stmt.setInt(1, this.supprrm.getId_salle());
            int reponse = stmt.executeUpdate();
            if(reponse == 1){
                this.inserted = true;
            }
            stmt.close();
            cx.close();
        }
    }

/* On doit crée les classes
* @RessourcesTable et
* @RessoucresModel avant. "../model"w
*/
     private void getRessourcesMain() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("SELECT * FROM salle");
        ResultSet rs = stmt.executeQuery();

        RessourcesTable rt = new RessourcesTable();
        while(rs.next()){
            int id_salle = rs.getInt("id_salle");
            String nom_salle = rs.getString("nom_salle");
            int ordi = rs.getInt("ordi");
            int video_proj = rs.getInt("video_proj");
            int nbplace = rs.getInt("tabl");
            String batiment = rs.getString("batiment");

            RessourcesModel rm = new RessourcesModel(id_salle, ordi, video_proj, nbplace, nom_salle, batiment);
            this.rt.ajouteRes(rm);
        }
        rs.close();
        stmt.close();
        cx.close();
    }

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            if(demande.equals("insert")){
                addMainRessources();
            }else if(demande.equals("get")){
                getRessourcesMain();
            }else if(demande.equals("delete")){
                supprRessources();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public RessourcesTable getRt() { return rt; }
}
