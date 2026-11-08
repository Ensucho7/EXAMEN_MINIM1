package edu.upc.dsa.dto;

import edu.upc.dsa.domain.models.Dron;
import edu.upc.dsa.domain.models.Piloto;
import edu.upc.dsa.domain.models.PlanVuelo;
import edu.upc.dsa.domain.models.Posicion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//clase para pasar de los objetos del dominio a DTO y al reves
public class DtoMapper {

    //inicializar constructores en DTO
    private DtoMapper() {
    }

    //si el id esta bien se genera uno nuevo aleatorio
    private static String normalizarId(String id) {
        if (id == null) return null;
        String limpio = id.trim();
        if (limpio.isEmpty() || limpio.equalsIgnoreCase("string")) return null;
        return id;
    }

    public static DronDTO toDronDTO(Dron dron) {
        if (dron == null) return null;
        return new DronDTO(dron.getId(), dron.getNombre(), dron.getFabricante(), dron.getModelo(),
                dron.getHorasVuelo(), dron.isEnMantenimiento());
    }

    public static Dron toDron(DronDTO dto) {
        if (dto == null) return null;
        String id = normalizarId(dto.getId());
        return new Dron(id, dto.getNombre(), dto.getFabricante(), dto.getModelo());
    }

    public static List<DronDTO> toDronDTOList(List<Dron> drones) {
        if (drones == null) return new ArrayList<>();
        return drones.stream().map(DtoMapper::toDronDTO).collect(Collectors.toList());
    }

    public static PilotoDTO toPilotoDTO(Piloto piloto) {
        if (piloto == null) return null;
        return new PilotoDTO(piloto.getId(), piloto.getNombre(), piloto.getApellidos(), piloto.getHorasVuelo());
    }

    public static Piloto toPiloto(PilotoDTO dto) {
        if (dto == null) return null;
        String id = normalizarId(dto.getId());
        return new Piloto(id, dto.getNombre(), dto.getApellidos());
    }

    public static List<PilotoDTO> toPilotoDTOList(List<Piloto> pilotos) {
        if (pilotos == null) return new ArrayList<>();
        return pilotos.stream().map(DtoMapper::toPilotoDTO).collect(Collectors.toList());
    }

    public static PlanVueloDTO toPlanVueloDTO(PlanVuelo plan) {
        if (plan == null) return null;
        return new PlanVueloDTO(plan.getId(), plan.getDronId(), plan.getPilotoId(), plan.getFecha(),
                plan.getDuracionHoras(), toPosicionDTOList(plan.getPosiciones()));
    }

    public static PlanVuelo toPlanVuelo(PlanVueloDTO dto) {
        if (dto == null) return null;
        String id = normalizarId(dto.getId());
        PlanVuelo plan = new PlanVuelo(id, dto.getDronId(), dto.getPilotoId(), dto.getFecha(), dto.getDuracionHoras());
        plan.setPosiciones(toPosicionList(dto.getPosiciones()));
        return plan;
    }

    public static List<PlanVueloDTO> toPlanVueloDTOList(List<PlanVuelo> planes) {
        if (planes == null) return new ArrayList<>();
        return planes.stream().map(DtoMapper::toPlanVueloDTO).collect(Collectors.toList());
    }

    public static PosicionDTO toPosicionDTO(Posicion posicion) {
        if (posicion == null) return null;
        return new PosicionDTO(posicion.getLatitud(), posicion.getLongitud());
    }

    public static Posicion toPosicion(PosicionDTO dto) {
        if (dto == null) return null;
        return new Posicion(dto.getLatitud(), dto.getLongitud());
    }

    public static List<PosicionDTO> toPosicionDTOList(List<Posicion> posiciones) {
        if (posiciones == null) return new ArrayList<>();
        return posiciones.stream().map(DtoMapper::toPosicionDTO).collect(Collectors.toList());
    }

    public static List<Posicion> toPosicionList(List<PosicionDTO> posiciones) {
        if (posiciones == null) return new ArrayList<>();
        return posiciones.stream().map(DtoMapper::toPosicion).collect(Collectors.toList());
    }
}
