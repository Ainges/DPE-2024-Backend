package de.thi.service;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri="http://localhost:8080")
public interface AnnualStatementService {

    @GET
    @Path("/annual-statements/{id}/pdf")
    @Produces("text/plain")
    String getBase64String(@PathParam("id") long id);

}
