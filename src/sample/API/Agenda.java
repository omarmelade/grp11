package sample.API;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.Label;

import sample.Connexion;
import sample.model.AgendaModel;
import sample.model.AgendaTable;

import java.sql.Connection;
import java.sql.*;

public class Agenda implements Runnable {

    // attributs de connexion
    private Connection cx;
    private String demande;

    private AgendaTable at;
    private AgendaModel am;

    // attrubuts d'objets agenda
    public Label reunionName;
    public JFXComboBox<Label> reunionGroup;
    private JFXDatePicker reunionDate;
    public JFXTimePicker debutHoraire;
    private JFXTimePicker finHoraire;

    // attributs de succes de l'insertion
    public boolean inserted;

    public Agenda(AgendaModel am, String demande){
        this.am=am;
        this.demande=demande;
    }

    public Agenda(Label reunionName, JFXComboBox<Label> reunionGroup, JFXDatePicker reunionDate, JFXTimePicker debutHoraire, JFXTimePicker finHoraire, String demande){
        this.reunionName = reunionName;
        this.reunionGroup = reunionGroup;
        this.reunionDate = reunionDate;
        this.debutHoraire = debutHoraire;
        this.finHoraire = finHoraire;
        this.demande = demande;
    }

    public Agenda(String demande){
        this.at = new AgendaTable();
        this.demande = demande;
    }

    private void addMainAgenda() throws SQLException{
        this.inserted = false;
        if(reunionName != null && reunionDate != null){
            PreparedStatement stmt = cx.prepareStatement("INSERT INTO agenda (reunionName, reunionGroup, reunionDate, debutHoraire, finHoraire) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, String.valueOf(reunionName));
            stmt.setString(2, String.valueOf(reunionGroup));
            stmt.setString(3, String.valueOf(reunionDate));
            stmt.setString(4, String.valueOf(debutHoraire));
            stmt.setString(5, String.valueOf(finHoraire));

            int reponse = stmt.executeUpdate();
            System.out.println(reponse + " est la reponse a l'insertion dans l'agenda");
            if(reponse == 1){
                this.inserted = true;
            }
            stmt.close();
            cx.close();
        }
    }

    private void supprAgenda() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("DELETE FROM salle WHERE reunionName = ? ");
        if(this.am != null){
            stmt.setString(1, String.valueOf(this.am.getReunionName()));
            int reponse = stmt.executeUpdate();
            if(reponse == 1){
                this.inserted = true;
            }
            stmt.close();
            cx.close();
        }
    }


    private void getAgendaMain() throws SQLException{
        PreparedStatement stmt = cx.prepareStatement("SELECT * FROM agenda");
        ResultSet rs = stmt.executeQuery();

        AgendaTable at = new AgendaTable();
        while(rs.next()){
            Label reunionName = rs.getString("reunionName");
            JFXComboBox<Label> reunionGroup = rs.getString("reunionGroup");
            JFXDatePicker reunionDate = rs.getString("reunionDate");
            JFXTimePicker debutHoraire= rs.getString("debutHoraire");
            JFXTimePicker finHoraire = rs.getString("finHoraire");

            AgendaModel am = new AgendaModel(reunionName, reunionGroup, reunionDate, debutHoraire, finHoraire);
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
            if(demande.equals("insert")){
                addMainAgenda();
            }else if(demande.equals("get")){
                getAgendaMain();
            }else if(demande.equals("delete")){
                supprAgenda();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public AgendaTable getAt(){ return at;}
}
