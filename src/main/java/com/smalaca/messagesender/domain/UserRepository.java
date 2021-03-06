package com.smalaca.messagesender.domain;

public interface UserRepository {

    void add(User user);

    User getUserByLogin(String login);

    boolean exists(String login);

    boolean isBlocked(String login);

    boolean blockUser(String login);
}
