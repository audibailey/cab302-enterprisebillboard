package server.services;

import common.utils.RandomFactory;

import java.time.LocalDateTime;

public class Session {
    public String token;
    public String username;
    public LocalDateTime expireTime;

    public Session(String username) {
        this.token = RandomFactory.token();
        this.username = username;
        this.expireTime = LocalDateTime.now().plusHours(24);
    }
}
