package pers.ricardo.boundary;

import pers.ricardo.entity.Car;
import pers.ricardo.entity.Color;
import pers.ricardo.entity.EngineType;
import pers.ricardo.entity.Specification;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("cars") //the path will be ${domain}/resources/cars since we defined the jaxrsconfig as resources
@Produces(MediaType.APPLICATION_JSON) //everthing we return will be as app json
//@Consumes(MediaType.APPLICATION_JSON)//all endpoints consume json
public class CarResource {

    @Inject
    CarManufacture carManufacture;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray retrieveCars() {
        return carManufacture.retrieveCars()
        .stream()
                .map(c -> Json.createObjectBuilder()
                        .add("engine", c.getEngineType().name())
                        .add("color", c.getColor().name())
                        .add("id",c.getIdentifier())
                        .build()
                ).collect(JsonCollectors.toJsonArray());
    }

    @GET
    @Path("{id}")
    public Car retrieveCar(@PathParam("id") String identifier){
        return carManufacture.retrieveCar(identifier);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCar(/* JsonObject jsonObject (Json alternative) */ @Valid @NotNull Specification specification) {
        /* JsonObject alternative
            !!!!!!!!!!! If using this, make sure the default constructor is NOT declared and that specification fields ARE final !!!!!!
        EngineType engine = EngineType.valueOf(jsonObject.getString("engine"));
        Color color = Color.valueOf(jsonObject.getString("color"));
        Car car  = carManufacture.manufactureCar(new Specification(color,engine));
        */

        /*Specification alternative
            !!!!!!!!!!! If using this, make sure the default constructor is declared and that specification fields ARE NOT final !!!!!!
        * */
        Car car = carManufacture.manufactureCar(specification);

        URI uri = uriInfo.getBaseUriBuilder() //URI built using the URI info from jaxRS this will give localhost:8080/cars/resource/cars
                .path(this.getClass())
                .path(this.getClass(), "retrieveCar") //here it adds /car.getIdentifier() at the end of the URI
                .build(car.getIdentifier());

        return Response.created(uri)
                .build();

        /*
        Alternative way to response building that is more hardcoded

        URI uri = URI.create("http://localhost...")
        return Response.status(Response.Status.CREATED)
                .header(HttpHeaders.LOCATION, uri)
                .build()
         */

    }
}