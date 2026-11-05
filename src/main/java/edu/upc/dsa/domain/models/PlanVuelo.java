package edu.upc.dsa.domain.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.LinkedList;
import java.util.List;

public class PlanVuelo {

    //atributos
    private String id;
    private String dronId;
    private String pilotoId;
    private String fecha;
    private double duracionHoras;
    private List<Posicion> posiciones;

    //inicializar constructores
    public PlanVuelo() {
        this.setId(RandomUtils.getId());
        this.posiciones = new LinkedList<>();
    }

    public PlanVuelo(String dronId, String pilotoId, String fecha, double duracionHoras) {
        this(null, dronId, pilotoId, fecha, duracionHoras);
    }

    public PlanVuelo(String id, String dronId, String pilotoId, String fecha, double duracionHoras) {
        this();
        if (id != null) this.setId(id);
        this.setDronId(dronId);
        this.setPilotoId(pilotoId);
        this.setFecha(fecha);
        this.setDuracionHoras(duracionHoras);
    }

    public PlanVuelo(String dronId, String pilotoId, String fecha, double duracionHoras, List<Posicion> posiciones) {
        this(dronId, pilotoId, fecha, duracionHoras);
        if (posiciones != null) this.setPosiciones(posiciones);
    }

    //setters y getters
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

    public List<Posicion> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<Posicion> posiciones) {
        this.posiciones = posiciones;
    }

    //to string para parte 2
    @Override
    public String toString() {
        return "PlanVuelo [id=" + id + ", dronId=" + dronId + ", pilotoId=" + pilotoId + ", fecha=" + fecha + ", duracionHoras=" + duracionHoras + "]";
    }
}
