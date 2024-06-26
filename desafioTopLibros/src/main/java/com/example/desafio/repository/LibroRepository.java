
package com.example.desafio.repository;

import com.example.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    boolean existsByTituloIgnoreCase(String titulo);
    @Query(value = "SELECT * FROM tabla_libros l WHERE 'en' = ANY(l.idiomas)", nativeQuery = true)
    List<Libro> findAllByLanguageEn();

    @Query(value = "SELECT * FROM tabla_libros l WHERE 'es' = ANY(l.idiomas)", nativeQuery = true)
    List<Libro> findAllByLanguageEs();
}


