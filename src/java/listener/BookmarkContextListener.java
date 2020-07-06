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
        
        ServletContext sc = event.getServletContext();

        // Get action
        String action = "view";

        String message = "";

        ArrayList<Bookmark> globalBookmarks = BookmarkDB.getBookmarks(2);
        ArrayList<Bookmark> localBookmarks = BookmarkDB.getBookmarks(1);
        
        ArrayList<Bookmark> bookmarks = globalBookmarks;
        
        int globalUpdateNumber = -1;
        int localUpdateNumber = -2;
        
        if ( bookmarks == null || bookmarks.size() == 0){
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
        
        BookmarkSection bookmarkSection = new BookmarkSection();
        ArrayList<BookmarkSection> bookmarkSections = bookmarkSection.createSections(bookmarks);
        
        //printAll(bookmarks);
        sc.setAttribute("sections", sortByTypes(bookmarkSections));
        
        //BookmarkDB.syncDB("view");
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
