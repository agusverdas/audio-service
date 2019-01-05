package edu.epam.audio.entity.builder.impl;

import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.builder.Builder;

public class SongBuilder implements Builder<Song> {
    private Song song;

    public SongBuilder(){
        song = new Song();
    }

    public SongBuilder addId(Long id){
        song.setSongId(id);
        return this;
    }

    public SongBuilder addTitle(String title){
        song.setTitle(title);
        return this;
    }

    public SongBuilder addPath(String path){
        song.setPath(path);
        return this;
    }

    public SongBuilder addAlbumId(long albumId){
        song.setAlbumId(albumId);
        return this;
    }

    public SongBuilder addCost(double cost){
        song.setCost(cost);
        return this;
    }

    public Song build(){
        return song;
    }
}
