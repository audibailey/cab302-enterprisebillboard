package common.models;

import common.utils.RandomFactory;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Session implements Serializable {
    public String token;
    public int userId;
    public String username;
    public Permissions permissions;
    public LocalDateTime expireTime;

    public Session(int userId, String username, Permissions permissions) {
        this.token = RandomFactory.token();
        this.userId = userId;
        this.username = username;
        this.permissions = permissions;
        this.expireTime = LocalDateTime.now().plusHours(24);
    }
}
