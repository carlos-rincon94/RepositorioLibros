package com.example.desafio.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
