package com.alura.libreria.principal;

import com.alura.libreria.model.*;
import com.alura.libreria.repository.AutorRepository;
import com.alura.libreria.repository.LibroRepository;
import com.alura.libreria.sevice.ConsumoApi;
import com.alura.libreria.sevice.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;




    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository=autorRepository;
    }

    public Principal() {

    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar Autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libro por idioma
                    
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoreRegistrados();
                    break;
                case 4:
                     mostrarAutoresVivos();
                    break;
                case 5:
                    mostrarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }


    }

    private void mostrarAutoresVivos() {
        // Pedir al usuario el año
        System.out.println("Escribe el año para ver los autores vivos:");
        Integer anio = teclado.nextInt();  // Lee el año desde la entrada

        // Llamar al repositorio para obtener los autores vivos en ese año
        List<Autor> autoresVivos = autorRepository.findAutoresVivos(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
            // Mostrar los resultados
            autoresVivos.forEach(autor -> {
                System.out.println("\n-------AUTOR--------");
                System.out.println("Nombre: " + autor.getAutorNombre());
                System.out.println("Año de nacimiento: " + autor.getAñoNacimiento());
                System.out.println("Año de fallecimiento: " + (autor.getAñoFallecido() == null ? "N/A" : autor.getAñoFallecido()));
                System.out.println("Libros: ");
                autor.getLibros().forEach(libro -> {
                    System.out.println(" - " + libro.getTitulo());
                });
                System.out.println("--------------------\n");
            });
        }
    }

    private void mostrarLibrosIdioma() {
        System.out.println("Escribe el idioma por el cual deseas listar los libros:");
        System.out.println("es"+"-----Español");
        System.out.println("en"+"-----Inglés");
        System.out.println("fr"+"-----Francés");
        System.out.println("pt"+"----Portugués");

        String idioma = teclado.nextLine().toLowerCase();  // Convertimos a minúsculas para no tener problemas con las mayúsculas

        // Usar el repositorio para obtener los libros por idioma
        List<Libro> librosFiltrados = libroRepository.findByIdioma(idioma);

        if (librosFiltrados.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        } else {
            librosFiltrados.forEach(libro -> {
                System.out.println("\n-------LIBRO--------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Lenguajes: " + String.join(", ", libro.getLenguaje())); // Mostrar todos los idiomas
                System.out.println("Autor: " + libro.getNombreAutor());
                System.out.println("--------------------\n");
            });
        }



    }


    private void mostrarAutoreRegistrados() {
        List<Autor> autors= autorRepository.findAll();
        if (autors.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            autors.forEach(autor -> {
                // Imprimir detalles del autor
                System.out.println("\n-------AUTOR--------");
                System.out.println("Nombre: " + autor.getAutorNombre());
                System.out.println("Año de Nacimiento: " + autor.getAñoNacimiento());
                System.out.println("Año de Fallecimiento: " + autor.getAñoFallecido());

                // Mostrar los libros del autor
                if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                    System.out.println("Libros:");
                    autor.getLibros().forEach(libro -> {
                        System.out.println(" - " + libro.getTitulo());
                    });
                } else {
                    System.out.println("No tiene libros registrados.");
                }

                System.out.println("--------------------\n");
            });
        }
        }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros= libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> System.out.println(libro));
        }
    }

    private Datoslibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "search=" + nombreLibro.replace(" ", "+"));

        // Verificar si la respuesta de la API no es nula o vacía
        if (json == null || json.isEmpty()) {
            System.out.println("No se recibió una respuesta válida de la API.");
            return null;
        }

        var datos = conversor.obtenerDatos(json, DatosRespuestaLibros.class);

        // Verificar si la lista de libros es nula o vacía
        if (datos == null || datos.libros() == null || datos.libros().isEmpty()) {
            System.out.println("No se encontraron libros, inténtalo de nuevo.");
            return null;  // No encontramos libros
        }

        Optional<Datoslibro> libroBuscado = datos.libros().stream()
                .filter(t -> t.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        // Verificamos si se encontró el libro
        if (libroBuscado.isPresent()) {
            return libroBuscado.get();
        } else {
            System.out.println("Libro no encontrado, inténtalo otra vez.");
            return null;  // No se encontró el libro
        }
    }



    private void buscarLibroWeb() {

        Datoslibro datoslibro = getDatosLibro();


        if (datoslibro == null) {
            System.out.println("No se encontró el libro, intenta de nuevo.");
            return;

        }

        // Verificamos si el autor está presente antes de acceder a él
        if (datoslibro.autor() == null || datoslibro.autor().isEmpty()) {
            System.out.println("No se encontró un autor para el libro.");
            return;
        }

        // Si el libro y el autor son válidos, continuamos con el flujo normal
        Libro libro = new Libro(datoslibro);
        Autor autorExistente = autorRepository.findByAutorNombre(datoslibro.autor().get(0).autorNombre());

        if (autorExistente == null) {
            List<Datosautor> datosautor = datoslibro.autor();
            if (datosautor != null && !datosautor.isEmpty()) {
                var autorGuardado = new Autor(datosautor);
                autorRepository.save(autorGuardado);
                libro.setAutor(autorGuardado);
                libro.setNombreAutor(autorGuardado.getAutorNombre());
            } else {
                System.out.println("No se pudo encontrar un autor válido para este libro.");
                return;
            }
        } else {
            autorRepository.save(autorExistente);
            libro.setAutor(autorExistente);
            libro.setNombreAutor(autorExistente.getAutorNombre());
        }

        libroRepository.save(libro);
        System.out.println(libro);

    }






}