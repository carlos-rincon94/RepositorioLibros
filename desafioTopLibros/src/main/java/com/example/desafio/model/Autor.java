package com.example.desafio.model;

import jakarta.persistence.*;

@Entity
@Table(name ="tabla_autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column (unique = true, nullable = false)
    private String name;
    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name= "death_year")
    private Integer deathYear;

    public Autor(){

    }

    public Autor(DatosAutor datosAutor) {
        this.name = datosAutor.nombre();
        this.birthYear = datosAutor.getBirthYear();
        this.deathYear =  datosAutor.getDeathYear();
    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", birthyear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }
}
