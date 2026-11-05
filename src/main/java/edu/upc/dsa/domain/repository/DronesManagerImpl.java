package edu.upc.dsa.domain.repository;

import edu.upc.dsa.domain.models.Dron;
import edu.upc.dsa.domain.models.Piloto;
import edu.upc.dsa.domain.models.PlanVuelo;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public class DronesManagerImpl implements DronesManager {

    private static DronesManager instance;
    private HashMap<String, Dron> drones;
    private HashMap<String, Piloto> pilotos;
    private Deque<Dron> almacen;
    final static Logger logger = Logger.getLogger(DronesManagerImpl.class);

    private DronesManagerImpl() {
        this.drones = new HashMap<>();
        this.pilotos = new HashMap<>();
        this.almacen = new LinkedList<>();
    }

    //implementacion singleton
    public static DronesManager getInstance() {
        if (instance == null) instance = new DronesManagerImpl();
        return instance;
    }

    //operacion 1: añadir dron
    public Dron addDron(String id, String nombre, String fabricante, String modelo) {
        logger.info("addDron inicio: id=" + id + ", nombre=" + nombre + ", fabricante=" + fabricante + ", modelo=" + modelo);
        Dron dron = this.addDron(new Dron(id, nombre, fabricante, modelo));
        logger.info("addDron fin: " + dron);
        return dron;
    }

    //se comprueba que no sea null ni duplicado
    public Dron addDron(Dron dron) {
        logger.info("addDron inicio: " + dron);
        if (dron == null || dron.getId() == null) {
            logger.error("addDron error: dron o id nulo");
            return null;
        }
        if (this.drones.containsKey(dron.getId())) {
            logger.error("addDron error: ya existe el dron " + dron.getId());
            return null;
        }
        this.drones.put(dron.getId(), dron);
        logger.info("addDron fin: " + dron);
        return dron;
    }

    //operacion 2: añadir piloto
    public Piloto addPiloto(String id, String nombre, String apellidos) {
        logger.info("addPiloto inicio: id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos);
        Piloto piloto = this.addPiloto(new Piloto(id, nombre, apellidos));
        logger.info("addPiloto fin: " + piloto);
        return piloto;
    }

    //se comprueba que no sea null ni duplicado
    public Piloto addPiloto(Piloto piloto) {
        logger.info("addPiloto inicio: " + piloto);
        if (piloto == null || piloto.getId() == null) {
            logger.error("addPiloto error: piloto o id nulo");
            return null;
        }
        if (this.pilotos.containsKey(piloto.getId())) {
            logger.error("addPiloto error: ya existe el piloto " + piloto.getId());
            return null;
        }
        this.pilotos.put(piloto.getId(), piloto);
        logger.info("addPiloto fin: " + piloto);
        return piloto;
    }

    //operacion 3: obtener dron
    public Dron getDron(String id) {
        logger.info("getDron inicio: id=" + id);
        Dron dron = this.drones.get(id);
        if (dron == null) logger.error("getDron error: no existe el dron " + id);
        logger.info("getDron fin: " + dron);
        return dron;
    }

    //operacion 4: obtener piloto
    public Piloto getPiloto(String id) {
        logger.info("getPiloto inicio: id=" + id);
        Piloto piloto = this.pilotos.get(id);
        if (piloto == null) logger.error("getPiloto error: no existe el piloto " + id);
        logger.info("getPiloto fin: " + piloto);
        return piloto;
    }

    //operacion 5: obtener lista drones por horas de vuelo en sentido descendente (reversed)
    public List<Dron> getDronesOrderedByHorasVuelo() {
        logger.info("getDronesOrderedByHorasVuelo inicio");
        List<Dron> copia = new ArrayList<>(this.drones.values());
        copia.sort(Comparator.comparingDouble(Dron::getHorasVuelo).reversed());
        logger.info("getDronesOrderedByHorasVuelo fin: " + copia.size() + " drones");
        return copia;
    }

    //operacion 6: obtener lista pilotos por horas de vuelo en sentido descendente (reversed)
    public List<Piloto> getPilotosOrderedByHorasVuelo() {
        logger.info("getPilotosOrderedByHorasVuelo inicio");
        List<Piloto> copia = new ArrayList<>(this.pilotos.values());
        copia.sort(Comparator.comparingDouble(Piloto::getHorasVuelo).reversed());
        logger.info("getPilotosOrderedByHorasVuelo fin: " + copia.size() + " pilotos");
        return copia;
    }

    //operacion 7: guardar dron en mantenimiento mirando null y si ya esta en mantenimiento
    public Dron guardarDronEnMantenimiento(String id) {
        logger.info("guardarDronEnMantenimiento inicio: id=" + id);
        Dron dron = this.drones.get(id);
        if (dron == null) {
            logger.error("guardarDronEnMantenimiento error: no existe el dron " + id);
            return null;
        }
        if (dron.isEnMantenimiento()) {
            logger.error("guardarDronEnMantenimiento error: el dron " + id + " ya esta en mantenimiento");
            return null;
        }
        dron.setEnMantenimiento(true);
        this.almacen.push(dron);
        logger.info("guardarDronEnMantenimiento fin: " + dron);
        return dron;
    }

    //operacion 8: se repara el dron mas cerca de la puerta
    public Dron repararDron() {
        logger.info("repararDron inicio");
        Dron dron = this.almacen.poll();
        if (dron == null) {
            logger.error("repararDron error: el almacen esta vacio");
            return null;
        }
        dron.setEnMantenimiento(false);
        logger.info("repararDron fin: " + dron);
        return dron;
    }

    // operacion 9: crea plan de vuelo (inicializa la clase) comprobando los diferentes errores
    public PlanVuelo crearPlanVuelo(PlanVuelo plan) {
        logger.info("crearPlanVuelo inicio: " + plan);
        if (plan == null || plan.getDronId() == null || plan.getPilotoId() == null
                || plan.getFecha() == null || plan.getDuracionHoras() <= 0) {
            logger.error("crearPlanVuelo error: datos del plan incorrectos");
            return null;
        }
        Dron dron = this.drones.get(plan.getDronId());
        if (dron == null) {
            logger.error("crearPlanVuelo error: no existe el dron " + plan.getDronId());
            return null;
        }
        Piloto piloto = this.pilotos.get(plan.getPilotoId());
        if (piloto == null) {
            logger.error("crearPlanVuelo error: no existe el piloto " + plan.getPilotoId());
            return null;
        }
        if (dron.isEnMantenimiento()) {
            logger.error("crearPlanVuelo error: el dron " + dron.getId() + " esta en mantenimiento");
            return null;
        }
        for (PlanVuelo p : dron.getPlanesVuelo()) {
            if (this.seSolapan(p, plan)) {
                logger.error("crearPlanVuelo error: el dron " + dron.getId() + " tiene otro plan en ese intervalo");
                return null;
            }
        }
        for (PlanVuelo p : piloto.getPlanesVuelo()) {
            if (this.seSolapan(p, plan)) {
                logger.error("crearPlanVuelo error: el piloto " + piloto.getId() + " tiene otro plan en ese intervalo");
                return null;
            }
        }
        dron.getPlanesVuelo().add(plan);
        piloto.getPlanesVuelo().add(plan);
        dron.setHorasVuelo(dron.getHorasVuelo() + plan.getDuracionHoras());
        piloto.setHorasVuelo(piloto.getHorasVuelo() + plan.getDuracionHoras());
        logger.info("crearPlanVuelo fin: " + plan);
        return plan;
    }

    //operacion 10: obtener lista de planes de vuelo de piloto
    public List<PlanVuelo> getPlanesVueloPiloto(String pilotoId) {
        logger.info("getPlanesVueloPiloto inicio: pilotoId=" + pilotoId);
        Piloto piloto = this.pilotos.get(pilotoId);
        if (piloto == null) {
            logger.error("getPlanesVueloPiloto error: no existe el piloto " + pilotoId);
            return null;
        }
        List<PlanVuelo> planes = new ArrayList<>(piloto.getPlanesVuelo());
        logger.info("getPlanesVueloPiloto fin: " + planes.size() + " planes");
        return planes;
    }

    //operacion 11: obtener lista planes vuelo por dron
    public List<PlanVuelo> getPlanesVueloDron(String dronId) {
        logger.info("getPlanesVueloDron inicio: dronId=" + dronId);
        Dron dron = this.drones.get(dronId);
        if (dron == null) {
            logger.error("getPlanesVueloDron error: no existe el dron " + dronId);
            return null;
        }
        List<PlanVuelo> planes = new ArrayList<>(dron.getPlanesVuelo());
        logger.info("getPlanesVueloDron fin: " + planes.size() + " planes");
        return planes;
    }

    //implementacion singleton
    public void clear() {
        logger.info("clear inicio");
        this.drones.clear();
        this.pilotos.clear();
        this.almacen.clear();
        logger.info("clear fin");
    }

    //implementacion singelton
    public int size() {
        logger.info("size inicio");
        int ret = this.drones.size();
        logger.info("size fin: " + ret);
        return ret;
    }

    //metodo privada que compara fechas para ver si se solapan dos planes de vuelo
    private boolean seSolapan(PlanVuelo p1, PlanVuelo p2) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date inicio1 = formato.parse(p1.getFecha());
            Date inicio2 = formato.parse(p2.getFecha());
            long fin1 = inicio1.getTime() + (long) (p1.getDuracionHoras() * 3600000);
            long fin2 = inicio2.getTime() + (long) (p2.getDuracionHoras() * 3600000);
            return inicio1.getTime() < fin2 && inicio2.getTime() < fin1;
        } catch (Exception e) {
            logger.error("seSolapan error: fecha incorrecta");
            return true;
        }
    }
}
