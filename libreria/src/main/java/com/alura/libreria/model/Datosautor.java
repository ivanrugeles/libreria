package com.alura.libreria.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
    public record Datosautor(
            @JsonAlias("name") String autorNombre,
            @JsonAlias("birth_year") Integer anoNacimiento,
            @JsonAlias("death_year") Integer anoFallecido

    ) {

    }
