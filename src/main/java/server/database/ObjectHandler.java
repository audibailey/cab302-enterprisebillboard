package server.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class ObjectHandler<T> {
    protected Connection connection;

    public ObjectHandler(Connection connection) { this.connection = connection; }

    protected abstract Optional<T> get(String name) throws Exception;

    protected abstract List<T> getAll() throws Exception;

    protected abstract void insert(T t) throws Exception;

    protected abstract void update(T t) throws Exception;

    protected abstract void delete(T t) throws Exception;
}
