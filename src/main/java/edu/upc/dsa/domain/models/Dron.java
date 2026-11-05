package edu.upc.dsa.domain.models;

import edu.upc.dsa.util.RandomUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

public class Dron {

    //atributos
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private double horasVuelo;
    private boolean enMantenimiento;
    private List<PlanVuelo> planesVuelo;

    //inicializar constructores
    public Dron() {
        this.setId(RandomUtils.getId());
        this.enMantenimiento = false;
        this.planesVuelo = new LinkedList<>();
    }

    public Dron(String nombre, String fabricante, String modelo) {
        this(null, nombre, fabricante, modelo);
    }

    public Dron(String id, String nombre, String fabricante, String modelo) {
        this();
        if (id != null) this.setId(id);
        this.setNombre(nombre);
        this.setFabricante(fabricante);
        this.setModelo(modelo);
    }


    //getters y seters
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

    public double getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(double horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    //hecho para que en el swagger cuando añadas un dron no te salga para añadir un plan de vuelo tambien.
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
        return "Dron [id=" + id + ", nombre=" + nombre + ", fabricante=" + fabricante + ", modelo=" + modelo + ", horasVuelo=" + horasVuelo + ", enMantenimiento=" + enMantenimiento + "]";
    }
}
