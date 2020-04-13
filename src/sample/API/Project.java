package sample.API;

import sample.Connexion;
import sample.model.PersonModel;
import sample.model.PersonTable;
import sample.model.ProjectModel;
import sample.model.ProjectTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Project implements Runnable {


   /////////////////// ATTRIBUTS ///////////////////

    // attributs de connexion
    private Connection cx;

    private ProjectTable pt;
    public boolean connected;
    public boolean inserted;

    // definie l'action a faire
    private String demande;

    // attributs pour insereer
    private String nomNewProj, descNewProj;
    private int new_id_proprio;
    private PersonModel user;

    // attributs pour recup les membres
    private PersonTable userProject;
    private int numproj;

    /////////////////// CONSTRUCTEUR ///////////////////

    // pour recuperer tout les projets
    public Project(String demande) {
        this.pt = new ProjectTable();
        this.demande = demande;
    }

    // pour la demande unique des membre du projets
    public Project(String demande, int numproj) {
        this.demande = demande;
        this.numproj = numproj;
    }


    // pour cree un projet
    public Project(String nomNewProj, String descNewProj, int new_id_proprio, String demande, PersonModel user) {
        this.nomNewProj = nomNewProj;
        this.descNewProj = descNewProj;
        this.new_id_proprio = new_id_proprio;
        this.demande = demande;
        this.user = user;
    }


    /////////////////// METHODES ///////////////////
    // recuperer tout les projets de la base et leurs membres
    public void getProjet() throws SQLException {
        Statement stmt = cx.createStatement();
        String sql = "SELECT * FROM projet p JOIN utilisateurs u ON p.id_proprio = u.id_user ORDER BY p.date_crea";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                int id_proj = rs.getInt("p.id_projet");
                int id_proprio = rs.getInt("p.id_proprio");
                String nom_proj = rs.getString("p.nom_projet");
                String desc = rs.getString("p.description");
                String email_proprio = rs.getString("u.email");

                //on recuperer les membres
                PersonTable membreProj = getUserofProjet(id_proj);
                // on ajoute le projet a la table et ses membres
                ProjectModel pm = new ProjectModel(id_proj, id_proprio, nom_proj, desc, email_proprio, membreProj);
                this.pt.ajouteProj(pm);

                // la connexion a été effectué
                this.connected = true;
            }
            rs.close();
        }catch (SQLException sq){
            this.connected = false;
            sq.printStackTrace();
            System.err.println("Impossible de recuperer les projets.");
        }
        stmt.close();
        this.cx.close();
    }


    // recupere les membres d'un projet

    public PersonTable getUserofProjet(int id_proj) throws SQLException {
        Statement stmt = cx.createStatement();
        String sql = "SELECT u.id_user, u.email, u.nom, u.prenom, pm.valide FROM projet_membre pm " +
                "JOIN utilisateurs u ON pm.id_membre = u.id_user WHERE pm.id_projet = "+ id_proj;
        try {

            ResultSet rs = stmt.executeQuery(sql);
            PersonTable userMembreTable = new PersonTable();
            while(rs.next()){

                int idmembre = rs.getInt("u.id_user");
                int valide = rs.getInt("pm.valide");
                String nom = rs.getString("u.nom");
                String prenom = rs.getString("u.prenom");
                String email = rs.getString("u.email");

                PersonModel userMembre = new PersonModel(idmembre, email, nom, prenom, valide);
                userMembreTable.add(userMembre);
            }

            return userMembreTable;

        }catch (SQLException sq){
            sq.printStackTrace();
        }
        stmt.close();
        return new PersonTable();
    }

    // cree un projet
    public void insertProj() throws SQLException {
        if(!this.nomNewProj.equals("") && !this.descNewProj.equals("")) {
            Statement stmt = cx.createStatement();
            String sql = "INSERT INTO projet (nom_projet, description, id_proprio) VALUES ('" + this.nomNewProj + "', '" + this.descNewProj + "', '" + this.new_id_proprio + "')";
            try {
                int response = stmt.executeUpdate(sql);
                // si il retourne 1 tout s'est bien passé
                if (response == 1) {
                    this.inserted = true;
                } else {
                    this.inserted = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt.close();
        }else {
            this.inserted = false;
        }
        this.cx.close();
    }


    // cree un thread a part pour ne pas trop ralentir le logiciel

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            if(this.demande.equals("get")){
                getProjet();
            }else if(this.demande.equals("put")){
                insertProj();
            }else if(this.demande.equals("getUser")){
                this.userProject = getUserofProjet(this.numproj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // envoie la liste de tout les projets

    public ProjectTable getPt() throws SQLException { this.cx.close(); return pt; }

    // envoie la liste des membres d'un projet

    public PersonTable getMembersProjet() throws SQLException { this.cx.close(); return userProject; }
}
