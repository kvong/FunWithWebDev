package listener;

import javax.servlet.*;
import java.util.*;

import bookmarks.*;
import database.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import static servlets.BookmarkServlet.cleanInput;
import static servlets.BookmarkServlet.sortByTypes;
/**
 *
 * @author blank
 */
public class BookmarkContextListener implements ServletContextListener{
    public void contextInitialized(ServletContextEvent event){
        try{
                Class.forName("com.mysql.jdbc.Driver");
                
                String dbURL = "jdbc:mysql://localhost:3306/homepage";
                String username = "webmaster";
                String password = "gochujang";
                Connection connection = DriverManager.getConnection(
                        dbURL, username, password);
                     
            }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("ClassNotFoundException Thrown");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQLException Thrown");
        }
        
        
        String url = "";
        ServletContext sc = event.getServletContext();

        // Get action
        String action = "view";

        String message = "";

            
        ArrayList<BookmarkSection> bookmarkSections;
                    

        ArrayList<Bookmark> bookmarks = BookmarkDB.getBookmarks();
        BookmarkSection bookmarkSection = new BookmarkSection();
        bookmarkSections = bookmarkSection.createSections(bookmarks);
        printAll(bookmarks);
        sc.setAttribute("sections", sortByTypes(bookmarkSections));

    }
    public void contextDestroyed(ServletContextEvent event){
        // Do clean up
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
}
