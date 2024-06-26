package com.example.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaDeNacimiento,
        @JsonAlias("death_year") String fechaDeFallecimiento
) {
    @Override
    public String toString() {
        return
                "{ \"nombre\": \"" + nombre  + "\"," +
                        "\"fechaDeNacimiento\":" + fechaDeNacimiento + "," +
                        " \"fechaDeFallecimiento\":" + fechaDeFallecimiento +"}";

    }

    public Integer getBirthYear() {
        return fechaDeNacimiento != null ? Integer.parseInt(fechaDeNacimiento) : null;
    }

    public Integer getDeathYear() {
        return fechaDeFallecimiento != null ? Integer.parseInt(fechaDeFallecimiento) : null;
    }
}
