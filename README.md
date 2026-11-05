# Proyecto DSA — Flota de drones

Minimo 1 finalizado por Enzo Cobano en la materia de DSA sobre drones y pilotos.

## Porque he usado cada cosa?

- He usado `HashMap` tanto para los drones como para los pilotos, así puedo encontrarlos al instante por su ID.
- He implementado una pila LIFO en el almacén porque el enunciado pide que los drones entren por el fondo y se repare el que está más cerca de la puerta.
- Para validar el plan de vuelo, el codigo hace dos cosas: que el dron no esté metido en el almacén de mantenimiento y que no se cruce de hora con otro plan ya asignado a ese dron o piloto.
- Para sacar los rankings de drones y pilotos de forma descendente los ordeno con un `Comparator.reversed()`.

## Parte II: definición e implementación del servicio REST

La sección de API REST utiliza los siguientes endpoints utilizando JAX-RS y Swagger con sus respectivos codigos de respuesta.

**Base de la API:** `http://localhost:8080/dsaApp/flota`

| Método | Ruta | Descripción | Códigos de respuesta |
|--------|------|-------------|----------------------|
| POST | `/drones` | Añade un nuevo dron a la flota | 201 (Creado), 500 (Error) |
| POST | `/pilotos` | Añade un nuevo piloto | 201 (Creado), 500 (Error) |
| GET | `/drones` | Lista los drones ordenados por horas de vuelo | 201 (Lista OK) |
| GET | `/pilotos` | Lista los pilotos ordenados por horas de vuelo | 201 (Lista OK) |
| POST | `/drones/{id}/mantenimiento` | Guarda un dron en el almacén de mantenimiento | 201 (OK), 404/409 (Error) |
| POST | `/mantenimiento/reparar` | Repara y saca el último dron del almacén (puerta) | 201 (OK), 404 (Vacío) |
| POST | `/planes` | Crea un plan de vuelo validando solapamientos | 201 (OK), 400/409 (Error) |
| GET | `/pilotos/{id}/planes` | Devuelve los planes de vuelo de un piloto | 201 (Lista OK), 404 (No existe) |
| GET | `/drones/{id}/planes` | Devuelve los planes de vuelo de un dron | 201 (Lista OK), 404 (No existe) |

## Para ejecutar mi proyecto:

**Comprobar parte 1 (JUnit):**

```bash
mvn clean test
```

Comprueba todos los metodos implementados.

**Arrancar el servidor REST y Swagger:**

```bash
mvn exec:java
```

Luego entra en el navegador a: <http://localhost:8080/swagger/index.html>

## Estado del proyecto

Todo esta correctamente implementado con sus respectivas detecciones de errores.
