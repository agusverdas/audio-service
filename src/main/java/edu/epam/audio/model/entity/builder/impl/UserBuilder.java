package edu.epam.audio.model.entity.builder.impl;

import edu.epam.audio.model.entity.Privileges;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.Builder;

//todo: ask Это вообще хорошая идея?

public class UserBuilder implements Builder<User> {
    private User user;

    public UserBuilder(){
        user = new User();
    }

    public UserBuilder addId(Long id){
        user.setUserId(id);
        return this;
    }

    public UserBuilder addEmail(String email){
        user.setEmail(email);
        return this;
    }

    public UserBuilder addName(String name){
        user.setName(name);
        return this;
    }

    public UserBuilder addPhoto(String photo){
        user.setPhoto(photo);
        return this;
    }

    public UserBuilder addRole(Privileges role){
        user.setRole(role);
        return this;
    }

    public UserBuilder addBonus(Double bonus){
        user.setBonus(bonus);
        return this;
    }

    public User build(){
        return user;
    }
}
