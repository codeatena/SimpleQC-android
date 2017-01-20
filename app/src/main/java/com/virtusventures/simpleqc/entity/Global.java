package com.virtusventures.simpleqc.entity;

/**
 * Created by mac on 26/12/2016.
 */
public class Global {

    private static Global ourInstance = new Global();

    private User currentUser;

    public static Global getInstance() {
        return ourInstance;
    }

    private Global() {

    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User user)
    {
        currentUser = user;
    }

}
