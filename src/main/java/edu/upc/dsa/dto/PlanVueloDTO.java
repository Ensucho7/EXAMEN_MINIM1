package edu.upc.dsa.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class PlanVueloDTO {

    //atributos
    private String id;
    private String dronId;
    private String pilotoId;
    private String fecha;
    private double duracionHoras;
    private List<PosicionDTO> posiciones;

    //inicializar constructores en DTO
    public PlanVueloDTO() {
        this.posiciones = new ArrayList<>();
    }

    public PlanVueloDTO(String dronId, String pilotoId, String fecha, double duracionHoras, List<PosicionDTO> posiciones) {
        this(null, dronId, pilotoId, fecha, duracionHoras, posiciones);
    }

    public PlanVueloDTO(String id, String dronId, String pilotoId, String fecha, double duracionHoras, List<PosicionDTO> posiciones) {
        this.id = id;
        this.dronId = dronId;
        this.pilotoId = pilotoId;
        this.fecha = fecha;
        this.duracionHoras = duracionHoras;
        this.posiciones = posiciones != null ? posiciones : new ArrayList<>();
    }

    @ApiModelProperty(value = "ID del plan (opcional, si se deja vacio se genera uno aleatorio)")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDronId() {
        return dronId;
    }

    public void setDronId(String dronId) {
        this.dronId = dronId;
    }

    public String getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(String pilotoId) {
        this.pilotoId = pilotoId;
    }

    //poner formato de fecha para ponerlo bien en el swagger
    @ApiModelProperty(value = "Formato de fecha: yyyy-MM-dd HH:mm", example = "yyyy-MM-dd HH:mm")
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(double duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public List<PosicionDTO> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<PosicionDTO> posiciones) {
        this.posiciones = posiciones;
    }
}
