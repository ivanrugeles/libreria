package com.alura.libreria.repository;

import com.alura.libreria.model.Autor;
import com.alura.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByAutorNombre(String autorNombre);
    @Query("SELECT a FROM Autor a WHERE (a.anoFallecido IS NULL OR a.anoFallecido > :anio)")
    List<Autor> findAutoresVivos(@Param("anio") Integer anio);

}
