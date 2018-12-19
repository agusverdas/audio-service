package edu.epam.audio.model.entity.builder.impl;

import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.builder.Builder;

public class AuthorBuilder implements Builder<Author> {
    private Author author;

    public AuthorBuilder(){
        author = new Author();
    }

    public AuthorBuilder addId(long id){
        author.setAuthorId(id);
        return this;
    }

    public AuthorBuilder addName(String name){
        author.setName(name);
        return this;
    }

    @Override
    public Author build() {
        return author;
    }
}
