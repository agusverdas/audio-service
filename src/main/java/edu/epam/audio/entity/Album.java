package edu.epam.audio.entity;

import java.util.List;

public class Album extends Entity {
    private long albumId;
    private String albumTitle;
    private Author author;
    private String photo;
    private List<Song> songs;
    private double cost;

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (albumId != album.albumId) return false;
        if (Double.compare(album.cost, cost) != 0) return false;
        if (albumTitle != null ? !albumTitle.equals(album.albumTitle) : album.albumTitle != null) return false;
        if (author != null ? !author.equals(album.author) : album.author != null) return false;
        if (photo != null ? !photo.equals(album.photo) : album.photo != null) return false;
        return songs != null ? songs.equals(album.songs) : album.songs == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (albumId ^ (albumId >>> 32));
        result = 31 * result + (albumTitle != null ? albumTitle.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (songs != null ? songs.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", albumTitle='" + albumTitle + '\'' +
                ", author=" + author +
                ", photo='" + photo + '\'' +
                ", songs=" + songs +
                ", cost=" + cost +
                '}';
    }
}
