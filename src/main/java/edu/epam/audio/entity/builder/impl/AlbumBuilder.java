package edu.epam.audio.entity.builder.impl;

import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.builder.Builder;

import java.util.List;

public class AlbumBuilder implements Builder<Album> {
    private Album album;

    public AlbumBuilder(){
        album = new Album();
    }

    public AlbumBuilder addId(long id){
        album.setAlbumId(id);
        return this;
    }

    public AlbumBuilder addTitle(String title){
        album.setAlbumTitle(title);
        return this;
    }

    public AlbumBuilder addAuthorId(long id){
        album.setAlbumId(id);
        return this;
    }

    public AlbumBuilder addPhoto(String photo){
        album.setPhoto(photo);
        return this;
    }

    public AlbumBuilder addSongs(List<Song> songs){
        album.setSongs(songs);
        return this;
    }

    public AlbumBuilder addCost(double cost){
        album.setCost(cost);
        return this;
    }

    @Override
    public Album build() {
        return album;
    }
}
