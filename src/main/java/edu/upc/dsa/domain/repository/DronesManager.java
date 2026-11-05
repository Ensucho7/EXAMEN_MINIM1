package edu.upc.dsa.domain.repository;

import edu.upc.dsa.domain.models.Dron;
import edu.upc.dsa.domain.models.Piloto;
import edu.upc.dsa.domain.models.PlanVuelo;

import java.util.List;

public interface DronesManager {

    public Dron addDron(String id, String nombre, String fabricante, String modelo);
    public Dron addDron(Dron dron);
    public Piloto addPiloto(String id, String nombre, String apellidos);
    public Piloto addPiloto(Piloto piloto);

    public Dron getDron(String id);
    public Piloto getPiloto(String id);

    public List<Dron> getDronesOrderedByHorasVuelo();
    public List<Piloto> getPilotosOrderedByHorasVuelo();

    public Dron guardarDronEnMantenimiento(String id);
    public Dron repararDron();

    public PlanVuelo crearPlanVuelo(PlanVuelo plan);
    public List<PlanVuelo> getPlanesVueloPiloto(String pilotoId);
    public List<PlanVuelo> getPlanesVueloDron(String dronId);

    public void clear();
    public int size();
}
