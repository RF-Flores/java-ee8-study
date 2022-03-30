package pers.ricardo.control;

import pers.ricardo.entity.Specification;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped //we made this singleton / ApplicationScopped because the client is an resouce intensive object.
public class IdentifierAcessor {

    //JAX-RS client classes
    private Client client;
    private WebTarget target;

    //@PostConstruct UNCOMMENT THIS TO INIT CLIENT AND TARGET
    public void initClient() {
        client = ClientBuilder.newClient();
        target = client.target("https://example.com/UUIDgen");

    }

    //@PreDestroy THIS IS NEEDED TO CLOSE THE RESOURCE INTENSIVE RESOURCE
    public void closeClient() {
        client.close();
    }


    String retrieveCarUUID(Specification specification) {
        JsonObject entity = createRequestBody(specification);
        Response response = getResponse(entity);
        return extractUUID(response); //this assumes the response is ok that there is an identifer key in json response

    }

    private String extractUUID(Response response) {
        return response.readEntity(JsonObject.class).getString("identifier");
    }

    private Response getResponse(JsonObject entity) {
        return target.request(MediaType.APPLICATION_JSON).post(Entity.json(entity));
    }

    private JsonObject createRequestBody(Specification specification) {
        JsonObject entity = Json.createObjectBuilder()
                .add("engine", specification.getEngineType().name()).build();
        return entity;
    }
}