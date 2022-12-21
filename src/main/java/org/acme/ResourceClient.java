package org.acme;

import org.acme.model.AuthenticatieDienst;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface ResourceClient {

    @GET
    @Path("/relyingparties")
    @Produces(MediaType.APPLICATION_JSON)
    RelyingPartyResult relyingParties();

    @GET
    @Path("/authenticatiediensten/simple")
    @Produces(MediaType.APPLICATION_JSON)
    List<AuthenticatieDienst> authenticatiediensten();
}
