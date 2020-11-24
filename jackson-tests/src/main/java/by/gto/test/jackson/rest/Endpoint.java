package by.gto.test.jackson.rest;

import by.gto.test.jackson.model.Og;
import by.gto.test.jackson.model.OgEnvelope;
import by.gto.test.jackson.mybatis.TestMappers;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Endpoint {
    @POST
    @Path("generics")
    public Og generics(OgEnvelope data) {
        System.out.println(data);
        testMappers.saveOg(data.getDocument());
        return data.getDocument();
    }

    @POST
    @Path("plain")
    public Og plain(Og og) {
        System.out.println(og);
        testMappers.saveOg(og);
        return og;
    }

    @Inject
    private TestMappers testMappers;
}