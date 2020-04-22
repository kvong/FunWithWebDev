/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmarks;

import java.util.ArrayList;

/**
 *
 * @author blank
 * 
 */
public class BookmarkSection {
    private String type;
    private ArrayList<Bookmark> bookmarks;
    
    public BookmarkSection(){
        this.type = "";
        this.bookmarks = new ArrayList<Bookmark>();
    }
    
    public BookmarkSection(String type, ArrayList<Bookmark> bookmarks){
        this.type = type;
        this.bookmarks = bookmarks;
    }
    
    public ArrayList<BookmarkSection> createSections(ArrayList<Bookmark> bookmarks){
        ArrayList<BookmarkSection> bookmarkSection = new ArrayList<BookmarkSection>();
        ArrayList<String> seenTypes = new ArrayList<String>();
        
        for (Bookmark bookmarkType : bookmarks){
            String type = bookmarkType.getType();
            
            if (!hasBeenAdded(type, seenTypes)){
                ArrayList<Bookmark> thisSectionBookmark = new ArrayList<Bookmark>();
                for (Bookmark bookmark : bookmarks){
                    if (type.equals(bookmark.getType())){
                        thisSectionBookmark.add(bookmark);
                    }
                }                
                seenTypes.add(type);
                BookmarkSection newSection = new BookmarkSection(type, thisSectionBookmark);
                bookmarkSection.add(newSection);
            }
        }
        return bookmarkSection;
    }
    
    public boolean hasBeenAdded(String type, ArrayList<String> seenTypes){
        for (String seen : seenTypes){
            if (type.equals(seen))
                return true;
        }
        return false;
    }
    
    public String getType(){
        return this.type;
    }
    
    public ArrayList<Bookmark> getBookmarks(){
        return this.bookmarks;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setBookmarks(ArrayList<Bookmark> bookmarks){
        this.bookmarks = bookmarks;
    }
}
