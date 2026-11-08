package edu.upc.dsa.services;

import edu.upc.dsa.domain.models.Dron;
import edu.upc.dsa.domain.models.Piloto;
import edu.upc.dsa.domain.models.PlanVuelo;
import edu.upc.dsa.domain.models.Posicion;
import edu.upc.dsa.domain.repository.DronesManager;
import edu.upc.dsa.domain.repository.DronesManagerImpl;
import edu.upc.dsa.dto.DronDTO;
import edu.upc.dsa.dto.DtoMapper;
import edu.upc.dsa.dto.ErrorDTO;
import edu.upc.dsa.dto.PilotoDTO;
import edu.upc.dsa.dto.PlanVueloDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/flota", description = "Endpoint to Drones Service")
@Path("/flota")
public class DronesService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DronesManager dm;

    //inicializa singleton y carga 3 drones y 3 pilotos
    public DronesService() {
        this.dm = DronesManagerImpl.getInstance();
        if (this.dm.size() == 0) {
            this.dm.addDron("D1", "Dron 1", "SpaceX", "Eco");
            this.dm.addDron("D2", "Dron 2", "NASA", "Turbo");
            this.dm.addDron("D3", "Dron 3", "Hash", "Height");
            this.dm.addPiloto("P1", "Enzo", "Cobano");
            this.dm.addPiloto("P2", "Max", "Closa");
            this.dm.addPiloto("P3", "Xiao", "Wey");

            List<Posicion> posiciones1 = new LinkedList<>();
            posiciones1.add(new Posicion(42.8, 3.2));
            posiciones1.add(new Posicion(42.9, 3.4));
            this.dm.crearPlanVuelo(new PlanVuelo("D1", "P1", "2027-01-15 10:00", 2, posiciones1));

            List<Posicion> posiciones2 = new LinkedList<>();
            posiciones2.add(new Posicion(41.60, 2.30));
            posiciones2.add(new Posicion(41.80, 2.40));
            this.dm.crearPlanVuelo(new PlanVuelo("D2", "P2", "2027-01-16 10:00", 1.5, posiciones2));
        }
    }

    //Implementacion de los metodos existentes con sus respectivos endpoints y codigos

    //crea un dron nuevo, si no se manda id lo genera el sistema
    @POST
    @ApiOperation(value = "add a new Dron", notes = "Adds a drone to the fleet")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = DronDTO.class),
            @ApiResponse(code = 400, message = "Validation Error", response = ErrorDTO.class),
            @ApiResponse(code = 409, message = "Dron already exists", response = ErrorDTO.class)
    })
    @Path("/drones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDron(DronDTO dronDTO) {
        if (dronDTO == null || !textoValido(dronDTO.getNombre())
                || !textoValido(dronDTO.getFabricante()) || !textoValido(dronDTO.getModelo())) {
            return error(400, "Datos del dron invalidos");
        }
        Dron nuevo = this.dm.addDron(DtoMapper.toDron(dronDTO));
        if (nuevo == null) return error(409, "El dron ya existe");
        return Response.status(201).entity(DtoMapper.toDronDTO(nuevo)).build();
    }

    //crea un piloto nuevo
    @POST
    @ApiOperation(value = "add a new Piloto", notes = "Adds a pilot to the fleet")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PilotoDTO.class),
            @ApiResponse(code = 400, message = "Validation Error", response = ErrorDTO.class),
            @ApiResponse(code = 409, message = "Piloto already exists", response = ErrorDTO.class)
    })
    @Path("/pilotos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPiloto(PilotoDTO pilotoDTO) {
        if (pilotoDTO == null || !textoValido(pilotoDTO.getNombre()) || !textoValido(pilotoDTO.getApellidos())) {
            return error(400, "Datos del piloto invalidos");
        }
        Piloto nuevo = this.dm.addPiloto(DtoMapper.toPiloto(pilotoDTO));
        if (nuevo == null) return error(409, "El piloto ya existe");
        return Response.status(201).entity(DtoMapper.toPilotoDTO(nuevo)).build();
    }

    //devuelve los drones ordenados por horas de vuelo
    @GET
    @ApiOperation(value = "get all Drones ordered by flight hours", notes = "Highest flight hours first")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = DronDTO.class, responseContainer = "List")
    })
    @Path("/drones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrones() {
        List<DronDTO> drones = DtoMapper.toDronDTOList(this.dm.getDronesOrderedByHorasVuelo());
        GenericEntity<List<DronDTO>> entity = new GenericEntity<List<DronDTO>>(drones) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Pilotos ordered by flight hours", notes = "Highest flight hours first")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PilotoDTO.class, responseContainer = "List")
    })
    @Path("/pilotos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPilotos() {
        List<PilotoDTO> pilotos = DtoMapper.toPilotoDTOList(this.dm.getPilotosOrderedByHorasVuelo());
        GenericEntity<List<PilotoDTO>> entity = new GenericEntity<List<PilotoDTO>>(pilotos) {};
        return Response.status(200).entity(entity).build();
    }

    //manda el dron al almacen, el ultimo que entra es el primero que se repara
    @POST
    @ApiOperation(value = "send a Dron to maintenance", notes = "The drone is stored in the warehouse")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = DronDTO.class),
            @ApiResponse(code = 404, message = "Dron not found", response = ErrorDTO.class),
            @ApiResponse(code = 409, message = "Dron already in maintenance", response = ErrorDTO.class)
    })
    @Path("/drones/{id}/mantenimiento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarDronEnMantenimiento(
            @ApiParam(value = "ID del dron", required = true) @PathParam("id") String id) {
        Dron dron = this.dm.getDron(id);
        if (dron == null) return error(404, "Dron no encontrado");
        Dron guardado = this.dm.guardarDronEnMantenimiento(id);
        if (guardado == null) return error(409, "El dron ya esta en mantenimiento");
        return Response.status(201).entity(DtoMapper.toDronDTO(guardado)).build();
    }

    @POST
    @ApiOperation(value = "repair the Dron closest to the door", notes = "Repairs the last drone stored in the warehouse")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = DronDTO.class),
            @ApiResponse(code = 404, message = "Warehouse is empty", response = ErrorDTO.class)
    })
    @Path("/mantenimiento/reparar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response repararDron() {
        Dron dron = this.dm.repararDron();
        if (dron == null) return error(404, "El almacen esta vacio");
        return Response.status(201).entity(DtoMapper.toDronDTO(dron)).build();
    }

    //crea un plan si el dron y el piloto estan libres
    @POST
    @ApiOperation(value = "create a new PlanVuelo", notes = "The drone and the pilot must be available")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PlanVueloDTO.class),
            @ApiResponse(code = 400, message = "Invalid plan", response = ErrorDTO.class),
            @ApiResponse(code = 404, message = "Dron or Piloto not found", response = ErrorDTO.class),
            @ApiResponse(code = 409, message = "Dron in maintenance or time overlap", response = ErrorDTO.class)
    })
    @Path("/planes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPlanVuelo(PlanVueloDTO planDTO) {
        if (planDTO == null || !textoValido(planDTO.getDronId()) || !textoValido(planDTO.getPilotoId())
                || !fechaValida(planDTO.getFecha()) || planDTO.getDuracionHoras() <= 0
                || planDTO.getPosiciones() == null || planDTO.getPosiciones().isEmpty()) {
            return error(400, "Datos del plan de vuelo invalidos");
        }
        if (this.dm.getDron(planDTO.getDronId()) == null || this.dm.getPiloto(planDTO.getPilotoId()) == null) {
            return error(404, "Dron o piloto no encontrado");
        }
        PlanVuelo nuevo = this.dm.crearPlanVuelo(DtoMapper.toPlanVuelo(planDTO));
        if (nuevo == null) return error(409, "El dron esta en mantenimiento o existe solapamiento");
        return Response.status(201).entity(DtoMapper.toPlanVueloDTO(nuevo)).build();
    }

    @GET
    @ApiOperation(value = "get the PlanVuelo of a Piloto", notes = "Returns all flight plans of the pilot")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PlanVueloDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Piloto not found", response = ErrorDTO.class)
    })
    @Path("/pilotos/{id}/planes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlanesVueloPiloto(
            @ApiParam(value = "ID del piloto", required = true) @PathParam("id") String id) {
        List<PlanVuelo> planes = this.dm.getPlanesVueloPiloto(id);
        if (planes == null) return error(404, "Piloto no encontrado");
        List<PlanVueloDTO> planesDTO = DtoMapper.toPlanVueloDTOList(planes);
        GenericEntity<List<PlanVueloDTO>> entity = new GenericEntity<List<PlanVueloDTO>>(planesDTO) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get the PlanVuelo of a Dron", notes = "Returns all flight plans of the drone")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PlanVueloDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Dron not found", response = ErrorDTO.class)
    })
    @Path("/drones/{id}/planes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlanesVueloDron(
            @ApiParam(value = "ID del dron", required = true) @PathParam("id") String id) {
        List<PlanVuelo> planes = this.dm.getPlanesVueloDron(id);
        if (planes == null) return error(404, "Dron no encontrado");
        List<PlanVueloDTO> planesDTO = DtoMapper.toPlanVueloDTOList(planes);
        GenericEntity<List<PlanVueloDTO>> entity = new GenericEntity<List<PlanVueloDTO>>(planesDTO) {};
        return Response.status(200).entity(entity).build();
    }

    //comprueba que el texto no sea null ni este vacio
    private boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    //comprueba que la fecha tenga el formato que usamos
    private boolean fechaValida(String fecha) {
        if (fecha == null) return false;
        try {
            LocalDateTime.parse(fecha, FORMATO_FECHA);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    //crea la respuesta de error con el codigo y el mensaje
    private Response error(int codigo, String mensaje) {
        return Response.status(codigo).entity(new ErrorDTO(codigo, mensaje)).type(MediaType.APPLICATION_JSON).build();
    }
}
