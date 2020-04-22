/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmarks;

/**
 *
 * @author blank
 */

import java.io.Serializable;

public class Bookmark implements Serializable{
    private int id;
    private String type;
    private String name;
    private String url;
    private String icon;
    private String logo;

    public Bookmark(){
        this.id = -1;
        this.type = "";
        this.name = "";
        this.url = "";
        this.icon = "fas fa-bookmark";
        this.logo = "";
    }

    public Bookmark(int id, String type, String name, String url, String icon, String logo){
        this.id = id;
        this.type = type;
        this.name = name;
        this.url = url;
        if (icon.equals(""))
            icon = "fas fa-bookmark";
        this.icon = icon;
        this.logo = logo;
    }
    
    public Bookmark(String type, String name, String url, String icon, String logo){
        this.type = type;
        this.name = name;
        this.url = url;
        if (icon.equals(""))
            icon = "fas fa-bookmark";
        this.icon = icon;
        this.logo = logo;
    }
    
    public int getID(){
        return id;
    }
    
    public String getType(){
        return type;
    }
    
    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getIcon(){
        return icon;
    }
    
    public String getLogo(){
        return logo;
    }
    
    public void setID(int id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setIcon(String icon){
        this.icon =  icon;
    }
    
    public void setLogo(String logo){
        this.logo = logo;
    }
}

