package tr.com.huseyinaydin.services.impls.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
    T save(T entity);
    T update(ID id, T entity);
    void delete(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
}