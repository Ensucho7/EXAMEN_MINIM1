package edu.upc.dsa;

import edu.upc.dsa.dto.ErrorDTO;
import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = Logger.getLogger(MyExceptionMapper.class);

    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException) ex;
            int status = wae.getResponse().getStatus();
            logger.error("Error HTTP " + status, ex);
            return Response.status(status)
                    .entity(new ErrorDTO(status, wae.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        logger.error("Error no controlado", ex);
        return Response.status(500)
                .entity(new ErrorDTO(500, "Error interno del servidor"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
