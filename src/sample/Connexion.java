package sample;

import sample.model.PersonModel;
import sample.model.PersonTable;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;

public class Connexion {
    // champs static pour la connexion
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/l2_gr_11";
    static final String DB_USER = "l2_gr_11";
    static final String DB_PASS = "La1RQSOt";

    Connection conn = null;
    Statement stmt = null;


    public void sentToDB(String email, String name, String lastname, String table) throws SQLException {

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO " + table + "(email, nom, prenom)"
                    + " VALUES (?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, lastname);

            int rows = statement.executeUpdate();
            if (rows > 0){
                System.out.println("Un nouvel utilisateur a été inseré.");
            }

            conn.close();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public PersonTable getData(int nbUser, String table) throws SQLException {
        PersonTable personTable = null;
        ResultSet rs = null;
        try {
            // ouverture de la connexion
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            // execution de la requete / creation du statement
            stmt = conn.createStatement();
            String sql = "SELECT * FROM " + table;
            rs = stmt.executeQuery(sql);

            personTable = new PersonTable();
            // extraction des données
            while(rs.next()){
                // recuperation par nom de colonnes
                //int id  = rs.getInt("id_user");
                String email = rs.getString("email");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");

                //Display values
                //PersonModel p = new PersonModel(email, nom, prenom);
                //personTable.add(p);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return personTable;
    }

    public static Connection getConnection() throws SQLException{
        Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

        return conn;
    }
}
