package sample.API;

import sample.Connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Subscription implements Runnable{


    private Connection cx;
    private String email, nom, prenom, pass;
    public String res;
    public boolean sub;

    public Subscription(String email, String nom, String prenom, String pass) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.pass = pass;
    }

    public boolean emailExist(String emailtest) throws SQLException {
        Statement stmt = cx.createStatement();
        String sql = "SELECT email FROM utilisateurs WHERE email = '" + emailtest + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            this.sub = !rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.sub;
    }

    public void initSub() throws SQLException {
        if(emailExist(email)){
            try (Statement stmt = cx.createStatement()){
                String sql = "INSERT INTO utilisateurs (email, nom, prenom, password) VALUES ('" + this.email + "' , '" + this.nom + "' , '" + this.prenom + "' , '" + this.pass + "')";
                stmt.executeUpdate(sql);
            }catch (SQLException sq) {
                System.err.println(sq);
            }
            this.sub = true;
        }else{
            this.res = "Un compte existe déjà avec cet email";
            this.sub = false;
        }
    }

    @Override
    public void run() {
        try {
            this.cx = Connexion.getConnection();
            initSub();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
