package edu.upc.dsa.domain.models;

public class Posicion {

    //atributos
    private double latitud;
    private double longitud;

    //inicializar
    public Posicion() {
    }

    public Posicion(double latitud, double longitud) {
        this.setLatitud(latitud);
        this.setLongitud(longitud);
    }

    //getters y setters
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    //to string para parte 2
    @Override
    public String toString() {
        return "Posicion [latitud=" + latitud + ", longitud=" + longitud + "]";
    }
}
