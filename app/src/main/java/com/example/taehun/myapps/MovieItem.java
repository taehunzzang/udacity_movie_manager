package com.example.taehun.myapps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by taehun on 15. 6. 29..
 */
public class MovieItem implements Parcelable {


    String genre_ids;
    String id;
    String overview;
    String release_date;
    String poster_path;
    String popularity;
    String title;
    String vote_average;
    String vote_count;
    int favor;// 0 is false, 1 is true;

    public int getFavor(){
        return favor;
    }
    public void setFavor(int _favor){
        favor = _favor;
    }


    public MovieItem() {
    }

    public MovieItem(Parcel in) {
        readFromParcel(in);
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
        dest.writeString(genre_ids);
        dest.writeString(id);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(popularity);
        dest.writeString(title);
        dest.writeString(vote_average);
        dest.writeString(vote_count);
        dest.writeString(title);
        dest.writeInt(favor);

    }

    private void readFromParcel(Parcel in) {
        genre_ids = in.readString();
        id = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        popularity = in.readString();
        title = in.readString();
        vote_average = in.readString();
        vote_count = in.readString();
        favor = in.readInt();

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
