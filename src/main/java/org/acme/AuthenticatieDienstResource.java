package org.acme;

import org.acme.model.AuthenticatieDienst;
import org.jboss.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/authenticatiediensten")
public class AuthenticatieDienstResource {

    private static final Logger LOG = Logger.getLogger(AuthenticatieDienstResource.class);

    private final Set<AuthenticatieDienst> ads = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public AuthenticatieDienstResource() {
        ads.add(new AuthenticatieDienst("AD1", "Eerste Authenticatiedienst"));
        ads.add(new AuthenticatieDienst("AD2", "Tweede Authenticatiedienst"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<AuthenticatieDienst> listSimple() {
        LOG.info("AuthenticatieDiensten simple");
        return ads;
    }

    @POST
    public Set<AuthenticatieDienst> add(AuthenticatieDienst authenticatieDienst) {
        ads.add(authenticatieDienst);
        return ads;
    }

    @DELETE
    public Set<AuthenticatieDienst> delete(AuthenticatieDienst authenticatieDienst) {
        ads.removeIf(existingAuthenticatieDienst -> existingAuthenticatieDienst.name.contentEquals(authenticatieDienst.name));
        return ads;
    }
}
