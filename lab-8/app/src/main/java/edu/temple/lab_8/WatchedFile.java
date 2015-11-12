package edu.temple.lab_8;

/**
 * Created by samcouch on 11/11/15.
 */
public class WatchedFile {
    private String url;
    private String hash;
    private Boolean newOnUpdate;

    public WatchedFile(String url){
        this.url = url;
        this.hash = "";
        this.newOnUpdate = false;
    }

    public String getUrl(){
        return this.url;
    }

    public boolean updateHash(String newHash){
        if(!this.hash.equals(newHash)){
            this.hash = newHash;
            this.newOnUpdate = true;
        } else {
            this.newOnUpdate = false;
        }

        return this.newOnUpdate;
    }
}
