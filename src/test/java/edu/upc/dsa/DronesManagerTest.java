package edu.upc.dsa;

import edu.upc.dsa.domain.models.Dron;
import edu.upc.dsa.domain.models.Piloto;
import edu.upc.dsa.domain.models.PlanVuelo;
import edu.upc.dsa.domain.models.Posicion;
import edu.upc.dsa.domain.repository.DronesManager;
import edu.upc.dsa.domain.repository.DronesManagerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class DronesManagerTest {

    DronesManager dm;

    @Before
    public void setUp() {
        this.dm = DronesManagerImpl.getInstance();
        this.dm.clear();
        this.dm.addDron("D1", "Dron 1", "SpaceX", "Eco");
        this.dm.addDron("D2", "Dron 2", "NASA", "Turbo");
        this.dm.addDron("D3", "Dron 3", "Hash", "Height");
        this.dm.addPiloto("P1", "Enzo", "Cobano");
        this.dm.addPiloto("P2", "Max", "Closa");
        this.dm.addPiloto("P3", "Xiao", "Wey");
    }

    @After
    public void tearDown() {
        this.dm.clear();
    }

    //test 1: Añadir elementos y listar ordenados descendentemente por horas de vuelo
    @Test
    public void test1AddYListarOrdenados() {
        Assert.assertEquals(3, this.dm.size());
        this.dm.addDron("D4", "Dron 4", "Golum", "X2");
        Assert.assertEquals(4, this.dm.size());
        this.dm.addPiloto("P4", "Pablo", "Ruiz");
        Assert.assertNotNull(this.dm.getDron("D4"));
        Assert.assertNotNull(this.dm.getPiloto("P4"));

        this.dm.getDron("D1").setHorasVuelo(10);
        this.dm.getDron("D2").setHorasVuelo(30);
        this.dm.getDron("D3").setHorasVuelo(20);
        this.dm.getPiloto("P1").setHorasVuelo(5);
        this.dm.getPiloto("P2").setHorasVuelo(25);
        this.dm.getPiloto("P3").setHorasVuelo(15);

        List<Dron> drones = this.dm.getDronesOrderedByHorasVuelo();
        Assert.assertEquals("D2", drones.get(0).getId());
        Assert.assertEquals("D3", drones.get(1).getId());
        Assert.assertEquals("D1", drones.get(2).getId());

        List<Piloto> pilotos = this.dm.getPilotosOrderedByHorasVuelo();
        Assert.assertEquals("P2", pilotos.get(0).getId());
        Assert.assertEquals("P3", pilotos.get(1).getId());
        Assert.assertEquals("P1", pilotos.get(2).getId());
    }

    // test 2: Comportamiento LIFO del almacén de mantenimiento
    @Test
    public void test2AlmacenLifoMantenimiento() {
        Assert.assertNotNull(this.dm.guardarDronEnMantenimiento("D1"));
        Assert.assertNotNull(this.dm.guardarDronEnMantenimiento("D2"));
        Assert.assertTrue(this.dm.getDron("D1").isEnMantenimiento());
        Assert.assertTrue(this.dm.getDron("D2").isEnMantenimiento());

        Dron dronReparado1 = this.dm.repararDron();
        Assert.assertEquals("D2", dronReparado1.getId());
        Assert.assertFalse(dronReparado1.isEnMantenimiento());

        Dron dronReparado2 = this.dm.repararDron();
        Assert.assertEquals("D1", dronReparado2.getId());
        Assert.assertFalse(dronReparado2.isEnMantenimiento());

        Assert.assertNull(this.dm.repararDron());
    }

    // test 3: Creación correcta de un plan de vuelo y actualización de horas y listados
    @Test
    public void test3CrearPlanVueloExitoso() {
        List<Posicion> posiciones = new LinkedList<>();
        posiciones.add(new Posicion(41.30, 2.10));
        posiciones.add(new Posicion(41.40, 2.20));
        PlanVuelo plan = new PlanVuelo("D1", "P1", "2027-01-10 10:00", 2, posiciones);

        PlanVuelo creado = this.dm.crearPlanVuelo(plan);
        Assert.assertNotNull(creado);
        Assert.assertEquals(2.0, this.dm.getDron("D1").getHorasVuelo(), 0.001);
        Assert.assertEquals(2.0, this.dm.getPiloto("P1").getHorasVuelo(), 0.001);
        Assert.assertEquals(1, this.dm.getPlanesVueloDron("D1").size());
        Assert.assertEquals(1, this.dm.getPlanesVueloPiloto("P1").size());
    }

    //Test 4: Validación de errores de solapamiento y si un dron esta en mantenimiento
    @Test
    public void test4CrearPlanErroresSolapamientoYMantenimiento() {
        List<Posicion> posiciones = new LinkedList<>();
        posiciones.add(new Posicion(41.30, 2.10));
        PlanVuelo plan1 = new PlanVuelo("D1", "P1", "2027-01-10 10:00", 2, posiciones);
        Assert.assertNotNull(this.dm.crearPlanVuelo(plan1));

        PlanVuelo planSolapadoDron = new PlanVuelo("D1", "P2", "2027-01-10 11:00", 1, posiciones);
        Assert.assertNull(this.dm.crearPlanVuelo(planSolapadoDron));

        PlanVuelo planSolapadoPiloto = new PlanVuelo("D2", "P1", "2027-01-10 11:30", 1, posiciones);
        Assert.assertNull(this.dm.crearPlanVuelo(planSolapadoPiloto));

        this.dm.guardarDronEnMantenimiento("D3");
        PlanVuelo planMantenimiento = new PlanVuelo("D3", "P2", "2027-01-10 14:00", 2, posiciones);
        Assert.assertNull(this.dm.crearPlanVuelo(planMantenimiento));

        Assert.assertEquals(1, this.dm.getPlanesVueloDron("D1").size());
        Assert.assertEquals(1, this.dm.getPlanesVueloPiloto("P1").size());
        Assert.assertEquals(0, this.dm.getPlanesVueloDron("D3").size());
    }
}