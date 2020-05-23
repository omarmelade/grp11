 package sample.API;

import sample.Connexion;
import sample.model.PersonModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login implements Runnable{

    private Connection cx;
    private String email, password, nom, prenom;
    private int id, admin;
    public boolean connected;
    private PersonModel pm;

    public Login(String email, String password){
            this.email = email;
            this.password = password;
    }

    public boolean initConn() throws SQLException {
        Statement stmt = cx.createStatement();
        String sql = "SELECT * FROM utilisateurs";
        if(!email.equals("") && !password.equals("")) {
            try {
                ResultSet rs = stmt.executeQuery(sql);
                connected = false;
                int id = -1;
                String emailRes, passRes;

                while (rs.next() || !connected) {
                    emailRes = rs.getString("email");
                    passRes = rs.getString("password");

                    if (emailRes.equals(email) && passRes.equals(password)) {
                        connected = true;
                        this.nom = rs.getString("nom");
                        this.prenom = rs.getString("prenom");
                        this.id = rs.getInt("id_user");
                        this.admin = rs.getInt("admin");
                    }

                }
                rs.close();

            } catch (SQLException sq) {
                System.err.println(sq);
            }
        }
        stmt.close();
        cx.close();
        return connected;
    }

    public void run() {
        try {
            this.cx = Connexion.getConnection();
            this.connected = initConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PersonModel getPm() {
        return new PersonModel(this.id, this.email, this.nom, this.prenom, this.admin);
    }
}
