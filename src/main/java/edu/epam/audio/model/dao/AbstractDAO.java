package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Entity;

import java.util.List;

public abstract class AbstractDAO <K, T extends Entity> {
    public abstract List<T> findAll();
}
