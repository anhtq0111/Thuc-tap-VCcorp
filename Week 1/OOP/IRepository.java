package OOP;
import java.util.List;

public interface IRepository<T> {
    void add();
    void add(T entity);
    List<T> getAll();
    void update(T entity);
    void delete(T entity);
} 
