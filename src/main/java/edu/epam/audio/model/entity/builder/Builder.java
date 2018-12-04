package edu.epam.audio.model.entity.builder;

import edu.epam.audio.model.entity.Entity;

public interface Builder<T extends Entity> {
    T build();
}
