package edu.upc.dsa.services;

import edu.upc.dsa.domain.models.Dron;
import edu.upc.dsa.domain.models.Piloto;
import edu.upc.dsa.domain.models.PlanVuelo;
import edu.upc.dsa.domain.models.Posicion;
import edu.upc.dsa.domain.repository.DronesManager;
import edu.upc.dsa.domain.repository.DronesManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.LinkedList;
import java.util.List;

@Api(value = "/flota", description = "Endpoint to Drones Service")
@Path("/flota")
public class DronesService {

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
            posiciones1.add(new Posicion(41.30, 2.10));
            posiciones1.add(new Posicion(41.40, 2.20));
            this.dm.crearPlanVuelo(new PlanVuelo("D1", "P1", "2027-01-15 10:00", 2, posiciones1));

            List<Posicion> posiciones2 = new LinkedList<>();
            posiciones2.add(new Posicion(41.50, 2.30));
            posiciones2.add(new Posicion(41.60, 2.40));
            this.dm.crearPlanVuelo(new PlanVuelo("D2", "P2", "2027-01-16 10:00", 1.5, posiciones2));
        }
    }

    //Implementacion de los metodos existentes a Rest. Con sus respectivos endpoints y codigos

    @POST
    @ApiOperation(value = "add a new Dron", notes = "Adds a drone to the fleet")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class),
            @ApiResponse(code = 409, message = "Dron already exists"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/drones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDron(Dron dron) {
        if (dron == null || dron.getNombre() == null || dron.getFabricante() == null || dron.getModelo() == null) {
            return Response.status(500).build();
        }
        Dron nuevo = this.dm.addDron(dron);
        if (nuevo == null) return Response.status(409).build();
        return Response.status(201).entity(nuevo).build();
    }

    @POST
    @ApiOperation(value = "add a new Piloto", notes = "Adds a pilot to the fleet")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Piloto.class),
            @ApiResponse(code = 409, message = "Piloto already exists"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/pilotos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPiloto(Piloto piloto) {
        if (piloto == null || piloto.getNombre() == null || piloto.getApellidos() == null) {
            return Response.status(500).build();
        }
        Piloto nuevo = this.dm.addPiloto(piloto);
        if (nuevo == null) return Response.status(409).build();
        return Response.status(201).entity(nuevo).build();
    }

    @GET
    @ApiOperation(value = "get all Drones ordered by flight hours", notes = "Highest flight hours first")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class, responseContainer = "List")
    })
    @Path("/drones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrones() {
        List<Dron> drones = this.dm.getDronesOrderedByHorasVuelo();
        GenericEntity<List<Dron>> entity = new GenericEntity<List<Dron>>(drones) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Pilotos ordered by flight hours", notes = "Highest flight hours first")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Piloto.class, responseContainer = "List")
    })
    @Path("/pilotos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPilotos() {
        List<Piloto> pilotos = this.dm.getPilotosOrderedByHorasVuelo();
        GenericEntity<List<Piloto>> entity = new GenericEntity<List<Piloto>>(pilotos) {};
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "send a Dron to maintenance", notes = "The drone is stored in the warehouse")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class),
            @ApiResponse(code = 404, message = "Dron not found"),
            @ApiResponse(code = 409, message = "Dron already in maintenance")
    })
    @Path("/drones/{id}/mantenimiento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarDronEnMantenimiento(@PathParam("id") String id) {
        Dron dron = this.dm.getDron(id);
        if (dron == null) return Response.status(404).build();
        Dron guardado = this.dm.guardarDronEnMantenimiento(id);
        if (guardado == null) return Response.status(409).build();
        return Response.status(201).entity(guardado).build();
    }

    @POST
    @ApiOperation(value = "repair the Dron closest to the door", notes = "Repairs the last drone stored in the warehouse")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class),
            @ApiResponse(code = 404, message = "Warehouse is empty")
    })
    @Path("/mantenimiento/reparar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response repararDron() {
        Dron dron = this.dm.repararDron();
        if (dron == null) return Response.status(404).build();
        return Response.status(201).entity(dron).build();
    }

    @POST
    @ApiOperation(value = "create a new PlanVuelo", notes = "The drone and the pilot must be available")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PlanVuelo.class),
            @ApiResponse(code = 400, message = "Invalid plan"),
            @ApiResponse(code = 404, message = "Dron or Piloto not found"),
            @ApiResponse(code = 409, message = "Dron in maintenance or time overlap")
    })
    @Path("/planes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPlanVuelo(PlanVuelo plan) {
        if (plan == null || plan.getDronId() == null || plan.getPilotoId() == null
                || plan.getFecha() == null || plan.getDuracionHoras() <= 0
                || plan.getPosiciones() == null || plan.getPosiciones().isEmpty()) {
            return Response.status(400).build();
        }
        if (this.dm.getDron(plan.getDronId()) == null || this.dm.getPiloto(plan.getPilotoId()) == null) {
            return Response.status(404).build();
        }
        PlanVuelo nuevo = this.dm.crearPlanVuelo(plan);
        if (nuevo == null) return Response.status(409).build();
        return Response.status(201).entity(nuevo).build();
    }

    @GET
    @ApiOperation(value = "get the PlanVuelo of a Piloto", notes = "Returns all flight plans of the pilot")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PlanVuelo.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Piloto not found")
    })
    @Path("/pilotos/{id}/planes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlanesVueloPiloto(@PathParam("id") String id) {
        List<PlanVuelo> planes = this.dm.getPlanesVueloPiloto(id);
        if (planes == null) return Response.status(404).build();
        GenericEntity<List<PlanVuelo>> entity = new GenericEntity<List<PlanVuelo>>(planes) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get the PlanVuelo of a Dron", notes = "Returns all flight plans of the drone")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PlanVuelo.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Dron not found")
    })
    @Path("/drones/{id}/planes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlanesVueloDron(@PathParam("id") String id) {
        List<PlanVuelo> planes = this.dm.getPlanesVueloDron(id);
        if (planes == null) return Response.status(404).build();
        GenericEntity<List<PlanVuelo>> entity = new GenericEntity<List<PlanVuelo>>(planes) {};
        return Response.status(201).entity(entity).build();
    }
}
