package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blank
 */

import java.io.*;
import java.io.FileWriter;   
import java.io.IOException;  
import javax.servlet.*;
import java.util.*;
import javax.servlet.http.*;
import java.sql.*;
import bookmarks.Bookmark;
import bookmarks.BookmarkSection;
import database.BookmarkDB;
import java.util.ArrayList;


public class BookmarkServlet extends HttpServlet{

    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
            String url = "";
            ServletContext sc = getServletContext();

            // Get action
            String action = request.getParameter("action");

            String message = "";
            HttpSession session = request.getSession();
            
            // Making session thread-safe
            final Object lock = session.getId().intern();

            // Resolve action
            switch(action){
                case "view":{
                    url = "/bookmarks.jsp";
                    ArrayList<BookmarkSection> bookmarkSections;
                    
                    // All session will synchronize lock to make thread-safe
                    synchronized(lock){
                        // Get session attribute if attribute has been set
                        bookmarkSections = 
                                (ArrayList<BookmarkSection>) session.getAttribute("sections");
                    }
                    
                    // If session attribute has not been set yet,
                    // then create a new object by grabbing info from database
                    if (bookmarkSections == null) {
                        ArrayList<Bookmark> globalBookmarks = BookmarkDB.getBookmarks(2);
                        ArrayList<Bookmark> localBookmarks = BookmarkDB.getBookmarks(1);
                        BookmarkSection bookmarkSection = new BookmarkSection();
                        
                        ArrayList<Bookmark> bookmarks = globalBookmarks;
                        
                        int globalUpdateNumber = -1;
                        int localUpdateNumber = -2;
                        
                        if (bookmarks == null || bookmarks.size() == 0){
                            bookmarks = localBookmarks;
                        }
                        else{
                            // Update local with global if needed
                            globalUpdateNumber = Integer.parseInt(globalBookmarks.get(0).getName());
                            localUpdateNumber = Integer.parseInt(localBookmarks.get(0).getName());

                            // Determine whether local or global is ahead
                            if (globalUpdateNumber > localUpdateNumber){
                                // Updating local
                                BookmarkDB.deleteAll(1);
                                localBookmarks.get(0).setName(String.valueOf(globalUpdateNumber++));
                                globalBookmarks.get(0).setName(String.valueOf(globalUpdateNumber));
                                BookmarkDB.insertBulk(globalBookmarks, 1);
                                bookmarks = globalBookmarks;
                            }
                            else if (localUpdateNumber > globalUpdateNumber){
                                // Updating global
                                BookmarkDB.deleteAll(2);
                                globalBookmarks.get(0).setName(String.valueOf(localUpdateNumber++));
                                localBookmarks.get(0).setName(String.valueOf(localUpdateNumber));
                                BookmarkDB.insertBulk(localBookmarks, 2);
                                bookmarks = localBookmarks;
                            }
                            else
                                bookmarks = localBookmarks;
                        }
                        
                        bookmarkSections = bookmarkSection.createSections(bookmarks);
                        
                    }
                    
                    // All session will synchronize lock to make thread-safe
                    synchronized(lock){
                        // Update session attribute for JSTL component in JSP file
                        session.setAttribute("sections", sortByTypes(bookmarkSections));
                    }
                    break;
                }
                // Adding a new bookmark to database
                case "add":{
                    // Reload to bookmarks.jsp for this option
                    url = "/bookmarks.jsp";
                    
                    // Get arguments from <input> tag under specific names
                    String bookmarkType = cleanInput(request.getParameter("type"));
                    String bookmarkName = cleanInput(request.getParameter("name"));
                    String bookmarkUrl = cleanInput(request.getParameter("url"));
                    String bookmarkIcon = cleanInput(request.getParameter("icon"));
                    String bookmarkLogo = cleanInput(request.getParameter("logo"));
                    
                    // Create a new bookmark from the information received
                    Bookmark bookmark = new Bookmark(bookmarkType,
                        bookmarkName, bookmarkUrl,
                        bookmarkIcon, bookmarkLogo);
                    
                    
                    // Check if the this bookmark has already been created
                    if (BookmarkDB.bookmarkExist(bookmark.getName(), 1)){
                        // Print error message and break out of switch statement
                        message = "Bookmark named '" + bookmark.getName() +
                                 "' already exists.";
                        break;
                    }
                    
                    // If the new bookmark does not exist in the database,
                    // insert it as a new entry
                    BookmarkDB.insert(bookmark, 1);
                    
                    incrementUpdateNumber(1);
                    
                    // Get the updated database list
                    ArrayList<Bookmark> bookmarks = BookmarkDB.getBookmarks(1);
                    
                    // Separate the different bookmarks into sections by
                    // their Types
                    BookmarkSection bookmarkSection = new BookmarkSection();
                    ArrayList<BookmarkSection> bookmarkSections = bookmarkSection.createSections(bookmarks);
                    bookmarkSections = sortByTypes(bookmarkSections);
                    
                    // All session will synchronize lock to make thread-safe
                    synchronized(lock){
                        // Update session attribute for JSTL component in JSP file
                        session.setAttribute("sections", sortByTypes(bookmarkSections));
                    }
                    break;
                }
                // Delete existing bookmark from database
                case "delete": {         
                    // Set next url, in this case we will simply reload since
                    // this is the page we are currently on.
                    url = "/bookmarks.jsp";
                    
                    // Get the name of the bookmark we wish to delete from the
                    // HTML <input> tag with name='name'
                    String bookmarkName = request.getParameter("name");
                    
                    // Create a bookmark for database lookup
                    Bookmark bookmark = new Bookmark();
                    bookmark.setName(bookmarkName);
                    
                    // Check if there is a bookmark to delete
                    if (!BookmarkDB.bookmarkExist(bookmark.getName(), 1)){
                        // Print error message and break out of switch statement
                        message = "Bookmark named '" + bookmark.getName() +
                                 "' does not exists.";
                        break;
                    }
                    
                    // Delete the bookmark from the database using our database
                    // connection
                    BookmarkDB.delete(bookmark, 1);
                    
                    incrementUpdateNumber(1);
                    
                    // Get the updated database list
                    ArrayList<Bookmark> bookmarks = BookmarkDB.getBookmarks(1);
                    
                    // Separate the different bookmarks into sections by
                    // their Types
                    BookmarkSection bookmarkSection = new BookmarkSection();
                    ArrayList<BookmarkSection> bookmarkSections = bookmarkSection.createSections(bookmarks);
                    
                    // All session will synchronize lock to make thread-safe
                    synchronized(lock){
                        // Update session attribute for JSTL component in JSP file
                        session.setAttribute("sections", sortByTypes(bookmarkSections));
                    }
                    break;
                }
                // Update bookmark entry in database
                case "update": {          
                    // Reload the page to see updated results
                    url = "/bookmarks.jsp";
                    
                    // Get the name of the bookmark we want to delete,
                    // provided in the <input> tag with then name 'name'
                    String bookmarkName = cleanInput(request.getParameter("name"));
                    String bookmarkType = cleanInput(request.getParameter("type"));
                    String bookmarkNewName = cleanInput(request.getParameter("new-name"));
                    String bookmarkUrl = cleanInput(request.getParameter("url"));
                    String bookmarkIcon = cleanInput(request.getParameter("icon"));
                    String bookmarkLogo = cleanInput(request.getParameter("logo"));

                    
                    // Check if there is a bookmark to delete
                    if (!BookmarkDB.bookmarkExist(bookmarkName, 1)){
                        // Print error message and break out of switch statement
                        message = "Bookmark named '" + bookmarkName +
                                 "' does not exists.";
                        break;
                    }
                                        
                    // Create a bookmark for database lookup
                    Bookmark newBookmark = new Bookmark();
                    
                    // Grab old bookmark for updating
                    Bookmark oldBookmark = BookmarkDB.getBookmarkByName(bookmarkName, 1);
                    // Do not update empty inputs
                    
                    // Updating type
                    if (bookmarkType.equals(""))
                        newBookmark.setType(oldBookmark.getType());
                    else
                        newBookmark.setType(bookmarkType);
                    
                    // Updating name
                    if (bookmarkNewName.equals(""))
                        newBookmark.setName(oldBookmark.getName());
                    else
                        newBookmark.setName(bookmarkNewName);
                    
                    // Updating url
                    if (bookmarkUrl.equals(""))
                        newBookmark.setUrl(oldBookmark.getUrl());
                    else
                        newBookmark.setUrl(bookmarkUrl);
                    
                    // Updating icon
                    if (bookmarkIcon.equals(""))
                        newBookmark.setIcon(oldBookmark.getIcon());
                    else
                        newBookmark.setIcon(bookmarkIcon);
                    
                    // Updating logo
                    if (bookmarkLogo.equals(""))
                        newBookmark.setLogo(oldBookmark.getLogo());
                    else
                        newBookmark.setLogo(bookmarkLogo);
                    
                    // Updating database
                    BookmarkDB.update(bookmarkName, newBookmark, 1);
                    
                    incrementUpdateNumber(1);
                    
                    // Get the updated database list
                    ArrayList<Bookmark> bookmarks = BookmarkDB.getBookmarks(1);
                    
                    // Separate the different bookmarks into sections by
                    // their Types
                    BookmarkSection bookmarkSection = new BookmarkSection();
                    ArrayList<BookmarkSection> bookmarkSections 
                            = bookmarkSection.createSections(bookmarks);
                    
                    // All session will synchronize lock to make thread-safe
                    synchronized(lock){
                        // Update session attribute for JSTL component in JSP file
                        session.setAttribute("sections", sortByTypes(bookmarkSections));
                    }
                    break;
                }
            }
            // All session will synchronize lock to make thread-safe
            synchronized(lock){
                // Set error message
                session.setAttribute("message", message);
            }
            
