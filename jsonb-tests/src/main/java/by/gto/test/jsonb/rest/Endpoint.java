package by.gto.test.jsonb.rest;

import by.gto.test.jsonb.model.Og;
import by.gto.test.jsonb.model.OgEnvelope;
import by.gto.test.jsonb.model.Test;
import by.gto.test.jsonb.mybatis.TestMappers;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
        Og og = data.getDocument();
        testMappers.saveOg(og);
        return og;
    }

    @POST
    @Path("plain")
    public Og plain(Og og) {
        System.out.println(og);
        testMappers.saveOg(og);
        return og;
    }

    @GET
    @Path("date")
    public Test date() {
        return new Test();
    }

    @Inject
    private TestMappers testMappers;
}