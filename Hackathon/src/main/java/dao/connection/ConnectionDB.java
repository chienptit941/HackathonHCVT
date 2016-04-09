package dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ndt
 */
public class ConnectionDB {

    private Connection con;
    private static ConnectionDB instance = null;

    
    protected ConnectionDB() {
        createConnection(ConfigDB.USER_DB,ConfigDB.PASS_DB);
    }

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    private void createConnection(String dbUsername, String dbPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + ConfigDB.IP_DB + "/" + ConfigDB.NAME_DB
                    + "?characterEncoding=UTF-8", dbUsername, dbPassword);            
        } catch (Exception e) {
            e.printStackTrace();
        }         
    }

    public Connection getConnection() {
        return con;
    }

    public static void main(String[] args) {
        System.out.println("Test connect");
        getInstance();
    }
}
