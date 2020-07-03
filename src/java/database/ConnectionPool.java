package database;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static DataSource dataSource_1 = null;
    private static DataSource dataSource_2 = null;

    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource_1 = (DataSource) ic.lookup("java:/comp/env/jdbc/homepage_local");
        }
        catch (NamingException e) {
            System.out.println(e);
        }
        try {
            InitialContext ic = new InitialContext();
            dataSource_2 = (DataSource) ic.lookup("java:/comp/env/jdbc/homepage_global");
        }
        catch (NamingException e) {
            System.out.println( e);
        }    
    }

    // At most, 1 instance will be created
    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    } // Singleton

    public Connection getConnection(int source) {
        try {
            if (source == 2)
                return dataSource_2.getConnection();
            else
                return dataSource_1.getConnection();
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
