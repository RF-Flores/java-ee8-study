package pers.ricardo.boundary;

import pers.ricardo.entity.Car;
import pers.ricardo.entity.EngineType;
import pers.ricardo.entity.Specification;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonCollectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@Path("cars") //the path will be ${domain}/resources/cars since we defined the jaxrsconfig as resources
@Produces(MediaType.APPLICATION_JSON) //everthing we return will be as app json
//@Consumes(MediaType.APPLICATION_JSON)//all endpoints consume json
public class CarResource {

    @Inject
    CarManufacturer carManufacture;

    @Context
    UriInfo uriInfo;

    /* !!! This is another way of doing validations see retrieveCars with query param to see an example
    @Inject
    Validator validator;
     */

    @Inject
    ManagedExecutorService mes; //This is required for async executtion

    /*
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
     */

    @GET
    @Path("all")
    public JsonArray retrieveCars(){
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
    public JsonArray retrieveCars(@NotNull @QueryParam("filter") EngineType engineType) {
        //validator.METHODcall -> THIS is another way to validate
        return carManufacture.retrieveCars(engineType)
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
    public Response createCar(/* JsonObject jsonObject (Json alternative) */@NotNull @Valid Specification specification) {
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
                .path(CarResource.class)
                .path(CarResource.class, "retrieveCar") //here it adds /car.getIdentifier() at the end of the URI
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

    @POST
    @Path("/async")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createCarAsyncRequest(@Valid @NotNull Specification specification,
                                   @Suspended AsyncResponse asyncResponse) {
        mes.execute(() -> asyncResponse.resume(createCarAsyncProccess(specification))); //This makes sure we send the response back once it is done
        //This also makes sure we dont use the HTTP reserved thread pool but the Executor service thread pool.
    }

    private Response createCarAsyncProccess(Specification specification) {
        carManufacture.manufactureCar(specification);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    //--------------------- Async endpoint alternative since JAX-RS 2.1 and Java EE 8 ---------------------

    @POST
    @Path("/asyncModern")
    @Consumes(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> createCarAsyncRequestModern(@Valid @NotNull Specification specification,
                                                                 @Suspended AsyncResponse asyncResponse /*This is a more advanced way*/) {

        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler(response ->
                response.resume(Response.status(Response.Status.REQUEST_TIMEOUT).build())); //This makes sure that if the time is reached, the response will be a timeout


        return CompletableFuture.supplyAsync(() -> createCarAsyncProccess(specification), mes);
    }



}
