package com.alura.libreria.repository;


import com.alura.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l WHERE :idioma IN (l.lenguaje)")
    List<Libro> findByIdioma(@Param("idioma") String idioma);


}
