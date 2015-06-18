package com.example.taehun.myapps.spotify;

/**
 * Created by taehun on 15. 6. 9..
 */
public class SpotifyItem {

    private String imgName;
    private String artistName;
    private String artistEtc;
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistEtc() {
        return artistEtc;
    }

    public void setArtistEtc(String artistEtc) {
        this.artistEtc = artistEtc;
    }
}
