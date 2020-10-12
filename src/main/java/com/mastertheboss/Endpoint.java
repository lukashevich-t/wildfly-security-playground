package com.mastertheboss;

import org.json.JSONObject;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.auth.server.SecurityIdentity;
import org.wildfly.security.authz.Attributes;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@PermitAll
@Path("/")
public class Endpoint {

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public String getInfo() {
        return identityInfoAsJson();
    }

    @POST
    @RolesAllowed({"admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveData(Data data) {

        System.out.println(data);
        return Response.ok("Done!").build();
    }

    private String identityInfoAsJson() {
        SecurityIdentity identity = SecurityDomain.getCurrent().getCurrentSecurityIdentity();
        JSONObject response = new JSONObject();
        response.put("name", securityContext.getUserPrincipal().getName());
        response.put("attributes", attributesAsJson(identity.getAttributes()));
        return response.toString();
    }

    private JSONObject attributesAsJson(Attributes attributes) {
        JSONObject attributesObject = new JSONObject();
        for (String attribute : attributes.keySet()) {
            attributesObject.put(attribute, attributes.get(attribute));
        }
        return attributesObject;
    }

}