package com.example.taehun.myapps.movies;

/**
 * Created by taehun on 15. 6. 29..
 */
public class MovieItem {
//    "adult":false,
//            "backdrop_path":"\/dkMD5qlogeRMiEixC4YNPUvax2T.jpg",
//            "genre_ids":[28,12,878,53],
//            "id":135397,
//            "original_language":"en",
//            "original_title":"Jurassic World",
//            "overview":"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
//            "release_date":"2015-06-12",
//            "poster_path":"\/uXZYawqUsChGSj54wcuBtEdUJbh.jpg",
//            "popularity":97.733248,
//            "title":"Jurassic World",
//            "video":false,
//            "vote_average":7,"vote_count":742

    boolean adult=false;
    String backdrop_path;
    String genre_ids;
    String id;
    String original_language;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    String popularity;
    String title;
    boolean video=false;
    String vote_average;
    String vote_count;


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }
}
