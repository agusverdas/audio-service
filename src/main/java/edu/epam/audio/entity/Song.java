package edu.epam.audio.entity;

import java.util.ArrayList;
import java.util.List;

public class Song extends Entity{
    private long songId;
    private String title;
    private String path;
    private long albumId;
    private double cost;
    private List<Author> authorList;

    public Song(){
        authorList = new ArrayList<>();
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (songId != song.songId) return false;
        if (albumId != song.albumId) return false;
        if (Double.compare(song.cost, cost) != 0) return false;
        if (title != null ? !title.equals(song.title) : song.title != null) return false;
        if (path != null ? !path.equals(song.path) : song.path != null) return false;
        return authorList != null ? authorList.equals(song.authorList) : song.authorList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (songId ^ (songId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (int) (albumId ^ (albumId >>> 32));
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (authorList != null ? authorList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", albumId=" + albumId +
                ", cost=" + cost +
                ", authorList=" + authorList +
                '}';
    }
}
