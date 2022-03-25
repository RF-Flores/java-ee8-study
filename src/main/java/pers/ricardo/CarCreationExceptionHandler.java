package pers.ricardo;

import pers.ricardo.control.CarCreationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CarCreationExceptionHandler implements ExceptionMapper<CarCreationException> {

    @Override
    public Response toResponse(CarCreationException e) {
        return Response.serverError()
                .header("X-Car-Error", e.getMessage())
                .entity(e.getMessage())
                .build();
    }
}
