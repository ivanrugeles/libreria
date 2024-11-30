package com.alura.libreria.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Datoslibro(
            @JsonAlias("title")String titulo,
            @JsonAlias("authors") List<Datosautor> autor,
            @JsonAlias("languages")List<String> lenguaje,
            @JsonAlias("download_count")Integer numDescargas
           ) {

    }

