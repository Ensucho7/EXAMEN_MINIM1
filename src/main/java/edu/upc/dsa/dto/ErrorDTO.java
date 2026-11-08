package edu.upc.dsa.dto;

//DTO para devolver los errores con su codigo y su mensaje
public class ErrorDTO {

    private int codigo;
    private String mensaje;

    //inicializar constructores en DTO
    public ErrorDTO() {
    }

    public ErrorDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
