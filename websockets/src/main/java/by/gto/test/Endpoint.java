package by.gto.test;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.json.JSONObject;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.auth.server.SecurityIdentity;
import org.wildfly.security.authz.Attributes;

@PermitAll
@Path("/")
public class Endpoint {

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String test() {
        return "test " + System.currentTimeMillis();
    }

    @GET
    @Path("/infoAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public String infoAdmin() {
        return "Only admin: " +  identityInfoAsJson();
    }

    @GET
    @Path("/infoUser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user"})
    public String infoUser() {
    	return "Only user: " +  identityInfoAsJson();
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