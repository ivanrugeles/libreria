package com.alura.libreria.model;

import jakarta.persistence.*;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Entity
@Table(name = "libros")
public class Libro {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @JoinColumn(unique = true)
    private String titulo;
    private List<String> lenguaje;
    private Integer numDescargas;
    private String nombreAutor;
    @ManyToOne
    private Autor autor;




    public Libro(){}



    public Libro(Datoslibro d){
        this.titulo=d.titulo();
        this.lenguaje=d.lenguaje();
        this.numDescargas=d.numDescargas();
    }



    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public List<String> getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<String> lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(Integer numDescargas) {
        this.numDescargas = numDescargas;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    @Override
    public String toString() {
        return  "\n"+"-------LIBRO--------"+"\n"+
                "titulo= " + titulo + "\n" +
                "lenguaje= " + lenguaje +"\n"+
                "numDescargas= " + numDescargas +"\n"+
                "autor= " + autor +"\n"+"-----------------"+"\n";

    }
}