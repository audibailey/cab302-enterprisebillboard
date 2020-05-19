package client.services;

import common.models.Session;

public class SessionService {
    private Session session;

    protected SessionService() {}

    private static class SessionServiceHolder {
        private final static SessionService INSTANCE = new SessionService();
    }

    public static Session getInstance() { return SessionServiceHolder.INSTANCE.session; }

    public static void setInstance(Session session) { SessionServiceHolder.INSTANCE.session = session; }
}
