package edu.upc.dsa.dto;

import io.swagger.annotations.ApiModelProperty;

public class PilotoDTO {

    //atributos que se ven en el swagger
    private String id;
    private String nombre;
    private String apellidos;
    private double horasVuelo;

    //inicializar constructores en DTO
    public PilotoDTO() {
    }

    public PilotoDTO(String id, String nombre, String apellidos, double horasVuelo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.horasVuelo = horasVuelo;
    }

    @ApiModelProperty(value = "ID del piloto (opcional, si se deja vacio se genera uno aleatorio)")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    //las horas de vuelo se calculan sumando los planes, no se ponen al crear
    @ApiModelProperty(hidden = true)
    public double getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(double horasVuelo) {
        this.horasVuelo = horasVuelo;
    }
}
