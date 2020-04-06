package server.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DBHandler<T> {

    Optional<T> get(String name) throws Exception;

    List<T> getAll() throws Exception;

    void insert(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;
}
