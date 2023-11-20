package by.gto.test.jackson.rest;

import by.gto.test.jackson.model.Envelope;
import by.gto.test.jackson.model.GenericEnvelope;
import by.gto.test.jackson.model.Og;
import by.gto.test.jackson.mybatis.TestMappers;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
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
    public Og generics(GenericEnvelope<Og> data) {
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

    @POST
    @Path("plainEnvelope")
    public Og plainEnvelope(Envelope envelope) throws IOException {
        System.out.println(envelope);
        final Og og = envelope.getDocument(Og.class);
        testMappers.saveOg(og);
        return og;
    }

    @GET
    @Path("secured")
    @RolesAllowed("admin")
    public String securedArea() {
        return "secured";
    }

    @Inject
    private TestMappers testMappers;
}