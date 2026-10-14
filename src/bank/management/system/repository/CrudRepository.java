package bank.management.system.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Generic CRUD contract: reusable for different model types and identifier types. */
public interface CrudRepository<T, ID> {
    T create(T item) throws SQLException;
    Optional<T> findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean update(T item) throws SQLException;
    boolean deleteById(ID id) throws SQLException;
}
