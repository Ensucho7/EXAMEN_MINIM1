# Proyecto DSA — Flota de drones

Minimo 1 finalizado por Enzo Cobano en la materia de DSA sobre drones y pilotos.

## Porque he usado cada cosa?

- He usado `HashMap` tanto para los drones como para los pilotos, así puedo encontrarlos al instante por su ID.
- He implementado una pila LIFO en el almacén porque el enunciado pide que los drones entren por el fondo y se repare el que está más cerca de la puerta.
- Para validar el plan de vuelo, el codigo hace dos cosas: que el dron no esté metido en el almacén de mantenimiento y que no se cruce de hora con otro plan ya asignado a ese dron o piloto.
- Para sacar los rankings de drones y pilotos de forma descendente los ordeno con un `Comparator.reversed()`.
- En la parte REST he añadido objetos de transferencia (`DronDTO`, `PilotoDTO`, `PlanVueloDTO`, `PosicionDTO`) como pide el enunciado.
- Toda la logica va en `DronesManagerImpl`, nada en el servicio REST.

## Estructura

- `domain.models`: entidades del dominio.
- `domain.repository`: interfaz y fachada Singleton.
- `services`: servicio REST.
- `dto`: objetos de transferencia y mapper.
- `util`: utilidades.

## Parte III: definición e implementación del servicio REST

La sección de API REST utiliza los siguientes endpoints utilizando JAX-RS y Swagger con sus respectivos codigos de respuesta.

**Base de la API:** `http://localhost:8080/dsaApp/flota`

| Método | Ruta | Descripción | Códigos de respuesta |
|--------|------|-------------|----------------------|
| POST | `/drones` | Añade un nuevo dron a la flota | 201 (Creado), 400 (Datos inválidos), 409 (Duplicado) |
| POST | `/pilotos` | Añade un nuevo piloto | 201 (Creado), 400 (Datos inválidos), 409 (Duplicado) |
| GET | `/drones` | Lista los drones ordenados por horas de vuelo | 200 (Lista OK) |
| GET | `/pilotos` | Lista los pilotos ordenados por horas de vuelo | 200 (Lista OK) |
| POST | `/drones/{id}/mantenimiento` | Guarda un dron en el almacén de mantenimiento | 201 (OK), 404 (No existe), 409 (Ya está en mantenimiento) |
| POST | `/mantenimiento/reparar` | Repara y saca el último dron del almacén (puerta) | 201 (OK), 404 (Vacío) |
| POST | `/planes` | Crea un plan de vuelo validando solapamientos | 201 (OK), 400 (Datos inválidos), 404 (Dron/piloto no existe), 409 (Solapamiento/mantenimiento) |
| GET | `/pilotos/{id}/planes` | Devuelve los planes de vuelo de un piloto | 200 (Lista OK), 404 (No existe) |
| GET | `/drones/{id}/planes` | Devuelve los planes de vuelo de un dron | 200 (Lista OK), 404 (No existe) |


## Para ejecutar mi proyecto:

**Comprobar parte II (JUnit):**

```bash
mvn clean test
```

Comprueba los 4 tests del manager.

**Arrancar el servidor REST y Swagger:**

```bash
mvn exec:java
```

Luego entra en el navegador a: <http://localhost:8080/swagger/index.html>

## Estado actual

He terminado las partes II y III y a priori no queda nada por desarrollar. Todo funciona correctamente.

## Errores/problemas encontrados

Una vez implementado todo me encontré problemas para ocultar cosas que no se vieran en el swagger o crear el ID aleatorio. También me costo implementar toda la lógica con los DTOs.

