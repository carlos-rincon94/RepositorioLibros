package com.example.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
       @JsonAlias("title") String titulo,
       @JsonAlias("authors") List <DatosAutor> autor,
       @JsonAlias("languages") List<String> idiomas,
       @JsonAlias("download_count") double numeroDeDescargas
) {
    @Override
    public String toString() {
        return "DatosLibros{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idiomas=" + idiomas +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }
}
