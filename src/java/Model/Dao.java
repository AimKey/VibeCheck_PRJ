package Model;


 // @author phamm
import java.util.ArrayList;
import java.util.Optional;


public interface Dao<T> {
    
    public Optional<T> get(long id);
    public ArrayList<T> getAll();
    public boolean insert(T t);
    public boolean update(T t, String[] params);
    public boolean delete(int id);
}