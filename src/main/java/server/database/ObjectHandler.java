package server.database;

import java.util.List;
import java.util.Optional;

public interface ObjectHandler<T> {
    Optional<T> get(int id) throws Exception;

    List<T> getAll() throws Exception;

    void insert(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;

    void deleteAll() throws Exception;
}
