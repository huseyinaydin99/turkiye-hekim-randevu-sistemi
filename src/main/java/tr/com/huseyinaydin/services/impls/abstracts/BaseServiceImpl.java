package tr.com.huseyinaydin.services.impls.abstracts;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.services.impls.base.BaseService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }
}