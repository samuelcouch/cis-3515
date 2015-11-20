package edu.temple.lab_8;

/**
 * Created by samcouch on 11/9/15.
 */
public class WatchedFile {
    public String url;
    public byte[] hash;

    public WatchedFile(String url){
        this.url = url;
        this.hash = new byte[]{};
    }
}
