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
    private String type;
    private String name;
    private String url;
    private String icon;
    private String logo;
    private Boolean display;

    public Bookmark(){
        this.type = "";
        this.name = "";
        this.url = "";
        this.icon = "fas fa-bookmark";
        this.logo = "";
        this.display = true;
    }

    public Bookmark(String type, String name, String url, String icon,
            String logo, Boolean display){
        this.type = type;
        this.name = name;
        this.url = urlFormatter(url);
        if (icon.equals(""))
            icon = "fas fa-bookmark";
        this.icon = icon;
        this.logo = logo;
        this.display = display;
    }
    
    public Bookmark(String type, String name, String url, String icon, String logo){
        this.type = type;
        this.name = name;
        this.url = urlFormatter(url);
        if (icon.equals(""))
            icon = "fas fa-bookmark";
        this.icon = icon;
        this.logo = logo;
        this.display = true;
    }
    
    public String urlFormatter(String url){
        String redirectSyntax = "http://";
        try{
            if (url.substring(0, 4).equals("http")) {
                return url;
            }
            else
                return redirectSyntax + url;
        }
        catch (StringIndexOutOfBoundsException e){
            return "";
        }
        
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
    
    public Boolean getDisplay(){
        return display;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setUrl(String url){
        this.url = urlFormatter(url);
    }

    public void setIcon(String icon){
        this.icon =  icon;
    }
    
    public void setLogo(String logo){
        this.logo = logo;
    }
    
    public void setDisplay(Boolean display){
        this.display = display;
    }
}

