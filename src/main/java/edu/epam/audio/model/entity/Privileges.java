package edu.epam.audio.model.entity;

public enum Privileges {
    USER, ADMIN;

    @Override
    public String toString() {
        return name();
    }
}
