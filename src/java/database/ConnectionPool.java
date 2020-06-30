package database;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    private ConnectionPool() {
        
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/homepage_global");
        } catch (NamingException e_global) {
            System.out.println(e_global);
            System.out.println("Trying local database...");
            
            try {
                InitialContext ic = new InitialContext();
                dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/homepage_global");
            }
            catch (NamingException e_local) {
                System.out.println(e_global);
            }
        }
    }

    // At most, 1 instance will be created
    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    } // Singleton

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
