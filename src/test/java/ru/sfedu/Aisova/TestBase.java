package ru.sfedu.Aisova;

import ru.sfedu.Aisova.bean.User;

public class TestBase {

    public User createUser (long id, String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
}
