package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    
    private static final String url = "jdbc:sqlserver://localhost:1433;database=sistemaReposteria;encrypt=false;trustServerCertificate=true;";

    private String user;

    private String password;

    private Connection con;

    public Connection getConnection() {
        con = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }

    public String getUser() {return user;}

    public void setUser(String user) {this.user = user;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

}