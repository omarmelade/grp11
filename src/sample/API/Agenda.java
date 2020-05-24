package sample.API;

import sample.Connexion;
import sample.model.AgendaModel;
import sample.model.AgendaTable;
import sample.model.PersonModel;
import sample.model.ProjectModel;

import java.sql.*;
import java.time.LocalTime;

public class Agenda implements Runnable {

    // attributs de connexion
    private Connection cx;
    private String demande;

    private String color;
    private AgendaTable at;
    private AgendaModel am;

    // attrubuts d'objets agenda
    private String reunionName;
    private String reunionGroup;
    private int id_projet;
    private String reunionDate;
    private LocalTime debutHoraire;
    private LocalTime finHoraire;

    private ProjectModel pm;
    private PersonModel personm;

    private int id_salle;

    public Agenda(int id_projet, String reunionName, String reunionGroup,
                  String reunionDate, LocalTime debutHoraire, LocalTime finHoraire, String color, int id_salle, String demande) {
        this.reunionName = reunionName;
        this.reunionGroup = reunionGroup;
        this.id_projet = id_projet;
        this.reunionDate = reunionDate;
        this.debutHoraire = debutHoraire;
        this.finHoraire = finHoraire;
        this.demande = demande;
        this.color = color;
        this.id_salle = id_salle;
    }

    // attributs de succes de l'insertion
    public boolean inserted;

    public Agenda(AgendaModel am, String demande) {
        this.am = am;
        this.demande = demande;
    }


    public Agenda(String demande) {
        this.at = new AgendaTable();
        this.demande = demande;
    }

    public Agenda(String demande, ProjectModel pm) {
        this.pm = pm;
        this.at = new AgendaTable();
        this.demande = demande;
    }

    public Agenda(String demande, PersonModel personm) {
        this.personm = personm;
        this.at = new AgendaTable();
        this.demande = demande;
    }

    public Agenda(String demande, int id_salle) {
        this.at = new AgendaTable();
        this.demande = demande;
        this.id_salle = id_salle;
    }


    private void addMainAgenda() throws SQLException {
        this.inserted = false;
        if (reunionName != null && reunionDate != null) {
            PreparedStatement stmt = cx.prepareStatement("INSERT INTO agenda (id_projet, reunionName, reunionGroup, reunionDate, debutHoraire, finHoraire, couleur, id_salle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, id_projet);
            stmt.setString(2, String.valueOf(reunionName));
            stmt.setString(3, String.valueOf(reunionGroup));
            stmt.setString(4, String.valueOf(reunionDate));
            stmt.setString(5, String.valueOf(debutHoraire));
            stmt.setString(6, String.valueOf(finHoraire));
            stmt.setString(7, color);
            stmt.setInt(8, id_salle);

            int reponse = stmt.executeUpdate();
            System.out.println(reponse + " est la reponse a l'insertion dans l'agenda");
            if (reponse == 1) {
                this.inserted = true;
            }
            stmt.close();
            cx.close();
        }
    }

    private void supprAgenda() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("DELETE FROM salle WHERE reunionName = ? ");
        if(this.am != null){
            stmt.setString(1, String.valueOf(this.am.getNomreu()));
            int reponse = stmt.executeUpdate();
            if(reponse == 1){
                this.inserted = true;
            }
            stmt.close();
            cx.close();
        }
    }


    private void getAgendaMain() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("SELECT * FROM agenda ORDER BY reunionDate");
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {

            int id_reu = rs.getInt("id_reunion");
            int id_projet = rs.getInt("id_projet");
            String nomreu = rs.getString("reunionName");
            String dateReu = rs.getString("reunionDate");
            Time debutReu = rs.getTime("debutHoraire");
            Time finReu = rs.getTime("finHoraire");
            String color = rs.getString("couleur");
            int id_salle = rs.getInt("id_salle");

            AgendaModel am = new AgendaModel(id_reu, id_projet, nomreu, dateReu, debutReu, finReu, color, id_salle);
            this.at.ajouteAgenda(am);
        }
        rs.close();
        stmt.close();
        cx.close();
    }

    private void getAgendaProj() throws SQLException {
        PreparedStatement stmt = cx.prepareStatement("SELECT * FROM agenda a JOIN projet p ON a.id_projet = p.id_projet WHERE a.id_projet = ?");
        stmt.setInt(1, pm.getId_projet());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int id_reu = rs.getInt("id_reunion");
            int id_projet = rs.getInt("id_projet");
            String nomreu = rs.getString("reunionName");
            String dateReu = rs.getString("reunionDate");
            Time debutReu = rs.getTime("debutHoraire");
            Time finReu = rs.getTime("finHoraire");
            String color = rs.getString("couleur");
            int id_salle = rs.getInt("id_salle");

            AgendaModel am = new AgendaModel(id_reu, id_projet, nomreu, dateReu, debutReu, finReu, color, id_salle);
            this.at.ajouteAgenda(am);
        }
        rs.close();
        stmt.close();
        cx.close();
    }


    private void getAgendaPerson() throws SQLException {
        PreparedStatement stmt =
                cx.prepareStatement("SELECT * FROM agenda a " +
                        "JOIN projet p ON a.id_projet = p.id_projet " +
                        "JOIN projet_membre pm ON pm.id_projet = p.id_projet " +
                        "WHERE pm.id_membre = ?"
                );

        stmt.setInt(1, personm.getId());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int id_reu = rs.getInt("id_reunion");
            int id_projet = rs.getInt("id_projet");
            String nomreu = rs.getString("reunionName");
            String dateReu = rs.getString("reunionDate");
            Time debutReu = rs.getTime("debutHoraire");
            Time finReu = rs.getTime("finHoraire");
            String color = rs.getString("couleur");
            int id_salle = rs.getInt("id_salle");

            AgendaModel am = new AgendaModel(id_reu, id_projet, nomreu, dateReu, debutReu, finReu, color, id_salle);
            this.at.ajouteAgenda(am);
        }
        rs.close();
        stmt.close();
        cx.close();
    }

    private void getAgendaSalle() throws SQLException {
        PreparedStatement stmt =
                cx.prepareStatement("SELECT * FROM agenda a WHERE a.id_salle =  ?");

        stmt.setInt(1, this.id_salle);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int id_reu = rs.getInt("id_reunion");
            int id_projet = rs.getInt("id_projet");
            String nomreu = rs.getString("reunionName");
            String dateReu = rs.getString("reunionDate");
            Time debutReu = rs.getTime("debutHoraire");
            Time finReu = rs.getTime("finHoraire");
            String color = rs.getString("couleur");
            int id_salle = rs.getInt("id_salle");


            AgendaModel am = new AgendaModel(id_reu, id_projet, nomreu, dateReu, debutReu, finReu, color, id_salle);
            this.at.ajouteAgenda(am);
        }
        rs.close();
        stmt.close();
        cx.close();
    }

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            switch (demande) {
                case "insert":
                    addMainAgenda();
                    break;
                case "get":
                    getAgendaMain();
                    break;
                case "delete":
                    supprAgenda();
                    break;
                case "proj":
                    getAgendaProj();
                    break;
                case "person":
                    getAgendaPerson();
                    break;
                case "salle":
                    if (id_salle != -1) {
                        getAgendaSalle();
                    }
                    break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public AgendaTable getAt(){ return at;}
}
