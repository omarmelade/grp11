package sample.API;

import sample.Connexion;

import java.sql.*;

public class Ressources implements Runnable {

    /////////////////// ATTRIBUTS ///////////////////

    // attributs de connexion
    private Connection cx;
    private String demande;

    // attributs d'objet ressources
    private String nom_salle, batiment;
    private int ordi, video_proj, tabl;

    // attributs de succes de l'insertion
    public boolean inserted;


    public Ressources(String nom_salle, String batiment, int ordi, int video_proj, int tabl, String demande) {
        this.nom_salle = nom_salle;
        this.batiment = batiment;
        this.ordi = ordi;
        this.video_proj = video_proj;
        this.tabl = tabl;
        this.demande = demande;
    }

    private void addMainRessources() throws SQLException{
        this.inserted = false;
        if(!nom_salle.equals("") && !batiment.equals("")){
            PreparedStatement stmt = cx.prepareStatement("INSERT INTO salle (nom_salle, ordi, video_proj, tabl, batiment) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, nom_salle);
            stmt.setInt(2, ordi);
            stmt.setInt(3, video_proj);
            stmt.setInt(4, tabl);
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

/* On doit crée les classe
* @RessourcesTable et
* @RessoucresModel avant. "../model"

     private RessourcesTable getRessourcesMain() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("SELECT * FROM salle");
        ResultSet rs = stmt.executeQuery();

        RessourcesTable rt = new RessourcesTable();
        while(rs.next()){
            int id_salle = rs.getInt("id_salle");
            String nom_salle = rs.getString("nom_salle");
            int ordi = rs.getInt("ordi");
            int video_proj = rs.getInt("video_proj");
            int tabl = rs.getInt("tabl");
            String batiment = rs.getString("batiment");

            RessourcesModel rm = new RessourcesModel(id_salle, nom_salle, ordi, video_proj, tabl, batiment);
            rt.add(rm);
        }
        rs.close();
        stmt.close();
        cx.close();
        return rt;
    }*/

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            if(demande.equals("insert")){
                addMainRessources();
            }else if(demande.equals("get")){
                //getRessourcesMain();
                System.err.println("veuillez crée le model avant");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
