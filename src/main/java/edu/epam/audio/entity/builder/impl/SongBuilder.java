package edu.epam.audio.entity.builder.impl;

import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.builder.Builder;

import java.util.List;

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

    public SongBuilder addCost(double cost){
        song.setCost(cost);
        return this;
    }

    public SongBuilder addAuthors(List<Author> authors){
        song.setAuthorList(authors);
        return this;
    }

    public Song build(){
        return song;
    }
}
