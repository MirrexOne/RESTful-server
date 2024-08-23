package dev.mirrex.restserver.repository;

import dev.mirrex.restserver.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    void deleteAllByStatusTrue();

    long countByStatus(Boolean status);

    Page<Todo> findByStatus(Boolean status, Pageable pageable);

    @Modifying
    @Query(value = "TRUNCATE TABLE todos RESTART IDENTITY", nativeQuery = true)
    void truncateTableAndResetSequence();

    @Modifying
    @Query(value = "ALTER SEQUENCE todos_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}
