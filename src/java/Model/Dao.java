package Model;

// @author phamm

import java.util.ArrayList;
import java.util.Optional;

public interface Dao<T> {

    // READ
    Optional<T> get(long id);
    ArrayList<T> getAll();
    // POST
    boolean save(T t);
    // UPDATE
    boolean update(T t, String[] params);
    // DELETE
    boolean delete(T t);
}