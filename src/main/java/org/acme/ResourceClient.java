package org.acme;

import org.acme.model.AuthenticatieDienst;
import org.acme.model.Dienstverlener;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface ResourceClient {
    @GET
    @Path("/dienstverlener/simple")
    @Produces(MediaType.APPLICATION_JSON)
    List<Dienstverlener> dienstverleners();

    @GET
    @Path("/authenticatiediensten/simple")
    @Produces(MediaType.APPLICATION_JSON)
    List<AuthenticatieDienst> authenticatiediensten();
}
