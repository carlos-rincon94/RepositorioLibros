package com.example.desafio.principal;

import com.example.desafio.model.*;
import com.example.desafio.repository.AutorRepository;
import com.example.desafio.repository.LibroRepository;
import com.example.desafio.service.ConsumoAPI;
import com.example.desafio.service.ConvierteDatos;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {


    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI= new ConsumoAPI();

    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository LibroRepositorio;
    private AutorRepository AutorRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.LibroRepositorio=libroRepository;
        this.AutorRepositorio=autorRepository;
    }

    public void muestraElMenu(){
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma inglés
                    6-  Listar libros por idioma español
                                                     
                    0 - Salir
                    """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorNombre();
                    break;
                case 2:
                    listaLibrosRegistrados();
                    break;
                case 3:
                    listaAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosPorAno();
                    break;
                case 5:
                    listarLibrosEnIngles();
                    break;

                case 6:
                    listarLibrosEnEspanol();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
            }

        //Trabajando con estadisticas

//        DoubleSummaryStatistics est = datos.resultados().stream()
//                .filter(d-> d.numeroDeDescargas()> 0)
//                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
//        System.out.println("Cantidad media de descargas: "+ est.getAverage());
//        System.out.println("Cantidad máxima de descargas: " + est.getMax());
//        System.out.println("Cantidad mínima de descargas: " + est.getMin());
//        System.out.println("Total registros evaluados para calc estadistica: "+ est.getCount());
    }



    private void buscarLibroPorNombre() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();


        if(LibroRepositorio.existsByTituloIgnoreCase(tituloLibro)){
            System.out.println("Libro encontrado");
            System.out.println("El libro buscado es: " + LibroRepositorio.findByTituloIgnoreCase(tituloLibro));
        }
        else{
            String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            var datosBusqueda= conversor.obtenerDatos(json, Datos.class);
            Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l-> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();
            if (libroBuscado.isPresent()){
                System.out.println("Libro encontrado");
                System.out.println(libroBuscado.get());
                DatosLibros datosLibros = libroBuscado.get();
                List<DatosAutor> datosAutores = libroBuscado.get().autor();
                Libro libro = new Libro(datosLibros);
                List<Autor> autores = datosAutores.stream().map(a -> new Autor(a)).toList();
                LibroRepositorio.save(libro);
                autores.forEach(a -> AutorRepositorio.save(a));

            }else {
                System.out.println("Libro no encontrado");
            }
        }

    }

    private void listaLibrosRegistrados() {
            libros = LibroRepositorio.findAll();
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);

    }

    private void listaAutoresRegistrados() {
        autores = AutorRepositorio.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::toString))
                .forEach(System.out::println);
    }

    private DatosAutor autoresVivosPorAno() {
        autores = AutorRepositorio.findAll();
        System.out.println("Ingrese el año que desea buscar para verificar autores vivos");
        int currentYear = teclado.nextInt();
        List<Autor> autoresVivos = autores.stream()
                .filter(dto -> dto.getBirthYear() != null && (dto.getDeathYear() == null || dto.getDeathYear() > currentYear))
                .collect(Collectors.toList());
        System.out.println(autoresVivos);
        return null;
    }

    private void listarLibrosEnIngles() {
        List<Libro> libros = LibroRepositorio.findAllByLanguageEn();
        libros.stream().forEach( libro -> System.out.println(libro));
    }

    private void listarLibrosEnEspanol() {
        List<Libro> libros = LibroRepositorio.findAllByLanguageEs();
        libros.stream().forEach( libro -> System.out.println(libro));
    }

}
