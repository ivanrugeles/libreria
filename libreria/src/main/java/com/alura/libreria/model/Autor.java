package com.alura.libreria.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String autorNombre;
    private Integer anoNacimiento;
    private Integer anoFallecido;
   @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

   public Autor(){}

    public Autor(List<Datosautor> datosautor){

        this.autorNombre = datosautor.stream()
                .map(Datosautor::autorNombre)
                .findFirst()  // Devuelve el primer valor de la lista
                .orElse(null); // Si no hay autor, devuelve null

        // Obtenemos el primer año de nacimiento (suponiendo que la lista no está vacía)
        this.anoNacimiento = datosautor.stream()
                .map(Datosautor::anoNacimiento)
                .findFirst()  // Devuelve el primer año de nacimiento
                .orElse(null); // Si no hay año, devuelve null

        // Obtenemos el primer año de fallecimiento (suponiendo que la lista no está vacía)
        this.anoFallecido = datosautor.stream()
                .map(Datosautor::anoFallecido)
                .findFirst()  // Devuelve el primer año de fallecimiento
                .orElse(null); // Si no hay año, devuelve null
    }
   public  List<Libro> getLibros(){return libros;}
    public void setLibros(List<Libro> libros){
       libros.forEach(e->e.setAutor(this));
       this.libros= libros;
    }


    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public Integer getAñoNacimiento() {
        return anoNacimiento;
    }

    public void setAñoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAñoFallecido() {
        return anoFallecido;
    }

    public void setAñoFallecido(Integer anoFallecido) {
        this.anoFallecido = anoFallecido;
    }

    @Override
    public String toString() {
        return  "\n"+
                "Nombre= " + autorNombre + "\n" +
                "año de Nacimiento= " + anoNacimiento +"\n"+
                "año de Fallecido= " + anoFallecido
                ;
    }
}
