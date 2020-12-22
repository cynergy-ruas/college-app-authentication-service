package io.github.cynergy.authservice.database;

import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.UserNotFoundException;

public interface UserDao {

    /**
     * Gets a user who has the given roll number
     * @param rollNumber
     * @return The user
     */
    public User getUser(String rollNumber) throws UserNotFoundException;

    /**
     * Adds the user to the database.
     * @param user
     */
    public String addUser(User user);
}
