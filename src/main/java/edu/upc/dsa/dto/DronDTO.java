package edu.upc.dsa.dto;

import io.swagger.annotations.ApiModelProperty;

public class DronDTO {

    //atributos para el swagger
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private double horasVuelo;
    private boolean enMantenimiento;

    //inicializar constructores en DTO
    public DronDTO() {
    }

    public DronDTO(String id, String nombre, String fabricante, String modelo, double horasVuelo, boolean enMantenimiento) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = horasVuelo;
        this.enMantenimiento = enMantenimiento;
    }

    @ApiModelProperty(value = "ID del dron (opcional, si se deja vacio se genera uno aleatorio)")
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

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    //las horas de vuelo no se ponen a mano, se calculan con los planes de vuelo
    @ApiModelProperty(hidden = true)
    public double getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(double horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    //hecho para que en el swagger no salga enMantenimiento al añadir un dron, lo cambia el manager
    @ApiModelProperty(hidden = true)
    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }
}
