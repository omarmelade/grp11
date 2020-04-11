package sample.API;

import sample.Connexion;
import sample.model.PersonModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Subscription implements Runnable{


    private Connection cx;
    private String email, nom, prenom, pass;
    public String res;
    public boolean sub;
    private PersonModel newUser;

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
        System.out.println(email + " "+ pass);
        if(!this.email.equals("") && !this.pass.equals("") && !this.nom.equals("") && !this.prenom.equals("")) {
            System.out.println("Les champs ne sont pas vides");
            if (emailExist(email)) {
                System.out.println("L'email n'existe pas dans la base");
                try (Statement stmt = cx.createStatement()) {
                    System.out.println("on execute l'insertion");
                    String sql = "INSERT INTO utilisateurs (email, nom, prenom, password) VALUES ('" + this.email + "' , '" + this.nom + "' , '" + this.prenom + "' , '" + this.pass + "')";
                    stmt.executeUpdate(sql);
                    this.sub = true;
                    loadUser(this.email, this.pass);
                } catch (SQLException sq) {
                    System.err.println(sq);
                }
            } else {
                this.res = "Un compte existe déjà avec cet email";
                this.sub = false;
            }
        }else {
            this.sub = false;
            this.res = "Les champs doivent être remplis correctement";
        }
    }

    private void loadUser(String email, String pass) throws SQLException {
        Login lg = new Login(email, pass);
        lg.run();
        this.newUser = lg.getPm();
    }


    public PersonModel getNewUser() {
        return newUser;
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
