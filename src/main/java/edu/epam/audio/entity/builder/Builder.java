package edu.epam.audio.entity.builder;

import edu.epam.audio.entity.Entity;

public interface Builder<T extends Entity> {
    T build();
}
