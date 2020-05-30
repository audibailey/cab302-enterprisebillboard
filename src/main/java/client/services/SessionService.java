package client.services;

import common.utils.session.Session;

/**
 * This class is responsible for the backend sessions service for the client/viewer.
 *
 * @author Jamie Martin
 */
public class SessionService {
    private Session session;

    /**
     * initialise new session service
     */
    protected SessionService() {}

    /**
     * static singleton holder for session service
     */
    private static class SessionServiceHolder {
        private final static SessionService INSTANCE = new SessionService();
    }

    /**
     * get the session service instance
     * @return
     */
    public static Session getInstance() { return SessionServiceHolder.INSTANCE.session; }

    /**
     * set the session service instance
     * @return
     */
    public static void setInstance(Session session) { SessionServiceHolder.INSTANCE.session = session; }
}
