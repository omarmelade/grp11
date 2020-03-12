 package sample.API;

import sample.Connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login{

    private Connection cx;
    private String email, password;
    private boolean connected;

    public Login(String email, String password) throws SQLException {
            this.email = email;
            this.password = password;
            this.cx = Connexion.getConnection();
            this.connected = initConn();
    }

    public boolean initConn() throws SQLException {
        Statement stmt = cx.createStatement();
        String sql = "SELECT email, password FROM utilisateurs";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            connected = false;
            int id;
            String emailRes, passRes;

            while (rs.next() || !connected) {
                emailRes = rs.getString("email");
                passRes = rs.getString("password");

                if (emailRes.equals(emailRes) && passRes.equals(password)) {
                    connected = true;
                }

            }
        } catch (SQLException sq) {
            System.err.println(sq);
        }
        return connected;
    }

}
