package com.example.taehun.myapps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by taehun on 15. 6. 29..
 */
public class MovieItem implements Parcelable {


    boolean adult = false;
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
    boolean video = false;
    String vote_average;
    String vote_count;


    public MovieItem() {
    }

    public MovieItem(Parcel in) {
        readFromParcel(in);
    }


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeString(genre_ids);
        dest.writeString(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(vote_average);
        dest.writeString(vote_count);
        dest.writeString(title);

    }

    private void readFromParcel(Parcel in) {
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        genre_ids = in.readString();
        id = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        popularity = in.readString();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readString();
        vote_count = in.readString();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
