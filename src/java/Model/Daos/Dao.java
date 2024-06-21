package Model.Daos;


 // @author phamm
import java.util.ArrayList;
import java.util.Optional;


public interface Dao<T> {
    
    Optional<T> get(int id);
    ArrayList<T> getAll();
    boolean insert(T t);
    boolean update(T t, String[] params);
    boolean delete(int id);
}