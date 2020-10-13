package com.mastertheboss;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
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

@Stateless
@PermitAll
@Path("/")
public class Endpoint {
	
	@Context
	private SecurityContext securityContext;

	@GET
	@Path("/jndi")
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public String jndi() throws NamingException {
		StringBuilder sb = new StringBuilder();
		InitialContext c = new InitialContext();
		
		enumerateJndi(c, "", "java:/", sb);
//		while(list.hasMore()) {
//			NameClassPair next = list.next();
//			sb.append(next.getName()).append(" isRelative: ").append(next.isRelative())
//			.append(" getClassName: ").append(next.getClassName())
//			.append(" java Class: ").append(next.getClass().getCanonicalName())
//			.append("\n");
//		}
		return sb.toString();
	}
	
	
	private void enumerateJndi(InitialContext c, String prefix, String jndiPrefix, StringBuilder sb) throws NamingException {
		NamingEnumeration<NameClassPair> list = c.list(jndiPrefix);
		List<NameClassPair> nestedContexts = new ArrayList<>();
		while(list.hasMore()) {
			NameClassPair next = list.next();
			if(next.getClassName() == "javax.naming.Context") {
				nestedContexts.add(next);
			} else {
				sb.append(prefix).append(next.getName())
				.append(" of class ").append(next.getClassName()).append("###\n");
			}
			for(NameClassPair entry: nestedContexts) {
				sb.append(prefix).append(entry.getName()).append(":\n");
				enumerateJndi(c, prefix + "    ", "java:/" + entry.getName() + "/", sb);
			}
		}
	}

	@GET
	@Path("/infoPublic")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public String infoPublic() {
		return "endpoint for all: " + identityInfoAsJsonPublic();
	}

	@GET
	@Path("/infoAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "admin" })
	public String infoAdmin() {
		return "Only admin: " + identityInfoAsJson();
	}

	@GET
	@Path("/infoUser")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "user" })
	public String infoUser() {
		return "Only user: " + identityInfoAsJson();
	}

	@POST
	@RolesAllowed({ "admin" })
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

	private String identityInfoAsJsonPublic() {
		JSONObject response = new JSONObject();
		
		SecurityDomain secDomain = SecurityDomain.getCurrent();
		response.put("SecurityDomain", secDomain);
		if (secDomain == null) {
			return response.toString();
		}
		
		SecurityIdentity identity = secDomain.getCurrentSecurityIdentity();
		response.put("SecurityIdentity", identity);
		if (identity == null) {
			return response.toString();
		} else {
			response.put("attributes", attributesAsJson(identity.getAttributes()));
		}
		Principal userPrincipal = securityContext.getUserPrincipal();
		response.put("userPrincipal", userPrincipal);
		if (userPrincipal == null) {
			return response.toString();
		} else {
			response.put("userPrincipal.name", userPrincipal.getName());
		}

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