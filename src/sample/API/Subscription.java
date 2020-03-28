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
        ResultSet rs = null;
        try {
            rs = cx.createStatement().executeQuery("SELECT email FROM utilisateurs WHERE email = '" + emailtest + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rs != null) {
            if (rs.getString("email").equals(emailtest)) {
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public void initSub() throws SQLException {
        if(emailExist(email)){
            try (Statement stmt = cx.createStatement()) {
                String sql = "INSERT INTO utilisateur (email, nom, prenom, password) VALUES (" + email + ", " + nom + ", " + prenom + ", " + pass + ")";
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
