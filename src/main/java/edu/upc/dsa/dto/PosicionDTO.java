package edu.upc.dsa.dto;

public class PosicionDTO {

    //atributos
    private double latitud;
    private double longitud;

    //inicializar constructores en DTO
    public PosicionDTO() {
    }

    public PosicionDTO(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

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
}
