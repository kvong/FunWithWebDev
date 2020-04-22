/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import bookmarks.Bookmark;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author blank
 */
public class BookmarkDB {
    public static int insert(Bookmark bookmark){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query = 
                "INSERT INTO bookmark (Type, Name, Url, Icon, Logo)"
                + "VALUES (?,?,?,?,?)";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, bookmark.getType());
            ps.setString(2, bookmark.getName());
            ps.setString(3, bookmark.getUrl());
            ps.setString(4, bookmark.getIcon());
            ps.setString(5, bookmark.getLogo());
            return ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
            return 0;
        }
        finally{
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int update(String bookmarkName, Bookmark bookmark){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query =
                "UPDATE bookmark SET " +
                "Type = ? ," +
                "Name = ? ," +
                "Url = ? ," +
                "Icon = ? ," +
                "Logo = ? " +
                "WHERE Name = ? ";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, bookmark.getType());
            ps.setString(2, bookmark.getName());
            ps.setString(3, bookmark.getUrl());
            ps.setString(4, bookmark.getIcon());
            ps.setString(5, bookmark.getLogo());
            ps.setString(6, bookmarkName);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
            return 0;
        }
        finally{
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int delete(Bookmark bookmark){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query = "DELETE FROM bookmark " +
                        "WHERE Name = ?";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, bookmark.getName());
            return ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
            return 0;
        }
        finally{
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean bookmarkExist(String name){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT Name FROM bookmark " +
                "WHERE Name = ?";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e){
            System.out.println(e);
            return false;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static Bookmark getBookmark(String Name){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM bookmark " + 
                "WHERE Name = ?";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, Name);
            rs = ps.executeQuery();
            
            Bookmark bookmark = null;
            if (rs.next()){
                bookmark = new Bookmark();
                bookmark.setID(Integer.parseInt(rs.getString("BookmarkID")));
                bookmark.setType(rs.getString("Type"));
                bookmark.setName(rs.getString("Name"));
                bookmark.setUrl(rs.getString("Url"));
                bookmark.setIcon(rs.getString("Icon"));
            }
            return bookmark;
        }catch (SQLException e){
            System.out.println(e);
            return null;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Bookmark> getBookmarks(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Statement s = null;
        ResultSet rs = null;
        
        ArrayList<Bookmark> bookmarks = null;
        String query = "SELECT * FROM bookmark ";
        
        try{
            s = connection.createStatement();
            rs = s.executeQuery(query);
            
            bookmarks = new ArrayList<Bookmark>();
            
            while(rs.next()){
                int BookmarkID = Integer.parseInt(rs.getString(1));
                String Type = rs.getString(2);
                String Name = rs.getString(3);
                String URL = rs.getString(4);
                String Icon = rs.getString(5);
                String Logo = rs.getString(6);
                
                // Must use setters; Argumented constructor will not work
                Bookmark bookmark = new Bookmark(BookmarkID, Type, Name, URL, Icon, Logo);                

                bookmarks.add(bookmark);
            }
            return bookmarks;
        }
        catch (SQLException e){
            System.out.println(e);
            return null;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(s);
            pool.freeConnection(connection);
        }  
    }
}
