package edu.epam.audio.entity;

public enum Privileges {
    USER, ADMIN;

    @Override
    public String toString() {
        return name();
    }
}