            // Forward to next url
            sc.getRequestDispatcher(url).forward(request, response);
        }
    
    // Print all entry of arraylist in INSERT format
    public static void printAll(ArrayList<Bookmark> bookmarks){
        for(Bookmark bookmark : bookmarks){
            System.out.println(
                    "('" +
                    bookmark.getType() + "', '" +
                    bookmark.getName() + "', '" +
                    bookmark.getUrl() + "', '" +
                    bookmark.getIcon() + "', '" +
                    bookmark.getLogo() + "'), "
            ); 
        }
    }
    
    
    // Function to handle whitespaces in input
    public static String cleanInput(String input){
        String cleanedInput = "";
        // Check that we are not simply empty string
        if (input.equals(""))
            return cleanedInput;
        cleanedInput = input.trim();
        cleanedInput = cleanedInput.replaceAll("\\s+", " ");
        return cleanedInput;
    }
    
    public static ArrayList<BookmarkSection> sortByTypes(ArrayList<BookmarkSection> bookmarkSection){
        // Hold newly sorted sections
        ArrayList<BookmarkSection> sortedSections = new ArrayList<BookmarkSection>();
        
        // Sort different bookmark types alphabetically
        String [] types = new String[bookmarkSection.size()];
        int count = 0;
        for (BookmarkSection section : bookmarkSection){
            types[count++] = section.getType();
        }
        Arrays.sort(types);
        
        for (int i = 0; i < types.length; i++){
            for (int j = 0; j < types.length; j++){
                if (types[i].equals(bookmarkSection.get(j).getType())){
                    BookmarkSection section = 
                            new BookmarkSection(bookmarkSection.get(j).getType(),
                                                bookmarkSection.get(j).getBookmarks()
                                                );
                    
                    sortedSections.add(section);
                    break;
                }
            }
        }
        return sortedSections;
    }
    
    public static void incrementUpdateNumber(int scope){
        Bookmark bookmark = BookmarkDB.getBookmarkByType("Update", scope);
        String oldName = bookmark.getName();
        int newUpdateNumber = Integer.parseInt(bookmark.getName()) + 1;
        String newUpdateStr = String.valueOf(newUpdateNumber);
        bookmark.setName(newUpdateStr);
        BookmarkDB.update(oldName, bookmark, scope);
    }
    
}
