package edu.upc.dsa.domain.models;

import edu.upc.dsa.util.RandomUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

public class Piloto {

    //Atributos
    private String id;
    private String nombre;
    private String apellidos;
    private double horasVuelo;


    private List<PlanVuelo> planesVuelo;

    //Inicializar constructores
    public Piloto() {
        this.setId(RandomUtils.getId());
        this.planesVuelo = new LinkedList<>();
    }

    public Piloto(String nombre, String apellidos) {
        this(null, nombre, apellidos);
    }

    public Piloto(String id, String nombre, String apellidos) {
        this();
        if (id != null) this.setId(id);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
    }

    //set y getters publicos

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public double getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(double horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    //hecho para que en el swagger cuando añadas un piloto no te salga para añadir un plan de vuelo tambien.
    @ApiModelProperty(hidden = true)
    public List<PlanVuelo> getPlanesVuelo() {
        return planesVuelo;
    }

    public void setPlanesVuelo(List<PlanVuelo> planesVuelo) {
        this.planesVuelo = planesVuelo;
    }

    //to string para parte 2

    @Override
    public String toString() {
        return "Piloto [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", horasVuelo=" + horasVuelo + "]";
    }



}
